package de.thatsich.soundcloud.player;


import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.OptionalInt;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * TODO add description
 *
 * TODO add meaning
 *
 * TODO add usage
 *
 * @author thatsIch (thatsich[at]mail[dot]de)
 * @version 1.0.0-SNAPSHOT
 * @since 1.0.0-SNAPSHOT 25.04.2016
 */
public class JavaFXTrayIcon extends Application
{
	private static final int MINIMAL_ICON_SIZE = 16;

	// application stage is stored so that it can be shown and hidden based on system tray icon operations.
	private Stage stage;

	// a timer allowing the tray icon to provide a periodic notification event.
	private final Timer notificationTimer = new Timer();

	// format used to display the current time in a tray icon notification.
	private final DateFormat timeFormat = SimpleDateFormat.getTimeInstance();

	// sets up the javafx application.
	// a tray icon is setup for the icon, but the main stage remains invisible until the user
	// interacts with the tray icon.
	@Override
	public void start( final Stage stage )
	{
		// stores a reference to the stage.
		this.stage = stage;

		final List<javafx.scene.image.Image> images = new IconSizeProvider()
			.getSizes()
			.stream()
			.map( size -> new IconNameGenerator().generateIconFileNameFromIconSize( size ) )
			.map( name -> this.getClass().getResourceAsStream( name ) )
			.map( javafx.scene.image.Image::new )
			.collect( Collectors.toList() );
		this.stage.getIcons().addAll( images );

		// instructs the javafx system not to exit implicitly when the last application window is shut.
		Platform.setImplicitExit( false );

		// sets up the tray icon (using awt code run on the swing thread).
		SwingUtilities.invokeLater( this::addAppToTray );

		// out stage will be translucent, so give it a transparent style.
		stage.initStyle( StageStyle.TRANSPARENT );

		// create the layout for the javafx stage.
		final StackPane layout = new StackPane( createContent() );
		layout.setStyle( "-fx-background-color: rgba(255, 255, 255, 0.5);" );
		layout.setPrefSize( 300, 200 );

		// this dummy app just hides itself when the app screen is clicked.
		// a real app might have some interactive UI and a separate icon which hides the app window.
		layout.setOnMouseClicked( event -> stage.hide() );

		// a scene with a transparent fill is necessary to implement the translucent app window.
		final Scene scene = new Scene( layout );
		scene.setFill( Color.TRANSPARENT );

		stage.setScene( scene );
	}

	/**
	 * For this dummy app, the (JavaFX scenegraph) content, just says "hello,
	 * world". A real app, might load an FXML or something like that.
	 *
	 * @return the main window application content.
	 */
	private Node createContent()
	{
		final Label hello = new Label( "hello, world" );
		hello.setStyle( "-fx-font-size: 40px; -fx-text-fill: forestgreen;" );
		final Label instructions = new Label( "(click to hide)" );
		instructions.setStyle( "-fx-font-size: 12px; -fx-text-fill: orange;" );

		final VBox content = new VBox( 10, hello, instructions );
		content.setAlignment( Pos.CENTER );

		return content;
	}

	/**
	 * Sets up a system tray icon for the application.
	 */
	private void addAppToTray()
	{
		try
		{
			// ensure awt toolkit is initialized.
			Toolkit.getDefaultToolkit();

			// app requires system tray support, just exit if there is no support.
			if( !SystemTray.isSupported() )
			{
				System.out.println( "No system tray support, application exiting." );
				Platform.exit();

				return;
			}

			// set up a system tray icon.
			final SystemTray tray = SystemTray.getSystemTray();
			final Dimension trayIconSize = tray.getTrayIconSize();
			final OptionalInt max = new IconSizeProvider()
				.getSizes()
				.stream()
				.mapToInt( Integer::intValue )
				.filter( size -> size <= trayIconSize.getHeight() || size <= trayIconSize.getWidth() )
				.max();
			final int bestSize = max.orElse( MINIMAL_ICON_SIZE );
			System.out.println( "bestSize = " + bestSize );

			final String iconName = new IconNameGenerator().generateIconFileNameFromIconSize( bestSize );
			final URL imageURL = new IconLoader().getURLFrom( iconName );
			final Image image = ImageIO.read( imageURL );
			if( image == null )
			{
				System.out.println( "Image was not correctly loaded." );
				Platform.exit();

				return;
			}

			final TrayIcon trayIcon = new TrayIcon( image );

			// if the user double-clicks on the tray icon, show the main app stage.
			trayIcon.addActionListener( event -> Platform.runLater( this::showStage ) );

			// if the user selects the default menu item (which includes the app name),
			// show the main app stage.
			final MenuItem openItem = new MenuItem( "hello, world" );
			openItem.addActionListener( event -> Platform.runLater( this::showStage ) );

			// the convention for tray icons seems to be to set the default icon for opening
			// the application stage in a bold font.
			final Font defaultFont = Font.decode( null );
			final Font boldFont = defaultFont.deriveFont( Font.BOLD );
			openItem.setFont( boldFont );

			// to really exit the application, the user must go to the system tray icon
			// and select the exit option, this will shutdown JavaFX and remove the
			// tray icon (removing the tray icon will also shut down AWT).
			final MenuItem exitItem = new MenuItem( "Exit" );
			exitItem.addActionListener( event -> {
				notificationTimer.cancel();
				Platform.exit();
				tray.remove( trayIcon );
			} );

			// setup the popup menu for the application.
			final PopupMenu popup = new PopupMenu();
			popup.add( openItem );
			popup.addSeparator();
			popup.add( exitItem );
			trayIcon.setPopupMenu( popup );

			// create a timer which periodically displays a notification message.
			notificationTimer.schedule( new TimerTask()
			{
				@Override
				public void run()
				{
					SwingUtilities.invokeLater( () -> trayIcon.displayMessage( "hello", "The time is now " + timeFormat
						.format( new Date() ), TrayIcon.MessageType.INFO ) );
				}
			}, 5_000, 60_000 );

			// add the application tray icon to the system tray.
			tray.add( trayIcon );
		}
		catch( AWTException | IOException e )
		{
			System.out.println( "Unable to init system tray" );
			e.printStackTrace();
		}
		catch( final URISyntaxException e )
		{
			e.printStackTrace();
		}
	}

	/**
	 * Shows the application stage and ensures that it is brought ot the front
	 * of all stages.
	 */
	private void showStage()
	{
		if( stage != null )
		{
			stage.show();
			stage.toFront();
		}
	}

	public static void main( final String[] args ) throws IOException, AWTException
	{
		// Just launches the JavaFX application.
		// Due to way the application is coded, the application will remain running
		// until the user selects the Exit menu option from the tray icon.
		launch( args );
	}
}

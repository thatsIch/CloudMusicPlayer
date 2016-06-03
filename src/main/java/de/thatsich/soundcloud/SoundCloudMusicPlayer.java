package de.thatsich.soundcloud;


import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import com.microsoft.alm.oauth2.useragent.AuthorizationException;
import com.soundcloud.api.ApiWrapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.thatsich.soundcloud.api.SoundCloudAPI;
import de.thatsich.soundcloud.credential.Client;
import de.thatsich.soundcloud.credential.ConnectionManager;
import de.voidplus.soundcloud.Playlist;
import de.voidplus.soundcloud.SoundCloud;
import de.voidplus.soundcloud.Track;
import de.voidplus.soundcloud.User;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;


/**
 * TODO add description
 *
 * TODO add meaning
 *
 * TODO add usage
 *
 * @author thatsIch (thatsich[at]mail[dot]de)
 * @version 1.0.0-SNAPSHOT
 * @since 1.0.0-SNAPSHOT 17.05.2016
 */
public class SoundCloudMusicPlayer
{
	private static final Logger LOGGER = LogManager.getLogger();

	public static void main( final String... args ) throws URISyntaxException, ClassNotFoundException, AuthorizationException, IOException
	{
		final InetAddress address = InetAddress.getByName( "soundcloud.com" );
		if( address.isReachable( 1000 ) )
		{
			final ConnectionManager connectionManager = new ConnectionManager();
			final ApiWrapper apiWrapper = connectionManager.connectToSoundCloudAPI();

			final SoundCloud soundCloud = new SoundCloudAPI( Client.ID, Client.SECRET, apiWrapper );
			//

			final User me = soundCloud.getMe();
			LOGGER.info( "User: " + me.getUsername() );

			final List<Playlist> playlists = soundCloud.getMePlaylists();
			final Playlist playlist = playlists.get( 1 );
			//			for( final Playlist playlist : playlists )
			//			{
			LOGGER.info( "Playlist: " + playlist.getTitle() );
			final List<Track> tracks = playlist.getTracks();
			for( final Track track : tracks )
			{
				LOGGER.info( "Track: " + track.getTitle() );
				track.setSoundCloud( soundCloud );
				final String streamURLString = track.getStreamUrl();
				final URL streamURL = new URL( streamURLString );
				try( final InputStream stream = streamURL.openStream() )
				{
					final Player player = new Player( stream );
					player.play();
				}
				catch( final JavaLayerException e )
				{
					e.printStackTrace();
				}

				//					final DownloadManager downloadManager = new DownloadManager();
				//					final File file = new File( ApplicationPath.PATH, "sample.mp3" );
				//					downloadManager.downloadURLString( streamUrl ).toFile( file );

				return;
			}
			//			}
		}
		else {
			LOGGER.warn( "No internet connection to soundcloud.com was found" );
		}
	}
}

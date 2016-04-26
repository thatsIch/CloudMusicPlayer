package de.thatsich.soundcloud.player;


import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javafx.scene.image.Image;


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
class IconLoader
{
	public Image getJavaFXImageFrom( final String iconPath )
	{
		return new Image( iconPath );
	}

	URL getURLFrom( final String iconName ) throws MalformedURLException, URISyntaxException
	{
		final URL resource = this.getClass().getResource( iconName );
		if( resource == null )
		{
			System.out.println( "No resource with name '" + iconName + "' was found at this class path." );
			throw new IllegalStateException( "No resource with name '" + iconName + "' was found at this class path." );
		}

		return resource.toURI().toURL();
	}
}

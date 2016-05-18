package de.thatsich.soundcloud;


import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;

import com.microsoft.alm.oauth2.useragent.AuthorizationException;
import com.soundcloud.api.ApiWrapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.thatsich.soundcloud.api.SoundCloudAPI;
import de.thatsich.soundcloud.credential.Client;
import de.thatsich.soundcloud.credential.TokenFetcher;
import de.voidplus.soundcloud.SoundCloud;
import de.voidplus.soundcloud.User;


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
public class SoundCloudMusikPlayer
{
	private static final Logger LOGGER = LogManager.getLogger();

	public static void main( final String... args ) throws URISyntaxException, ClassNotFoundException, AuthorizationException, IOException
	{
		final InetAddress address = InetAddress.getByName( "soundcloud.com" );
		if( address.isReachable( 1000 ) )
		{
			final TokenFetcher tokenFetcher = new TokenFetcher();
			final ApiWrapper apiWrapper = tokenFetcher.fetchOrCreateToken();
			final SoundCloud soundCloud = new SoundCloudAPI( Client.ID, Client.SECRET, apiWrapper );

			final User me = soundCloud.getMe();
			System.out.println( "me = " + me );
		}
	}
}

package de.thatsich.soundcloud.connection;


import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * TODO add description
 *
 * TODO add meaning
 *
 * TODO add usage
 *
 * @author thatsIch (thatsich[at]mail[dot]de)
 * @version 1.0.0-SNAPSHOT
 * @since 1.0.0-SNAPSHOT 03.06.2016
 */
public class SoundCloudConnectionTester
{
	private static final Logger LOGGER = LogManager.getLogger();

	public boolean isReachableWithTimeout( final String urlString, final int timeout )
	{
		final LocalTime before = LocalTime.now();
		try
		{
			final URL soundCloudURL = new URL( urlString );
			final URLConnection connection = soundCloudURL.openConnection();
			connection.setConnectTimeout( timeout );
			connection.setReadTimeout( timeout );
			connection.connect();

			final LocalTime after = LocalTime.now();
			final long differenceInMS = ChronoUnit.MILLIS.between( before, after );
			LOGGER.info( "Reached url '" + urlString + "' in '" + differenceInMS + "' of max. '" + timeout + "' ms." );

			return true;
		}
		catch( final IOException e )
		{
			final LocalTime after = LocalTime.now();
			final long differenceInMS = ChronoUnit.MILLIS.between( before, after );

			LOGGER.warn( "Not reached url '" + urlString + "' in '" + differenceInMS + "' of max. '" + timeout + "' ms." );

			return false;
		}
	}
}

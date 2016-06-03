package de.thatsich.cmp.download;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;


/**
 * TODO add description
 *
 * TODO add meaning
 *
 * TODO add usage
 *
 * @author thatsIch (thatsich[at]mail[dot]de)
 * @version 1.0.0-SNAPSHOT
 * @since 1.0.0-SNAPSHOT 01.06.2016
 */
public class DownloadManager
{
	public HttpResource downloadURL( final URL url )
	{
		return new HttpResource( url );
	}

	public HttpResource downloadURLString( final String urlString ) throws MalformedURLException
	{
		final URL url = new URL( urlString );

		return new HttpResource( url );
	}

	public static final class HttpResource
	{
		private final URL url;

		private HttpResource( final URL url )
		{
			this.url = url;
		}

		public void toFile( final File destination ) throws IOException
		{
			FileUtils.copyURLToFile( this.url, destination );
		}
	}
}

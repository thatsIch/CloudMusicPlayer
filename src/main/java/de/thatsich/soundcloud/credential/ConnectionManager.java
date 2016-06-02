package de.thatsich.soundcloud.credential;


import java.io.IOException;
import java.net.URISyntaxException;

import com.microsoft.alm.oauth2.useragent.AuthorizationException;
import com.soundcloud.api.ApiWrapper;

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
 * @since 1.0.0-SNAPSHOT 18.05.2016
 */
public class ConnectionManager
{
	private static final Logger LOGGER = LogManager.getLogger();

	public ApiWrapper connectToSoundCloudAPI() throws ClassNotFoundException, URISyntaxException, AuthorizationException, IOException
	{
		final TokenFetcher fetcher = new TokenFetcher();
		final ApiWrapper api = fetcher.fetchOrRecreateAccessToken();

		return api;
	}
}

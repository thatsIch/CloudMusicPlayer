package de.thatsich.cmp.credential.token.access;


import java.io.File;
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
 * @since 1.0.0-SNAPSHOT 02.06.2016
 */
public class AccessTokenManager
{
	private static final Logger LOGGER = LogManager.getLogger();

	public ApiWrapper fetchOrRecreateAccessToken() throws IOException, ClassNotFoundException, URISyntaxException, AuthorizationException
	{
		final File accessTokenFile = new File( CredentialPath.ACCESS_TOKEN_PATH );
		if( accessTokenFile.exists() && accessTokenFile.isFile() )
		{
			LOGGER.info( "Found access token file at '" + accessTokenFile + "'" );

			return ApiWrapper.fromFile( accessTokenFile );
		}
		else {
			final AccessTokenFetcher fetcher = new AccessTokenFetcher();
			final ApiWrapper apiWrapper = fetcher.fetchAccessToken();
			apiWrapper.toFile( accessTokenFile );

			return apiWrapper;
		}
	}
}

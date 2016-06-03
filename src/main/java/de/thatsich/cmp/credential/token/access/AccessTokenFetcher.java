package de.thatsich.cmp.credential.token.access;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.microsoft.alm.oauth2.useragent.AuthorizationException;
import com.soundcloud.api.ApiWrapper;
import com.soundcloud.api.Token;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.thatsich.cmp.credential.SoundCloudClient;
import de.thatsich.cmp.credential.Redirect;
import de.thatsich.cmp.credential.token.connection.ConnectionTokenManager;


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
class AccessTokenFetcher
{
	private static final Logger LOGGER = LogManager.getLogger();

	ApiWrapper fetchAccessToken() throws URISyntaxException, IOException, AuthorizationException, ClassNotFoundException
	{
		final URI redirectUri = new URI( Redirect.URL );
		LOGGER.info( "Using redirect URI '" + redirectUri + "' for access token. This is only to use the API properly. If the redirect URLs are not matching the OAuth2.0 will be canceled." );

		final ConnectionTokenManager connectionTokenManager = new ConnectionTokenManager();
		final String connectionToken = connectionTokenManager.fetchOrRecreateConnectionToken();
		final ApiWrapper api = new ApiWrapper( SoundCloudClient.ID, SoundCloudClient.SECRET, redirectUri, null );
		final Token token = api.authorizationCode( connectionToken );

		final String access = token.access;
		LOGGER.info( "Fetched access token '"+access+"' for connection token '" + connectionToken + "'" );

		return api;
	}
}

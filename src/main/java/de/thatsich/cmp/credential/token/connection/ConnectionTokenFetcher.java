package de.thatsich.cmp.credential.token.connection;


import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import com.microsoft.alm.oauth2.useragent.AuthorizationException;
import com.microsoft.alm.oauth2.useragent.AuthorizationResponse;
import com.microsoft.alm.oauth2.useragent.UserAgent;
import com.microsoft.alm.oauth2.useragent.UserAgentImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.thatsich.cmp.credential.SoundCloudClient;
import de.thatsich.cmp.credential.Redirect;


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
class ConnectionTokenFetcher
{
	private static final Logger LOGGER = LogManager.getLogger();

	String fetchConnectionToken() throws URISyntaxException, UnsupportedEncodingException, AuthorizationException
	{
		final URI redirectUri = new URI( Redirect.URL );
		LOGGER.info( "Using redirect URI '" + redirectUri + "' for connection token. This is only to use the API properly. If the redirect URLs are not matching the OAuth2.0 will be canceled." );
		final String encodedRedirect = URLEncoder.encode( redirectUri.toString(), "utf-8" );

		final URI authorizationEndpoint = URI.create( "https://soundcloud.com/connect?state=SoundCloud_Dialog_ab5ab&client_id=" + SoundCloudClient.ID + "&redirect_uri=" + encodedRedirect + "&response_type=code_and_token&scope=non-expiring&display=popup" );
		final UserAgent userAgent = new UserAgentImpl();

		final AuthorizationResponse authorizationResponse = userAgent.requestAuthorizationCode( authorizationEndpoint, redirectUri );
		final String connectionCode = authorizationResponse.getCode();
		LOGGER.info( "Retrieved connection connectionCode from login: '" + connectionCode + "'" );

		return connectionCode;
	}
}

package de.thatsich.soundcloud.credential;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import com.microsoft.alm.oauth2.useragent.AuthorizationException;
import com.microsoft.alm.oauth2.useragent.AuthorizationResponse;
import com.microsoft.alm.oauth2.useragent.UserAgent;
import com.microsoft.alm.oauth2.useragent.UserAgentImpl;
import com.soundcloud.api.ApiWrapper;
import com.soundcloud.api.Token;

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
public class TokenFetcher
{
	private static final Logger LOGGER = LogManager.getLogger();

	public ApiWrapper fetchToken() throws URISyntaxException, UnsupportedEncodingException, AuthorizationException
	{
		final URI redirectUri = new URI( Redirect.URL );
		LOGGER.info( "Will be redirecting to '" + redirectUri + "'. This is only to use the API properly. If the redirect URLs are not matching the OAuth2.0 will be canceled." );
		final String encodedRedirect = URLEncoder.encode( redirectUri.toString(), "utf-8" );

		final URI authorizationEndpoint = URI.create( "https://soundcloud.com/connect?state=SoundCloud_Dialog_ab5ab&client_id=" + Client.ID + "&redirect_uri=" + encodedRedirect + "&response_type=code_and_token&scope=non-expiring&display=popup" );
		final UserAgent userAgent = new UserAgentImpl();

		final AuthorizationResponse authorizationResponse = userAgent.requestAuthorizationCode( authorizationEndpoint, redirectUri );
		final String connectionCode = authorizationResponse.getCode();
		LOGGER.info( "Retrieved connection connectionCode from login: '" + connectionCode + "'" );

		final Token token = new Token( connectionCode, "", "non-expiring" );
		final ApiWrapper tokenAPI = new ApiWrapper( Client.ID, Client.SECRET, null, token );

		return tokenAPI;
	}

	public ApiWrapper fetchOrCreateToken() throws AuthorizationException, IOException, URISyntaxException, ClassNotFoundException
	{
		final File tokenFile = new File( CredentialPath.CONNECTION_TOKEN_PATH );
		if( tokenFile.exists() && tokenFile.isFile() )
		{
			LOGGER.info( "Found token file at '" + tokenFile + "'" );

			final ApiWrapper apiWrapper = ApiWrapper.fromFile( tokenFile );

			return apiWrapper;
		}
		else
		{
			LOGGER.info( "No token file at '" + tokenFile + "'; creating new one via OAuth2.0" );

			final ApiWrapper token = this.fetchToken();
			final boolean success = tokenFile.getParentFile().mkdirs();
			if (success) {
				LOGGER.info( "Created directories required for token file: '" + tokenFile );
			}

			token.toFile( tokenFile );

			return token;
		}
	}
}

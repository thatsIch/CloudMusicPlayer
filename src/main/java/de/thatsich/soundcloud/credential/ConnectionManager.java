package de.thatsich.soundcloud.credential;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
public class ConnectionManager
{
	private static final Logger LOGGER = LogManager.getLogger();

	private String fetchConnectionToken() throws URISyntaxException, UnsupportedEncodingException, AuthorizationException
	{
		final URI redirectUri = new URI( Redirect.URL );
		LOGGER.info( "Will be redirecting to '" + redirectUri + "'. This is only to use the API properly. If the redirect URLs are not matching the OAuth2.0 will be canceled." );
		final String encodedRedirect = URLEncoder.encode( redirectUri.toString(), "utf-8" );

		final URI authorizationEndpoint = URI.create( "https://soundcloud.com/connect?state=SoundCloud_Dialog_ab5ab&client_id=" + Client.ID + "&redirect_uri=" + encodedRedirect + "&response_type=code_and_token&scope=non-expiring&display=popup" );
		final UserAgent userAgent = new UserAgentImpl();

		final AuthorizationResponse authorizationResponse = userAgent.requestAuthorizationCode( authorizationEndpoint, redirectUri );
		final String connectionCode = authorizationResponse.getCode();
		LOGGER.info( "Retrieved connection connectionCode from login: '" + connectionCode + "'" );

		return connectionCode;
	}

	private ApiWrapper fetchAccessToken() throws URISyntaxException, IOException, AuthorizationException, ClassNotFoundException
	{
		final URI redirectUri = new URI( Redirect.URL );
		LOGGER.info( "Will be redirecting to '" + redirectUri + "'. This is only to use the API properly. If the redirect URLs are not matching the OAuth2.0 will be canceled." );

		final String connectionToken = this.fetchOrRecreateConnectionToken();
		final ApiWrapper api = new ApiWrapper( Client.ID, Client.SECRET, redirectUri, null );
		final Token token = api.authorizationCode( connectionToken );

		final String access = token.access;
		System.out.println( "access = " + access );

		return api;
	}

	public ApiWrapper fetchOrRecreateAccessToken() throws IOException, ClassNotFoundException, URISyntaxException, AuthorizationException
	{
		final File accessTokenFile = new File( CredentialPath.ACCESS_TOKEN_PATH );
		if( accessTokenFile.exists() && accessTokenFile.isFile() )
		{
			LOGGER.info( "Found access token file at '" + accessTokenFile + "'" );

			return ApiWrapper.fromFile( accessTokenFile );
		}
		else {
			final ApiWrapper apiWrapper = this.fetchAccessToken();
			apiWrapper.toFile( accessTokenFile );

			return apiWrapper;
		}
	}

	private String fetchOrRecreateConnectionToken() throws IOException, ClassNotFoundException, URISyntaxException, AuthorizationException
	{
		final File connectionTokenFile = new File( CredentialPath.CONNECTION_TOKEN_PATH );
		if( connectionTokenFile.exists() && connectionTokenFile.isFile() )
		{
			LOGGER.info( "Found connection token file at '" + connectionTokenFile + "'" );

			try(
				final FileInputStream file = new FileInputStream( CredentialPath.CONNECTION_TOKEN_PATH );
				final BufferedInputStream buffer = new BufferedInputStream( file );
				final ObjectInputStream input = new ObjectInputStream( buffer );
			)
			{
				final Object object = input.readObject();
				final String casted = (String) object;

				LOGGER.info( "Read token file with content: '" + casted + "'" );

				return casted;
			}
		}
		else
		{
			LOGGER.info( "No connection token file at '" + connectionTokenFile + "'; creating new one via OAuth2.0" );

			final String connectionToken = this.fetchConnectionToken();
			final boolean success = connectionTokenFile
				.getParentFile()
				.mkdirs();

			if( success )
			{
				LOGGER.info( "Created directories required for token file: '" + connectionTokenFile );
			}

			try(
				final FileOutputStream file = new FileOutputStream( CredentialPath.CONNECTION_TOKEN_PATH );
				final BufferedOutputStream buffer = new BufferedOutputStream( file );
				final ObjectOutputStream output = new ObjectOutputStream( buffer )
			)
			{
				output.writeObject( connectionToken );
			}

			return connectionToken;
		}
	}
}

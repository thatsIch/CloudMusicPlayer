package de.thatsich.cmp.credential.token.connection;


import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import com.microsoft.alm.oauth2.useragent.AuthorizationException;

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
public class ConnectionTokenManager
{
	private static final Logger LOGGER = LogManager.getLogger();

	public String fetchOrRecreateConnectionToken() throws IOException, ClassNotFoundException, URISyntaxException, AuthorizationException
	{
		final ConnectionTokenRepository repository = new ConnectionTokenRepository();
		final Optional<String> maybeAccessToken = repository.fetchAccessToken();

		if( maybeAccessToken.isPresent() )
		{
			return maybeAccessToken.get();
		}
		else
		{
			final File connectionTokenFile = new File( CredentialPath.CONNECTION_TOKEN_PATH );
			LOGGER.info( "No connection token file at '" + connectionTokenFile + "'; creating new one via OAuth2.0" );

			final ConnectionTokenFetcher fetcher = new ConnectionTokenFetcher();
			final String connectionToken = fetcher.fetchConnectionToken();

			repository.storeAccessToken( connectionToken );

			return connectionToken;
		}
	}
}

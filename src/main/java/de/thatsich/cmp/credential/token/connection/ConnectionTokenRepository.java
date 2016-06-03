package de.thatsich.cmp.credential.token.connection;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;

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
class ConnectionTokenRepository
{
	private static final Logger LOGGER = LogManager.getLogger();

	void storeAccessToken( final String connectionToken ) throws IOException
	{
		final File connectionTokenFile = new File( CredentialPath.CONNECTION_TOKEN_PATH );
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
	}

	Optional<String> fetchAccessToken() throws IOException, ClassNotFoundException
	{
		final File connectionTokenFile = new File( CredentialPath.CONNECTION_TOKEN_PATH );
		if( connectionTokenFile.exists() && connectionTokenFile.isFile() )
		{
			LOGGER.info( "Found connection token file at '" + connectionTokenFile + "'" );

			try(
				final FileInputStream file = new FileInputStream( CredentialPath.CONNECTION_TOKEN_PATH );
				final BufferedInputStream buffer = new BufferedInputStream( file );
				final ObjectInputStream input = new ObjectInputStream( buffer )
			)
			{
				final Object object = input.readObject();
				final String casted = (String) object;

				LOGGER.info( "Read token file with content: '" + casted + "'" );

				return Optional.of( casted );
			}
		}

		return Optional.empty();
	}
}

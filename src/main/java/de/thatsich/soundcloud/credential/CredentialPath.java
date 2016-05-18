package de.thatsich.soundcloud.credential;


import de.thatsich.soundcloud.ApplicationPath;


/**
 * TODO add description
 *
 * TODO add meaning
 *
 * TODO add usage
 *
 * @author thatsIch (thatsich[at]mail[dot]de)
 * @version 1.0.0-SNAPSHOT
 * @since 1.0.0-SNAPSHOT 17.05.2016
 */
public interface CredentialPath
{
	String CONNECTION_TOKEN_PATH = ApplicationPath.PATH + "\\connect.token";
	String AUTH_TOKEN_PATH = ApplicationPath.PATH + "\\auth.token";
}

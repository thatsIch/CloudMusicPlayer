package de.thatsich.cmp.credential;


import java.io.IOException;
import java.net.URISyntaxException;

import com.microsoft.alm.oauth2.useragent.AuthorizationException;
import com.soundcloud.api.ApiWrapper;

import de.thatsich.cmp.credential.token.access.AccessTokenManager;


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
	public ApiWrapper connectToSoundCloudAPI() throws ClassNotFoundException, URISyntaxException, AuthorizationException, IOException
	{
		final AccessTokenManager manager = new AccessTokenManager();
		final ApiWrapper api = manager.fetchOrRecreateAccessToken();

		return api;
	}
}

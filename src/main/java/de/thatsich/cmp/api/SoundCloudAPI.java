package de.thatsich.cmp.api;


import com.soundcloud.api.ApiWrapper;

import de.voidplus.soundcloud.SoundCloud;


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
public class SoundCloudAPI extends SoundCloud
{
	public SoundCloudAPI( final String clientID, final String clientSecret, final ApiWrapper wrapper )
	{
		super( clientID, clientSecret );

		this.wrapper = wrapper;
		this.token = wrapper.getToken();
	}
}

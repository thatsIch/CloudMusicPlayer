package de.thatsich.soundcloud.api;


/**
 * oEmbed is an open standard to easily embed content from oEmbed providers into
 * your site. The SoundCloud oEmbed endpoint will serve the widget embed code
 * for any SoundCloud URL pointing to a user, group, set or a playlist. To find
 * out more about the oEmbed standard, have a look at <a href="http://oembed.com/">http://oembed.com/</a>.
 *
 * @author thatsIch (thatsich[at]mail[dot]de)
 * @version 1.0.0-SNAPSHOT
 * @since 1.0.0-SNAPSHOT 25.04.2016
 */
public class OembedEndpoint implements Endpoint
{
	private static final String ENDPOINT_PATH = "/oembed";

	public String getEndPointPath()
	{
		return ENDPOINT_PATH;
	}
}

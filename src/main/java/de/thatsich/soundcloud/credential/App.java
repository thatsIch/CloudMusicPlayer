package de.thatsich.soundcloud.credential;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import com.microsoft.alm.oauth2.useragent.AuthorizationException;
import com.microsoft.alm.oauth2.useragent.AuthorizationResponse;
import com.microsoft.alm.oauth2.useragent.UserAgent;
import com.microsoft.alm.oauth2.useragent.UserAgentImpl;
import com.soundcloud.api.ApiWrapper;
import com.soundcloud.api.Token;

import de.thatsich.soundcloud.player.Client;


/**
 * TODO add description
 *
 * TODO add meaning
 *
 * TODO add usage
 *
 * @author thatsIch (thatsich[at]mail[dot]de)
 * @version 1.0.0-SNAPSHOT
 * @since 1.0.0-SNAPSHOT 26.04.2016
 */
public class App
{
	public static void main( final String[] args ) throws AuthorizationException, URISyntaxException, IOException
	{
		final URI redirectUri = new URI( Redirect.URL );
		System.out.println( "redirectUri = " + redirectUri.toString() );
		final String encodedRedirect = URLEncoder.encode( redirectUri.toString(), "utf-8" );

		final URI authorizationEndpoint = URI.create( "https://soundcloud.com/connect?state=SoundCloud_Dialog_ab5ab&client_id=" + Client.ID + "&redirect_uri=" + encodedRedirect + "&response_type=code_and_token&scope=non-expiring&display=popup" );
		final UserAgent userAgent = new UserAgentImpl();

		final AuthorizationResponse authorizationResponse = userAgent.requestAuthorizationCode( authorizationEndpoint, redirectUri );
		final String code = authorizationResponse.getCode();
		System.out.print( "Authorization Code: " );
		System.out.println( code );
		final Token token = new Token( code, "", "non-expiring" );
		final ApiWrapper api = new ApiWrapper( Client.ID, Client.SECRET, redirectUri, token );
		//		api.toFile( new File( "soundcloud.token" ) );


	}
}

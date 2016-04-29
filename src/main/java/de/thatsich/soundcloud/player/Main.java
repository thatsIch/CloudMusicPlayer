package de.thatsich.soundcloud.player;


import java.io.IOException;

import com.soundcloud.api.ApiWrapper;
import com.soundcloud.api.Token;


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
public class Main
{
	public static void main( String[] args )
	{
		final ApiWrapper api = new ApiWrapper(Client.ID, Client.SECRET, null, null);
		final Token token = api.getToken();
		try
		{
			final Token login = api.login( "thatsIch", "Schn1ps3l", Token.SCOPE_NON_EXPIRING);

			System.out.println( "login = " + login );
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}
	}
}

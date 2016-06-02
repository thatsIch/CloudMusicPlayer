package de.thatsich.soundcloud.file;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;


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
public final class FileHideManager
{
	private static final String ATTRIBUTE = "dos:hidden";

	public HideStatus hideFile( final File toBeHidden) throws IOException
	{
		final Path path = toBeHidden.toPath();
		final Boolean hidden = (Boolean) Files.getAttribute( path, ATTRIBUTE, LinkOption.NOFOLLOW_LINKS );
		if (hidden != null && !hidden) {
			Files.setAttribute( path, ATTRIBUTE, Boolean.TRUE, LinkOption.NOFOLLOW_LINKS );
		}

		// FIXME invent correct hide status
		return null;
	}
}

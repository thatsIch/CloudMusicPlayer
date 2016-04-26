package de.thatsich.soundcloud.player;


/**
 * TODO add description
 *
 * TODO add meaning
 *
 * TODO add usage
 *
 * @author thatsIch (thatsich[at]mail[dot]de)
 * @version 1.0.0-SNAPSHOT
 * @since 1.0.0-SNAPSHOT 25.04.2016
 */
class IconNameGenerator
{
	String generateIconFileNameFromIconSize( final int iconSize ) {
		return "soundcloud-icon_" + iconSize + "x" + iconSize + ".png";
	}
}

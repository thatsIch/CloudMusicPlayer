package de.thatsich.soundcloud.player;


import java.util.List;

import jersey.repackaged.com.google.common.collect.Lists;


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
class IconSizeProvider
{
	private final List<Integer> sizes;

	IconSizeProvider()
	{
		this.sizes = Lists.newArrayList( 64, 56, 48, 40, 32, 24, 16 );
	}

	List<Integer> getSizes()
	{
		return sizes;
	}
}

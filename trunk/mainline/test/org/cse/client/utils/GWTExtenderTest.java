package org.cse.client.utils;

import org.cse.client.AbstractCSETest;

/**
 * 
 * @author KBac
 *
 */
public class GWTExtenderTest extends AbstractCSETest {

	public void testGetCurrentLocale() {
		String s = new GWTExtensions().getCurrentLocale();
        assertEquals(0, s.length());
	}

}

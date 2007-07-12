package org.cse.client.bkm.impl;

import java.util.ArrayList;
import java.util.List;

import org.cse.client.AbstractCSETest;
import org.cse.client.bkm.impl.YPipe;

/**
 * 
 * @author KBac
 *
 */
public class YPipeTest extends AbstractCSETest {

	private static final String EXCLUDED = "excluded";
    private static final String USER = "myUserresUym";
	private static final String GAMMA = "gamma";
	private static final String BETA = "beta";
	private static final String ALPHA = "alpha";

	public void testBuildURL() {
		List includedTags = new ArrayList();
		includedTags.add(TagImplTest.newTag(ALPHA, 1));
		includedTags.add(TagImplTest.newTag(BETA, 2));
		includedTags.add(TagImplTest.newTag(GAMMA, 3));
        
        List excludedTags = new ArrayList();
        excludedTags.add(TagImplTest.newTag(EXCLUDED, 1));
		
		String url = YPipe.buildURL(USER, includedTags, excludedTags);
		final int aIndex = url.indexOf(ALPHA);
		final int bIndex = url.indexOf(BETA);
		final int cIndex = url.indexOf(GAMMA);
		final int dIndex = url.indexOf(YPipe.DUMMY_TAG);
        final int eIndex = url.indexOf(EXCLUDED);
		
		assertEquals(0, url.indexOf(YPipe.CSE_BUILDER_PIPE_URL));
		assertTrue(url.indexOf(USER) > 0);
		
		//ensure sort
		assertTrue("Invalid sort order - most scored tags to be in front", aIndex > bIndex);
		assertTrue("Invalid sort order - most scored tags to be in front", bIndex > cIndex);
		assertTrue("Invalid sort order - most scored tags to be in front", dIndex > aIndex);
        
        //ensure excluded tag first
        assertTrue("Invalid sort order - excluded tag expected first", cIndex > eIndex);

	}

}

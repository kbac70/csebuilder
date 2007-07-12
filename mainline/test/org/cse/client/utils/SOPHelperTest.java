package org.cse.client.utils;

import org.cse.client.AbstractCSETest;

import com.google.gwt.user.client.ResponseTextHandler;

/**
 * 
 * @author KBac
 *
 */
public class SOPHelperTest extends AbstractCSETest {

	public void testdispatchJSON() {
		final String jsonString = "blah";
		SOPHelper.dispatchJSON(jsonString, new ResponseTextHandler() {
			public void onCompletion(String responseText) {
				assertSame(jsonString, responseText);
			}			
		});
	}

}

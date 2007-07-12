package org.cse.client.utils;

import com.google.gwt.user.client.ResponseTextHandler;

/**
 * Interface defining properties of a JSON provider 
 * 
 * @author KBac
 *
 */
public interface JSONProvider {

	/**
	 * Call this method to issue async JSON request to be handled by the handler. 
	 * @param url to the site feeding back JSON
	 * @param handler the instance of <code>{@link ResponseTextHandler}</code> responsible 
	 * for handling asyn request and parsing the retrieved JSON value 
	 */
	void makeJSONRequest(String url, ResponseTextHandler handler);
	
}

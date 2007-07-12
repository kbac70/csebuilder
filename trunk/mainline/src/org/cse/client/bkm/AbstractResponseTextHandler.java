package org.cse.client.bkm;

import com.google.gwt.user.client.ResponseTextHandler;

/**
 * Abstract base implementation of the <code>{@link ResponseTextHandler}</code> interface, which allows 
 * external party to associate a parser to the relevant response type.
 * 
 * @author KBac
 *
 */
public abstract class AbstractResponseTextHandler implements ResponseTextHandler {
	private ResponseParser parser;
	/**
	 * @return valid parser instance which has been assocated witht this response handler
	 */
	public ResponseParser getParser() {
		return parser;
	}
	/**
	 * Sets valid parser instance which is to be assocated witht this response handler
	 */
	public void setParser(ResponseParser parser) {
		this.parser = parser;
	}		
}
package org.cse.client.bkm;

import java.util.List;
import java.util.Map;

import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

/**
 * Abstract implementation of the <code>ResponseParser</code> encapsulating 
 * JSON parsing functionality.
 *  
 * @author KBac
 *
 */
public abstract class AbstractJSONResponseParser implements ResponseParser {

	public final List getBookmarks(String text, Map activeTags) {
		return getBookmarks(JSONParser.parse(text), activeTags);
	}

	public final List getTags(String text) {
		return getTags(JSONParser.parse(text));
	}
	/**
	 * All descendants are required to provide with relevant JSON parsing implementation. 
     * @param response as <code>JSONValue</code>
	 * @param activeTags as <code>Map&lt;tagName,{@link Tag}&gt;
     * @return <code>List&lt;{@link Bookmark}&gt;</code> created after parsing the text
	 */
	protected abstract List getBookmarks(JSONValue response, Map activeTags);
    /**
     * All descendants are required to provide with relevant JSON parsing implementation.
     * @param response as <code>JSONValue</code>
     * @return <code>List&lt;{@link Tag}&gt;</code> associated with this tag
     */
	protected abstract List getTags(JSONValue response);
	
}
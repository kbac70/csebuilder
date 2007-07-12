package org.cse.client.bkm;

import java.util.List;
import java.util.Map;

/**
 * Interface definining the properties of a response parser 
 * @author KBac
 *
 */
public interface ResponseParser {
    /**
     * @param responseText to parse
     * @return <code>List&lt;{@link Tag}&gt;</code> created after parsing the text
     */
	public List getTags(String responseText);
    /**
     * @param responseText to parse
     * @param activeTags <code>Map&lt;tagName,{@link Tag}&gt;
     * @return <code>List&lt;{@link Bookmark}&gt;</code> created after parsing the text
     */
	public List getBookmarks(String responseText, Map activeTags); 
    
}
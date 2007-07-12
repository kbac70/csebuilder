package org.cse.client.bkm;

import java.util.List;


/**
 * Interface of any service capable of providing with bookmark and tag information
 * 
 * @author KBac
 *
 */
public interface BookmarkProvider {
	
	
    /**
     * @return name of the provider implementation
     */
    String getName();
    /**
     * @return HTML string defining small image / logo of the provider 
     */
    String getSmallImageHtml();
    /**
     * Call this method to retrieve tags from the instance of the <code>TagProvider</code> 
     * @param uid to whom the tags belong
     * @param pwd (optional) 
     * @param handler which is an extension of <code>{@link AbstractResponseTextHandler}</code> responsible 
     * for initiating the request and processing the response
     * <P><B> NB: </B> Provider is responsible for assigning the relevant parser to the handler.
     */
	void fetchTags (String uid, String pwd, AbstractResponseTextHandler handler);
    /**
     * Call this method to retrieve bookmarks from the instance of the <code>TagProvider</code> 
     * @param uid to whom the bookmarks belong
     * @param includedTags <code>List&lt;{@link Tag}&gt;</code> bookmarks should be tagged with
     * @param excludedTags <code>List&lt;{@link Tag}&gt;</code> bookmarks should not be tagged with
     * @param handler which is an extension of <code>{@link AbstractResponseTextHandler}</code> responsible 
     * for initiating the request and processing the response
     * <P><B> NB: </B> Provider is responsible for assigning the relevant parser to the handler.
     */
    void fetchBookmarks(String uid, List includedTags, List excludedTags, AbstractResponseTextHandler handler);
    
}

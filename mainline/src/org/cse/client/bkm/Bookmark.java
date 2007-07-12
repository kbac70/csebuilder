package org.cse.client.bkm;

import java.util.List;

/**
 * Interface defining the capabilities of a Bookmark
 * 
 * @author KBac
 *
 */
public interface Bookmark {
    /**
     * @return user defined description of the Bookmark
     */
	public String getDescription();
    /**
     * @return <code>List&lt;{@link Tag}&gt;</code> associated with this tag
     */
	public List getTags();
    /**
     * @return URL identifying the bookmarked resource
     */
    public String getLink(); 
}
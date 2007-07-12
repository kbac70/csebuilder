package org.cse.client.bkm.impl;

import java.util.List;

import org.cse.client.bkm.Bookmark;

/**
 * Boookmark class encapsulates properties of a bookmark.
 *  
 * @author KBac
 *
 */
public class BookmarkImpl implements Bookmark {

    private final String link;
    private final List tags;
    private final String description;
    public BookmarkImpl(String link, String description, List tags) {
        this.link = link;
        this.description = description;
        this.tags = tags;
    }


	public String getDescription() {
		return description;
	}


    /**
     * @return URL identifying the bookmarked resource
     */
	public String getLink() {
		return link;
	}


    /**
     * @return <code>List&lt;Tag&gt;</code> associated with this bookmark instance
     */
	public List getTags() {
		return tags;
	}
    
    
}

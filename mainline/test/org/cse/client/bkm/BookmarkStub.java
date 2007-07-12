package org.cse.client.bkm;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author KBac
 *
 */
public class BookmarkStub implements Bookmark {
    
    public static final String DEFAULT_DESC = "desc ";
    public static final String DEFAULT_LINK = "link ";
    
    private final String description;
    
    private final String link;
    
    private final List tags;
    
    public static List newBookmarks(int count) {
        List result = new ArrayList();
        List tags = TagStub.newTags(count); 
            
        for (int i = 0; i < count; i++) {
            Bookmark b = new BookmarkStub(DEFAULT_DESC + i, DEFAULT_LINK + i, tags);
            result.add(b);
        }
        
        return result;
    }
    
    public BookmarkStub(String description, String link, List tags) {
        this.description = description;
        this.link = link;
        this.tags = tags;
    }

	public String getDescription() {
		return this.description;
	}

	public String getLink() {
		return this.link;
	}

	public List getTags() {
		return this.tags;
	}

}

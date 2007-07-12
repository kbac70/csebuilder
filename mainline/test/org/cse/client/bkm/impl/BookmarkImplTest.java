package org.cse.client.bkm.impl;


import java.util.List;

import org.cse.client.AbstractCSETest;
import org.cse.client.bkm.impl.BookmarkImpl;



/**
 * 
 * @author KBac
 *
 */
public class BookmarkImplTest extends AbstractCSETest{
	
	public static final String LINK = "link";
    public static final String DESCRIPTION  = "desc";

    public void testGetDescriptionInitializedOk() {
		BookmarkImpl bkm = BookmarkImplTest.newBookmark();
		assertEquals(BookmarkImplTest.DESCRIPTION, bkm.getDescription());
	}

	public void testGetLinkInitializedOk() {
		BookmarkImpl bkm = BookmarkImplTest.newBookmark();
		assertEquals(BookmarkImplTest.LINK, bkm.getLink());
	}

	public void testGetTagsInitializedOk() {
		BookmarkImpl bkm = BookmarkImplTest.newBookmark();
		assertNull(bkm.getTags());
		
		bkm = BookmarkImplTest.newBookmark(TagImplTest.newTags(10));
		assertNotNull(bkm.getTags());
	}

    public static BookmarkImpl newBookmark() {
    	return newBookmark(null);
    }

    public static BookmarkImpl newBookmark(List tags) {
    	BookmarkImpl result = new BookmarkImpl(LINK, DESCRIPTION, tags);
    	assertNotNull(result);
    	return result;
    }	
}

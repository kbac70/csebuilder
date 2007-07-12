package org.cse.client.bkm;

import java.util.List;
import java.util.Map;

import org.cse.client.bkm.AbstractResponseTextHandler;
import org.cse.client.bkm.BookmarkProvider;

/**
 * 
 * @author KBac
 *
 */
public class BookmarkProviderStub implements BookmarkProvider {
	
	public static final String DEFAULT_BOOKMARKS = "gimme bookmarks";
	
	public static final String DEFAULT_TAGS = "gimme tags";
	
	private final String bookmarks;
	
	private final String tags;
	
	public BookmarkProviderStub() {
		this(DEFAULT_BOOKMARKS, DEFAULT_TAGS);
	}
	
	public BookmarkProviderStub(String bookmarks, String tags) {
		this.bookmarks = bookmarks;
		this.tags = tags;
	}

	public void fetchBookmarks(String uid, List includedTags,
			List excludedTags, AbstractResponseTextHandler handler) {
		handler.setParser(new ResponseParser() {

			public List getBookmarks(String responseText, Map activeTags) {
				return BookmarkStub.newBookmarks(10);
			}

			public List getTags(String responseText) {
				return null;
			}
		});
		handler.onCompletion(bookmarks);
	}

	public void fetchTags(String uid, String pwd,
			AbstractResponseTextHandler handler) {
		handler.setParser(new ResponseParser() {

			public List getBookmarks(String responseText, Map activeTags) {
				return null;
			}

			public List getTags(String responseText) {
				return TagStub.newTags(10);
			}
		});
		handler.onCompletion(tags);
	}

	public String getName() {
		return "BookmarkProviderStub";
	}

	public String getSmallImageHtml() {
		return "BookmarkProviderStub";
	}

}

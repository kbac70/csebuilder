package org.cse.client;

import org.cse.client.bkm.TagProvider;

import com.google.gwt.user.client.ui.Widget;

/**
 * Wrapper class to carry information about changes within <code>{@link TagProviderPanel}</code>
 * 
 * @author KBac
 *
 */
public class TagProviderChangedEvent extends Widget {

	public final String userID;
	
	public final TagProvider tagProvider;
	
	public TagProviderChangedEvent(String userID, TagProvider tagProvider) {
		this.userID = userID;
		this.tagProvider = tagProvider;
	}
	
	public TagProviderChangedEvent(String userID) {
		this(userID, null);
	}
	
	public TagProviderChangedEvent(TagProvider tagProvider) {
		this(null, tagProvider);
	}
	
	
	
}

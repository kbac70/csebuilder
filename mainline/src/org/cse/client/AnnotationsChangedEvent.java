package org.cse.client;

import org.cse.client.utils.CSE;

import com.google.gwt.user.client.ui.Widget;

/**
 * Wrapper class to carry information about changes within <code>{@link AnnotationsPanel}</code>
 * 
 * @author KBac
 *
 */
public class AnnotationsChangedEvent extends Widget {

	public final CSE.Annotations annotations;
	
	public final boolean isFetchingBookmarks;
	
	public AnnotationsChangedEvent(CSE.Annotations annotations) {
		this(annotations, false);		
	}
	
	public AnnotationsChangedEvent(boolean isFetchingBookmarks) {
		this(null, isFetchingBookmarks);		
	}	
	
	protected AnnotationsChangedEvent(CSE.Annotations annotations, 
			boolean isFetchingBookmarks) {
		this.annotations = annotations;
		this.isFetchingBookmarks = isFetchingBookmarks;
	}
	
	
}

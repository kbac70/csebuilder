package org.cse.client;

import java.util.Map;

import org.cse.client.bkm.TagProvider;

import com.google.gwt.user.client.ui.ChangeListener;

/**
 * 
 * @author KBac
 *
 */
public class TagProviderChangedEventTest extends AbstractCSETest {

	public void testTagsChangedEvent() {
		String UID = "uid";
		TagProviderChangedEvent event = new TagProviderChangedEvent(UID, null);
		assertEquals(null, event.tagProvider);
		assertSame(UID, event.userID);
	}

	public void testTagsChangedEventString() {
		String UID = "uid";
		TagProviderChangedEvent event = new TagProviderChangedEvent(UID);
		assertEquals(null, event.tagProvider);
		assertSame(UID, event.userID);
	}
	
	public void testTagsChangedEventTagProvider() {
		TagProvider tagProviderStub = new TagProvider() {

			public void addChangeListener(ChangeListener listener) {
				// TODO Auto-generated method stub
				
			}

			public Map getActive() {
				// TODO Auto-generated method stub
				return null;
			}

			public Map getExcluded() {
				// TODO Auto-generated method stub
				return null;
			}

			public Map getIncluded() {
				// TODO Auto-generated method stub
				return null;
			}

			public void removeChangeListener(ChangeListener listener) {
				// TODO Auto-generated method stub
				
			}
			
		};

		TagProviderChangedEvent event = new TagProviderChangedEvent(tagProviderStub);
		assertSame(tagProviderStub, event.tagProvider);
		assertNull(event.userID);
	}
	

}

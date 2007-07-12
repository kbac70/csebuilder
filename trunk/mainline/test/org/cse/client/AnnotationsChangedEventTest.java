package org.cse.client;


/**
 * 
 * @author KBac
 *
 */
public class AnnotationsChangedEventTest extends AbstractCSETest {

	public void testAnnotationsChangedEventAnnotations() {
		AnnotationsChangedEvent event = new AnnotationsChangedEvent(null, false);
		assertEquals(null, event.annotations);
		assertEquals(false, event.isFetchingBookmarks);
	}

	public void testAnnotationsChangedEventBoolean() {
		AnnotationsChangedEvent event = new AnnotationsChangedEvent(true);
		assertEquals(null, event.annotations);
		assertEquals(true, event.isFetchingBookmarks);
	}

	public void testAnnotationsChangedEventAnnotationsBoolean() {
		AnnotationsChangedEvent event = new AnnotationsChangedEvent(null);
		assertEquals(null, event.annotations);
		assertEquals(false, event.isFetchingBookmarks);
	}

}

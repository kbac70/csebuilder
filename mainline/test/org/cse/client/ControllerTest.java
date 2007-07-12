package org.cse.client;

import org.cse.client.bkm.BookmarkProviderStub;
import org.cse.client.bkm.TagProvider;
import org.cse.client.bkm.TagProviderDummy;
import org.cse.client.utils.CSE;
import org.cse.client.utils.GWTExtenderStub;

import com.google.gwt.user.client.History;

/**
 * 
 * @author KBac
 *
 */
public class ControllerTest extends AbstractCSETest {

	protected static Controller newController() {
		Controller controller = new Controller(
				new BookmarkProviderStub(), new GWTExtenderStub()
				);		
		return controller;
	}
	
	public void testControllerInitialization(){
		Controller controller = newController();
		
		assertNotNull(controller);
		assertNotNull(controller.root);
		assertNotNull(controller.main);
		assertNotNull(controller.saveDlg);
		assertNull(controller.root.getWidget());
		
		assertNotNull(controller.getBookmarkProvider());
		assertNull(controller.getAnnotations());
		assertNotNull(controller.getI18NFactory());
		assertNull(controller.getTagProvider());
	    assertNotNull(controller.getUIHelper());
		assertNotNull(controller.getUserId());
		assertNotNull(controller.getValidator());
		assertSame(controller, controller.getValidator());	
		
		controller.init();
		assertSame(controller.main, controller.root.getWidget());
	}
	
	public void testNotifyUserName(){
		Controller c = newController();
		
		assertNotNull(c.getUserId());
		assertTrue(c.getUserId().length() == 0);
		
		final String uid = "123";
		c.widgetChanged(new TagProviderChangedEvent(uid));
		
		assertNotNull(c.getUserId());
		assertEquals(uid, c.getUserId());		
	}
	
	public void testNotifyTagProvider() {
		Controller c = newController();
		
		assertNull(c.getTagProvider());
		
		final TagProvider tp = new TagProviderDummy();
		c.widgetChanged(new TagProviderChangedEvent(tp));
		
		assertSame(tp, c.getTagProvider());
	}
	
	public void testNotifyAnnotations() {
		Controller c = newController();
		
		assertNull(c.getAnnotations());
		
		final CSE.Annotations a = new CSE.Annotations(null, null, null, null);
		c.widgetChanged(new AnnotationsChangedEvent(a));
		
		assertSame(a, c.getAnnotations());		
	}
	
	
	public void testHistoryManagement() {
		Controller c = newController();
		c.init();
		
		assertSame(c.main, c.root.getWidget());
		
		History.newItem(Application.HISTORY_TOKEN_ANNOTATIONS_DLG);
		assertSame(c.saveDlg, c.root.getWidget());
		
		History.back();
c.onHistoryChanged("");//workaround - back does not trigger the event
		assertSame(c.main, c.root.getWidget());

		History.forward();
c.onHistoryChanged(History.getToken());//workaround - forward does not trigger the event		
		assertSame(c.saveDlg, c.root.getWidget());
	}
}

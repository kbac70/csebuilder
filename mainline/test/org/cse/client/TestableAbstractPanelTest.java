package org.cse.client;


import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author KBac
 *
 */
public class TestableAbstractPanelTest extends AbstractCSETest {
	
	private TestableAbstractPanel newTestableAbstractPanel() {
		TestableAbstractPanel result = new TestableAbstractPanel(new ApplicationDummy());
		assertFalse(result.isInitCalled);
		assertTrue(result.isInitialyOpen);
		assertFalse(result.isValidated);
		assertNotNull(result.help);
		assertTrue(result.changeListeners.isEmpty());
		return result;
	}
	

	public void testInitialize() {
		TestableAbstractPanel panel = newTestableAbstractPanel();
		panel.initialize();
		assertTrue(panel.isInitCalled);
		assertTrue(panel.isValidated);
		
		panel.isInitCalled = false;
		panel.initialize();
		assertFalse("initialization should execute only once", panel.isInitCalled);		
	}

	public void testChangeListenerManagement() {
		TestableAbstractPanel panel = newTestableAbstractPanel();
		ChangeListener listener = new ChangeListener() {
			public void onChange(Widget sender) {
				// TODO Auto-generated method stub				
			}			
		};
		panel.addChangeListener(listener);
		assertEquals(1, panel.changeListeners.size());
		panel.removeChangeListener(listener);
		assertEquals(0, panel.changeListeners.size());		
	}

}

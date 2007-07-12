package org.cse.client.i18n;

import org.cse.client.AbstractCSETest;

/**
 * 
 * @author KBac
 *
 */
public class I18NFactoryTest extends AbstractCSETest {

	public void testNewMainPanelConstants() {
		assertNotNull(new I18NFactory().newMainPanelConstants());
	}
	
	public void testNewUIHelperConstants() {
		assertNotNull(new I18NFactory().newUIHelperConstants());
	}
	
	public void testnewTagProviderConstants() {
		assertNotNull(new I18NFactory().newTagProviderConstants());
	}
	
	public void testnewContextPanelConstants() {
		assertNotNull(new I18NFactory().newContextPanelConstants());
	}

	public void testnewAnnotationsConstants() {
		assertNotNull(new I18NFactory().newAnnotationsConstants());
	}
	
	public void testnewTextAreaControlsWidgetConstants() {
		assertNotNull(new I18NFactory().newTextAreaControlsWidgetConstants());
	}
	
	public void testnewSaveDlgConstants() {
		assertNotNull(new I18NFactory().newSaveDlgConstants());
	}

}

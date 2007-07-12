package org.cse.client.widgets;

import org.cse.client.AbstractCSETest;

/**
 * 
 * @author KBac
 *
 */
public class SaveAsXMLDlgTest extends AbstractCSETest {
	
	final String LAST_ELEM_NAME = "element";
	final String LAST_ELEM_END = "</" + LAST_ELEM_NAME + ">";
	
	SaveAsXMLDlg newSaveAsXMLDlg() {
		SaveAsXMLDlg result = new SaveAsXMLDlg();

		return result;
	}

	public void testSaveAsXMLDlg() {
		SaveAsXMLDlg d = newSaveAsXMLDlg();
		
		assertNotNull(d);
		assertNotNull(d.btnClose);
		assertNotNull(d.caption);
		assertNotNull(d.message);
		assertNull(d.lastElem);
		assertNull(d.lastElemXML);
	}

	public void testSetRootElementEnd() {
		SaveAsXMLDlg d = newSaveAsXMLDlg();
		
		assertNull(d.lastElem);
		assertNull(d.lastElemXML);
		
		try {
			d.setRootElementEnd("blah>");
			assertFalse("Should thrown as root element has invalid format", true);			
		} catch (RuntimeException e) {
			//ok
		}
		
		d.setRootElementEnd(LAST_ELEM_END);

		assertEquals(LAST_ELEM_END, d.lastElem);
		assertEquals("&lt;/" + LAST_ELEM_NAME + "&gt;", d.lastElemXML);		
	}

	public void testSetXML() {
		SaveAsXMLDlg d = newSaveAsXMLDlg();
		
		assertNull(d.lastElem);
		try {
			d.setXML("blah");
			assertFalse("Should have thrown as no root elem setup", true);
		} catch (RuntimeException e) {
			//ok
		}
		
		d.setRootElementEnd(LAST_ELEM_END);
		try {
			d.setXML("blah");
			assertFalse("Should have thrown as root elem not matching", true);
		} catch (RuntimeException e) {
			//ok
		}
		
		d.setXML(LAST_ELEM_END);		
	}

}

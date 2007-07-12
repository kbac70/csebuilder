package org.cse.client.widgets;

import org.cse.client.AbstractCSETest;

/**
 * 
 * @author KBac
 *
 */
public class TimedHTMLTest extends AbstractCSETest {

	private TimedHTML newTimedHTML() {
    	TimedHTML html = new TimedHTML();
    	assertNotNull(html);
    	assertEquals("", html.getHTML());
    	assertEquals(TimedHTML.DEFAULT_TIMEOUT, html.getTimeoutMillis());
    	assertEquals(TimedHTML.DEFAULT_STYLE_NAME, html.getStyleName());
    	return html;
	}
	
    public void testSetStyleNameString() {
    	TimedHTML html = newTimedHTML();
    	final String dummyStyle = "dd";
    	html.setStyleName(dummyStyle);
    	assertEquals(TimedHTML.DEFAULT_STYLE_NAME, html.getStyleName());
    }

    public void testSetHTML() {
    	TimedHTML html = newTimedHTML();
    	final String dummyHTML = "html";
    	html.setHTML(dummyHTML);
    	assertEquals(dummyHTML, html.getHTML());
    }

    public void testSetTimeoutMillis() {
    	TimedHTML html = newTimedHTML();
    	final int ms = 100;
    	html.setTimeoutMillis(ms);
    	assertEquals(ms, html.getTimeoutMillis());
    }
    
    public void testSpacing() {
    	TimedHTML html = newTimedHTML();
    	assertEquals(0, html.getSpacing());
    	html.setSpacing(10);
    	assertEquals(10, html.getSpacing());
    	assertEquals(10, html.panel.getSpacing());
    }

}

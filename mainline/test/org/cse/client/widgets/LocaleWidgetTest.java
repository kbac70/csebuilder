package org.cse.client.widgets;

import org.cse.client.AbstractCSETest;
import org.cse.client.utils.GWTExtender;
import org.cse.client.utils.GWTExtenderStub;
import org.cse.client.widgets.LocaleWidget.Locale;

/**
 * 
 * @author KBac
 *
 */
public class LocaleWidgetTest extends AbstractCSETest {
    
    public static final String HOST_NAME = "LocaleWidgetTest";
    public static final String QRY_STRING = "QRY";
    

    public static LocaleWidget newLocaleWidget() {
        return newLocaleWidget(new GWTExtenderStub());
    }
    
    public static LocaleWidget newLocaleWidget(GWTExtender gwtExtenions) {
        LocaleWidget result = new LocaleWidget(gwtExtenions, HOST_NAME);
        assertNotNull(result);
        return result;    	
    }
    
    public void testLocaleWidgetInitializedOk() {
        LocaleWidget lw = newLocaleWidget();
        
        assertEquals(0, lw.locales.size());
        assertEquals(HOST_NAME, lw.htmHostName);
    }
    
    private void validateLocale(LocaleWidget.Locale a, boolean isActive) {
        validateLocale(a, isActive, QRY_STRING, QRY_STRING);
    }
    
    private void validateLocale(LocaleWidget.Locale a, boolean isActive, 
            String qryName, String displayName) {
        
        assertEquals(qryName, a.qryValue);
        assertEquals(displayName, a.getDisplayName());
        assertEquals(isActive, a.isActive());
    }
    

    public void testDefaultActiveLocales(){
		LocaleWidget lw = newLocaleWidget();
        
        assertEquals(0, lw.locales.size());
        assertFalse(lw.hasActiveLocale());
        
        lw.addLocale(QRY_STRING);
        assertTrue(lw.hasActiveLocale());
    }
    
  
    public void testDefaultActiveLocalesFromQueryString(){  
    	final GWTExtenderStub stub = new GWTExtenderStub();
    	stub.setCurrentLocale(QRY_STRING);
    	stub.setLocation(HOST_NAME + "?locale=" + QRY_STRING);
    	
    	LocaleWidget lw = newLocaleWidget(stub);
        assertEquals(0, lw.locales.size());
        assertFalse(lw.hasActiveLocale());
        
        lw.addLocale("ABC");
        assertFalse(lw.hasActiveLocale());
        lw.addLocale(QRY_STRING);
        assertTrue("The locale should be active as present within the query string.", lw.hasActiveLocale());        
    }

	
	public void testAddLocaleString() {
		LocaleWidget lw = newLocaleWidget();
        
        assertEquals(0, lw.locales.size());        
        lw.addLocale(QRY_STRING);
        assertEquals(1, lw.locales.size());     
    
        validateLocale((Locale) lw.locales.get(0), true);
	}

	
	public void testAddLocaleStringBoolean() {
        LocaleWidget lw = newLocaleWidget();
        
        assertEquals(0, lw.locales.size());        
        lw.addLocale(QRY_STRING, true);
        assertEquals(1, lw.locales.size());        
    
        validateLocale((Locale) lw.locales.get(0), true);
	}

	
	public void testAddLocaleStringStringBoolean() {
        LocaleWidget lw = newLocaleWidget();
        
        final String DISPLAY_NAME = "ABC";
        assertEquals(0, lw.locales.size());        
        lw.addLocale(QRY_STRING, DISPLAY_NAME, true);
        assertEquals(1, lw.locales.size());        
    
        validateLocale((Locale) lw.locales.get(0), true, QRY_STRING, DISPLAY_NAME);
	}

	
	public void testActivateLocale() {
        LocaleWidget lw = newLocaleWidget();
        
        assertEquals(0, lw.locales.size());        
        lw.addLocale(QRY_STRING, true);
        
        assertTrue(lw.activateLocale(QRY_STRING));
        assertFalse(lw.activateLocale("ABC"));
	}
	
	public void testActivateLocaleWithDefault() {
        LocaleWidget lw = newLocaleWidget();
        
        assertEquals(0, lw.locales.size());        
        lw.addLocale(QRY_STRING);
        
        assertTrue(lw.activateLocale(QRY_STRING, ""));
        assertTrue(lw.activateLocale("ABC", QRY_STRING));
        assertFalse(lw.activateLocale("ABC", ""));	    
	}

}

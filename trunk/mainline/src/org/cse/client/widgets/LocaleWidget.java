package org.cse.client.widgets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.cse.client.utils.GWTExtensions;
import org.cse.client.utils.GWTExtender;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Widget;

/**
 * Utility showing styled textual locale selector acting as external hyperlinks
 * 
 * @author KBac
 *
 */
public class LocaleWidget extends Composite {
    
    /**
     * 
     * @author KBac
     *
     */
    class Locale extends FocusWidget {
    	/**
    	 * General style name for a locale indicator
    	 */
        public static final String LOCALE_STYLE_NAME = "locale";
    	/**
    	 * Additional style name for active locale
    	 */
        public static final String ACTIVE_STYLE_NAME = "locale-active";
    	/**
    	 * Additional style name for inactive locale
    	 */        
        public static final String INACTIVE_STYLE_NAME = "locale-inactive";
        
        private final String url;
        
        private final String displayName;
        
        private final boolean isDefault;
        
        protected final String qryValue;
        
        private boolean isActive;
        
        private String styleName;
        
        public Locale(String href, String qryValue, String displayName, boolean isDefault) {
            super(DOM.createSpan());

            this.url = href + "?" + GWTExtensions.LOCALE_QRY_STRING + "=" + qryValue;
            this.qryValue = qryValue;
            this.displayName = displayName;
            this.isDefault = isDefault;

            Element span = getElement();
            Element label = DOM.createLabel();
            DOM.appendChild(span, label);
            DOM.setElementProperty(label, "class", "pointer");
            DOM.setInnerText(label, displayName);
             
            super.setStyleName(LOCALE_STYLE_NAME); 
            this.setStyleName(INACTIVE_STYLE_NAME);
            this.setActive(isDefault);
        }
        
  
        public boolean isActive() {
            return isActive;
        }
        
        public void setStyleName(String style) {            
            if (this.styleName != null) {
                this.removeStyleName(styleName);
            }
            
            super.addStyleName(style);
            this.styleName = style;
        }
        
        public void setActive(boolean isActive) {
              if (this.isActive != isActive) {
                this.isActive = isActive;
    
                if (this.isActive ) {
                    setStyleName(ACTIVE_STYLE_NAME);
                    activate();
                } else {
                    setStyleName(INACTIVE_STYLE_NAME);
                }
            }
        }
        
        private void activate() {
        	//make sure query string correct
        	final String currentLocale = LocaleWidget.this.gwtExtenions.getCurrentLocale().toLowerCase(); 
        	final boolean isDefaultQryString = currentLocale.length() == 0; 
        	
            if (!(isDefault && isDefaultQryString) && 
            		currentLocale.indexOf(qryValue.toLowerCase()) == -1) {
                LocaleWidget.this.gwtExtenions.setLocation(url);
            }
        }
        
        public String getQueryValue() {
            return this.qryValue;
        }
        
        public String getDisplayName() {
            return this.displayName;
        }
    }
    
    private Locale activeLocale;
    
    protected final String htmHostName;
    
    protected final List locales;
    
    protected final GWTExtender gwtExtenions;
    
    private final FlowPanel panel = new FlowPanel();
    
    /**
     * @param htmlHostName - the name of your html form hosting the widget
     */
    public LocaleWidget(GWTExtender gwtExtenions, String htmlHostName) {
        this.gwtExtenions = gwtExtenions;
        this.htmHostName = htmlHostName;
        this.locales = new ArrayList();
        
        this.initWidget(panel);
    }
    
    private void setActiveLocale(Widget w) {
        if (this.activeLocale != w ) {
            if (this.activeLocale != null) {
                this.activeLocale.setActive(false);
            }
            this.activeLocale = (Locale) w;
            this.activeLocale.setActive(true);
        }
    }
    /**
     * @return true when active {@link Locale} has been defined during setup, false otherwise
     */
    public boolean hasActiveLocale() {
    	return this.activeLocale != null;
    }    
    /**
     * Adds {@link Locale} entry to the widget.
     * @param queryName - locale name as part of the URL query string (http://your_host?locale=queryName)
     * The uppercased value is going to be used for a display.
     */
    public void addLocale(String queryName) {
    	final String currentLocale = this.gwtExtenions.getCurrentLocale().toLowerCase();
    	boolean isDefault = currentLocale.length() == 0 ?
    			this.locales.isEmpty() : // make first the default
    			currentLocale.indexOf(queryName.toLowerCase()) != -1 //make the one present in the query string the default
    			;
        addLocale(queryName, isDefault);
    }
    
    /**
     * Adds {@link Locale} entry to the widget.
     * @param queryName - locale name as part of the URL query string (http://your_host?locale=queryName). 
     * The uppercased value is going to be used for a display. 
     * @param isDefault - true to indicate locale should be considered default
     */
    public void addLocale(String queryName, boolean isDefault) {
        addLocale(queryName, queryName.toUpperCase(), isDefault);
    }
    
    /**
     * Adds {@link Locale} entry to the widget.
     * @param queryName - locale name as part of the URL query string (http://your_host?locale=queryName)
     * @param displayName 
     * @param isDefault - true to indicate locale should be considered default
     */
    protected void addLocale(String queryName, String displayName, boolean isDefault) {
        Locale a = new Locale(this.htmHostName, queryName, displayName, isDefault);
        
        a.addClickListener(new ClickListener() {
            public void onClick(Widget sender) { setActiveLocale(sender); }                
        });
        
        this.panel.add(a);
        this.locales.add(a);
        
        if (isDefault) {
            this.setActiveLocale(a);
        }
    }
    
    /**
     * Call this method to activate locale using its queryName
     * @param queryName - locale name as part of the URL query string (http://your_host?locale=queryName)
     * @return true on success when locale found, false otherwise
     */
    public boolean activateLocale(String queryName) {
        for (Iterator iter = locales.iterator(); iter.hasNext();) {
            Locale a = (Locale) iter.next();
            if (a.getQueryValue().equalsIgnoreCase(queryName)) {
                this.setActiveLocale(a);
                return true;
            }            
        }
        return false;
    }
    /**
     * Call this method to activate locale using its queryName. If queryName 
     * not found use defaultLocale as active one.
     * @param queryName
     * @param defaultLocale
     * @return true on success when locale found, false otherwise
     */
    public boolean activateLocale(String queryName, String defaultLocale) {
        if (!activateLocale(queryName)) {
            return activateLocale(defaultLocale);
        }
        
        return true;
    }

}

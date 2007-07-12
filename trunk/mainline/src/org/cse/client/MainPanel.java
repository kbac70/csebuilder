package org.cse.client;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.cse.client.i18n.MainPanelConstants;
import org.cse.client.utils.Validatable;
import org.cse.client.widgets.LocaleWidget;
import org.cse.client.widgets.TimedHTML;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SourcesChangeEvents;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * The Main panel is responsible for configuring and linking all application panels.
 * 
 * @author KBac
 *
 */
public class MainPanel extends Composite implements Validatable, 
		SourcesChangeEvents {
	
	protected final Application application;
	private final ChangeListenerCollection changeListeners;
    //UI
    private final Map panels;
    private final VerticalPanel content;
    private final MainPanelConstants constants;
    private final TimedHTML status;

    public MainPanel(Application application) { 
    	this.application = application;
    	this.changeListeners = new ChangeListenerCollection();
    	
        this.panels = new HashMap();
        this.content = new VerticalPanel();
        this.constants = application.getI18NFactory().newMainPanelConstants();
        this.status = new TimedHTML();
        
        this.initWidget(content);        
    }
    /**
     * Initialises the layout of the main window
     */
    public void init() {
        if (panels.size() != 0) {
            return;
        }

        Widget w = null;
        
        HorizontalPanel header = new HorizontalPanel();        
            w = new HTML(MainPanelConstants.CUSTOM_SEARCH_ENGINE_BUILDER);
        header.add(w);
        header.setCellHorizontalAlignment(w, HorizontalPanel.ALIGN_LEFT);
        header.setCellVerticalAlignment(w, HorizontalPanel.ALIGN_MIDDLE);
        header.setWidth("100%");
        HorizontalPanel h = new HorizontalPanel();
        	h.setSpacing(2);
        	h.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
            h.setStyleName(Styles.SMALLER_FONT);
            w = new HTML(constants.LICENCED_BY() +
            		"&nbsp;<a href=\"http://kbac70.blogspot.com\">KBac</a> &nbsp;");
            h.add(w);
            w = new HTML(
                    "&nbsp;<a rel='license' href='http://creativecommons.org/licenses/by-nc-sa/3.0/" +
                    this.constants.LICENSE_DEED_URL_SUFFIX() + "'>" +
                    "<img alt=\"Creative Commons License\" style=\"border-width:0\" src=\"http://creativecommons.org/images/public/somerights20.png\" />" +                    
                    "</a>");
            h.add(w);
            h.setCellVerticalAlignment(w, HorizontalPanel.ALIGN_TOP);
            w = new HTML("&nbsp;");
            h.add(w);
            w = new HTML(
            		"<form action=\"https://www.paypal.com/cgi-bin/webscr\" method=\"post\">" +
            		"<input type=\"hidden\" name=\"cmd\" value=\"_s-xclick\">" +
            		"<input type=\"image\" src=\"https://www.paypal.com/en_US/i/btn/x-click-but04.gif\" border=\"0\" name=\"submit\" alt=\"Buy KBac a beer\">" +
            		"<img alt=\"\" border=\"0\" src=\"https://www.paypal.com/en_GB/i/scr/pixel.gif\" width=\"1\" height=\"1\">" +
            		"<input type=\"hidden\" name=\"encrypted\" value=\"-----BEGIN PKCS7-----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-----END PKCS7-----" +
            		"\"></form>"
            		);
            h.add(w);
        header.add(h);
        header.setCellHorizontalAlignment(h, HorizontalPanel.ALIGN_RIGHT);
        header.setCellVerticalAlignment(h, HorizontalPanel.ALIGN_MIDDLE);
        header.setStyleName(Styles.HEADER);
        header.setSpacing(4);
        content.add(header);

        HorizontalPanel info = new HorizontalPanel();
        info.setWidth("100%");
        info.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
        info.add(status);
        info.setCellHorizontalAlignment(status, HorizontalPanel.ALIGN_LEFT);
            LocaleWidget lw = new LocaleWidget(application.getGWTExtender(), "csebuilder.html");
            lw.addLocale("en"); 
            lw.addLocale("pl");
            if (!lw.hasActiveLocale()) {
            	lw.activateLocale(application.getGWTExtender().getCurrentLocale(), "en");
            }
        info.add(lw);
        info.setCellHorizontalAlignment(lw, HorizontalPanel.ALIGN_RIGHT);
        content.add(info);

        content.setWidth("100%");
        content.setSpacing(4);  
        content.setStyleName(Styles.MAIN);
        content.setVerticalAlignment(VerticalPanel.ALIGN_TOP);
          
        addPanel(new TagProviderPanel(application));
        addPanel(new AnnotationsPanel(application));
        addPanel(new ContextPanel(application));
        
        status.setHTML(constants.CSE_BUILDER_DESC());
        status.setWidth("80%");
    }    
    /**
     * Utility allowing to genericaly add a panel to the main window
     * @param panel to add
     */
    protected final void addPanel(final AbstractPanel panel) {
        panel.initialize();
        
        panels.put(panel.getID(), panel);
        
        content.add(panel);
        content.setCellVerticalAlignment(panel, VerticalPanel.ALIGN_TOP);
        
        panel.addChangeListener(new ChangeListener() {
            public void onChange(Widget sender) {
                widgetChanged(sender);                
            }            
        });
    }
    
    public void setStatus(final String html) {
        this.status.setHTML(html);
    }    
    
    protected final AbstractPanel getPanel(String panelID) {
    	return (AbstractPanel)this.panels.get(panelID);
    }

	private void widgetChanged(Widget sender) {
		changeListeners.fireChange(sender);
	}

    //////////////////////////////////////////
    //Validatable implementation
    public void validate() {
        for (Iterator iter = panels.values().iterator(); iter.hasNext();) {
            Validatable panel = (Validatable) iter.next();
            panel.validate();
        }
    }
    //////////////////////////////////////////
    //SourcesChangeEvents implementation
	public void addChangeListener(ChangeListener listener) {
		changeListeners.add(listener);
		
	}
	public void removeChangeListener(ChangeListener listener) {
		changeListeners.remove(listener);		
	}


}

package org.cse.client.widgets;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;

/**
 * 
 * @author KBac
 *
 */
public class TimedHTML extends Composite {
    
    public static final String DEFAULT_STYLE_NAME = "timed-msg";
    public static final String HIGHLIGHT_STYLE_NAME = "timed-msg-higlight";
    public static final int DEFAULT_TIMEOUT = 2000;
    
    protected final HorizontalPanel panel;
    private final HTML html;
    private Timer timer;
    private String oldStyleName;
    private int timeoutMillis;    
    
    public TimedHTML() {
        this.panel = new HorizontalPanel();
        this.html = new HTML();
        
        this.panel.add(html);
        this.panel.setCellWidth(html, "100%");
        this.panel.setStyleName(DEFAULT_STYLE_NAME);
        this.html.setWordWrap(true);
        this.oldStyleName = "";
        this.timeoutMillis = DEFAULT_TIMEOUT;
        
        this.initWidget(panel);
    }
    
    public void setWidth(String width) {
        this.panel.setWidth(width);
    }
    
    public String getStyleName() {
        return this.panel.getStyleName();
    }
    
    public void setStyleName(String style) {
        this.panel.addStyleName(style);
    }
    
    public void removeStyleName(String style) {
        this.panel.removeStyleName(style);
    }
    
    public void setHTML(String html) {
        if (timer != null) {
            timer.cancel();
        }
        
        timer = new Timer() {
            public void run() {
                changeStyleName("");
            }            
        };
        timer.schedule(timeoutMillis);
        changeStyleName(HIGHLIGHT_STYLE_NAME);

        this.html.setHTML(html);
    }
    
    private void changeStyleName(String style) {
        if (oldStyleName.length() != 0) {
            removeStyleName(oldStyleName);
        }
        if (style.length() != 0) {
            setStyleName(style);
        }
        oldStyleName = style;
    }
    
    public String getHTML() {
        return this.html.getHTML();
    }

    public int getTimeoutMillis() {
        return timeoutMillis;
    }

    public void setTimeoutMillis(int timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
    }
    
    public int getSpacing() {
    	return this.panel.getSpacing();
    }
    
    public void setSpacing(int spacing) {
    	this.panel.setSpacing(spacing);
    }
}

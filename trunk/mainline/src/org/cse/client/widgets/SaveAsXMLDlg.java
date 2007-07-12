package org.cse.client.widgets;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Generic Window to allow end user persisting XML content using <i>Save Page As</i> 
 * browser command. It should be displayed on its own and have the XML object populated. 
 * The dialog assumes that the user has navigated to it using GWT item, which uses 
 * <code>{@link History}</code> subsystem like e.g. <code>{@link Hyperlink}</code> 
 * object does. 
 * <br/>
 * <br/>
 * The whole idea is based on the fact that the browser will persist text  
 * exposed by the currently visible DOM objects. So providing that your host page
 * does not include textual fields this widget will be able to control all the
 * displayable elements. <br/> 
 * Therefore this widget contains HTML elements, styled not to be displayed, 
 * which will blend their contents to form valid XML document while page gets saved. 
 * The whole trick is about inserting XML comments just before closing part of 
 * the root element. This way textual page content will end up within
 * XML comment and the whole page can be persisted a valid XML (that is if you 
 * do not mind some rubbish in comments;)  e.g. 
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8" ?&gt;
 * &lt;YourRootElem&gt;
 *     &lt;YourElem... /&gt;
 *     
 *     &lt;!-- 
 *     contents of the dialog goes here
 *     --&gt;
 * &lt;/YourRootElem&gt;
 * </pre>
 *
 * @author KBac
 *
 */
public class SaveAsXMLDlg extends Composite  {
 
	//comment must be enough as CSE parser does not like CDATA
	protected static final String LT = "&lt;!-- ";	//<!-- 
	protected static final String GT = " -->";		//-->
	
	private final DockPanel root;
	private final VerticalPanel content;
    private final HTML xml;
    private final HTML commentPre;
    private final HTML commentPost;
    
    /**
     * Class extensions to provide with relevant text value
     */
    protected final Button btnClose;
    /**
     * Class extensions to provide with relevant text value
     */    
    protected final HTML caption;
    /**
     * Class extensions to provide with relevant text value
     */    
    protected final HTML message;
    
    protected String lastElem;
    protected String lastElemXML;
    
    public SaveAsXMLDlg() {
    	root = new DockPanel();
    	content = new VerticalPanel();
    	xml = new HTML();
    	commentPre = new HTML(LT);    	
    	commentPost = new HTML(GT);
    	caption = new HTML(); 
    	message = new HTML();
    	btnClose = new Button("Close");
    	
    	root.setWidth("100%");
    	root.setHeight("100%");
    	
    	xml.setStyleName("make-invisible"); //display:none;
    	commentPre.setStyleName("make-invisible"); //display:none;
    	commentPost.setStyleName("make-invisible"); //display:none;
    	
    	content.setStyleName("save-xml-dlg");
    	caption.setStyleName("Caption");
    	
    	root.add(content, DockPanel.CENTER);
    	content.setSpacing(4);
    		content.add(xml);
    		content.add(commentPre);
    			HorizontalPanel titleBar = new HorizontalPanel(); 
    			titleBar.setStyleName("save-xml-dlg-header");
    			titleBar.add(caption);
    			titleBar.setCellHorizontalAlignment(caption, HorizontalPanel.ALIGN_LEFT);    			
    		content.add(titleBar);
    		content.add(new HTML("  "));
    		content.add(message);
    			HorizontalPanel btns = new HorizontalPanel();
    				btns.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
    				btnClose.addClickListener( new ClickListener(){
						public void onClick(Widget sender) {
							History.back(); //this is going to navigate back in the history stack
						}
    				});
    				btns.add(btnClose);
    				btns.add(commentPost);    				
    		content.add(btns);
    	
    	this.initWidget(root);
    }
    /**
     * 
     */
    protected final void checkHasRootElementEnd() {
    	if (lastElem == null) {
    		throw new RuntimeException("Unspecified XML root element end");
    	}
    }
    /**
     * Most of the documents, one deals with, are of known format therefore we can
     * predefine last element rather than extract it from XML itself
     * @param rootElementEnd e.g. <code>&lt;/YourRootElem&gt;</code>
     */
    public void setRootElementEnd(String rootElementEnd) {    	
  		//escape invalid xml chars    
    	int idxLT = rootElementEnd.indexOf("<");
    	int idxGT = rootElementEnd.indexOf(">");
    	
    	if (idxLT == -1 || idxGT == -1) {
    		throw new RuntimeException("Invalid rootElementEnd format");
    	}

    	this.lastElem = rootElementEnd;
    	this.lastElemXML = "&lt;" + rootElementEnd.substring(idxLT + 
    			"<".length(), idxGT) + "&gt;";

    	this.commentPost.setText(GT + rootElementEnd);
    }
    /**
     * Set XML to be saved using this method
     * @param xml
     */
    public void setXML(String xml) {
    	checkHasRootElementEnd();
    	int idx = xml.indexOf(lastElem);
    	if (idx != -1 ) {
    		this.xml.setText(xml.substring(0, idx));
    	} else {
    		throw new RuntimeException("Invalid root element end");
    	}
    }

}

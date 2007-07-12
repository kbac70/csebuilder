package org.cse.client.widgets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.cse.client.bkm.Tag;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.google.gwt.user.client.ui.Widget;

/**
 * Tag widget class.
 * 
 * @author KBac
 *
 */
public class TagWidget extends FocusWidget implements SourcesClickEvents {

	public static final String TAG = "tag";
	
	private final Tag tag;
	private final Element labelElem;
	private final List clickListeners;	
	private String styleName;
	
	public TagWidget(Tag tag) {
		super(DOM.createSpan());
		this.tag = tag;
		this.labelElem = DOM.createLabel();
		this.clickListeners = new ArrayList();
		
		Element e = getElement();

	    DOM.appendChild(e, labelElem);
		DOM.setInnerText(labelElem, tag.getName()+ "[" + tag.getCount() + "] ");
		DOM.setElementProperty(labelElem, "id", tag.getId());
		DOM.setElementProperty(labelElem, "class", "pointer");
		
		this.setStyleName(TAG);
        this.applyStyle();
        
		super.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
		        applyNextStyle();
		        fireOnClick(sender);				
			}			
		});
	}
    
    public Tag getTag() {
        return this.tag;
    }
    
    public void setInfo(int info) {
        this.tag.setState(info);
        applyStyle();
    }
    
    protected final void applyNextStyle() {
        this.tag.nextState();
        this.applyStyle();
    }

	private void applyStyle() {	
		String newStyleName = "";

		switch (tag.getState()) {
			case Tag.StateEnum.EXCLUDED : newStyleName = "excluded"; break;
			case Tag.StateEnum.INCLUDED : newStyleName = "included"; break; 
			default: newStyleName = "undefined"; break;
		}

		if (styleName != null) {
			this.removeStyleName(styleName);
		}
		
		styleName = newStyleName;
        this.addStyleName(styleName);       
	}
    
    public String getAdditionalStyleName() {
        return this.styleName;
    }

	private void fireOnClick(Widget sender) {
		for (Iterator iter = clickListeners.iterator(); iter.hasNext();) {
			final ClickListener listener = (ClickListener) iter.next();
			listener.onClick(sender);
		}		
	}

	public String getText() {
		return DOM.getInnerText(labelElem);
	}
	
	public void addClickListener(ClickListener listener) {
		this.clickListeners.add(listener);
	}
	
	public void removeClickListener(ClickListener listener) {
		this.clickListeners.remove(listener);
	}
} 

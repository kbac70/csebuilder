package org.cse.client.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.cse.client.bkm.Tag;
import org.cse.client.bkm.TagProvider;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SourcesChangeEvents;
import com.google.gwt.user.client.ui.Widget;

/**
 * Widget responsible for showing the cloud of TagWidgets
 * 
 * @author KBac
 *
 */
public class TagCloudWidget extends FlowPanel implements TagProvider, 
		SourcesChangeEvents {
    
	protected final Map tags;
	protected final List changeListeners;
    
    /**
     * @param tags <code>List&lt;{@link Tag}&gt;</code>
     */
    public TagCloudWidget(List tags) {
    	super();

        this.tags = new HashMap();
        this.changeListeners = new ArrayList();
    	
        for (int i = 0; i < tags.size(); i++) {
			Tag tag = (Tag) tags.get(i);
            this.tags.put(tag.getName(), tag);
            
            TagWidget w = new TagWidget(tag);
            w.addClickListener(
            		new ClickListener() {
            			public void onClick(Widget sender) {
            				fireWidgetChanged(sender);
            			}
            		}
            	);
			this.add(w);
		}   
    }
        
    protected void fireWidgetChanged(Widget sender) {
		for (Iterator iter = changeListeners.iterator(); iter.hasNext();) {
			ChangeListener listener = (ChangeListener) iter.next();
			listener.onChange(sender);
		}		
	}

	protected static Comparable newComparable(final int info) {
        return new Comparable() {
            public int compareTo(Object arg0) {
                return ((Integer)arg0).intValue() - info;
            }            
        };
    }

    public Map getIncluded(){
        return getSubset(newComparable(Tag.StateEnum.INCLUDED));
    }

    public Map getExcluded() {
        return getSubset(newComparable(Tag.StateEnum.EXCLUDED));
    }

    public Map getActive() {
        return getSubset(new Comparable() {
            public int compareTo(Object arg0) {
                final int info = ((Integer)arg0).intValue();
                return info != Tag.StateEnum.UNDEFINED? 0 : 1;
            }
        });
    }
    /**
     * @return total amount of tags within the cloud
     */
    public int getTagsSize() {
        return this.tags.size();
    }
    /**
     * Call this method to indicate all tags should be marked as excluded 
     */
    public void excludeAll() {
        setInfo(Tag.StateEnum.EXCLUDED);
    }
    
    private void setInfo(int info) {
        final int count = this.getWidgetCount(); 
        for (int i = 0; i < count; i++) {
            TagWidget widget = (TagWidget) this.getWidget(i);
            widget.setInfo(info);
        } 
        fireWidgetChanged(this);
    }
    /**
     * Call this method to indicate all tags should be marked as included 
     */
    public void includeAll() {
        setInfo(Tag.StateEnum.INCLUDED);
    }
    /**
     * Call this method to indicate all tags should be marked as undefined 
     */
    public void resetAll() {
        setInfo(Tag.StateEnum.UNDEFINED);        
    }
    
    protected final Map getSubset(Comparable infoChecker) {
        Map result = new HashMap();
        
        for (Iterator iter = tags.values().iterator(); iter.hasNext();) {
            Tag tag = (Tag) iter.next();
            if (infoChecker.compareTo(new Integer(tag.getState())) == 0) {
                result.put(tag.getName(), tag);
            }
        }
        
        return result;
    }

	public void addChangeListener(ChangeListener listener) {
		this.changeListeners.add(listener);		
	}

	public void removeChangeListener(ChangeListener listener) {
		this.changeListeners.add(listener);		
	}            	

      
}

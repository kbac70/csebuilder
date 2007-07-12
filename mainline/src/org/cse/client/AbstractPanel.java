package org.cse.client;

import java.util.ArrayList;
import java.util.List;

import org.cse.client.utils.Validatable;
import org.cse.client.widgets.TimedHTML;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SourcesChangeEvents;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Abstract panel is to contain widgets allowing end user to perform a single action
 * in the flow of the application.
 * 
 * @author KBac
 *
 */
public abstract class AbstractPanel extends Composite implements Validatable,
		SourcesChangeEvents {

    private final VerticalPanel content;
    private final TimedHTML status;    
    protected final boolean isInitialyOpen;
    protected final Application application;
    protected final HTML help;
	protected final List changeListeners;

    
    public AbstractPanel(final Application application, boolean isInitialyOpen) {
        this.application = application;     
        this.content = new VerticalPanel();        
        this.help = new HTML();
        this.status = new TimedHTML();
        this.changeListeners = new ArrayList();
        this.isInitialyOpen = isInitialyOpen;
        this.initWidget(content);
    }
    
    public void initialize() {
        if (content.getWidgetCount() != 0) {
            return;
        }
        
        init();
        
        DisclosurePanel panel = new DisclosurePanel(getName(), isInitialyOpen);
        	DockPanel drawer = new DockPanel();
        	Widget w = getContent();
        	drawer.add(w, DockPanel.CENTER);
        	drawer.setCellHeight(w, "100%");
        	drawer.add(status, DockPanel.SOUTH);
        	w = getHelp();
        	drawer.add(w, DockPanel.EAST);
        	drawer.setCellVerticalAlignment(w, DockPanel.ALIGN_MIDDLE);
        	drawer.setCellWidth(w, "15%");
        	drawer.setWidth("100%");
        	drawer.setHeight("100%");
        panel.setContent(drawer);
        
        content.add(panel);
        content.setSpacing(2);
        
        panel.setWidth("100%");
        content.setWidth("100%");
        
        status.setStyleName(Styles.STATUS);
        help.setStyleName(Styles.HELP_LAYOUT);
        
        validate();
    }
    
    protected final void updateStatus(final String html) {
    	this.status.setHTML(html);
    }
          
    protected Widget getHelp() {
        return help;
    }
    
	public void addChangeListener(ChangeListener listener) {
		this.changeListeners.add(listener);		
	}

	public void removeChangeListener(ChangeListener listener) {
		this.changeListeners.remove(listener);		
	}
	
    protected void addRow(Grid grid, int row, String caption, Widget w) {
        Label label = new Label(caption);
        label.setStyleName(Styles.LABEL_NOWRAP);
        addRow(grid, row, label, w);
    }
    
    protected void addRow(Grid grid, int row, Widget lhs, Widget rhs) {
        grid.setWidget(row, 0, lhs);        
        grid.setWidget(row, 1, rhs);
        grid.getCellFormatter().setHorizontalAlignment(row, 1, HasHorizontalAlignment.ALIGN_LEFT);
    }    
	/**
     * Implementation of fhis method is responsible for setting up the layout  
     *
	 */
    protected abstract void init();
    /**
     * @return the title of the panel
     */
    protected abstract String getName();
    /**
     * @return the <code>{@link Widget}</code> which is a container of all panel's
     * widgets.
     */
    protected abstract Widget getContent();
    /**
     * @return True when main action of the panel is executable, false otherwise
     */
    protected abstract boolean isEnabled();
    /**
     * @return String ID of the panel which is going to be used when storing the 
     * reference in the map of all panels
     */
	public abstract String getID();	
}

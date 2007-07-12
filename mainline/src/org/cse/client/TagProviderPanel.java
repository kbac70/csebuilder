package org.cse.client;

import java.util.Iterator;
import java.util.List;

import org.cse.client.bkm.AbstractResponseTextHandler;
import org.cse.client.i18n.TagProviderConstants;
import org.cse.client.utils.UIHelper;
import org.cse.client.widgets.TagCloudWidget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FormHandler;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormSubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormSubmitEvent;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Panel wrapping controls allowing end user to fetch tags and configure them
 * for further consumption.
 *  
 * 
 * @author KBac
 *
 */
public class TagProviderPanel extends AbstractPanel {
    
    private final Button btnFetch;
    
    private final TextBox txtUID;
    
    private final DockPanel body;
    
    private final TagProviderConstants constants;

    private final VerticalPanel content;
    
    private final FormPanel tagProvider; 

    private TagCloudWidget tagCloud;
	
	private boolean isFetching;	
    

    public TagProviderPanel(final Application application) {
        super(application, true);
        this.constants = application.getI18NFactory().newTagProviderConstants();
        this.txtUID = new TextBox();
        this.body = new DockPanel();
        this.content = new VerticalPanel();
        this.tagProvider = new FormPanel();
        this.tagProvider.addFormHandler(new FormHandler() {
            public void onSubmit(FormSubmitEvent event) {
                fetchTags();
                event.setCancelled(true);                
            }
            public void onSubmitComplete(FormSubmitCompleteEvent event) {}
        });
        this.btnFetch = new Button(constants.FETCH(), new ClickListener() {
            public void onClick(Widget sender) {
                fetchTags();
            }
        });
    }

    protected Widget getContent() {
        return this.content;
    }

    protected String getName() {
        return constants.TITLE();
    }

    protected void init() {
        if (this.content.getWidgetCount() != 0) {
            return;
        }
        
        content.setSpacing(4);
    
        Widget w = null;
        HorizontalPanel hp = null;
        HTML h = null;        
        
        content.setSpacing(4);
        content.setWidth("100%");        
            hp = new HorizontalPanel();
            hp.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);        
            w = new HTML(application.getBookmarkProvider().getSmallImageHtml() + 
                    "&nbsp;" + constants.USER_NAME() + ":&nbsp;");        
            hp.add(w);
            w = application.getUIHelper().makeNonEmptyTextBox(txtUID, application.getValidator());
            hp.add(w);
            hp.add(btnFetch);     
        tagProvider.add(hp);
        tagProvider.setHeight("30px");
        content.add(tagProvider);
            hp = new HorizontalPanel();
            hp.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);        
            hp.setSpacing(4);
            hp.setStyleName(Styles.SMALLER_FONT);
                h = new HTML(UIHelper.getJavaScriptURL(constants.INCLUDE_ALL()));
                h.addClickListener(new ClickListener() {
                    public void onClick(Widget sender) {
                        if (tagCloud != null){
                            tagCloud.includeAll();
                        }                  
                    }            
                });
            hp.add(h);        
                h = new HTML(UIHelper.getJavaScriptURL(constants.EXCLUDE_ALL()));
                h.addClickListener(new ClickListener() {
                    public void onClick(Widget sender) {
                        if (tagCloud != null) {
                            tagCloud.excludeAll();
                        }
                    }            
                });
            hp.add(h);
                h = new HTML(UIHelper.getJavaScriptURL(constants.RESET()));
                h.addClickListener(new ClickListener() {
                    public void onClick(Widget sender) {
                        if (tagCloud != null){
                            tagCloud.resetAll();
                        }
                    }            
                });
            hp.add(h);
        content.add(hp);        
            ScrollPanel sp = new ScrollPanel(body);
            sp.setStyleName(Styles.SCROLLER);
        content.add(sp);
        content.setCellHorizontalAlignment(sp, DockPanel.ALIGN_CENTER);
        content.setCellVerticalAlignment(sp, DockPanel.ALIGN_MIDDLE);  
        
        updateStatus(constants.READY());
    }

    protected boolean isInitialyOpen() {
        return true;
    }
    
    private void fetchTags() {
    	fetchTags(this.txtUID.getText());
    }

    /**
     * Call this method to fetch tag information for a provided user Id
     * @param userID 
     */
    protected final void fetchTags(String userID) {
        if (!txtUID.getText().equalsIgnoreCase(userID)) {
            txtUID.setText(userID);
        }
        
    	isFetching = true;
        updateStatus(UIHelper.getProgressHTML(constants.FETCHING_TAG_INFORMATION()));
    	
    	this.fireTagProviderChanged(new TagProviderChangedEvent(userID));
        
        application.getBookmarkProvider().fetchTags(userID, "", 
                new AbstractResponseTextHandler() {
            public void onCompletion(String responseText) {
                try {
                    List tags = this.getParser().getTags(responseText);

                    body.remove(tagCloud);
                    tagCloud = new TagCloudWidget(tags);
                    
                    body.add(tagCloud, DockPanel.CENTER);
                    body.setCellWidth(tagCloud, "100%");
                    body.setCellHeight(tagCloud, "100%");
                    body.setCellVerticalAlignment(tagCloud, DockPanel.ALIGN_MIDDLE);

                    String html = constants.RETRIEVED_TAGS(tagCloud.getTagsSize());
                    
                    updateStatus(UIHelper.getSuccessHTML(html)); 
                    
                    tagCloud.includeAll();
                    
                 } catch (Exception e) {
                    updateStatus(UIHelper.getErrorHTML(e.getLocalizedMessage()));
                    GWT.log("Error fetching tags. Check your network connection or " + 
                    		application.getBookmarkProvider().getName() + 
                    		" may be down.", e);

                 }
                 isFetching = false;
                 fireTagProviderChanged(new TagProviderChangedEvent(application.getUserId(), tagCloud));
            }
        });
    }
    private void fireTagProviderChanged(TagProviderChangedEvent event) {		
		for (Iterator iter = this.changeListeners.iterator(); iter.hasNext();) {
			ChangeListener listener = (ChangeListener) iter.next();
			listener.onChange(event);
		}		
	}

    public void validate() {
        btnFetch.setEnabled(!isFetching && txtUID.getText().length() != 0);   
        
        if (isFetching) {
        	help.setHTML(constants.HELP_FETCHING_TAGS());
        } else if (!btnFetch.isEnabled()) {
        	help.setHTML(constants.HELP_PROVIDE_UID());
        } else if (btnFetch.isEnabled() && application.getTagProvider() == null) {
        	help.setHTML(constants.HELP_FETCH_TAGS());
        } else {
        	help.setHTML(constants.HELP_SETUP_TAGS());
        }
    }

	public String getID() {
		return "TagProviderPanel";
	}
    
    protected boolean isEnabled() {
        return btnFetch.isEnabled();
    }

}

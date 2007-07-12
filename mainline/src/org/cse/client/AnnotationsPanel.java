package org.cse.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.cse.client.bkm.AbstractResponseTextHandler;
import org.cse.client.bkm.TagProvider;
import org.cse.client.i18n.AnnotationsConstants;
import org.cse.client.utils.CSE;
import org.cse.client.utils.UIHelper;
import org.cse.client.widgets.TextAreaControlsWidget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Panel wrapping controls allowing end user to fetch bookmarks and to be able 
 * to generate CSE annotations for further consumption.
 * 
 * @author KBac
 *
 */
public class AnnotationsPanel extends AbstractPanel {
    
	private final HorizontalPanel buttons;
    private final Button btnGenerate;
    private final AnnotationsConstants constants;
    private final VerticalPanel content;
    private final TextArea txtAnnotations;
    private final TextAreaControlsWidget textAreaControls;
	private final TextBox txtIncludedTagName;
	private final TextBox txtExcludedTagName;
    private CSE.Annotations annotations;
    private boolean isFetching;


    public AnnotationsPanel(final Application application) {
        super(application, false);
        this.constants = application.getI18NFactory().newAnnotationsConstants();
        this.content = new VerticalPanel();
        this.txtAnnotations = new TextArea();
        this.buttons = new HorizontalPanel();  
        this.txtIncludedTagName = new TextBox();
        this.txtExcludedTagName = new TextBox();
        this.btnGenerate = new Button(constants.GENERATE_ANNOTATION(), 
        		new ClickListener() {
            public void onClick(Widget sender) {
                generateAnnotations();
            }
        });        
        this.textAreaControls = new TextAreaControlsWidget(application, 
        		txtAnnotations, Application.HISTORY_TOKEN_ANNOTATIONS_DLG); 
    }

    protected Widget getContent() {
        return this.content;
    }

    protected String getName() {
        return constants.NAME();
    }

    protected void init() {
        if (this.content.getWidgetCount() != 0) {
            return;
        }
        content.setSpacing(4);
        txtIncludedTagName.setText("");
        txtExcludedTagName.setText("");
	        Grid config = new Grid(2,2);
	        config.setStyleName(Styles.CONFIG);
	        config.setWidth("100%");
	        config.getColumnFormatter().setWidth(1, "100%");            
	        addRow(config, 0, constants.CSE_BOOSTED(), application.getUIHelper().makeNonEmptyTextBox(txtIncludedTagName, this));
	        addRow(config, 1, constants.CSE_FILTERED(), application.getUIHelper().makeNonEmptyTextBox(txtExcludedTagName, this));
	    content.add(config);
        
        buttons.setSpacing(1);
        buttons.add(this.btnGenerate);
        
        content.add(buttons);
        content.add(textAreaControls);

        content.add(this.txtAnnotations);
        content.setCellHeight(this.txtAnnotations, "100%");
        
        txtAnnotations.setWidth("100%");
        txtAnnotations.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				application.getValidator().validate();
			}        	
        });

        txtIncludedTagName.setWidth("100%");
        txtExcludedTagName.setWidth("100%");
        content.setWidth("100%");
        content.setHeight("100%");    
    }

    protected boolean isInitialyOpen() {
        return false;
    }

    public void validate() {
        final TagProvider tagProvider = this.application.getTagProvider();
        final boolean hasTags = tagProvider != null && !tagProvider.getActive().isEmpty();
		
        txtIncludedTagName.setEnabled(!isFetching && hasTags);
		txtExcludedTagName.setEnabled(txtIncludedTagName.isEnabled());
		
		final boolean hasTagNames = txtIncludedTagName.getText().length() > 0 &&
				txtExcludedTagName.getText().length() > 0;
		
        btnGenerate.setEnabled(txtIncludedTagName.isEnabled() && hasTagNames);
        txtAnnotations.setEnabled(btnGenerate.isEnabled());
        if (!txtAnnotations.isEnabled()) {
            txtAnnotations.setText("");
        }
        
        textAreaControls.setVisible(txtAnnotations.getText().length() != 0);
        
        if (txtIncludedTagName.isEnabled() && !btnGenerate.isEnabled()) {
        	help.setHTML(constants.HELP_PROVIDE_TAG_LABELS());
        } else if (!btnGenerate.isEnabled()){
        	help.setHTML(constants.HELP_DEFINE_TAGS());
        } else if (txtAnnotations.isEnabled() && txtAnnotations.getText().length() == 0) {
        	help.setHTML(constants.HELP_GENERATE());
        } else if (!isFetching && txtAnnotations.isEnabled() && txtAnnotations.getText().length() != 0) {
        	help.setHTML(constants.HELP_COPYOUT());
        } 
    }
    
    protected void generateAnnotations(String included, String excluded) {
    	this.txtIncludedTagName.setText(included);
    	this.txtExcludedTagName.setText(excluded);
    	generateAnnotations();
    }
    
    protected void generateAnnotations() {
        updateStatus(UIHelper.getProgressHTML(constants.FETCHING_BOOKMARK_INFORMATION_USING_YAHOO_PIPES()));

        isFetching = true;
        this.fireAnnotationsChanged(new AnnotationsChangedEvent(true));
        
        fetchBookmarks();
    }

    private void fetchBookmarks() {
        final List includedTags = new ArrayList();
        includedTags.addAll(this.application.getTagProvider().getIncluded().values());
        final List excludedTags = new ArrayList();
        excludedTags.addAll(this.application.getTagProvider().getExcluded().values());
        
        this.application.getBookmarkProvider().fetchBookmarks(
                application.getUserId(), includedTags, excludedTags, 
                new AbstractResponseTextHandler() {
            public void onCompletion(String responseText) {
                try {
                    List bookmarks = this.getParser().getBookmarks(responseText, 
                            application.getTagProvider().getActive());
                    
                    String html = constants.RETRIEVED_BOOKMARKS(bookmarks.size());
                    updateStatus(UIHelper.getSuccessHTML(html));
                    
                    if (bookmarks.size() == 0) {
                        txtAnnotations.setText(responseText);
                        annotations = null;
                    } else {
                        annotations = application.getCSE().generateAnnotationInfo(
                        		bookmarks, 
                        		application.getTagProvider().getExcluded(),
                        		txtIncludedTagName.getText(),
                        		txtExcludedTagName.getText()
                        		);
                        txtAnnotations.setText(annotations.xml);
                    }
                 } catch (Exception e) {
                    updateStatus(UIHelper.getErrorHTML(e.getLocalizedMessage() + "<p>" + responseText));
                    GWT.log("Error fetching bookmarks. Check your network connection or YPipes might be down.", e);
                 }
                 
                 isFetching = false;
                 fireAnnotationsChanged(new AnnotationsChangedEvent(annotations));
            }            
        });
    }

	private void fireAnnotationsChanged(AnnotationsChangedEvent event) {
		for (Iterator iter = changeListeners.iterator(); iter.hasNext();) {
			ChangeListener listener = (ChangeListener) iter.next();
			listener.onChange(event);
		}
		
	}

	public String getID() {
		return "AnnotationsPanel";
	}

    protected boolean isEnabled() {
        return btnGenerate.isEnabled();
    }

}

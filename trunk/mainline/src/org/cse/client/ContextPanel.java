package org.cse.client;

import org.cse.client.bkm.TagProvider;
import org.cse.client.i18n.ContextPanelConstants;
import org.cse.client.utils.CSE;
import org.cse.client.widgets.TextAreaControlsWidget;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Panel wrapping controls allowing end user to compile CSE context based on the
 * tag and annotation information captured by other panels.
 * 
 * @author KBac
 *
 */
public class ContextPanel extends AbstractPanel {

	private final HorizontalPanel buttons;
    private final VerticalPanel content;
    private final ContextPanelConstants constants;
    private final TextBox txtTitle;
    private final TextArea txtDesc;
    private final Button btnGenerate;
    private final TextAreaControlsWidget textAreaControls;  
    private final CheckBox chkVolunteers;
    private final TextArea txtContext;
    private final CheckBox chKBackLbl; 
    private final TextBox txtBackLbl;

    public ContextPanel(Application application) {
        super(application, false);
        this.content = new VerticalPanel();
        this.constants = this.application.getI18NFactory().newContextPanelConstants();
        this.txtTitle = new TextBox();
        this.txtDesc = new TextArea();
        this.chkVolunteers = new CheckBox();
        this.chKBackLbl = new CheckBox();
        this.txtBackLbl = new TextBox();
        this.txtContext = new TextArea();
        this.buttons = new HorizontalPanel();
        this.btnGenerate = new Button(constants.GENERATE_CONTEXT(), new ClickListener() {
            public void onClick(Widget sender) {
                generateContext();
            }
        }); 
        this.textAreaControls = new TextAreaControlsWidget(application, 
        		txtContext, Application.HISTORY_TOKEN_CONTEXT_DLG);
        this.chKBackLbl.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                validate();
                
                if (!chKBackLbl.isChecked()) {
                    txtBackLbl.setText("");
                } else {
                	txtBackLbl.setFocus(true);
                }
            }
        });
        
        txtTitle.setEnabled(false);
    }

    protected final void generateContext() {
        CSE.ContextInfo context = new CSE.ContextInfo(
                application.getAnnotations().tags.values(), 
                application.getTagProvider().getExcluded(), 
                txtTitle.getText(), 
                txtDesc.getText(),
                chkVolunteers.isChecked(),
                txtBackLbl.getText()
            );
        generateContext(context);
    }
    
    protected final void generateContext(CSE.ContextInfo contextInfo) {        
        final CSE.Context ci = application.getCSE().generateContextInfo(contextInfo);                
        txtContext.setText(ci.xml);
        application.getValidator().validate();
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
        
        buttons.setSpacing(1);
        buttons.add(btnGenerate);
        
        content.setSpacing(4);
            Grid config = new Grid(4,2);
            config.setStyleName(Styles.CONFIG);
            config.setWidth("100%");
            config.getColumnFormatter().setWidth(1, "100%");            
            addRow(config, 0, constants.CSE_TITLE(), application.getUIHelper().makeNonEmptyTextBox(txtTitle, this));
            addRow(config, 1, constants.CSE_DESCRIPTION(), application.getUIHelper().makeNonEmptyTextBox(txtDesc, this));

            HorizontalPanel backLbl = new HorizontalPanel();
            backLbl.add(chKBackLbl);
            backLbl.add(txtBackLbl);
            backLbl.setSpacing(6);
            addRow(config, 2, "", backLbl);
            
            VerticalPanel vp = new VerticalPanel();
            vp.add(chkVolunteers);
            vp.setStyleName(Styles.CONFIG_CHK);
            addRow(config, 3, "", vp);        
            
        content.add(config);
        content.add(buttons);
        content.add(textAreaControls);  
        content.add(txtContext);
        
        chkVolunteers.setHTML(constants.VOLUNTEERS());
        chKBackLbl.setHTML(constants.BACK_LABEL());
        
        txtTitle.setWidth("100%");
        txtDesc.setWidth("100%");
        txtContext.setWidth("100%");
        content.setWidth("100%");
    }

    protected boolean isInitialyOpen() {
        return false;
    }

    public void validate() {
        final TagProvider tagProvider = this.application.getTagProvider(); 
        final boolean hasTags = tagProvider != null && !tagProvider.getActive().isEmpty();
        txtTitle.setEnabled(hasTags);
        txtDesc.setEnabled(hasTags);
        chkVolunteers.setEnabled(hasTags);
        
        final boolean hasAnnotations = hasTags && application.getAnnotations() != null;        
        btnGenerate.setEnabled(hasAnnotations && txtTitle.getText().length() != 0 && 
                txtDesc.getText().length() != 0);
        txtContext.setEnabled(btnGenerate.isEnabled());
        if (!txtContext.isEnabled()) {
            txtContext.setText("");
        }
        
        textAreaControls.setVisible(txtContext.getText().length() != 0);
        
        chKBackLbl.setEnabled(hasTags);
        txtBackLbl.setEnabled(chKBackLbl.isEnabled() && chKBackLbl.isChecked());
        
        if (!hasTags) {
        	help.setHTML(constants.HELP_NEED_TAGS());
        } else if (!hasAnnotations) {
        	help.setHTML(constants.HELP_NEED_ANNOTATIONS());
        } else if (txtContext.getText().length() == 0){
        	help.setHTML(constants.HELP_GENERATE());
        } else if (txtContext.getText().length() != 0) {
        	help.setHTML(constants.HELP_COPYOUT());
        }
        
    }

	public String getID() {
		return "ContextPanel";
	}

    protected boolean isEnabled() {
        return btnGenerate.isEnabled();
    }
    
    protected final void setContextInfo(CSE.ContextInfo context) {
        txtTitle.setText(context.title); 
        txtDesc.setText(context.description);
        chkVolunteers.setChecked(Boolean.valueOf(context.volunteers).booleanValue());
        validate();
    }

}

package org.cse.client.i18n;

import com.google.gwt.core.client.GWT;

/**
 * Factory for all contant instances as defined within i18n package.
 * 
 * @author KBac
 *
 */
public class I18NFactory {
	
	private MainPanelConstants uiControllerConstants;
	
	private UIHelperConstants uiHelperConstants;
    
    private TagProviderConstants tagProviderConstants;
    
    private AnnotationsConstants annotationsConstants;

    private ContextPanelConstants contextPanelConstants;
    
    private TextAreaControlsWidgetConstants textAreaControlsWidgetConstants;
    
    private SaveDlgConstants saveDlgConstants;
	
	/**
	 * @return new Instance of <code>{@link MainPanelConstants}</code>
	 */
	public MainPanelConstants newMainPanelConstants() {
		if (uiControllerConstants == null) { 
			uiControllerConstants = (MainPanelConstants) GWT.create(MainPanelConstants.class);
		}
		return uiControllerConstants;
	}
	/**
	 * @return new Instance of <code>{@link UIHelperConstants}</code>
	 */	
	public UIHelperConstants newUIHelperConstants() {
		if (uiHelperConstants == null) {
			uiHelperConstants = (UIHelperConstants) GWT.create(UIHelperConstants.class);
		}
		return uiHelperConstants;
	}
    /**
     * @return new Instance of <code>{@link TagProviderConstants}</code>
     */
    public TagProviderConstants newTagProviderConstants() {
        if (tagProviderConstants == null) {
            tagProviderConstants = (TagProviderConstants) GWT.create(TagProviderConstants.class);
        }
        return tagProviderConstants;
    }
    /**
     * @return new Instance of <code>{@link AnnotationsConstants}</code>
     */
    public AnnotationsConstants newAnnotationsConstants() {
        if (annotationsConstants == null) {
            annotationsConstants = (AnnotationsConstants) GWT.create(AnnotationsConstants.class);
        }
        return annotationsConstants;
    }
    /**
     * @return new Instance of <code>{@link ContextPanelConstants}</code>
     */    
    public ContextPanelConstants newContextPanelConstants() {
        if (contextPanelConstants == null) {
            contextPanelConstants = (ContextPanelConstants) GWT.create(ContextPanelConstants.class);
        }
        return contextPanelConstants;
    }
    /**
     * @return new Instance of <code>{@link TextAreaControlsWidgetConstants}</code>
     */ 
	public TextAreaControlsWidgetConstants newTextAreaControlsWidgetConstants() {
	       if (textAreaControlsWidgetConstants == null) {
	    	   textAreaControlsWidgetConstants = (TextAreaControlsWidgetConstants) GWT.create(TextAreaControlsWidgetConstants.class);
	        }
		return textAreaControlsWidgetConstants;
	}
    /**
     * @return new Instance of <code>{@link SaveDlgConstants}</code>
     */ 
	public SaveDlgConstants newSaveDlgConstants() {
	       if (saveDlgConstants == null) {
	    	   saveDlgConstants = (SaveDlgConstants) GWT.create(SaveDlgConstants.class);
	        }
		return saveDlgConstants;
	}
}

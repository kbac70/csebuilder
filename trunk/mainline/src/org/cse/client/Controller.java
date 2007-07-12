package org.cse.client;

import org.cse.client.bkm.BookmarkProvider;
import org.cse.client.bkm.TagProvider;
import org.cse.client.i18n.I18NFactory;
import org.cse.client.utils.CSE;
import org.cse.client.utils.CSEHelper;
import org.cse.client.utils.GWTExtender;
import org.cse.client.utils.UIHelper;
import org.cse.client.utils.Validatable;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * The controller provides with utilities which include:
 * {@link I18NFactory}, {@link UIHelper}, {@link Application} implementation
 *  and notifications via {@link ChangeListener}  
 * 
 * @author KBac
 *
 */
public class Controller extends Composite implements 
		Application, HistoryListener, Validatable {

    protected final SimplePanel root;
    protected final MainPanel main;
    protected final SaveAsDlg saveDlg;
    
    //Application support
    private final BookmarkProvider bookmarkProvider;
	private final I18NFactory i18nFactory;
	private final UIHelper uiHelper;
    private final GWTExtender gwtExtender;
	private final CSE cse;    
	private TagProvider tagProvider;
	private String uid;
	private CSE.Annotations annotations;
	//History support
    private Widget currentContent;
    
    public Controller(BookmarkProvider bookmarkProvider, GWTExtender gwtExtender) {
        this.root = new SimplePanel();
        
    	this.bookmarkProvider = bookmarkProvider;
        this.i18nFactory = new I18NFactory();
        this.uiHelper = new UIHelper(i18nFactory);
        this.gwtExtender = gwtExtender;
        this.cse = new CSEHelper();  
        this.uid = "";
        
        this.main = new MainPanel(this);
        this.saveDlg = new SaveAsDlg(this);
        this.saveDlg.setRootElementEnd(CSE.GOOGLE_CUSTOMIZATIONS_END_ELEM);
        
        History.addHistoryListener(this);
        
        this.initWidget(root);
    }
    /**
     * 
     */
    public void init() {
    	if (root.getWidget() != null) {
    		return;
    	}
    	
    	main.init();
    	main.addChangeListener( new ChangeListener() {
			public void onChange(Widget sender) {
				widgetChanged(sender);				
			}    		
    	});
    	
        // Show the initial screen.
        String initToken = History.getToken();
        if (initToken.length() == 0) {
        	initToken = Application.HISTORY_TOKEN_MAIN;
        }
        init(initToken);
    }
    /**
     * 
     * @param historyToken
     */
	private void init(String historyToken) {
		if (this.currentContent != null) {
			this.root.remove(currentContent);
		}
		
		if (historyToken.length() == 0 || 
				historyToken.equals(Application.HISTORY_TOKEN_MAIN)) {		
			currentContent = main;
		} else if (historyToken.equals(Application.HISTORY_TOKEN_ANNOTATIONS_DLG)){
			currentContent = saveDlg;
			saveDlg.show("annotations.xml");
		} else if (historyToken.equals(Application.HISTORY_TOKEN_CONTEXT_DLG)) {
			currentContent = saveDlg;
			saveDlg.show("context.xml");
		}
		
		root.add(currentContent);		
	}
	
    //////////////////////////////////////////
    //Validatable implementation	
    public void validate() {
    	main.validate();
    }	
    //////////////////////////////////////////
    //HistoryListener implementation	
	public void onHistoryChanged(String historyToken) {
		init(historyToken);		
	}	
    //////////////////////////////////////////
    //Application implementation
    public BookmarkProvider getBookmarkProvider() {
        return bookmarkProvider;
    }

    public I18NFactory getI18NFactory() {
        return i18nFactory;
    }

    public TagProvider getTagProvider() {
        return tagProvider;
    }
    
    private void setTagProvider(TagProvider tagProvider) {
    	if (this.tagProvider != tagProvider) {
        	this.tagProvider = tagProvider;
        	
        	validate();
    	}
    }

    public UIHelper getUIHelper() {
        return uiHelper;
    }

    public Validatable getValidator() {
        return this;
    }

    public void updateStatus(String html) {
        this.main.setStatus(html);        
    }

    public String getUserId() {
        return uid;
    }

    private void setUserId(String userId) {
        if (!uid.equalsIgnoreCase(userId)) {
            uid = userId;
            annotations = null;
            
            validate();
        }
    }

    public CSE.Annotations getAnnotations() {
        return annotations;
    }
    
    private void setAnnotations(CSE.Annotations annotations) {
    	if (this.annotations != annotations) {
    		this.annotations = annotations;
    		
    		validate();
    	}
    }

	protected final void widgetChanged(Widget sender) {
		if (sender instanceof TagProviderChangedEvent) {
			TagProviderChangedEvent event = (TagProviderChangedEvent)sender;
			this.setUserId(event.userID);
			this.setTagProvider(event.tagProvider);
			
		} else if (sender instanceof AnnotationsChangedEvent) {
			AnnotationsChangedEvent event = (AnnotationsChangedEvent)sender;			
			this.setAnnotations(event.annotations);
			
		}
	}
	
    public GWTExtender getGWTExtender() {
        return this.gwtExtender;
    }
    
	public CSE getCSE() {
		return this.cse;
	}
	
	public void showTextDlg(String contents) {
		this.saveDlg.setXML(contents);
	}
	

}

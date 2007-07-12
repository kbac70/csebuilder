package org.cse.client;

import org.cse.client.bkm.BookmarkProvider;
import org.cse.client.bkm.TagProvider;
import org.cse.client.i18n.I18NFactory;
import org.cse.client.utils.CSE;
import org.cse.client.utils.GWTExtender;
import org.cse.client.utils.UIHelper;
import org.cse.client.utils.Validatable;

/**
 * Interface defining all methods as required by the CSE-Builder application. 
 * In order to support low coupling, the application implementation should rely 
 * on notifications provided by the eventing system to obtain valid and up to 
 * date data (no setters are defined).
 * 
 * @author KBac
 *
 */
public interface Application {
	public static final String HISTORY_TOKEN_MAIN = "showMain";
	public static final String HISTORY_TOKEN_ANNOTATIONS_DLG = "annotations.xml";
	public static final String HISTORY_TOKEN_CONTEXT_DLG = "context.xml";

    /**
     * Each user id can in effect be used to fetch associated tags from a
     * <code>{@link BookmarkProvider}</code>. Therefore application is to expose 
     * current TagProvider instance which is created after fetching the Tags.
     * @return instance of <code>{@link TagProvider}</code> which is currently setup
     */
    public TagProvider getTagProvider();
    /**
     * @return instance of <code>{@link I18NFactory}</code> to be used by all the 
     * panels to obtain relevent localizable content
     */
    public I18NFactory getI18NFactory();
    /**
     * @return instance of <code>{@link Validatable}</code> which is responsible 
     * for the application validation
     */
    public Validatable getValidator();
    /**
     * @return instance of <code>{@link BookmarkProvider}</code> which is the 
     * service allowing the application component to fetch information about Tags 
     * and Bookmarks
     */
    public BookmarkProvider getBookmarkProvider();
    /**
     * @return instance of <code>{@link UIHelper}</code> serving here as global
     * UI utility provider
     */
    public UIHelper getUIHelper();    
    /**
     * @return <code>String</code> value of user id for whom the tags and bookmarks
     * are retrieved
     */   
    public String getUserId();
    /**
     * @return instance of <code>CSE.Annotations</code> which have been build for 
     * specified <code>{@link TagProvider}</code>
     */
    public CSE.Annotations getAnnotations();
    /**
     * @param html text which is to be used to update application's status bar
     */
    public void updateStatus(final String html);
    /**
     * @return instance of the class implementing <code>{@link GWTExtender}</code> interface
     */
    public GWTExtender getGWTExtender();
    /**
     * @return instance of <code>{@link CSE}</code> implementation
     */
    public CSE getCSE();
    /**
     * Shows the styled text page so that the user can save its contents.
     * @param contents
     */
    public void showTextDlg(String contents);
}

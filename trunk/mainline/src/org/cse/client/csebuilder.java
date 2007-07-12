package org.cse.client;

import org.cse.client.bkm.BookmarkProvider;
import org.cse.client.bkm.impl.Delicious;
import org.cse.client.utils.GWTExtender;
import org.cse.client.utils.GWTExtensions;
import org.cse.client.utils.SOPHelper;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Main Entry point point class where the configuration of the environment happens.
 * This includes:
 * <li> <code>{@link GWTExtensions}</code> to be implementation of the <code>{@link GWTExtender}</code>
 * <li> <code>{@link Delicious}</code> to be implementation of the <code>{@link BookmarkProvider}</code>
 * <li> <code>{@link SOPHelper}</code> to be implementation of the <code>JSONProvider</code>
 * 
 * @author KBac
 */
public class csebuilder implements EntryPoint {
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		//TODO IOC if ever required
		GWTExtender gwtExtender = new GWTExtensions();
		BookmarkProvider bookmarkProvider = new Delicious(new SOPHelper());        
		Controller controller = new Controller(bookmarkProvider, gwtExtender);
    
        controller.init();
        
        RootPanel.get("main").add(controller);
	}
}

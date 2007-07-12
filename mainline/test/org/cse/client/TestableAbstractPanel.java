/**
 * 
 */
package org.cse.client;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author KBac
 *
 */
class TestableAbstractPanel extends AbstractPanel {
	
	boolean isInitCalled = false;
	boolean isValidated = false;

	public TestableAbstractPanel(Application application) {
		super(application, true);
		// TODO Auto-generated constructor stub
	}

	protected Widget getContent() {
		Widget result = new HTML("");
		return result;
	}

	public String getID() {
		return "id";
	}

	protected String getName() {
		return "null";
	}

	protected void init() {
		isInitCalled = true;			
	}

	protected boolean isInitialyOpen() {
		// TODO Auto-generated method stub
		return false;
	}

	public void validate() {
		isValidated = true;			
	}

    protected boolean isEnabled() {
        return false;
    }
	
}
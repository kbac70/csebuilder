package org.cse.client;

import org.cse.client.i18n.SaveDlgConstants;
import org.cse.client.widgets.SaveAsXMLDlg;

/**
 * 
 * @author KBac
 *
 */
public class SaveAsDlg extends SaveAsXMLDlg {

	protected final Application application;
	protected final SaveDlgConstants constants;
	
	public SaveAsDlg(Application application) {
		this.application = application;		
		this.constants = application.getI18NFactory().newSaveDlgConstants();
		
		this.btnClose.setText(constants.CLOSE());
		this.caption.setHTML(constants.SAVES_TITLE());
	}
	
	public void show(String fileName) {
		this.message.setHTML(constants.MAIN_MSG(fileName));
	}
}

package org.cse.client.widgets;

import org.cse.client.Application;
import org.cse.client.i18n.TextAreaControlsWidgetConstants;
import org.cse.client.utils.UIHelper;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author KBac
 *
 */
public class TextAreaControlsWidget extends Composite {

	private static final String STYLENAME_SMALLER_FONT = "smaller-font";
	
	private final TextArea textArea;
	private final HorizontalPanel hp;
	private final Application application;
	
	public TextAreaControlsWidget(Application application, TextArea textArea, 
			String targetHistoryToken) {
		this.textArea = textArea;
		this.application = application;		
		this.hp = new HorizontalPanel();
		
		final TextAreaControlsWidgetConstants constants = 
			application.getI18NFactory().newTextAreaControlsWidgetConstants();
		
		hp.setSpacing(4);
		hp.setVerticalAlignment(HorizontalPanel.ALIGN_BOTTOM);		
		hp.addStyleName(STYLENAME_SMALLER_FONT);			
			HTML h = new HTML(UIHelper.getJavaScriptURL(constants.SELECT_ALL()));
			h.addClickListener( new ClickListener() {
				public void onClick(Widget sender) { selectAll(); }	
			});
		hp.add(h);
			Hyperlink hl = new Hyperlink();
			hl.setHTML(constants.OPEN_IN_NEW_WINDOW());
			hl.addClickListener(new ClickListener() {
				public void onClick(Widget sender) { openInNewWindonw(); }				
			});
			hl.setTargetHistoryToken(targetHistoryToken);
		hp.add(hl);

		this.initWidget(hp);
	}
	
	protected void openInNewWindonw() {	
		String textToDisplay = textArea.getSelectionLength() != 0 ?
				textArea.getSelectedText() : textArea.getText();
		application.showTextDlg(textToDisplay);
	}

	protected void selectAll() {
		if (textArea.isEnabled()) {
			textArea.selectAll();		
			textArea.setFocus(true);
		}
	}
}

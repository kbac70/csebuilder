package org.cse.client.utils;

import org.cse.client.i18n.I18NFactory;
import org.cse.client.i18n.UIHelperConstants;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * General UI utilities
 * 
 * @author KBac
 *
 */
public class UIHelper {
    
    private static final String WARNING = "warning";
	private static final String LABEL = "label";
	
	private final UIHelperConstants constants;
    
    public UIHelper(I18NFactory factory) {
        this.constants = factory.newUIHelperConstants();
    }
    
    public static final String IMG_ERROR = "exclamation.png";
    public static final String IMG_WARNING = "error.png";
    public static final String IMG_SUCCESS = "accept.png";
    public static final String IMG_PROGRESS = "pie.gif";
    public static final String IMG_INFO = "information.png";

    public static String getImgIntoText(String imgName, String altMsg) {
        return " <img src='" + imgName + "' class='image' alt='" + altMsg + "'> ";
    }    
    
    public static String getImgIntoText(String imgName) {
        return " <img src='" + imgName + "' class='image'> ";
    }    
    
    public static String getErrorHTML(String msg) {
        return getImgIntoText(IMG_ERROR) + msg;
    }
    
    public static String getProgressHTML(String msg) {
        return getImgIntoText(IMG_PROGRESS) + msg;
    }
    
    public static String getWarningHTML(String msg) {
        return getImgIntoText(IMG_WARNING) + msg;
    }
    
    public static String getSuccessHTML(String msg) {
        return  getImgIntoText(IMG_SUCCESS) + msg;
    }
    
    public static String getInfoHTML(String msg) {
        return getImgIntoText(IMG_INFO) + msg;
    }
    
    public static String getJavaScriptURL(String urlText) {
        return "<a href='javascript:;'>" + urlText + "</a>";
    }

    public HTML makeLabel(String caption) {
        HTML html = new HTML(caption);
        html.setStyleName(LABEL);
        return html;
    }
    
    public Widget makeValidatedTextBox(final TextBoxBase txtBox, final AbstractTextBoxValidator validator) {
        VerticalPanel p = new VerticalPanel();

        p.add(txtBox);
        p.setStyleName(WARNING);
        p.setHeight("40");
        p.setWidth("100%"); 
//        txtBox.setWidth("100%");//IE resizing
        
        if (validator != null) {
            p.add(validator.getEcho());
        }
        
        return p;
    }
    
    public Widget makeNonEmptyTextBox(final TextBoxBase txtBox, final Validatable validator) {
        final AbstractTextBoxValidator v = new AbstractTextBoxValidator(txtBox, null) {
            public void validate() {
                if (txtBox.isEnabled() && txtBox.getText().length() == 0) {
                    echo.setHTML(UIHelper.getWarningHTML(constants.TXT_CANNOT_BE_EMPTY()));
                } else {
                    echo.setHTML("");
                }
                if (validator != null) {
                	validator.validate();
                }
            }
        };
        
        v.validate();
        
        return makeValidatedTextBox(txtBox, v);
    }
    
    public Widget makeNumericTextBox(final TextBoxBase txtBox, final Validatable validator) {
        return makeValidatedTextBox(txtBox, new AbstractTextBoxValidator(txtBox) {
            public void validate() {
                final String txt = txtBox.getText();
                if (txtBox.isEnabled() && txt.length() != 0 && txt.matches("\\D+")) {
                    echo.setHTML(UIHelper.getWarningHTML(constants.TXT_NUMERIC_VALUES_ONLY()));
                } else {
                    echo.setHTML("");
                }
                if (validator != null) {
                	validator.validate();
                }
            }
        });
    }
}

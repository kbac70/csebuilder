/**
 * 
 */
package org.cse.client.utils;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.KeyboardListenerAdapter;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;

/**
 * Helper class encapsulating functionality resposible for validating TextBox.
 * 
 * @author KBac
 *
 */
public abstract class AbstractTextBoxValidator implements Validatable {
    
    protected final TextBoxBase txtBox;  
    protected final HTML echo;

    public AbstractTextBoxValidator(TextBoxBase b, HTML e) {
        txtBox = b;
    
        txtBox.addKeyboardListener(new KeyboardListenerAdapter() {
            public void onKeyUp(Widget sender, char keyCode, int modifiers) {
                if (sender == txtBox) {
                    validate();
                }
            }
        });
    
        txtBox.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                if (sender == txtBox) {
                    validate();
                }
            }
        });
        
        echo = e == null ? new HTML("") : e;
    }

    public AbstractTextBoxValidator(TextBoxBase b) {
        this(b, null);  
    }

    public HTML getEcho() {
        return echo;
    }

    public abstract void validate();  
}
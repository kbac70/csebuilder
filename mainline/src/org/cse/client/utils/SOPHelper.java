package org.cse.client.utils;

import com.google.gwt.user.client.ResponseTextHandler;

/**
 * <b>Same Origin Policy</b> helper class allowing to fetch JSON into the current site in
 * form of <code>JavaScript</code> element.
 * 
 * @author KBac
 *
 */
public class SOPHelper implements JSONProvider {     
	/**
	 * Constant defining common JSON callback name
	 */
    public static final String JSON_CALLBACK_NAME = "gwtJSONCallback";
    /**
     * Call this method to fetch XML from the site
     * @param url identifying the site to feed XML from
     * @param handler which is responsible for handling async request parsing it 
     */
    public void makeXMLRequest(String url, ResponseTextHandler handler) {
        makeJSONRequest(url, handler); 
    }

    public void makeJSONRequest(String url, ResponseTextHandler handler) {
        makeJSONRequest(url, "", handler);
    }
    /**
     * This is the heart of the helper written in <code>JSNDI</code> injecting the 
     * new script elements into DOM and managing their lifetime.
     * @param url
     * @param callbackName
     * @param handler
     */
    public static native void makeJSONRequest(String url, String callbackName, ResponseTextHandler handler) /*-{
        $wnd.gwtJSONCallback = function(jsonObj)
        {
            var jsonObjText = jsonObj.toJSONString (); // requires inclusion of <script type='text/javascript' src='http://www.json.org/json.js'></script>
            //TODO figure out how pass SOPHelper info as arg
            @org.cse.client.utils.SOPHelper::dispatchJSON(Ljava/lang/String;Lcom/google/gwt/user/client/ResponseTextHandler;)(jsonObjText, handler);
        }
         
        // create SCRIPT tag, and set SRC attribute equal to JSON feed URL + callback function name
        var script = $wnd.document.createElement("script");
        var urlCallBack = url + callbackName;
        script.setAttribute("src", urlCallBack);
        script.setAttribute("type", "text/javascript");
        script.setAttribute("id", urlCallBack);
        
        // maintain single callback script element
        var callbackElem = $wnd.document.getElementById(urlCallBack);
        if (callbackElem != null)
        {
            callbackElem.parentNode.removeChild(callbackElem);
        }
        $wnd.document.getElementsByTagName("head")[0].appendChild(script);
    }-*/;
    /**
     * This is the utility method which is going to be called after the response
     * has been fetched
     * @param jsonString which has been injected into DOM
     * @param handler to handle the jsonString
     */
    public static void dispatchJSON(String jsonString, ResponseTextHandler handler) {    
       handler.onCompletion(jsonString);
    }

}

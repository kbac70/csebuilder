package org.cse.client.utils;

/**
 * GWT extensions.
 * 
 * @author KBac
 *
 */
public class GWTExtensions implements GWTExtender {

    public String getCurrentLocale() {
        return getQueryStringValue(LOCALE_QRY_STRING);
    }

    /**
     * Window class extensions
     * 
     * @author KBac
     *
     */
    public static class Window {
        /**
         * Sets location value for the current window to the newLocation. 
         * <b>Warning:</b> This will browser navigate to the new location
         * @param newLocation
         */
    	public static native void setLocation(String newLocation) /*-{ 
	    	$wnd.location.href = newLocation;
	 	}-*/;
        /**
         * @return URL location of the current document 
         */
    	public static native String getLocation() /*-{
	    	return $wnd.location.href;
		}-*/;   	
        /**
         * @param paramName
         * @return value associated with the paramName provided, empty string returned 
         * when query string not found
         */
    	public static native String getQueryStringValue(String paramName) /*-{
		    var s = $wnd.location.href;
		
		    // find query string.
		    var i = s.indexOf('?');
		    if (i != -1) {
		      s = s.substring(i + 1, s.length);
		
		      var paramIdx = s.indexOf(paramName + "=");
		      i = s.indexOf('&');
		      if (i == -1) {
		        i = s.length;
		      }
		      s = s.substring(paramIdx + 7, i); 
		
		    } else { //default value
		      s = ""; 
		    }
		    
		    return s;
		}-*/;
    	/**
    	 * Open new window and write content into its new document    	 * 
    	 * @param contentType e.g. "text/html" for HTML content
    	 * @param content
    	 * 	    	//var d = w.document.open(contentType, 'replace');

    	 */
    	public static native void popupNewDocument(String contentType, String content)/*-{
	    	var w = window.open('','_blank');
	    	var d = w.document;
	    	d.write(content);
	    	d.close();
		}-*/;
    }

	public String getLocation() {
		return Window.getLocation();
	}

	public String getQueryStringValue(String paramName) {
		return Window.getQueryStringValue(paramName);
	}

	public void setLocation(String newLocation) {
		Window.setLocation(newLocation);		
	}

	public void popupNewDocument(String contentType, String content) {
		Window.popupNewDocument(contentType, content);		
	}

}

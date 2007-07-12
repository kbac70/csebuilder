package org.cse.client.utils;

/**
 * GWT extensions encapsulated for testability
 * 
 * @author KBac
 *
 */
public interface GWTExtender {
    /**
     * 
     */
    public static final String LOCALE_QRY_STRING = "locale";    
    /**
     * @return locale name defined as part of the URL query string if found,
     * empty string otherwise
     */
    public String getCurrentLocale();
    /**
     * @param paramName
     * @return value associated with the paramName provided, empty string returned 
     * when query string not found
     */
    public String getQueryStringValue(String paramName);
    /**
     * Sets location value for the current window to the newLocation. 
     * <b>Warning:</b> This will browser navigate to the new location
     * @param newLocation
     */
    public void setLocation(String newLocation);
    /**
     * @return URL location of the current document 
     */
    public String getLocation();
	/**
	 * Open new window and write content into its new document    	 * 
	 * @param contentType e.g. "text/html" for HTML content
	 * @param content
	 */
    public void popupNewDocument(String contentType, String content);

}

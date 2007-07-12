package org.cse.client.utils;

/**
 * 
 * @author KBac
 *
 */
public class GWTExtenderStub implements GWTExtender {

	private String locale = "";
	private String location = "";
	private String qryStringValue = "";
	
	public void setCurrentLocale(String newLocale) {
		this.locale = newLocale;
	}

	public String getCurrentLocale() {
		return locale;
	}

	public String getLocation() {
		return location;
	}

	public String getQueryStringValue(String paramName) {
		return qryStringValue;
	}

	public void popupNewDocument(String contentType, String content) {
	}

	public void setLocation(String newLocation) {
		location = newLocation;
	}

}

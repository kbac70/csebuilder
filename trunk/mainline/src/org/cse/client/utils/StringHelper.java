package org.cse.client.utils;


/**
 * String helper class.
 * 
 * @author KBac
 *
 */
public class StringHelper {

	/**
	 * Call this utility function to uncamel the camelCasedText. So from 
	 * "CamelCasedText" the return should be "Camel Cased Text"  
	 * @param camelCasedText text to be converted
	 * @return uncameled text value
	 */
	public static String uncamelCase(String camelCasedText) {
		return camelCasedText.replaceAll("([A-Z][a-z]+)", " $1").trim();
	}

    /**
     * Call this utility to skip trailing s hoping for the text to be in singular form
     * @param text to be made of singular form
     * @return text without single trailing s
     */
    public static String removePlural(String text) {
        int to = text.endsWith("s") ? text.length() - 1 : text.length();           
        return text.substring(0, to); //skip plurals       
    }

}

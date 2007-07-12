package org.cse.client.bkm.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cse.client.bkm.AbstractJSONResponseParser;
import org.cse.client.bkm.AbstractResponseTextHandler;
import org.cse.client.bkm.BookmarkProvider;
import org.cse.client.utils.JSONProvider;
import org.cse.client.utils.SOPHelper;
import org.cse.client.utils.UIHelper;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

/**
 * Del.icio.us <code>TagProvider</code> interface implementation.
 * 
 * @author KBac
 *
 */
public class Delicious implements BookmarkProvider {	

    public static final String DEL_ICIOU_US = "del.iciou.us";
	public static final String ROOT_URL = "http://del.icio.us";
    public static final String JSON_URL = ROOT_URL + "/feeds/json/";
    public static final String JSON_TAGS_URL = JSON_URL + "tags/";
    public static final String JSON_POSTS_URL = JSON_URL;
    public static final String RSS_URL = ROOT_URL + "/rss/";
    
    public static final String API_GET_TAGS = "https://api.del.icio.us/v1/tags/get";
    public static final String API_GET_ALLPOSTS = "https://api.del.icio.us/v1/posts/all";
    
    protected final JSONProvider jsonProvider;
    
    public Delicious(JSONProvider provider) {  	
    	this.jsonProvider = provider;
    }
    
    /**
     * JSON response parser class dedicated to parsing JSON format as provided 
     * by the Delicious
     *  
     * @author KBac
     *
     */
    public static class JSONResponseParser extends AbstractJSONResponseParser {

        protected List getBookmarks(JSONValue response, Map activeTags) {
            // TODO Auto-generated method stub
            return null;
        }
		protected List getTags(JSONValue response) {
			JSONObject o = response.isObject();
            
            List tags = new ArrayList();
            if ((o = response.isObject()) != null) {
                Set keys = o.keySet();
                for (Iterator iter = keys.iterator(); iter.hasNext();) {
                    String key = (String) iter.next(); 
                    JSONValue value = o.get(key);
                    tags.add(new org.cse.client.bkm.impl.TagImpl(key, new Double(value.isNumber().getValue()).intValue()));
                }
            };   
            Collections.sort(tags, new Comparator() {
				public int compare(Object o1, Object o2) {
					return ((TagImpl)o1).getName().compareTo(((TagImpl)o2).getName());
				}            	
            });
			return tags;
		}    	
    }    
    
	public void fetchTags (String uid, String pwd, AbstractResponseTextHandler handler) {
		if (uid.length() == 0 && pwd.length() == 0 ) {
			// rely on HTTPS channel
			//TODO
//			handler.setParser(new APITagsParser());			
//	   		SOPHelper.makeXMLRequest(API_GET_TAGS, handler);
			handler.onCompletion("");
		} else if (uid.length() != 0 && pwd.length() == 0) {
			handler.setParser(new JSONResponseParser()); 
	   		this.jsonProvider.makeJSONRequest(JSON_TAGS_URL + uid + "?raw&callback=" + 
	   				SOPHelper.JSON_CALLBACK_NAME
	   				, handler
	   				);			
		} else {
			//TODO
			handler.onCompletion("");
		}
    }
	
    public void fetchBookmarks(String uid, List includedTags, List excludedTags, AbstractResponseTextHandler handler) {
		if (uid.length() == 0 && includedTags.size() == 0 ) {
			// rely on HTTPS channel
			//TODO wait for delicious to deliver json or ypipes to allow https
//			handler.setParser(new APIPOostsParser());				
//	   		SOPHelper.makeXMLRequest(API_GET_ALLPOSTS, handler);
            handler.onCompletion(YPipe.EMPTY_RESPONSE);           
		} else if (uid.length() != 0 && includedTags.size() == 0) {
			//TODO 
			handler.onCompletion(YPipe.EMPTY_RESPONSE);			
		} else {
            //delegate to YPipe
			handler.setParser(new YPipe.JSONResponseParser()); 
	   		this.jsonProvider.makeJSONRequest(
	   				YPipe.buildURL(uid, includedTags, excludedTags) + 
	   				SOPHelper.JSON_CALLBACK_NAME
	   				, handler
	   				);			
		}
    }

    public String getName() {
        return DEL_ICIOU_US;
    }

	public String getSmallImageHtml() {
		return UIHelper.getImgIntoText("delicious.small.gif", getName());
	}
    

}

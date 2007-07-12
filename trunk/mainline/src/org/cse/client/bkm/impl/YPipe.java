package org.cse.client.bkm.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.cse.client.bkm.AbstractJSONResponseParser;
import org.cse.client.bkm.Tag;
import org.cse.client.utils.CSE;
import org.cse.client.utils.CSEHelper;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

/**
 * Interface allowing access of the Delicious RSS feeds using
 * multiple de-duped JSON streams 
 * 
 * @author KBac
 *
 */
public class YPipe {
	
	public static final String RSS_CSE_BUILDER_PIPE_URL = 
		"http://pipes.yahoo.com/pipes/pipe.run?_id=gq3rvUQJ3BGabq_fX0sBXw&_render=json&_run=1";
	
	public static final String JSON_CSE_BUILDER_PIPE_URL = 
		"http://pipes.yahoo.com/pipes/pipe.run?_id=nDSgHqcd3BGze11CJhOy0Q&_render=json&_run=1";

	public static final String CSE_BUILDER_PIPE_URL = JSON_CSE_BUILDER_PIPE_URL;
	
	public static final String EMPTY_RESPONSE = 
		"{\"count\":0,\"value\":{\"title\":\"CSE-Builder\",\"description\":\"Pipes Output\",\"link\":\"http://pipes.yahoo.com/pipes/pipe.info?_id=gq3rvUQJ3BGabq_fX0sBXw\",\"pubDate\":\"Thu, 24 May 2007 04:59:56 -0700\",\"generator\":\"http://pipes.yahoo.com/pipes/\",\"callback\":\"\",\"items\":[]}}";
	
	public static final String DUMMY_TAG = "cse-dummy-tag"; 
	
	public static final int MAX_TAGS = 10;
	
    /**
     * @param uid - user id
     * @param includedTags - <code>List&lt;{@link Tag}&gt;</code>
     * @param excludedTags - optional <code>List&lt;{@link Tag}&gt;</code>
     * @return URL to YPipe which is to return JSON containing Bookmarks as determined
     * by provided tag information
     */
	public static String buildURL(String uid, List includedTags, List excludedTags) {
		//due to limited amount of args for the pipe sort the list 
		//to push most scored high
		CSE cse = new CSEHelper();
        includedTags = cse.sortTagsCountDescending(includedTags);
        
        String tagToExclude = "";
        int initTagNo = 0;
        if (excludedTags != null && excludedTags.size() > 0) {
            //place single excluded and most scored tag as first tag on the list
            excludedTags = cse.sortTagsCountDescending(excludedTags);
            tagToExclude = "&tag1=" + ((TagImpl)excludedTags.get(0)).getName();
            initTagNo = 1;
        }
		
		String result = CSE_BUILDER_PIPE_URL;        
		result += "&uid=" + uid + tagToExclude;
        
		for (int i = 0, j = initTagNo + 1, max = MAX_TAGS - initTagNo;  i < max; i++, j++) {
            if (i < includedTags.size()) {
                TagImpl tag = (TagImpl) includedTags.get(i);
                result += "&tag" + j + "=" + tag.getName();
            } else {
                // fill missing tags with dummy placeholders
                result += "&tag" + j + "=" + DUMMY_TAG + j;;
            }
		}
		
		result +="&_callback=" ;
		return result;
	}

	public static class JSONResponseParser extends AbstractJSONResponseParser {

		protected List getRSSBookmarks(JSONValue response, Map activeTags) {
			final JSONObject jsonResponse = response.isObject();
			final JSONArray bookmarks = jsonResponse.get("value").isObject().get("items").isArray();

			List result = new ArrayList();
			if (jsonResponse.get("count").isNumber().getValue() > 0) {
				for (int i = 0; i < bookmarks.size(); i++) {
					JSONObject bookmark = bookmarks.get(i).isObject();
					String link = bookmark.get("link").isString().stringValue();
					String desc = bookmark.get("description").isString().stringValue();
					
					JSONArray jsonTags = bookmark.get("taxo:topics").isObject().get("rdf:Bag").isObject().get("rdf:li").isArray();
					List tags = new ArrayList();
                    
					for (int j = 0; j < jsonTags.size(); j++) {
						String tagName = jsonTags.get(j).isObject().get("resource").isString().stringValue();
                        tagName = tagName.substring(23);
                        
                        TagImpl tag = (TagImpl)activeTags.get(tagName);
                        if (tag == null) {
                            tag = new TagImpl(tagName, Tag.UNKNOWN_COUNT);
                        }
                        tags.add(tag);
					}
					
                    result.add(new BookmarkImpl(link, desc, tags));
				}
			}
			return result;
		}
		
		protected List getJSONBookmarks(JSONValue response, Map activeTags) {
			final JSONObject jsonResponse = response.isObject();
			final JSONArray bookmarks = jsonResponse.get("value").isObject().get("items").isArray();

			List result = new ArrayList();
			if (jsonResponse.get("count").isNumber().getValue() > 0) {
				for (int i = 0; i < bookmarks.size(); i++) {
					JSONObject bookmark = bookmarks.get(i).isObject();
					String link = bookmark.get("link").isString().stringValue();
					String desc = bookmark.get("title").isString().stringValue();
					
					JSONArray jsonTags = bookmark.get("tags").isArray();
					List tags = new ArrayList();
                    
					for (int j = 0; j < jsonTags.size(); j++) {
						String tagName = jsonTags.get(j).isString().stringValue();
                        TagImpl tag = (TagImpl)activeTags.get(tagName);
                        if (tag == null) {
                            tag = new TagImpl(tagName, Tag.UNKNOWN_COUNT);
                        }
                        tags.add(tag);
					}
					
                    result.add(new BookmarkImpl(link, desc, tags));
				}
			}
			return result;
		}
		

		protected List getTags(JSONValue response) {
			// TODO Auto-generated method stub
			return null;
		}

		protected List getBookmarks(JSONValue response, Map activeTags) {
			return this.getJSONBookmarks(response, activeTags);
		}
		
	}
}

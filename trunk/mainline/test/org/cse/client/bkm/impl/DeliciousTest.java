package org.cse.client.bkm.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.cse.client.AbstractCSETest;
import org.cse.client.bkm.AbstractResponseTextHandler;
import org.cse.client.bkm.ResponseParser;
import org.cse.client.bkm.impl.Delicious;
import org.cse.client.utils.JSONProvider;

import com.google.gwt.user.client.ResponseTextHandler;

/**
 * 
 * @author KBac
 *
 */
public class DeliciousTest extends AbstractCSETest {
	
	public static final String DELICIOUS_TAGS_ALL_JSON_FILENAME = "delicious.tags.all.json";
	public static final String DELICIOUS_BKMS_ALL_JSON_FILENAME = "delicious.posts.all.json";	
	public static final String UID = "uid";
	public static final String PWD = "";	
	
	public static final String JSON_TAGS = "{\"advice\":1,\"Agencies\":11,\".net\":39}";
	public static final String JSON_JSON_BMKS = "{\"count\":1,\"value\":" + "{\"title\":\"CSE-Builder\",\"description\":\"This pipe... \",\"link\":\"http:\\/\\/pipes.yahoo.com\\/pipes\\/pipe.info?_id=gq3rvUQJ3BGabq_fX0sBXw\",\"pubDate\":\"Thu, 24 May 2007 11:47:52 -0700\",\"generator\":\"http:\\/\\/pipes.yahoo.com\\/pipes\\/\",\"callback\":\"\",\"items\":[" + "{\"link\":\"http:\\/\\/KBac70.blogspot.com\\/\",\"y:repeatcount\":\"1\",\"u\":null,\"title\":\"BI Consultant.blogspot\",\"d\":null,\"tags\":[\"BI\",\"Blog\"],\"t\":null,\"description\":\"\",\"y:title\":\"\"}" + "]}}";

    public static final String JSON_RSS_BKMS = "{\"count\":1,\"value\":" +
        "{\"title\":\"CSE-Builder\",\"description\":\"This pipe... \",\"link\":\"http:\\/\\/pipes.yahoo.com\\/pipes\\/pipe.info?_id=gq3rvUQJ3BGabq_fX0sBXw\",\"pubDate\":\"Thu, 24 May 2007 11:47:52 -0700\",\"generator\":\"http:\\/\\/pipes.yahoo.com\\/pipes\\/\",\"callback\":\"\",\"items\":[" +
        "{\"link\":\"http:\\/\\/www.pracuj.pl\\/\",\"dc:date\":\"2007-05-17T09:55:01Z\",\"description\":\"\",\"y:repeatcount\":\"1\",\"dc:creator\":\"KBac70\",\"taxo:topics\":" +
        "{\"rdf:Bag\":" +
        "{\"rdf:li\":[" +
        "{\"resource\":\"http:\\/\\/del.icio.us\\/tag\\/name\"}," +
        "{\"resource\":\"http:\\/\\/del.icio.us\\/tag\\/excluded\"}" +
        "]}},\"rdf:about\":\"http:\\/\\/www.somesite.com\\/\",\"y:title\":\"Title\",\"dc:subject\":\"Subject\",\"title\":\"Title\",\"pubDate\":\"2007-03-09T13:24:27Z\",\"y:published\":" +
        "{\"hour\":\"21\",\"timezone\":\"UTC\",\"second\":\"27\",\"month\":\"3\",\"minute\":\"24\",\"utime\":\"1173475467\",\"day\":\"9\",\"day_of_week\":\"5\",\"year\":\"2007\"}" +
        "}]}}";
    public static final String JSON_BKMS = JSON_JSON_BMKS;

    
    
	public static class FileJSONProvider implements JSONProvider {
        private final String responseText;
		
		public FileJSONProvider(String responseText) {
			this.responseText = responseText;
		}
		
		public void makeJSONRequest(String url, ResponseTextHandler handler) {
            handler.onCompletion(responseText);
//            try {
//                File jsonFile = new File(
//                        this.getClass().getClassLoader().getResource("./res/" + fileName).toURI());
//                String jsonText = 
//                    new BufferedReader(new FileReader(jsonFile)).readLine();
//                handler.onCompletion(jsonText);
//            } catch (RuntimeException e) {
//                throw e;
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
		}		
	}
	
	
	public static Delicious newDelicious(JSONProvider provider) {
		Delicious result = new Delicious(provider);
		assertNotNull(result);
		return result;
	}
	
	public void testFetchTags() {
        ExecutableTest test = new ExecutableTest() {
			public void run() {
				Delicious d = newDelicious(new FileJSONProvider(JSON_TAGS));		
				d.fetchTags(UID, PWD, 
						new AbstractResponseTextHandler() {
							public void onCompletion(String responseText) {
                                assertNotNull("Response text cannot be NULL", responseText);
                                assertTrue("Response text must be of non zero length", responseText.length() > 0);
                                
								ResponseParser parser = this.getParser();
                                assertNotNull("Bookmark provider should assign a parser", parser);
                                
                                //test parsing
                                List tags = parser.getTags(responseText);
                                assertNotNull("Expecting non null list of tags", tags);                                
                                assertEquals("At least three tags expected", 3, tags.size());
                                
                                //test sorting
                                TagImpl tag = (TagImpl)tags.get(0);
                                assertEquals(".NET tag expected as the most scored", ".net", tag.getName());
							}
						}
				);				
			}
		};
		this.executeAsyncTest(test);
	}

	public void testFetchBookmarks() {
        ExecutableTest test = new ExecutableTest() {
			public void run() {
				Delicious d = newDelicious(new FileJSONProvider(JSON_BKMS));
                final List includedTags = TagImplTest.newTags(2);
                final List excludedTags = new ArrayList();

				d.fetchBookmarks(UID, includedTags,  
						excludedTags, new AbstractResponseTextHandler() {
							public void onCompletion(String responseText) {
                                assertNotNull("Response text cannot be null", responseText);
                                assertTrue("Response text must be of non zer length", responseText.length() > 0);
                                
                                ResponseParser parser = this.getParser();
                                assertNotNull("Bookmark provider must assign a parser", parser);
                                
                                Map activeTags = new HashMap();
                                for (Iterator iter = includedTags.iterator(); iter.hasNext();) {
									TagImpl tag = (TagImpl) iter.next();
									activeTags.put(tag.getName(), tag);
								}
                                
                                //test parsing
                                List bkms = parser.getBookmarks(responseText, activeTags);
                                assertNotNull("Non-null list of bookmarks expected", bkms);                                
                                assertEquals("Only 1 bookmark expected", 1, bkms.size());
                                
                                //test exclusion
                                excludedTags.add(new TagImpl("excluded", 111));
                                bkms = parser.getBookmarks(responseText, activeTags);
                                assertNotNull("Excluded bookmarks cannot be null", bkms);                                
                                assertEquals("Only 1 excluded bookmakr expected", 1, bkms.size());                                
							}
						}
				);
				
			}
		};
		this.executeAsyncTest(test);		
	}

	public void testGetName() {
		Delicious d = newDelicious(null);
		assertNotNull("Excepting non null provider name", d.getName());
		assertEquals(d.getName(), Delicious.DEL_ICIOU_US);
	}

}

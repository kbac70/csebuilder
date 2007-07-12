package org.cse.client.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cse.client.AbstractCSETest;
import org.cse.client.bkm.impl.BookmarkImplTest;
import org.cse.client.bkm.impl.TagImpl;
import org.cse.client.bkm.impl.TagImplTest;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;


/**
 * 
 * @author KBac
 *
 */
public class CSEHelperTest extends AbstractCSETest {	
    
    private static final String TEST_DESCRIPTION = "testDescription";
	private static final String TEST_TITLE = "testTitle";
	private static final String DUMMY_LABEL = "dummyLbl";
	private CSE cse;
	
	protected void setUp() throws Exception {
		super.setUp();
		cse = new CSEHelper(); //not triggered
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testGenerateAnnotationInfo() {
		List bookmarks = new ArrayList(); 

		bookmarks.add(BookmarkImplTest.newBookmark(TagImplTest.newTags(5)));
		bookmarks.add(BookmarkImplTest.newBookmark(TagImplTest.newTags(5)));
		
		Map excludedTags = new HashMap();
		
		CSE.Annotations annotation = cse.generateAnnotationInfo(bookmarks, 
				excludedTags, CSE.DEFAULT_TAG_INCLUDED, CSE.DEFAULT_TAG_EXCLUDED);
		assertTrue(annotation.xml.indexOf(CSE.GENERATOR) != -1);
		Document doc = XMLParser.parse(annotation.xml);
		
		NodeList nodes = doc.getElementsByTagName(CSE.ELEM_ANNOTATIONS);
		assertNotNull("Expecting ANNOTATIONS element", nodes);
		assertEquals(1, nodes.getLength());
		
		nodes = doc.getElementsByTagName(CSE.ELEM_ANNOTATION);
		assertNotNull("Expecting ANNOTATION element", nodes);
		assertEquals(2, nodes.getLength());
	}
	
	protected CSE.ContextInfo getContextInfo() {
		List tags = TagImplTest.newTags(5);		
		Map excludedTags = new HashMap();		
		return new CSE.ContextInfo(tags, excludedTags, TEST_TITLE, TEST_DESCRIPTION, false, DUMMY_LABEL);
	}

	public void testGenerateContextInfo() {		
		CSE.Context context = cse.generateContextInfo(getContextInfo());
		assertTrue(context.xml.indexOf(CSE.GENERATOR) != -1);

		Document doc = XMLParser.parse(context.xml);	
	
		NodeList nodes = doc.getElementsByTagName(CSE.ELEM_FACET);
		assertNotNull("Expecting FACET element", nodes);
		assertEquals(5, nodes.getLength());
		
//		nodes = doc.getElementsByTagName(CSE.ELEM_FACETITEM);
//		assertNotNull(nodes);
//		assertEquals(5, nodes.getLength());		
	}
	
	public void testSortTagsCountDescending() {
		List tags = new ArrayList();
		TagImpl tag1 = TagImplTest.newTag("a", 1);
		TagImpl tag2 = TagImplTest.newTag("a", 2);
		TagImpl tag3 = TagImplTest.newTag("a", 3);
		
		tags.add(tag1);
		tags.add(tag2);
		tags.add(tag3);
		assertEquals(tag1, tags.get(0));
		assertEquals(tag2, tags.get(1));
		assertEquals(tag3, tags.get(2));		
		cse.sortTagsCountDescending(tags);
		assertEquals(tag1, tags.get(2));
		assertEquals(tag2, tags.get(1));
		assertEquals(tag3, tags.get(0));		
	}

}

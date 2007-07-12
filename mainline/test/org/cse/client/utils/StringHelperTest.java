package org.cse.client.utils;

import org.cse.client.AbstractCSETest;

/**
 * 
 * @author KBac
 *
 */
public class StringHelperTest extends AbstractCSETest {

	abstract class AbstractCamelCaseTester {
		public void testUncamelCase() {
		    //test normal word
		    assertTrue("Tag name expected to equal: 'tag'", "tag".equalsIgnoreCase("tag"));
		
		    //test normal word
		    assertTrue("Tag name expected to equal: 'XXX'", "XXX".equalsIgnoreCase("XXX"));
		
		    //test camel cased tag name
		    final String[] words = uncamelCase("TagName").split(" ");
		    assertEquals(2, words.length);
		    assertTrue("Tag".equalsIgnoreCase(words[0]));
		    assertTrue("Name".equalsIgnoreCase(words[1]));
		}
		
		//allow testing different ways of un camel casing 
		protected abstract String uncamelCase(String camelCaseText);
	}
	

	public void testUncamelCase() {
		AbstractCamelCaseTester tester = new AbstractCamelCaseTester() {
			protected String uncamelCase(String camelCaseText) {
				return StringHelper.uncamelCase(camelCaseText);
			}			
		};
		tester.testUncamelCase();
	}


    public void testRemovePlural() {
        assertTrue(StringHelper.removePlural("Whitepapers").equalsIgnoreCase("Whitepaper"));
        assertTrue(StringHelper.removePlural("Whitepaperss").equalsIgnoreCase("Whitepapers"));
        assertTrue(StringHelper.removePlural("A").equalsIgnoreCase("A"));
    }
}

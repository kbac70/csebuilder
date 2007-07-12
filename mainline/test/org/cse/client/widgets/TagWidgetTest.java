package org.cse.client.widgets;

import org.cse.client.AbstractCSETest;
import org.cse.client.bkm.Tag;
import org.cse.client.bkm.impl.TagImpl;
import org.cse.client.bkm.impl.TagImplTest;

/**
 * 
 * @author KBac
 *
 */
public class TagWidgetTest extends AbstractCSETest {

	public static TagWidget newTagWidget(Tag tag) {
		TagWidget result = new TagWidget(tag);
		assertNotNull(result);
		return result;	}

	
	public static TagWidget newTagWidget() {
		return newTagWidget(TagImplTest.newTag());
	}

	public void testGetTag() {
		TagWidget w = newTagWidget();
		assertNotNull(w.getTag());
		assertEquals(TagImplTest.NAME, w.getTag().getName());
	}

	public void testSetInfo() {
		TagImpl tag = TagImplTest.newTag();
		assertEquals(Tag.StateEnum.UNDEFINED, tag.getState());

		TagWidget tw = newTagWidget(tag);
		tw.setInfo(Tag.StateEnum.EXCLUDED);
		assertEquals(Tag.StateEnum.EXCLUDED, tag.getState());		
	}

	public void testGetText() {
		TagImpl tag = TagImplTest.newTag();
		assertEquals(Tag.StateEnum.UNDEFINED, tag.getState());

		TagWidget tw = newTagWidget(tag);
		String s = tw.getText();
		assertEquals(tag.getName()+ "[" + tag.getCount() + "] ", s);
	}

    public void testGetAdditionalStyleName() {
        TagImpl tag = TagImplTest.newTag();
        assertEquals(Tag.StateEnum.UNDEFINED, tag.getState());

        TagWidget tw = newTagWidget(tag);       
        assertEquals("tag", tw.getStyleName());        
    }
	
	public void testApplyNextStyle() {
		TagImpl tag = TagImplTest.newTag();
		assertEquals(Tag.StateEnum.UNDEFINED, tag.getState());

		TagWidget tw = newTagWidget(tag);		
        assertEquals("tag", tw.getStyleName());
        String s = tw.getAdditionalStyleName();
        assertTrue(s.startsWith("undefined"));
		tw.applyNextStyle();
        assertEquals("tag", tw.getStyleName());
		s = tw.getAdditionalStyleName();
		assertTrue(s.startsWith("included"));
		tw.applyNextStyle();
        assertEquals("tag", tw.getStyleName());
		s = tw.getAdditionalStyleName();
		assertTrue(s.startsWith("excluded"));		
		tw.applyNextStyle();
        assertEquals("tag", tw.getStyleName());
		s = tw.getAdditionalStyleName();		
		assertTrue(s.startsWith("undefined"));		
	}
    

}

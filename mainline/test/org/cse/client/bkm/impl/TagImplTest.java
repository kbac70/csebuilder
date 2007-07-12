package org.cse.client.bkm.impl;


import java.util.ArrayList;
import java.util.List;

import org.cse.client.AbstractCSETest;
import org.cse.client.bkm.Tag;


/**
 * 
 * @author KBac
 *
 */
public class TagImplTest extends AbstractCSETest {
    	
	public static final String NAME = "name";
    public static final int COUNT = 111;

    public void testGetNameInitializedOk() {
		TagImpl tag = TagImplTest.newTag();
		assertEquals(TagImplTest.NAME, tag.getName());
	}

	public void testGetStateInitializedOk() {
		TagImpl tag = TagImplTest.newTag();
		assertEquals(Tag.StateEnum.UNDEFINED, tag.getState());		
	}

	public void testSetStateInitializedAndAssignsValues() {
		TagImpl tag = TagImplTest.newTag();
		assertEquals(Tag.StateEnum.UNDEFINED, tag.getState());		
		tag.setState(Tag.StateEnum.EXCLUDED);
		assertEquals(Tag.StateEnum.EXCLUDED, tag.getState());		
	}

	public void testNextStateIsCyclingStateValues() {
		TagImpl tag = TagImplTest.newTag();
		assertEquals(Tag.StateEnum.UNDEFINED, tag.getState());
		tag.nextState();
		assertEquals(Tag.StateEnum.INCLUDED, tag.getState());
		tag.nextState();
		assertEquals(Tag.StateEnum.EXCLUDED, tag.getState());
		tag.nextState();
		assertEquals(Tag.StateEnum.UNDEFINED, tag.getState());		
	}

	public void testGetIdAlgoWorking() {
		TagImpl tag = TagImplTest.newTag();
		final String ID = TagImplTest.NAME + TagImplTest.COUNT;
		assertEquals(ID, tag.getId());		
	}

	public void testGetCountInitializedOk() {
		TagImpl tag = TagImplTest.newTag();
		assertEquals(TagImplTest.COUNT, tag.getCount());
	}

	public void testGetSearchTextInitializedOk() {
		TagImpl tag = TagImplTest.newTag();
		assertNotNull(tag.getSearchText());
	}

    public static TagImpl newTag() {
    	return newTag(NAME);
    }

    public static TagImpl newTag(String name) {
    	return newTag(name, COUNT);
    }

    public static TagImpl newTag(String name, int count) {
    	TagImpl result = new TagImpl(name, count);
    	assertNotNull(result);
    	return result;
    }

    public static List newTags(final int size) {
    	List result = new ArrayList();
    	
    	for (int i = 0; i < size; i++) {
    		result.add(TagImplTest.newTag(NAME + i));
    	}
    	
    	assertNotNull(result);
    	assertEquals(result.size(), size);
    	
    	return result;
    }

}

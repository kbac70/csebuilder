package org.cse.client.widgets;

import java.util.List;
import java.util.Map;

import org.cse.client.AbstractCSETest;
import org.cse.client.bkm.Tag;
import org.cse.client.bkm.impl.TagImpl;
import org.cse.client.bkm.impl.TagImplTest;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author KBac
 *
 */
public class TagCloudWidgetTest extends AbstractCSETest{
    
    public static TagCloudWidget newTagCloudWidget(List tags) {
        TagCloudWidget result = new TagCloudWidget(tags);
        assertNotNull(result);
        return result;
    }

	public void testTagCloudWidget() {
        TagCloudWidget w = newTagCloudWidget(TagImplTest.newTags(10));
        assertNotNull(w.tags);
        assertEquals(10, w.tags.size());
        
        try {
            w = newTagCloudWidget(null);
            fail("TagCloudWidget does not tolerate null list of tags in ctor");
        } catch (NullPointerException e) {
            //ok
        }

	}
    
    public void testGetTagsSize() {
        TagCloudWidget w = newTagCloudWidget(TagImplTest.newTags(10));
        assertNotNull(w.tags);
        assertEquals(10, w.tags.size());
        assertEquals(10, w.getTagsSize());
    }
    
    private List getTags() {
        List result = TagImplTest.newTags(10);
        
        for (int i = 0; i < result.size(); i++) {
            final Tag tag = (Tag)result.get(i);
            if (i <= 3) {
                tag.setState(Tag.StateEnum.INCLUDED);                
            } else if (i <= 6) {
                tag.setState(Tag.StateEnum.EXCLUDED);
            } else {
                tag.setState(Tag.StateEnum.UNDEFINED);
            }
        }
        
        return result;
    }
    
    public void testNewComparable() {
        Comparable c = TagCloudWidget.newComparable(3);
        assertNotNull(c);
        assertEquals(0, c.compareTo(new Integer(3)));
        assertTrue(c.compareTo(new Integer(4)) > 0);
        assertTrue(c.compareTo(new Integer(2)) < 0);
    }
    
    public void testGetSubset() {
        TagCloudWidget w = newTagCloudWidget(getTags());
        Map m = null;
        
        m = w.getSubset(TagCloudWidget.newComparable(Tag.StateEnum.INCLUDED));
        assertNotNull(m);
        assertEquals(4, m.size());
        m = w.getSubset(TagCloudWidget.newComparable(Tag.StateEnum.EXCLUDED));
        assertNotNull(m);
        assertEquals(3, m.size());
        m = w.getSubset(TagCloudWidget.newComparable(Tag.StateEnum.UNDEFINED));
        assertNotNull(m);
        assertEquals(3, m.size());        
    }

    
    interface TagManager {
        Map getTags(TagCloudWidget w);
        boolean expectedInfoOk(int info);
        int expectedSize();
        boolean shouldTraceClick();
    }

    private void doTestTags(TagManager l) {
        TagCloudWidget w = newTagCloudWidget(getTags());
        assertNotNull(w.tags);
        assertEquals("Unexpected test map size", 10, w.tags.size());

        if (l.shouldTraceClick()) {
            w.addChangeListener(new ChangeListener() {
                public void onChange(Widget sender) {
                    assertTrue(sender instanceof TagCloudWidget);
                }                
            });
        }
        Map m = l.getTags(w);
        assertNotNull("Map must not be null", m);
        assertTrue("Map must not be empty", m.size() > 0);        
        assertTrue("Elements of unexpected type", l.expectedInfoOk(((TagImpl)m.values().iterator().next()).getState()));                
        assertEquals("Unexpected map size", l.expectedSize(), m.size());
    }

	public void testGetIncluded() {
        doTestTags(new TagManager() {
            public boolean expectedInfoOk(int info) { return info == Tag.StateEnum.INCLUDED; }
            public Map getTags(TagCloudWidget w) { return w.getIncluded();}
            public int expectedSize() { return 4; }
            public boolean shouldTraceClick() { return false; }            
        });
	}    

	public void testGetExcluded() {
        doTestTags(new TagManager() {
            public boolean expectedInfoOk(int info) { return info == Tag.StateEnum.EXCLUDED; }
            public Map getTags(TagCloudWidget w) { return w.getExcluded();}
            public int expectedSize() { return 3; }
            public boolean shouldTraceClick() { return false; }
        });
	}
    
    public void testGetActive() {
        doTestTags(new TagManager() {
            public boolean expectedInfoOk(int info) { return info != Tag.StateEnum.UNDEFINED; }
            public Map getTags(TagCloudWidget w) { return w.getActive();}
            public int expectedSize() { return 7; }
            public boolean shouldTraceClick() { return false; }            
        });
    }

	public void testExcludeAll() {
        doTestTags(new TagManager() {
            public boolean expectedInfoOk(int info) { return info == Tag.StateEnum.EXCLUDED; }
            public Map getTags(TagCloudWidget w) { w.excludeAll(); return w.tags;}
            public int expectedSize() { return 10; }
            public boolean shouldTraceClick() { return true; }
        });
	}

	public void testIncludeAll() {
        doTestTags(new TagManager() {
            public boolean expectedInfoOk(int info) { return info == Tag.StateEnum.INCLUDED; }
            public Map getTags(TagCloudWidget w) { w.includeAll(); return w.tags;}
            public int expectedSize() { return 10; }
            public boolean shouldTraceClick() { return true; }
        });
	}

	public void testResetAll() {
        doTestTags(new TagManager() {
            public boolean expectedInfoOk(int info) { return info == Tag.StateEnum.UNDEFINED; }
            public Map getTags(TagCloudWidget w) { w.resetAll(); return w.tags;}
            public int expectedSize() { return 10; }
            public boolean shouldTraceClick() { return true; }
        });
	}

}

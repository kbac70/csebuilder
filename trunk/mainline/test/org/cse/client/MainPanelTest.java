package org.cse.client;

import java.util.HashMap;

import org.cse.client.bkm.BookmarkProviderStub;
import org.cse.client.bkm.TagStub;
import org.cse.client.utils.CSE;
import org.cse.client.utils.GWTExtenderStub;


/**
 * 
 * @author KBac
 *
 */
public class MainPanelTest extends AbstractCSETest {
	
	protected static MainPanel newMainPanel() {
		Controller controller = new Controller(new BookmarkProviderStub(),
				new GWTExtenderStub());
		controller.init();
		
		assertNotNull(controller);
		assertNotNull(controller.main);
		
		return controller.main;
	}
	
	public void testValidate() {
		final MainPanel controller = newMainPanel();
		final TestableAbstractPanel panel = new TestableAbstractPanel(new ApplicationDummy());
        
        assertFalse(panel.isValidated);
		controller.addPanel(panel);		
		assertTrue(panel.isValidated);
		
        panel.isValidated = false;
		controller.validate();
		assertTrue(panel.isValidated);		
	}
    
    abstract class UIStateChain {
        
        private AbstractPanel panel;
        protected TagProviderPanel tp;
        protected AnnotationsPanel ap;
        protected ContextPanel cp;
        protected final UIStateChain prevState;        
        protected final MainPanel main;
        private boolean isInited;
        
        public UIStateChain(UIStateChain prevState) {
            
            this.prevState = prevState;
            if (prevState != null) {
                prevState.initPanels();
                main = prevState.main; 
            } else {
                main = newMainPanel();
                main.init();
            }
        }

		protected void initPanels(){           
        	if (isInited) {
        		return;
        	}
        	isInited = true;
        	
            panel = main.getPanel("TagProviderPanel");      
            assertNotNull(panel);
            tp = (TagProviderPanel)panel;
            
            panel = main.getPanel("AnnotationsPanel");
            assertNotNull(panel);
            ap = (AnnotationsPanel)panel;
            
            panel = main.getPanel("ContextPanel");
            assertNotNull(panel);       
            cp = (ContextPanel)panel;
        }
        
        public void testTransitions() {
            initPanels();            
            
            checkTagProviderPanel();
            checkAnnoationsPanel();
            checkContextPanel();
        }
        protected abstract void checkTagProviderPanel();
        protected abstract void checkAnnoationsPanel();        
        protected abstract void checkContextPanel();
    }
    
    class Initialized extends UIStateChain {        
        public Initialized() {
            super(null);
        }
        protected void checkTagProviderPanel() {
            assertTrue(tp.isInitialyOpen);
            assertFalse(tp.isEnabled());             
        }
        protected void checkAnnoationsPanel() {
            assertFalse(ap.isInitialyOpen);
            assertFalse(ap.isEnabled());                
        }         
        protected void checkContextPanel() {
            assertFalse(cp.isInitialyOpen);
            assertFalse(cp.isEnabled());               
        }       
    }

	public void testPanelsInitedOk() {
        new Initialized().testTransitions();
	}
    
    class TagsFetched extends UIStateChain {
        public TagsFetched() {
            this(new Initialized());
        }
        public TagsFetched(UIStateChain prevState) {
            super(prevState);
        }
        protected void checkTagProviderPanel() {
            this.prevState.checkTagProviderPanel();
            
            final String uid = "blah";
            assertNull(main.application.getTagProvider());
            
            tp.fetchTags(uid);
            
            assertTrue(tp.isEnabled());
            assertSame(uid, main.application.getUserId());
            assertNotNull(main.application.getTagProvider());                
        }
        protected void checkAnnoationsPanel() {            
            assertNull(main.application.getAnnotations());
            
        }
        protected void checkContextPanel() {
            assertFalse(cp.isEnabled());
            
        }   
    }
	
	public void testFetchTags() {
        new TagsFetched().testTransitions();        
	}
	
    class AnnotationsGenerated extends UIStateChain {
        public AnnotationsGenerated() {
            this(new TagsFetched());
        }
        public AnnotationsGenerated(UIStateChain prevState) {
            super(prevState);
        }
        protected void checkTagProviderPanel() {
            this.prevState.checkTagProviderPanel();

            assertNotNull(main.application.getTagProvider());
            assertTrue(tp.isEnabled());
        }
        protected void checkAnnoationsPanel() {
            this.prevState.checkAnnoationsPanel();
            
            ap.generateAnnotations(CSE.DEFAULT_TAG_INCLUDED, CSE.DEFAULT_TAG_EXCLUDED);
            
            assertNotNull(main.application.getUserId());
            assertTrue(ap.isEnabled());
            assertNotNull(main.application.getAnnotations());
            
        }
        protected void checkContextPanel() {
            this.prevState.checkContextPanel();
            
            assertNotNull(main.application.getAnnotations());
            assertFalse(cp.isEnabled());
            
        }   
    }

	public void testGenerateAnnotations() {
	    new AnnotationsGenerated().testTransitions();
	}
    
    class ContextGenerated extends UIStateChain {
        public ContextGenerated() {
            this(new AnnotationsGenerated());
        }
        public ContextGenerated(UIStateChain prevState) {
            super(prevState);
        }
        protected void checkTagProviderPanel() {
            this.prevState.checkTagProviderPanel();

            assertNotNull(main.application.getTagProvider());
            assertTrue(tp.isEnabled());
        }
        protected void checkAnnoationsPanel() {
            this.prevState.checkAnnoationsPanel();
           
            assertNotNull(main.application.getUserId());
            assertTrue(ap.isEnabled());
            assertNotNull(main.application.getAnnotations());
            
        }
        protected void checkContextPanel() {
            this.prevState.checkContextPanel();
            
            assertNotNull(main.application.getAnnotations());
            assertFalse(cp.isEnabled());
            
            CSE.ContextInfo ci = new CSE.ContextInfo(
                    TagStub.newTags(2), 
                    new HashMap(), 
                    "title", 
                    "description", 
                    false,
                    "label");
            cp.setContextInfo(ci);
            assertTrue(cp.isEnabled());
            
            cp.generateContext();
            
        }   
    }    

	public void testGenerateContext() {
		new ContextGenerated().testTransitions();
	}


}

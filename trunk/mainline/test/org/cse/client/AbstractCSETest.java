package org.cse.client;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Timer;

/**
 * GWT JUnit tests must extend GWTTestCase.
 */
public abstract class AbstractCSETest extends GWTTestCase {

	public AbstractCSETest() {
		super();
	}

	/**
	 * Must refer to a valid module that sources this class.
	 */
	public String getModuleName() {
	    return "org.cse.csebuilder";
	  }

    protected static interface ExecutableTest {
        void run();
    }
    
	protected void executeAsyncTest(final ExecutableTest testToRun) {
		this.executeAsyncTest(testToRun, 100);
	}
    
	protected void executeAsyncTest(final ExecutableTest testToRun, 
			final int expectedExecutionTimeInMs) {	
		
		//Setup an asynchronous event handler.
		Timer t = new Timer() {
			public void run() {
				//execute the test
				testToRun.run();					
				//tell the test system the test is now done
			    finishTest();				
			}			
		};
		
		// Set a delay period significantly longer than the
		// event is expected to take.
		delayTestFinish(expectedExecutionTimeInMs * 3);
		
		//Schedule the event and return control to the test system.
		t.schedule(expectedExecutionTimeInMs);	
	}

}
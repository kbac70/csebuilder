package org.cse.client.bkm;


import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author KBac
 *
 */
public class TagStub implements Tag {
    
    public static final int DEFAULT_COUNT = 10;
    public static final String DEFAULT_NAME = "name ";
    public static final int DEFAULT_STATE = 0; 
    
    public static List newTags(int count) {
        List result = new ArrayList();
        
        for (int i = 0; i < count; i++) {
            Tag t = new TagStub(DEFAULT_COUNT + i, DEFAULT_NAME + i, DEFAULT_STATE);
            result.add(t);
        }
        
        return result;
    }

    private final int count;
    private final String name;
    private final int state;
    
    public TagStub(int count, String name, int state) {
        this.count = count;
        this.name = name;
        this.state = state;
    }

	public int getCount() {
		return this.count;
	}

	public String getId() {
		return this.name + this.count;
	}

	public String getName() {
		return this.name;
	}

	public String getSearchText() {
		return getName();
	}

	public int getState() {
		return this.state;
	}

	public double getWeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void nextState() {
		// TODO Auto-generated method stub

	}

	public void setState(int state) {
		// TODO Auto-generated method stub

	}

	public void setWeight(double weight) {
		// TODO Auto-generated method stub

	}

 


}

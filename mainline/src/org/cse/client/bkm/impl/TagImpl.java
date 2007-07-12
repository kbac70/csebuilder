package org.cse.client.bkm.impl;


import org.cse.client.bkm.Tag;
import org.cse.client.utils.StringHelper;

/**
 * Tag implementation of <code>{@link Tag}</code> interface.
 * 
 * @author KBac
 *
 */
public class TagImpl implements Tag {
    
    private final String name;
    private final int count;
    private int state;
	private double weight;
    public TagImpl(String name, int count) {
        this.name = name;
        this.count = count;
        this.state = Tag.StateEnum.UNDEFINED;
    }

    public String getName() {
        return name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        if ( Tag.StateEnum.isValid(state)) {
            this.state = state;        
        }
    }

    public void nextState() {
    	if (this.state ==  Tag.StateEnum.UNDEFINED) {
    		this.state =  Tag.StateEnum.INCLUDED;
    	} else {
    		this.state ++;
    	}
    }
    /**
     * @return derived tag id as textual concat of its name and count of associated bookmarks
     */
    public String getId() {
    	return this.name + this.count;
    }

    public int getCount() {
        return count;
    }

    public String getSearchText() {
        return StringHelper.uncamelCase(this.name);
    }

	public double getWeight() {
		return this.weight;
	}

	public void setWeight(double weight) {
		//TODO if (weight > 1) throw new RuntimeException();
		this.weight = weight;
	}
    
}

package org.cse.client.bkm;

/**
 * Interface defining properties of a Tag 
 * 
 * @author KBac
 *
 */
public interface Tag {
    /**
     * Constant indicating unknown count
     */
    public static final int UNKNOWN_COUNT = -1;
    /**
     * Enum emulation defining state of a tag.
     * @author KBac
     *
     */
    public static class StateEnum {
        /**
         * Tag which is to be indicated as included in the annotation definition 
         */
        public static final int INCLUDED = 0;
        /**
         * Tag which is to be indicated as excluded in the annotation definition
         */
        public static final int EXCLUDED = 1;
        /**
         * Tag which is not to be considered when generating the annotation definition
         */
        public static final int UNDEFINED = 2;      
        /**
         * @param info which is the value to be tested 
         * @return true when info is from within valid range, false otherwise
         */
        public static boolean isValid(int info) {
            return info >= INCLUDED && info <= UNDEFINED;
        }
        
    };
    
    /**
     * @return amount of bookmarks tagged with this tag, <code>UNKNOWN_COUNT</code> otherwsise
     */
    public int getCount();
    /**
     * @return uri of the tag
     */
    public int getState();
    /**
     * @param state value from within <code>Tag.StateEnum</code> to be assigned for this tag instance
     */
    public void setState(int state);
    /**
     * @return uncameled text e.g. (CamelCase => Camel Case) be used in the rewrite part of the context entry 
     */
    public String getSearchText();
    /**
     * @return name of the tag
     */
    public String getName();
    /**
     * @return String identyfying the tag
     */
    public String getId();
    /**
     * Call this method to cycle through the <code>Tag.StateEnum</code>
     */
    public void nextState();
    /**
     * @return weight of the tag
     */
    public double getWeight();
    /**
     * Sets weight of the tag from between [0..1]
     * @param weight of the tag
     */
    public void setWeight(double weight);
}   

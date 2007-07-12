package org.cse.client.bkm;

import java.util.Map;

import com.google.gwt.user.client.ui.ChangeListener;

/**
 * Interface defining required entries for a tag provider implementation.
 * 
 * @author KBac
 *
 */
public interface TagProvider {
    /**
     * @return <code>Map&lt;tagName,Tag&gt;</code> of tags which are included or excluded 
     */
    public Map getActive();
    /**
     * @return <code>Map&lt;tagName,{@link Tag}&gt;</code> of tags marked as included
     */
    public Map getIncluded();
    /**
     * @return <code>Map&lt;tagName,{@link Tag}&gt;</code> of tags marked as excluded
     */
    public Map getExcluded();
    /**
     * Registers given listener for change notifications
     * @param listener 
     */
    public void addChangeListener(ChangeListener listener);
    /**
     * Removes given listener from chain of notifications
     * @param listener
     */
    public void removeChangeListener(ChangeListener listener);
}

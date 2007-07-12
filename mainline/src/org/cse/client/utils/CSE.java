package org.cse.client.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.cse.client.bkm.Bookmark;
import org.cse.client.bkm.Tag;

/**
 * 
 * @author KBac
 *
 */
public interface CSE {
	/**
	 * Maximum amount of bookmarks allowed for upload into CSE
	 */
	public static final int MAX_BOOKMARKS = 4999;
	/**
	 * Helper class, which wraps information produced by the annotation generation process.
	 * 
	 * @author KBac
	 *
	 */
	public static class Annotations {
		/**
		 * Label name for tags to be indicated as boosted
		 */
		public final String includedLabel;
		/**
		 * Label name for tags to be indicated as filtered
		 */
		public final String excludedLabel;
	    /**
	     * Annotation XML text
	     */
		public final String xml;
	    /**
	     * <code>Map&lt;String,{@link Tag}&gt;</code> of names to Tags used to build the Annotation XML
	     */
		public final Map tags;
		/**
	     * @param xml text
	     * @param tags <code>Map&lt;String,{@link Tag}&gt;</code> of names to Tags used to build the Annotation XML
	     * @param includedLabel label name for tags to be indicated as boosted
	     * @param excludedLabel label name for tags to be indicated as filtered
		 */
		public Annotations (String xml, Map tags, String includedLabel, String excludedLabel) {
			this.xml = xml;
			this.tags = tags; //could make unmodifiable but no use in GWT
			this.includedLabel = includedLabel;
			this.excludedLabel = excludedLabel;
		}
	}

	/**
	 * Helper class, which wraps information produced by the context generation process.
	 * 
	 * @author KBac
	 *
	 */
	public static class Context {
	    /**
	     * Context XML text
	     */
	    public final String xml;
	    
	    public Context(String xml) {
	        this.xml = xml;
	    }
	}

	/**
	 * Class encapsulating the input data required by the <code>generateContext</code>
	 *  
	 * @author KBac
	 */
	public static class ContextInfo {
		public final Collection includedTags;
		public final Map excludedTags;
		public final String title;
		public final String description;
		public final String volunteers;
		public final String backLabel;
		/**
		 * @param includedTags - <code>Collection&lt;{@link Tag}&gt;</code> to be considered
		 * @param excludedTags - <code>Map&lt;tagName,{@link Tag}&gt;</code> map of tags to be indicated as excluded
		 * @param title of the CSE
		 * @param description of the CSE
		 * @param volunteers (true to allow voluntary contributions)
		 */
		public ContextInfo(Collection includedTags, Map excludedTags, 
				String title, String description, boolean volunteers, String backLabel) {    		
			this.includedTags = includedTags;
			this.excludedTags = excludedTags;
			this.title = title;
			this.description = description;
			this.volunteers = String.valueOf(volunteers);
			this.backLabel = backLabel;
		}
	}
	/**
	 * 
	 */
	public static final String LINE_SEPARATOR = "\n";//NOGWT System.getProperty("line.separator");
	/**
	 * 
	 */
	public static final String GENERATOR = "<!-- Generated by CSE Builder -->";
	/**
	 * 
	 */
	public static final int MAX_KEYWORDS_LENGTH = 100;
	/**
	 * Constant identifying the excluded tags within the CSE context  
	 */
	public static final String DEFAULT_TAG_EXCLUDED = "_cse_exclude_tsgndoawkce";
	/**
	 * Constant identifying the included tags within the CSE context
	 */
	public static final String DEFAULT_TAG_INCLUDED = "_cse_tsgndoawkce";
	/**
	 * Constant for Annotations group XML Element from within CSE context
	 */
	public static final String ELEM_ANNOTATIONS = "Annotations";
	/**
	 * Constant for Annotation XML Element from within CSE context
	 */
	public static final String ELEM_ANNOTATION = "Annotation";
	/**
	 * Constant for Label XML Element from within CSE context
	 */
	public static final String ELEM_LABEL = "Label";
	/**
	 * Constant for Facet XML Element from within CSE context
	 */
	public static final String ELEM_FACET = "Facet";
	/**
	 * Constant for FacetItem XML Element from within CSE context
	 */
	public static final String ELEM_FACETITEM = "FacetItem";
	/**
	 * 
	 */
	public static final String GOOGLE_CUSTOMIZATIONS_END_ELEM = "</GoogleCustomizations>";

	/**
	 * Call this method to generate CSE annotations
	 * @param bookmarks <code>Collection&lt;{@link Bookmark}&gt;</code>
	 * @param excludedTags <code>Map&lt;tagName,{@link Tag}&gt;</code> list of tags to be indicated as excluded
	 * @param includedLabel is the name the Bookmark should be tagged with when considered boosted
	 * @param excludedLabel is the name the Bookmark should be tagged with when considered filtered
	 * @return instance of <code>Annotations</code> class
	 */
	public abstract CSE.Annotations generateAnnotationInfo(Collection bookmarks,
			Map excludedTags, String includedLabel, String excludedLabel);

	/**
	 * Call this method to generate CSE context information
	 * @param context carrying all the <code>{@link CSE.ContextInfo}</code>
	 * @return instance of <code>Context</code> class
	 */
	public abstract CSE.Context generateContextInfo(CSE.ContextInfo context);

	/**
	 * Call this utility to sort tags according to their count in the descending order
	 * @param tags <code>List&lt;{@link Tag}&gt;</code> to become sorted
	 * @return <code>List&lt;{@link Tag}&gt;</code> of sorted tags
	 */
	public abstract List sortTagsCountDescending(List tags);

}
package org.cse.client.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.cse.client.bkm.Bookmark;
import org.cse.client.bkm.Tag;

/**
 * Class encapsulating functionality related to Google Custom Search Engine (CSE).
 * 
 * @author KBac
 *
 */
public class CSEHelper implements CSE {	
	/* (non-Javadoc)
	 * @see org.cse.client.utils.CSE#generateAnnotationInfo(java.util.Collection, java.util.Map)
	 */
	public CSE.Annotations generateAnnotationInfo(Collection bookmarks, 
			Map excludedTags, String includedLabel, String excludedLabel) {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + LINE_SEPARATOR + 
        	GENERATOR + LINE_SEPARATOR;
        xml += "<GoogleCustomizations  version=\"1.0\">" + LINE_SEPARATOR + 
        	"  <" + ELEM_ANNOTATIONS + " num=\"" + bookmarks.size() + "\" total=\"" + bookmarks.size() + "\">" + LINE_SEPARATOR;        
        
        Map dedupedTags = new HashMap();
        
        for (Iterator iter = bookmarks.iterator(); iter.hasNext();) {
			final Bookmark bkm = (Bookmark) iter.next();
			//TODO scoring
			xml += "  <" + ELEM_ANNOTATION + " about=\"" + bkm.getLink() + "\">" + LINE_SEPARATOR; 
			String includeLabel = includedLabel; 
			
			final List tags = bkm.getTags();
			for (Iterator thisTag = tags.iterator(); thisTag.hasNext();) {
				final Tag tag = (Tag) thisTag.next();
				dedupedTags.put(tag.getName(), tag); //dedup tags
				xml += "    <" + ELEM_LABEL + " name=\"" + tag.getName() + "\" />" + LINE_SEPARATOR;				
				
				if (excludedTags.containsKey(tag.getName())) {
					includeLabel = excludedLabel;
				}
			}
            xml += "    <" + ELEM_LABEL + " name=\"" + includeLabel + "\" />" + LINE_SEPARATOR;			
			xml += "  </" + ELEM_ANNOTATION + ">" + LINE_SEPARATOR;
		}
        xml += " </" + ELEM_ANNOTATIONS + ">" + LINE_SEPARATOR +
        	 GENERATOR + LINE_SEPARATOR + CSE.GOOGLE_CUSTOMIZATIONS_END_ELEM;

        return new CSE.Annotations(xml, dedupedTags, includedLabel, excludedLabel);		
	}
    /* (non-Javadoc)
	 * @see org.cse.client.utils.CSE#generateContextInfo(org.cse.client.utils.CSEHelper.ContextInfo)
	 */
	public CSE.Context generateContextInfo(CSE.ContextInfo context) {
		String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + LINE_SEPARATOR + 
			GENERATOR + LINE_SEPARATOR + 
			"<GoogleCustomizations  version=\"1.0\">" + LINE_SEPARATOR + 
			" <CustomSearchEngine version=\"1.0\" volunteers=\"" + context.volunteers + "\" keywords=\"";
		String body = "  <Context>" + LINE_SEPARATOR;
		
		body += "   <BackgroundLabels>" + LINE_SEPARATOR;
		if (context.backLabel.length() > 0) {
		      body += "    <Label name=\"" + context.backLabel + "\" mode=\"BOOST\" />" + LINE_SEPARATOR;
		}
		body += "   </BackgroundLabels>" + LINE_SEPARATOR;
		

		List includedTags = new ArrayList();
		includedTags.addAll(context.includedTags);
		sortTagsCountDescending(includedTags);
		
		Map usedTags = new HashMap(); 
		String keywords = "";
		
		for (Iterator iter = includedTags.iterator(); iter.hasNext();) {
			final Tag tag = (Tag) iter.next();
			String tagName = tag.getName();
			
			tagName = StringHelper.removePlural(tagName);
            final String uppercasedTagName = tagName.toUpperCase();
            
            if (usedTags.containsKey(uppercasedTagName) || 
            		context.excludedTags.containsKey(tag.getName())) { 
                //continue;
            } else {
                usedTags.put(uppercasedTagName, tag);
                
                String searchText = "&quot;" + StringHelper.removePlural(tag.getSearchText()) + "&quot; ";
    			if (keywords.length() + searchText.length() < MAX_KEYWORDS_LENGTH) {
    				keywords += searchText;
    			}
    			
    			String rewrite = tag.getSearchText(); //TODO
    			
    			body += "    <" + ELEM_FACET + ">" + LINE_SEPARATOR + 
    				"      <" + ELEM_FACETITEM + " Title=\"" + tagName + "\">" + LINE_SEPARATOR +
    				"        <" + ELEM_LABEL + " name=\"" + tagName + "\" mode=\"BOOST\" Rewrite=\"" + rewrite + "\" IgnoreBackgroundLabels=\"false\" weight=\"0.7\" />" + LINE_SEPARATOR +
    				"      </" + ELEM_FACETITEM + ">" + LINE_SEPARATOR + 
    				"    </" + ELEM_FACET + ">" + LINE_SEPARATOR;
            }			
		}
		
		header += keywords + "\" Title=\"" + context.title + "\" Description=\"" + context.description + "\" language=\"en\">" + LINE_SEPARATOR;
		body += "  </Context>" + LINE_SEPARATOR + "  <BackgroundLabels>" + LINE_SEPARATOR;
		body += "    <" + ELEM_LABEL + " name=\"" + DEFAULT_TAG_INCLUDED + "\" mode=\"BOOST\" weight=\"0.7\" />" + LINE_SEPARATOR +
	      		"    <" + ELEM_LABEL + " name=\"" + DEFAULT_TAG_EXCLUDED + "\" mode=\"ELIMINATE\" />" + LINE_SEPARATOR + 
	      		"  </BackgroundLabels>";
		String footer = LINE_SEPARATOR + "  <LookAndFeel>" + LINE_SEPARATOR + 
			"    <Colors url=\"#008000\" background=\"#FFFFFF\" border=\"#FFFFFF\" title=\"#0000CC\" text=\"#000000\" visited=\"#663399\" light=\"#000000\" />" + LINE_SEPARATOR +
			"  </LookAndFeel>" + LINE_SEPARATOR +
			"   <AdSense />" + LINE_SEPARATOR + 
			" </CustomSearchEngine>" + LINE_SEPARATOR + GENERATOR + LINE_SEPARATOR +
			CSE.GOOGLE_CUSTOMIZATIONS_END_ELEM;
		
		return new CSE.Context(header + body + footer);
	}
	/* (non-Javadoc)
	 * @see org.cse.client.utils.CSE#sortTagsCountDescending(java.util.List)
	 */
	public List sortTagsCountDescending(List tags) {
		final Comparator countComparator = new Comparator() {
	        public int compare(Object o1, Object o2) {
	            return ((Tag)o2).getCount() - ((Tag)o1).getCount();
	        }               
	    }; 
		
	    Collections.sort(tags, countComparator);
		return tags;
	}

}

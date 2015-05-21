
package edu.njit.cs.saboc.nat.generic.gui.filterablelist;

import edu.njit.cs.saboc.nat.generic.data.BrowserConcept;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Chris O
 */
public class FilterablePathConceptEntry extends FilterableConceptEntry {
    
    private String relStr;
    
    public FilterablePathConceptEntry(BrowserConcept c) {
        super(c);

        /*
         ArrayList<LocalLateralRelationship> rels = concept.getAttributeRelationships();
         
         ArrayList<String> relStrs = new ArrayList<String>();

        for (LocalLateralRelationship rel : rels) {
            if (rel.getCharacteristicType() == 0) {
                String relName = rel.getRelationship().getName();
                relName = relName.substring(0, relName.lastIndexOf("(")).trim();
                
                String targetName = rel.getTarget().getName();
                targetName = targetName.substring(0, targetName.lastIndexOf("(")).trim();

                relStrs.add(String.format("<font color=\"red\">%s</font> => %s", relName, targetName));
            }
        }

        if (!relStrs.isEmpty()) {

            Collections.sort(relStrs);

            String relStr = "(";

            for (String rel : relStrs) {
                relStr += (rel + ", ");
            }

            relStr = relStr.substring(0, relStr.length() - 2);
            relStr += ")";

            this.relStr = relStr;
        } else {
            relStr = "";
        }
                */
    }
    
    protected String createEntryStr(String conceptName, String conceptId) {
        return super.createEntryStr(conceptName, conceptId) + " " + relStr;
    }
}

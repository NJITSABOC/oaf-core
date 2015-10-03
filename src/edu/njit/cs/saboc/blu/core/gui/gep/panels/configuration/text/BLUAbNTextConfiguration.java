
package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.text;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;

/**
 *
 * @author Chris O
 */
public interface BLUAbNTextConfiguration<
        ABN_T extends AbstractionNetwork, 
        GROUP_T extends GenericConceptGroup, 
        CONCEPT_T> {
    
    public String getAbNName();
    public String getAbNSummary();
    public String getAbNHelpDescription();
    
    public String getAbNTypeName(boolean plural);
    public String getGroupTypeName(boolean plural);
    public String getConceptTypeName(boolean plural);
    public String getGroupHelpDescriptions(GROUP_T group);
    
    public String getConceptName(CONCEPT_T concept);
    public String getGroupName(GROUP_T group);
}

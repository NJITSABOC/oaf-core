
package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.data;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public interface BLUAbNDataConfiguration<
        ABN_T extends AbstractionNetwork, 
        GROUP_T extends GenericConceptGroup, 
        CONCEPT_T> {
    
    public ArrayList<CONCEPT_T> getSortedConceptList(GROUP_T group);
    
}

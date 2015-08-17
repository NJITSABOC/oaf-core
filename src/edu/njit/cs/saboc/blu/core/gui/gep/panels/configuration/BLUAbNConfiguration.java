package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import SnomedShared.generic.GenericConceptGroup;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public interface BLUAbNConfiguration<CONCEPT_T, GROUP_T extends GenericConceptGroup> {
    public String getGroupTypeName(boolean plural);
    public String getConceptTypeName(boolean plural);
    
    public String getConceptName(CONCEPT_T concept);
    public String getGroupName(GROUP_T group);

    public ArrayList<CONCEPT_T> getSortedConceptList(GROUP_T group);
}

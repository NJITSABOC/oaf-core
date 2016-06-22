package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry;

import SnomedShared.generic.GenericConceptGroup;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class ContainerConceptEntry<CONCEPT_T, GROUP_T extends GenericConceptGroup> {
    protected final CONCEPT_T concept;
    
    protected final HashSet<GROUP_T> groups;
    
    public ContainerConceptEntry(CONCEPT_T concept, HashSet<GROUP_T> groups) {
        this.concept = concept;
        this.groups = groups;
    }
    
    public CONCEPT_T getConcept() {
        return concept;
    }
    
    public HashSet<GROUP_T> getGroups() {
        return groups;
    }
}

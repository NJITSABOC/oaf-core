package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry;

import SnomedShared.generic.GenericConceptGroup;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class ImportedConceptGroupReport<GROUP_T extends GenericConceptGroup, CONCEPT_T> {
    private final HashSet<GROUP_T> groups;
    
    private final CONCEPT_T concept;
    
    public ImportedConceptGroupReport(CONCEPT_T concept, HashSet<GROUP_T> groups) {
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

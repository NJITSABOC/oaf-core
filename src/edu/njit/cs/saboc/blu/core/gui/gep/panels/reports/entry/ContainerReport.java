package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry;

import SnomedShared.generic.GenericConceptGroup;
import SnomedShared.generic.GenericGroupContainer;
import java.util.HashSet;

/**
 *
 * @author Den
 */
public class ContainerReport<
        CONTAINER_T extends GenericGroupContainer, 
        GROUP_T extends GenericConceptGroup,
        CONCEPT_T> {
    
    private final CONTAINER_T container;
    
    private final HashSet<GROUP_T> groups;
    private final HashSet<CONCEPT_T> concepts;
    private final HashSet<CONCEPT_T> overlappingConcepts;

    public ContainerReport(CONTAINER_T container, HashSet<GROUP_T> groups, HashSet<CONCEPT_T> concepts, HashSet<CONCEPT_T> overlappingConcepts) {
        this.container = container;
        
        this.groups = groups;
        this.concepts = concepts;
        this.overlappingConcepts = overlappingConcepts;
    }
    
    public CONTAINER_T getContainer() {
        return container;
    }

    public HashSet<GROUP_T> getGroups() {
        return groups;
    }

    public HashSet<CONCEPT_T> getConcepts() {
        return concepts;
    }

    public HashSet<CONCEPT_T> getOverlappingConcepts() {
        return overlappingConcepts;
    }
}

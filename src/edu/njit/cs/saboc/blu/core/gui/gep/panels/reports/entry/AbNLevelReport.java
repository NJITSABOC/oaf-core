package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry;

import SnomedShared.generic.GenericConceptGroup;
import SnomedShared.generic.GenericGroupContainer;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class AbNLevelReport<CONCEPT_T, GROUP_T extends GenericConceptGroup, CONTAINER_T extends GenericGroupContainer> {
    
    private final int level;
    
    private final HashSet<CONCEPT_T> conceptsAtLevel;
    
    private final HashSet<CONCEPT_T> overlappingConceptsAtLevel;
    
    private final HashSet<GROUP_T> groupsAtLevel;
    
    private final HashSet<CONTAINER_T> containersAtLevel;

    public AbNLevelReport(int level, HashSet<CONCEPT_T> conceptsAtLevel, 
            HashSet<CONCEPT_T> overlappingConceptsAtLevel, 
            HashSet<GROUP_T> groupsAtLevel, 
            HashSet<CONTAINER_T> containersAtLevel) {
        
        this.level = level;
        
        this.conceptsAtLevel = conceptsAtLevel;
        
        this.overlappingConceptsAtLevel = overlappingConceptsAtLevel;
        
        this.groupsAtLevel = groupsAtLevel;
        
        this.containersAtLevel = containersAtLevel;
    }
    
    public int getLevel() {
        return level;
    }

    public HashSet<CONCEPT_T> getConceptsAtLevel() {
        return conceptsAtLevel;
    }

    public HashSet<CONCEPT_T> getOverlappingConceptsAtLevel() {
        return overlappingConceptsAtLevel;
    }

    public HashSet<GROUP_T> getGroupsAtLevel() {
        return groupsAtLevel;
    }

    public HashSet<CONTAINER_T> getContainersAtLevel() {
        return containersAtLevel;
    }
}

package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import SnomedShared.Concept;
import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public abstract class GenericPArea<CONCEPT_T, REL_T, PAREA_T extends GenericPArea<CONCEPT_T, REL_T, PAREA_T>> extends GenericConceptGroup {
    
    protected SingleRootedHierarchy<CONCEPT_T> conceptHierarchy;
    
    protected HashSet<GenericParentPAreaInfo<CONCEPT_T, PAREA_T>> parentPAreas;
    
    protected HashSet<REL_T> relationships;
    
    protected GenericPArea(int id, 
            Concept root, 
            SingleRootedHierarchy<CONCEPT_T> conceptHierarchy, 
            HashSet<Integer> parentIds,
            HashSet<REL_T> relationships) {
        
        super(id, root, conceptHierarchy.getNodesInHierarchy().size(), parentIds);
        
        this.conceptHierarchy = conceptHierarchy;
                
        this.relationships = relationships;
    }
    
    public SingleRootedHierarchy<CONCEPT_T> getHierarchy() {
        return conceptHierarchy;
    }
    
    public HashSet<REL_T> getRelationships() {
        return relationships;
    }
    
    public void setParentPAreaInfo(HashSet<GenericParentPAreaInfo<CONCEPT_T, PAREA_T>> parentPAreaInfo) {
        this.parentPAreas = parentPAreaInfo;
    }
    
    public HashSet<GenericParentPAreaInfo<CONCEPT_T, PAREA_T>> getParentPAreaInfo() {
        return parentPAreas;
    }
    
    public ArrayList<CONCEPT_T> getConceptsInPArea() {
        return new ArrayList<CONCEPT_T>(conceptHierarchy.getNodesInHierarchy());
    }
    
    public abstract HashSet<REL_T> getRelsWithoutInheritanceInfo();
}

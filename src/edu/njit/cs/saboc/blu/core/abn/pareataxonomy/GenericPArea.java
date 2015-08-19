package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import SnomedShared.Concept;
import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.GenericParentGroupInfo;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public abstract class GenericPArea<
        CONCEPT_T, 
        REL_T, 
        HIERARCHY_T extends SingleRootedHierarchy<CONCEPT_T, HIERARCHY_T>, 
        PAREA_T extends GenericPArea<CONCEPT_T, REL_T, HIERARCHY_T, PAREA_T>> extends GenericConceptGroup {
    
    protected HIERARCHY_T conceptHierarchy;
    
    protected HashSet<GenericParentGroupInfo<CONCEPT_T, PAREA_T>> parentPAreas;
    
    protected HashSet<REL_T> relationships;
    
    protected GenericPArea(int id, 
            Concept root, 
            HIERARCHY_T conceptHierarchy, 
            HashSet<Integer> parentIds,
            HashSet<REL_T> relationships) {
        
        super(id, root, conceptHierarchy.getNodesInHierarchy().size(), parentIds);
        
        this.conceptHierarchy = conceptHierarchy;
                
        this.relationships = relationships;
    }
    
    public HIERARCHY_T getHierarchy() {
        return conceptHierarchy;
    }
    
    public HashSet<REL_T> getRelationships() {
        return relationships;
    }
    
    public void setParentPAreaInfo(HashSet<GenericParentGroupInfo<CONCEPT_T, PAREA_T>> parentPAreaInfo) {
        this.parentPAreas = parentPAreaInfo;
    }
    
    public HashSet<GenericParentGroupInfo<CONCEPT_T, PAREA_T>> getParentPAreaInfo() {
        return parentPAreas;
    }
    
    public ArrayList<CONCEPT_T> getConceptsInPArea() {
        return new ArrayList<CONCEPT_T>(conceptHierarchy.getNodesInHierarchy());
    }
    
    public boolean equals(Object o) {
        if(o instanceof GenericPArea) {
            GenericPArea otherPArea = (GenericPArea)o;
            
            return otherPArea.getRoot().getId() == this.getRoot().getId();
        }
        
        return false;
    }
    
    public int hashCode() {
        return Long.hashCode(this.getRoot().getId());
    }
    
    public abstract HashSet<REL_T> getRelsWithoutInheritanceInfo();
}

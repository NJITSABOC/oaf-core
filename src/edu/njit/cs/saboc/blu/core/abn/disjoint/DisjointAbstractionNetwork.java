package edu.njit.cs.saboc.blu.core.abn.disjoint;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.nodes.DisjointGenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.GroupHierarchy;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Chris
 */
public abstract class DisjointAbstractionNetwork<
        PARENTABN_T extends AbstractionNetwork,
        GROUP_T extends GenericConceptGroup, 
        CONCEPT_T, 
        HIERARCHY_T extends SingleRootedHierarchy<CONCEPT_T, HIERARCHY_T>,
        DISJOINTGROUP_T extends DisjointGenericConceptGroup<GROUP_T, CONCEPT_T, HIERARCHY_T, DISJOINTGROUP_T>> extends AbstractionNetwork<DISJOINTGROUP_T> {
    
    
    protected HashSet<GROUP_T> allGroups;
    
    protected HashSet<GROUP_T> overlappingGroups;
    
    protected PARENTABN_T parentAbN;
    
    protected int levels;
    
    public DisjointAbstractionNetwork(PARENTABN_T abstractionNetwork, 
            HashMap<Integer, DISJOINTGROUP_T> disjointGroups, 
            GroupHierarchy<DISJOINTGROUP_T> groupHierarchy,
            int levels,
            HashSet<GROUP_T> allGroups,
            HashSet<GROUP_T> overlappingGroups) {
        
        super(disjointGroups, groupHierarchy);
        
        this.parentAbN = abstractionNetwork;
        
        this.allGroups = allGroups;
        
        this.overlappingGroups = overlappingGroups;
        
        this.levels = levels;
    }
    
    public PARENTABN_T getParentAbstractionNetwork() {
        return parentAbN;
    }
    
    public HashSet<GROUP_T> getOverlappingGroups() {
        return overlappingGroups;
    }
    
    public HashSet<DISJOINTGROUP_T> getDisjointGroups() {
        return new HashSet<>(groups.values());
    }

    public int getLevelCount() {
        return levels;
    }
    
    public DISJOINTGROUP_T getRootGroup() {
        return null;
    }
}

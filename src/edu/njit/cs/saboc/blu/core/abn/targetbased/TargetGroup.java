package edu.njit.cs.saboc.blu.core.abn.targetbased;

import SnomedShared.Concept;
import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import java.util.HashMap;
import java.util.HashSet;

/**
 * T is the type of the root (something that extends concept). V is what is actually summarized by this target group. Very clunky. This has to be fixed.
 * 
 * @author Chris O
 */
public abstract class TargetGroup<CONCEPT_T> extends GenericConceptGroup {
    
    private SingleRootedHierarchy<CONCEPT_T> hierarchy;
    
    private HashMap<CONCEPT_T, HashSet<CONCEPT_T>> incomingRelSources;
    
    public TargetGroup(int id, Concept root, HashSet<Integer> parentIds, 
            SingleRootedHierarchy<CONCEPT_T> groupHierarchy, HashMap<CONCEPT_T, HashSet<CONCEPT_T>> incomingRelSources) {
        
        super(id, root, groupHierarchy.getNodesInHierarchy().size(), parentIds);
        
        this.hierarchy = groupHierarchy;
        this.incomingRelSources = incomingRelSources;
    }
    
    public SingleRootedHierarchy<CONCEPT_T> getGroupHierarchy() {
        return hierarchy;
    }
    
    public HashMap<CONCEPT_T, HashSet<CONCEPT_T>> getGroupIncomingRelSources() {
        HashMap<CONCEPT_T, HashSet<CONCEPT_T>> groupIncomingSources = new HashMap<CONCEPT_T, HashSet<CONCEPT_T>>();
        
        for(CONCEPT_T concept : hierarchy.getNodesInHierarchy()) {
            if(incomingRelSources.containsKey(concept) && !incomingRelSources.get(concept).isEmpty()) {
                groupIncomingSources.put(concept, incomingRelSources.get(concept));
            }
        }
        
        return groupIncomingSources;
    }
    
    public boolean equals(Object o) {
        if(!(o instanceof TargetGroup)) {
            return false;
        }
        
        return ((TargetGroup)o).getRoot().equals(this.getRoot());
    }
    
    public int hashCode() {
        return this.getRoot().hashCode();
    }
    
}

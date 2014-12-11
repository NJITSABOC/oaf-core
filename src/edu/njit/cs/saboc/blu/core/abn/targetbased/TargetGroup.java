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
public abstract class TargetGroup<T extends Concept, V> extends GenericConceptGroup {
    
    private SingleRootedHierarchy<V> hierarchy;
    
    private HashMap<V, HashSet<V>> incomingRelSources;
    
    public TargetGroup(int id, T root, int conceptCount, HashSet<Integer> parentIds, 
            SingleRootedHierarchy<V> groupHierarchy, HashMap<V, HashSet<V>> incomingRelSources) {
        super(id, root, conceptCount, parentIds);
        
        this.hierarchy = groupHierarchy;
        this.incomingRelSources = incomingRelSources;
    }
    
    public SingleRootedHierarchy<V> getGroupHierarchy() {
        return hierarchy;
    }
    
    public HashMap<V, HashSet<V>> getGroupIncomingRelSources() {
        HashMap<V, HashSet<V>> groupIncomingSources = new HashMap<V, HashSet<V>>();
        
        for(V concept : hierarchy.getNodesInHierarchy()) {
            if(!incomingRelSources.get(concept).isEmpty()) {
                groupIncomingSources.put(concept, incomingRelSources.get(concept));
            }
        }
        
        return groupIncomingSources;
    }
    
}

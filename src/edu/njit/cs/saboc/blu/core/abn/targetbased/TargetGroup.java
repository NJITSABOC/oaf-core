package edu.njit.cs.saboc.blu.core.abn.targetbased;

import SnomedShared.Concept;
import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public abstract class TargetGroup<T extends Concept, V> extends GenericConceptGroup {
    
    private SingleRootedHierarchy<V> hierarchy;
    
    public TargetGroup(int id, T root, int conceptCount, HashSet<Integer> parentIds) {
        super(id, root, conceptCount, parentIds);
    }
    
}

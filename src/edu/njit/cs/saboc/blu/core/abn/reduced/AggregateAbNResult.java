package edu.njit.cs.saboc.blu.core.abn.reduced;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.GroupHierarchy;
import java.util.HashMap;

/**
 *
 * @author Chris O
 */
public class AggregateAbNResult<GROUP_T extends GenericConceptGroup, AGGREGATEGROUP_T extends GenericConceptGroup & AggregateableConceptGroup> {
    public final HashMap<Integer, AGGREGATEGROUP_T> reducedGroups;
    
    public final GroupHierarchy<AGGREGATEGROUP_T> reducedGroupHierarchy;
    
    public AggregateAbNResult(HashMap<Integer, AGGREGATEGROUP_T> reducedGroups, GroupHierarchy<AGGREGATEGROUP_T> reducedGroupHierarchy) {
        this.reducedGroups = reducedGroups;
        this.reducedGroupHierarchy = reducedGroupHierarchy;
    }
}

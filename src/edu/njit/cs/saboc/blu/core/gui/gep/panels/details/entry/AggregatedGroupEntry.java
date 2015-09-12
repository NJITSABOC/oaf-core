package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateableConceptGroup;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class AggregatedGroupEntry<CONCEPT_T, 
        GROUP_T extends GenericConceptGroup, 
        AGGREGATEGROUP_T extends GenericConceptGroup & AggregateableConceptGroup<CONCEPT_T, GROUP_T>>  {
    
    private final GROUP_T aggregatedGroup;
    
    private final HashSet<AGGREGATEGROUP_T> aggregatedInto;
    
    public AggregatedGroupEntry(GROUP_T aggregatedGroup, HashSet<AGGREGATEGROUP_T> aggregatedInto) {
        this.aggregatedGroup = aggregatedGroup;
        this.aggregatedInto = aggregatedInto;
    }
    
    public GROUP_T getAggregatedGroup() {
        return aggregatedGroup;
    }
    
    public HashSet<AGGREGATEGROUP_T> getAggregatedIntoGroups() {
        return aggregatedInto;
    }
}

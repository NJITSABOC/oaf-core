package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry;

import SnomedShared.generic.GenericConceptGroup;
import SnomedShared.generic.GenericGroupContainer;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateNode;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class AggregateContainerReport<CONTAINER_T extends GenericGroupContainer, 
        GROUP_T extends GenericConceptGroup,
        AGGREGATEGROUP_T extends GenericConceptGroup & AggregateNode<CONCEPT_T, GROUP_T>,
        CONCEPT_T> extends ContainerReport<CONTAINER_T, GROUP_T, CONCEPT_T> {
    
    public AggregateContainerReport(
            CONTAINER_T container, 
            HashSet<GROUP_T> groups, 
            HashSet<CONCEPT_T> concepts) {

        super(container, groups, concepts, new HashSet<>());
    }
    
}

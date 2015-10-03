package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.aggregate;

import SnomedShared.generic.GenericConceptGroup;
import SnomedShared.generic.GenericGroupContainer;
import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUPartitionedConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.AbstractAbNContainerReportPanel;

/**
 *
 * @author Chris O
 */

public class GenericAggregateContainerReportPanel<CONCEPT_T, 
        ABN_T extends PartitionedAbstractionNetwork,
        GROUP_T extends GenericConceptGroup, 
        CONTAINER_T extends GenericGroupContainer> extends AbstractAbNContainerReportPanel<ABN_T, CONTAINER_T, GROUP_T, CONCEPT_T> {
    
    public GenericAggregateContainerReportPanel(BLUPartitionedConfiguration config) {
        super(config, new GenericAggregateContainerReportTableModel<>(config));
    }
}


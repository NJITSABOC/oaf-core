package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.aggregate;

import SnomedShared.generic.GenericConceptGroup;
import SnomedShared.generic.GenericGroupContainer;
import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUPartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.GenericAbNLevelReportPanel;

/**
 *
 * @author Chris O
 */
public class GenericAggregateAbNLevelReportPanel<CONCEPT_T, 
        ABN_T extends PartitionedAbstractionNetwork,
        GROUP_T extends GenericConceptGroup, 
        CONTAINER_T extends GenericGroupContainer> extends GenericAbNLevelReportPanel<CONCEPT_T, ABN_T, CONTAINER_T, GROUP_T>{
    
    public GenericAggregateAbNLevelReportPanel(BLUPartitionedAbNConfiguration config) {
        super(config, new GenericAggregateAbNLevelReportTableModel<>(config));
    }
}

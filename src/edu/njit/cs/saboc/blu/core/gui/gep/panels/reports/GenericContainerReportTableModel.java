package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports;

import SnomedShared.generic.GenericConceptGroup;
import SnomedShared.generic.GenericGroupContainer;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUPartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.BLUAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry.ContainerReport;

/**
 *
 * @author Chris O
 */
public class GenericContainerReportTableModel<CONCEPT_T, CONTAINER_T extends GenericGroupContainer, GROUP_T extends GenericConceptGroup> 
    extends BLUAbstractTableModel<ContainerReport<CONTAINER_T, GROUP_T, CONCEPT_T>> {
    
    private final BLUPartitionedAbNConfiguration config;
    
    public GenericContainerReportTableModel(BLUPartitionedAbNConfiguration config) {
        
        super(new String[] {
            config.getContainerTypeName(false),
            String.format("# %s", config.getGroupTypeName(true)),
            String.format("# %s", config.getConceptTypeName(true)),
            String.format("# Overlapping %s", config.getConceptTypeName(true))
        });
        
        this.config = config;
    }
    
    @Override
    protected Object[] createRow(ContainerReport<CONTAINER_T, GROUP_T, CONCEPT_T> item) {
        return new Object[] {
            config.getContainerName(item.getContainer()).replaceAll(", ", "\n"),
            item.getGroups().size(),
            item.getConcepts().size(),
            item.getOverlappingConcepts()
        };
    }
}

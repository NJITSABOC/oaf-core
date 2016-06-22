package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.models;

import SnomedShared.generic.GenericConceptGroup;
import SnomedShared.generic.GenericGroupContainer;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUPartitionedConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry.ContainerReport;

/**
 *
 * @author Chris O
 */
public class GenericContainerReportTableModel<CONCEPT_T, CONTAINER_T extends GenericGroupContainer, GROUP_T extends GenericConceptGroup> 
    extends OAFAbstractTableModel<ContainerReport<CONTAINER_T, GROUP_T, CONCEPT_T>> {
    
    private final BLUPartitionedConfiguration config;
    
    public GenericContainerReportTableModel(BLUPartitionedConfiguration config) {
        
        super(new String[] {
            config.getTextConfiguration().getContainerTypeName(false),
            String.format("# %s", config.getTextConfiguration().getGroupTypeName(true)),
            String.format("# %s", config.getTextConfiguration().getConceptTypeName(true)),
            String.format("# Overlapping %s", config.getTextConfiguration().getConceptTypeName(true))
        });
        
        this.config = config;
    }
    
    @Override
    protected Object[] createRow(ContainerReport<CONTAINER_T, GROUP_T, CONCEPT_T> item) {
        return new Object[] {
            config.getTextConfiguration().getContainerName(item.getContainer()).replaceAll(", ", "\n"),
            item.getGroups().size(),
            item.getConcepts().size(),
            item.getOverlappingConcepts().size()
        };
    }
}

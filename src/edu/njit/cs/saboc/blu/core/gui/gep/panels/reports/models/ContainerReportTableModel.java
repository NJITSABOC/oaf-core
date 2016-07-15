package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.models;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry.ContainerReport;

/**
 *
 * @author Chris O
 */
public class ContainerReportTableModel extends OAFAbstractTableModel<ContainerReport> {
    
    public ContainerReportTableModel(PartitionedAbNConfiguration config) {
        
        super(new String[] {
            config.getTextConfiguration().getContainerTypeName(false),
            String.format("# %s", config.getTextConfiguration().getNodeTypeName(true)),
            String.format("# %s", config.getTextConfiguration().getConceptTypeName(true)),
            String.format("# Overlapping %s", config.getTextConfiguration().getConceptTypeName(true))
        });
    }
    
    @Override
    protected Object[] createRow(ContainerReport item) {
        return new Object[] {
            item.getContainer().getName("\n"),
            item.getGroups().size(),
            item.getConcepts().size(),
            item.getOverlappingConcepts().size()
        };
    }
}

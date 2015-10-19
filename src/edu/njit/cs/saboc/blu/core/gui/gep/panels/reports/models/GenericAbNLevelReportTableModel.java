package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.models;

import SnomedShared.generic.GenericConceptGroup;
import SnomedShared.generic.GenericGroupContainer;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUPartitionedConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.BLUAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry.AbNLevelReport;

/**
 *
 * @author Chris O
 */
public class GenericAbNLevelReportTableModel<CONCEPT_T, 
        GROUP_T extends GenericConceptGroup, 
        CONTAINER_T extends GenericGroupContainer> extends BLUAbstractTableModel<AbNLevelReport<CONCEPT_T, GROUP_T, CONTAINER_T>> {

    public GenericAbNLevelReportTableModel(BLUPartitionedConfiguration config) {
        super(new String [] { 
           "Level",
            String.format("# %s", config.getTextConfiguration().getContainerTypeName(true)),
            String.format("# %s", config.getTextConfiguration().getGroupTypeName(true)),
            String.format("# %s", config.getTextConfiguration().getConceptTypeName(true)),
            String.format("# Overlapping %s", config.getTextConfiguration().getConceptTypeName(true))
        });
    }
    
    @Override
    protected Object[] createRow(AbNLevelReport item) {
        return new Object [] {
            item.getLevel(),
            item.getContainersAtLevel().size(),
            item.getGroupsAtLevel().size(),
            item.getConceptsAtLevel().size(),
            item.getOverlappingConceptsAtLevel().size()
        };
    }
}

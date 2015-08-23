package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports;

import SnomedShared.generic.GenericConceptGroup;
import SnomedShared.generic.GenericGroupContainer;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUPartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.BLUAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry.AbNLevelReport;

/**
 *
 * @author Chris O
 */
public class GenericAbNLevelReportTableModel<CONCEPT_T, 
        GROUP_T extends GenericConceptGroup, 
        CONTAINER_T extends GenericGroupContainer> extends BLUAbstractTableModel<AbNLevelReport<CONCEPT_T, GROUP_T, CONTAINER_T>> {

    public GenericAbNLevelReportTableModel(BLUPartitionedAbNConfiguration config) {
        super(new String [] { 
           "Level",
            String.format("# %s", config.getContainerTypeName(true)),
            String.format("# %s", config.getGroupTypeName(true)),
            String.format("# %s", config.getConceptTypeName(true)),
            String.format("# Overlapping %s", config.getConceptTypeName(true))
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

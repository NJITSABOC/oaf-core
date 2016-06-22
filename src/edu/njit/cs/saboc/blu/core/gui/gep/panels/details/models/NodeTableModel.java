package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUConfiguration;

/**
 *
 * @author Chris O
 */
public class BLUAbstractGroupTableModel<GROUP_T extends GenericConceptGroup> extends BLUAbstractTableModel<GROUP_T> {
    
    protected final BLUConfiguration configuration;
    
    public BLUAbstractGroupTableModel(BLUConfiguration configuration) {
        super(
                new String[] {
                    configuration.getTextConfiguration().getGroupTypeName(false),
                    String.format("# %s", configuration.getTextConfiguration().getConceptTypeName(true))
            });
        
        this.configuration = configuration;
    }

    @Override
    protected Object[] createRow(GROUP_T group) {
        return new Object [] {
            configuration.getTextConfiguration().getGroupName(group),
            group.getConceptCount()
        };
    }
}

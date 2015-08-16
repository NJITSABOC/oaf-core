package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models;

import SnomedShared.generic.GenericConceptGroup;

/**
 *
 * @author Chris O
 */
public class BLUAbstractGroupTableModel<GROUP_T extends GenericConceptGroup> extends BLUAbstractTableModel<GROUP_T> {
    
    protected final BLUAbNConfiguration configuration;
    
    public BLUAbstractGroupTableModel(BLUAbNConfiguration configuration) {
        super(
                new String[] {
                    configuration.getGroupTypeName(false),
                    String.format("# %s", configuration.getConceptTypeName(true))
            });
        
        this.configuration = configuration;
    }

    @Override
    protected Object[] createRow(GROUP_T group) {
        return new Object [] {
            configuration.getGroupName(group),
            group.getConceptCount()
        };
    }
}

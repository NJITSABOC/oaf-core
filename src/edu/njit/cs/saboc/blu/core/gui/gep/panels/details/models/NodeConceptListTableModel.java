package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class NodeConceptListTableModel extends AbstractNodeEntityTableModel<Concept> {
    
    private final AbNConfiguration config;
    
    public NodeConceptListTableModel(AbNConfiguration config) {
        super(new String[] {
            config.getTextConfiguration().getConceptTypeName(false),
            "ID"
        });
        
        this.config = config;
    }
    
    protected AbNConfiguration getConfiguration() {
        return config;
    }

    @Override
    protected Object[] createRow(Concept item) {
        return new Object [] {
            item.getName(),
            item.getIDAsString()
        };
    }
}

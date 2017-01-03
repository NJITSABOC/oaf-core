package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.AbstractNodeEntityTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.NodeConceptListTableModel;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author Chris O
 */
public class NodeConceptList<T extends Node> extends NodeEntityList<T, Concept> {
    
    private AbNConfiguration config;
    
    public NodeConceptList(AbstractNodeEntityTableModel<Concept> model, 
            AbNConfiguration config) {
        
        super(model);
        
        this.config = config;
    }
    
    public NodeConceptList(AbNConfiguration config) {
        this(new NodeConceptListTableModel(config), config);
    }
    
    @Override
    protected String getBorderText(Optional<ArrayList<Concept>> entities) {
        if(entities.isPresent()) {            
            return String.format("%s (%d)", 
                    config.getTextConfiguration().getConceptTypeName(true),
                    entities.get().size());
        } else {
            return "Concepts";
        }
    }
}

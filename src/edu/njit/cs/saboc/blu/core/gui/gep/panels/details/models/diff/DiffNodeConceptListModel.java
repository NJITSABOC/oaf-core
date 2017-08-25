package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.DiffNodeInstance;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.ConceptAddedToNode;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.ConceptMovedFromNode;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.ConceptMovedToNode;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.ConceptRemovedFromNode;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.NodeConceptChange;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.AbstractNodeEntityTableModel;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class DiffNodeConceptListModel<T extends Node> extends AbstractNodeEntityTableModel<Concept, T> {
    
    private final Map<Concept, NodeConceptChange> changedConcepts = new HashMap<>();
    
    private final AbNConfiguration config;
    
    public DiffNodeConceptListModel(AbNConfiguration config) {
        super(new String[] {
            config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(false),
            "Change",
            "Change Explanation"
        });
        
        this.config = config;
    }
    
    protected AbNConfiguration getConfiguration() {
        return config;
    }

    @Override
    public void clearCurrentNode() {
        super.clearCurrentNode();
        
        changedConcepts.clear();
    }

    @Override
    public void setCurrentNode(T node) {
        super.setCurrentNode(node);
        
        Set<NodeConceptChange> changes = ((DiffNodeInstance)node).getDiffNode().getChangeDetails().getConceptChanges();
        
        changes.forEach( (change) -> {
            changedConcepts.put(change.getConcept(), change);
        });
    }

    @Override
    protected Object[] createRow(Concept item) {
        
        if(changedConcepts.containsKey(item)) {
            NodeConceptChange change = changedConcepts.get(item);

            return new Object [] {
                item,
                change.getChangeName(config.getTextConfiguration()),
                change.getChangeDescription(config.getTextConfiguration())
            };

        } else {
            return new Object [] {
                item,
                "",
                ""
            };
        }
        
    }
}

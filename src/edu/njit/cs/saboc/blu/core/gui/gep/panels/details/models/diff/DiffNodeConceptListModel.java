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
                getChangeName(change),
                getChangeExplanation(change)
            };

        } else {
            return new Object [] {
                item,
                "",
                ""
            };
        }
        
    }
    
    protected String getChangeName(NodeConceptChange change) {
        
        switch(change.getChangeType()) {

            case AddedToOnt:
                return "Added to ontology";
                
            case AddedToHierarchy:
                return "Added to subhierarchy";
                
            case AddedToNode:
                return String.format("Added to %s (still summarized by other %s)", 
                        config.getTextConfiguration().getNodeTypeName(false).toLowerCase(),
                        config.getTextConfiguration().getNodeTypeName(true).toLowerCase());
                
            case MovedFromNode:
                return String.format("Moved from %s", 
                        config.getTextConfiguration().getNodeTypeName(false));
    

            case RemovedFromOnt:
                return "Removed from ontology";
                
            case RemovedFromHierarchy:
                return "Removed from subhierarchy";
                
            case RemovedFromNode:
                return String.format("Removed from %s (still in other %s)", 
                        config.getTextConfiguration().getNodeTypeName(false).toLowerCase(),
                        config.getTextConfiguration().getNodeTypeName(true).toLowerCase());
                
            case MovedToNode:
                return String.format("Moved to %s", 
                        config.getTextConfiguration().getNodeTypeName(false));
        }
        
        return "[UNKNOWN CHANGE TYPE]";
    }
    
    private String getChangeExplanation(NodeConceptChange change) {
        switch(change.getChangeType()) {

            case AddedToOnt:
                return "";
                
            case AddedToHierarchy:
                return "";
                
            case AddedToNode:
                ConceptAddedToNode addedToNodeChange = (ConceptAddedToNode)change;
                
                ArrayList<String> otherNodeNames = new ArrayList<>();

                addedToNodeChange.getOtherNodes().forEach( (node) -> {
                    otherNodeNames.add(node.getName());
                });
                
                Collections.sort(otherNodeNames);

                
                String nameList = otherNodeNames.toString();
                
                nameList = nameList.substring(1, nameList.length() - 1);
                
                return nameList;
                
            case MovedFromNode:
                ConceptMovedFromNode movedFromNodeChange = (ConceptMovedFromNode)change;
                
                return movedFromNodeChange.getMovedFrom().getName();
    
            case RemovedFromOnt:
                return "";
                
            case RemovedFromHierarchy:
                return "";
                
            case RemovedFromNode:
                ConceptRemovedFromNode removedFromNodeChange = (ConceptRemovedFromNode)change;
                
                ArrayList<String> remainingNodeNames = new ArrayList<>();

                removedFromNodeChange.getOtherNodes().forEach( (node) -> {
                    remainingNodeNames.add(node.getName());
                });
                
                Collections.sort(remainingNodeNames);
                
                String remainingNodeNamesStr = remainingNodeNames.toString();

                remainingNodeNamesStr = remainingNodeNamesStr.substring(1, remainingNodeNamesStr.length() - 1);
                
                return remainingNodeNamesStr;
                
            case MovedToNode:
                ConceptMovedToNode movedToNodeChange = (ConceptMovedToNode)change;
                
                return movedToNodeChange.getMovedTo().getName();
        }
        
        return "[UNKNOWN CHANGE TYPE]";
    }
}

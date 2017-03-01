package edu.njit.cs.saboc.blu.core.abn.diff.change.concepts;

import edu.njit.cs.saboc.blu.core.abn.diff.change.AbNChange;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 * Represents changes to the set of nodes(s) that summarize a concept.
 * 
 * @author Chris
 */
public abstract class NodeConceptChange implements AbNChange {
    
    private final Node node;
    private final Concept concept;
    private final NodeConceptSetChangeType changeType;
    
    protected NodeConceptChange(NodeConceptSetChangeType changeType, Node node, Concept concept) {
        this.changeType = changeType;
        this.node = node;
        this.concept = concept;
    }
    
    public NodeConceptSetChangeType getChangeType() {
        return changeType;
    }
    
    public Node getNode() {
        return node;
    }
    
    public Concept getConcept() {
        return concept;
    }

    @Override
    public String getChangeName() {
        switch(changeType) {
            case AddedToOnt:
                return "Added to ontology";
            case AddedToHierarchy:
                return "Added to hierarchy";
            case AddedToNode:
                return "Added to node";
            case MovedFromNode:
                return "Moved from other node(s)";
    
            case RemovedFromOnt:
                return "Removed from ontology";
            case RemovedFromHierarchy:
                return "Removed from hierarchy";
            case RemovedFromNode:
                return "Removed from node";
            case MovedToNode:
                return "Moved to other node(s)";
                
            default:
                return "[UNKNOWN CHANGE TYPE]";
        }
    }

    @Override
    public String getChangeDescription() {
        String conceptName = concept.getName();
        
        switch(changeType) {
            case AddedToOnt:
                return String.format("%s added to ontology", conceptName);
            case AddedToHierarchy:
                return String.format("%s added to hierarchy", conceptName);
            case AddedToNode:
                return String.format("%s added to node", conceptName);
            case MovedFromNode:
                return String.format("%s moved from other node(s)", conceptName);
    
            case RemovedFromOnt:
                return String.format("%s removed from ontology", conceptName);
            case RemovedFromHierarchy:
                return String.format("%s removed from hierarchy", conceptName);
            case RemovedFromNode:
                return String.format("%s removed from node", conceptName);
            case MovedToNode:
                return String.format("%s moved to other node(s)", conceptName);
                
            default:
                return "[UNKNOWN CHANGE TYPE]";
        }
    }
}

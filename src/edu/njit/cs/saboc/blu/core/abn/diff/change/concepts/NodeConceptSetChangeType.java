package edu.njit.cs.saboc.blu.core.abn.diff.change.concepts;

/**
 *
 * @author Chris O
 */
public enum NodeConceptSetChangeType {
    // Concept added to node
    AddedToOnt,
    AddedToHierarchy,
    AddedToNode,
    MovedFromNode,
    
    // Concept removed from node
    RemovedFromOnt,
    RemovedFromHierarchy,
    RemovedFromNode,
    MovedToNode
}

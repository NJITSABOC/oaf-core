package edu.njit.cs.saboc.blu.core.abn.diff;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.diff.change.NodeChange;
import edu.njit.cs.saboc.blu.core.abn.diff.change.NodeIntroduced;
import edu.njit.cs.saboc.blu.core.abn.diff.change.NodeModified;
import edu.njit.cs.saboc.blu.core.abn.diff.change.NodeRemoved;
import edu.njit.cs.saboc.blu.core.abn.diff.change.NodeUnmodified;
import edu.njit.cs.saboc.blu.core.abn.diff.change.childof.ChildOfAdded;
import edu.njit.cs.saboc.blu.core.abn.diff.change.childof.ChildOfChange;
import edu.njit.cs.saboc.blu.core.abn.diff.change.childof.ChildOfRemoved;
import edu.njit.cs.saboc.blu.core.abn.diff.change.childof.ChildOfUnchanged;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.ConceptAddedToNode;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.ConceptAddedToOntology;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.ConceptMovedFromNode;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.ConceptMovedIntoHierarchy;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.ConceptMovedOutOfHierarchy;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.ConceptMovedToNode;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.ConceptRemovedFromNode;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.ConceptRemovedFromOntology;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.NodeConceptChange;
import edu.njit.cs.saboc.blu.core.abn.diff.utils.SetUtilities;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class DiffAbstractionNetworkGenerator {

    public void diff(Ontology fromOnt, AbstractionNetwork<Node> fromAbN, Ontology toOnt, AbstractionNetwork<Node> toAbN) {

        Set<Concept> fromConcepts = fromOnt.getConceptHierarchy().getNodesInHierarchy();
        Set<Concept> toConcepts = toOnt.getConceptHierarchy().getNodesInHierarchy();
        
        Set<Concept> addedConcepts = SetUtilities.getSetDifference(toConcepts, fromConcepts);
        Set<Concept> removedConcepts = SetUtilities.getSetDifference(fromConcepts, toConcepts);

        Set<Node> fromNodes = fromAbN.getNodes();
        Set<Node> toNodes = toAbN.getNodes();
        
        Map<Concept, Set<Node>> fromConceptNodes = new HashMap<>();
        Map<Concept, Set<Node>> toConceptNodes = new HashMap<>();

        fromNodes.forEach((node) -> {
            node.getConcepts().forEach((concept) -> {
                if (!fromConceptNodes.containsKey(concept)) {
                    fromConceptNodes.put(concept, new HashSet<>());
                }

                fromConceptNodes.get(concept).add(node);
            });
        });

        toNodes.forEach((node) -> {
            node.getConcepts().forEach((concept) -> {

                if (!toConceptNodes.containsKey(concept)) {
                    toConceptNodes.put(concept, new HashSet<>());
                }

                toConceptNodes.get(concept).add(node);
            });
        });

        Set<Node> removedNodes = SetUtilities.getSetDifference(fromNodes, toNodes);
        Set<Node> introducedNodes = SetUtilities.getSetDifference(toNodes, fromNodes);
        
        Set<Node> transferredNodes = SetUtilities.getSetIntersection(toNodes, fromNodes);

        Map<Node, NodeChange> nodeChanges = new HashMap<>();

        removedNodes.forEach((removedNode) -> {

            Set<NodeConceptChange> conceptChanges = new HashSet<>();

            removedNode.getConcepts().forEach((concept) -> {
                if (removedConcepts.contains(concept)) {
                    conceptChanges.add(new ConceptRemovedFromOntology(removedNode, concept));
                } else {
                    if(toConceptNodes.containsKey(concept)) {
                        Set<Node> currentConceptNodes = toConceptNodes.get(concept);
                        Set<Node> previousConceptNodes = fromConceptNodes.get(concept);
                        
                        Set<Node> newConceptNodes = SetUtilities.getSetDifference(currentConceptNodes, previousConceptNodes);
                        
                        newConceptNodes.forEach((newNode) -> {
                            conceptChanges.add(new ConceptMovedToNode(removedNode, newNode, concept));
                        });
                    } else {
                        conceptChanges.add(new ConceptMovedOutOfHierarchy(removedNode, concept));
                    }
                }
            });
            
            Set<ChildOfChange> childOfChanges = new HashSet<>();
            
            fromAbN.getNodeHierarchy().getParents(removedNode).forEach( (parentNode) -> {
                childOfChanges.add(new ChildOfRemoved(removedNode, parentNode));
            });

            NodeRemoved removedNodeDetails = new NodeRemoved(removedNode, conceptChanges, childOfChanges);

            nodeChanges.put(removedNode, removedNodeDetails);
        });

        introducedNodes.forEach( (introducedNode) -> {
            Set<NodeConceptChange> conceptChanges = new HashSet<>();
            
            introducedNode.getConcepts().forEach((concept) -> {
                if(addedConcepts.contains(concept)) {
                    conceptChanges.add(new ConceptAddedToOntology(introducedNode, concept));
                } else {
                    if(fromConceptNodes.containsKey(concept)) {
                        Set<Node> currentConceptNodes = toConceptNodes.get(concept);
                        Set<Node> previousConceptNodes = fromConceptNodes.get(concept);
                        
                        Set<Node> oldConceptNodes = SetUtilities.getSetDifference(previousConceptNodes, currentConceptNodes);
                        
                        oldConceptNodes.forEach((oldNode) -> {
                             conceptChanges.add(new ConceptMovedFromNode(introducedNode, oldNode, concept));
                        });
                        
                    } else {
                        conceptChanges.add(new ConceptMovedIntoHierarchy(introducedNode, concept));
                    }
                }
            });
            
            Set<ChildOfChange> childOfChanges = new HashSet<>();
            
            toAbN.getNodeHierarchy().getParents(introducedNode).forEach( (parentNode) -> {
                childOfChanges.add(new ChildOfAdded(introducedNode, parentNode));
            });

            NodeIntroduced introducedNodeDetails = new NodeIntroduced(introducedNode, conceptChanges, childOfChanges);
            nodeChanges.put(introducedNode, introducedNodeDetails);
        });
        
        transferredNodes.forEach( (transferredNode) -> {
            Node toNode = transferredNode;
            
            Optional<Node> fromNodeResult = fromNodes.stream().filter( (node) -> {
               return node.equals(transferredNode);
            }).findFirst();
            
            Node fromNode = fromNodeResult.get(); // This is safe since the node is a transferred node...
            
            Set<Node> fromParentNodes = fromAbN.getNodeHierarchy().getParents(fromNode);
            Set<Node> toParentNodes = toAbN.getNodeHierarchy().getParents(toNode);
            
            Set<Node> addedParentNodes = SetUtilities.getSetDifference(toParentNodes, fromParentNodes);
            Set<Node> removedParentNodes = SetUtilities.getSetDifference(fromParentNodes, toParentNodes);
            Set<Node> unchangedParentNodes = SetUtilities.getSetIntersection(fromParentNodes, toParentNodes);
 
            Set<ChildOfChange> childOfChanges = new HashSet<>();
            
            addedParentNodes.forEach( (parent) -> {
                childOfChanges.add(new ChildOfAdded(toNode, parent));
            });
            
            removedParentNodes.forEach( (parent) -> {
                childOfChanges.add(new ChildOfRemoved(toNode, parent));
            });
            
            unchangedParentNodes.forEach((parent) -> {
                childOfChanges.add(new ChildOfUnchanged(toNode, parent));
            });
            
            if (toNode.strictEquals(fromNode)) {
                if (childOfChanges.isEmpty()) {
                    NodeUnmodified nodeUnmodifiedDetails = new NodeUnmodified(toNode);
                    nodeChanges.put(toNode, nodeUnmodifiedDetails);
                } else {
                     NodeModified nodeModifiedChanges = new NodeModified(toNode, Collections.emptySet(), childOfChanges);
                     nodeChanges.put(toNode, nodeModifiedChanges);
                }
            } else {
                Set<Concept> nodeRemovedConcepts = SetUtilities.getSetDifference(fromNode.getConcepts(), toNode.getConcepts());
                Set<Concept> nodeAddedConcepts = SetUtilities.getSetDifference(toNode.getConcepts(), fromNode.getConcepts());

                Set<NodeConceptChange> conceptChanges = new HashSet<>();

                nodeRemovedConcepts.forEach((concept) -> {

                    if (removedConcepts.contains(concept)) {
                        conceptChanges.add(new ConceptRemovedFromOntology(toNode, concept));
                    } else {
                        if (toConceptNodes.containsKey(concept)) {
                            Set<Node> currentConceptNodes = toConceptNodes.get(concept);
                            Set<Node> previousConceptNodes = fromConceptNodes.get(concept);
                            
                            Set<Node> transferredConceptNodes = SetUtilities.getSetIntersection(currentConceptNodes, previousConceptNodes);
                            
                            if (transferredConceptNodes.isEmpty()) {
                                Set<Node> newConceptNodes = SetUtilities.getSetDifference(currentConceptNodes, previousConceptNodes);

                                newConceptNodes.forEach((newNode) -> {
                                    conceptChanges.add(new ConceptMovedToNode(toNode, newNode, concept));
                                });
                                
                            } else {
                                conceptChanges.add(new ConceptRemovedFromNode(toNode, concept, transferredConceptNodes));
                            }
                        } else {
                            conceptChanges.add(new ConceptMovedOutOfHierarchy(toNode, concept));
                        }
                    }
                });
                
                nodeAddedConcepts.forEach((concept) -> {
                    if (addedConcepts.contains(concept)) {
                        conceptChanges.add(new ConceptAddedToOntology(toNode, concept));
                    } else {
                        if (fromConceptNodes.containsKey(concept)) {
                            Set<Node> currentConceptNodes = toConceptNodes.get(concept);
                            Set<Node> previousConceptNodes = fromConceptNodes.get(concept);
                            
                            Set<Node> transferredConceptNodes = SetUtilities.getSetIntersection(currentConceptNodes, previousConceptNodes);
                            
                            if (transferredConceptNodes.isEmpty()) {
                                Set<Node> oldConceptNodes = SetUtilities.getSetDifference(previousConceptNodes, currentConceptNodes);

                                oldConceptNodes.forEach((oldNode) -> {
                                    conceptChanges.add(new ConceptMovedFromNode(toNode, oldNode, concept));
                                });
                                
                            } else {
                                conceptChanges.add(new ConceptAddedToNode(toNode, concept, transferredConceptNodes));
                            }
                        } else {
                            conceptChanges.add(new ConceptMovedIntoHierarchy(toNode, concept));
                        }
                    }
                });
                
                NodeModified nodeModifiedChanges = new NodeModified(toNode, conceptChanges, childOfChanges);
                nodeChanges.put(toNode, nodeModifiedChanges);
            }
        });
    }
}

package edu.njit.cs.saboc.blu.core.abn.diff;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.diff.change.ChangeState;
import edu.njit.cs.saboc.blu.core.abn.diff.change.IntroduceNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.diff.change.ModifiedNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.diff.change.RemovedNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.diff.change.UnmodifiedNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.diff.change.childof.ChildOfChange;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.ConceptAddedToNode;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.ConceptAddedToOntology;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.ConceptMovedFromNode;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.ConceptMovedIntoHierarchy;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.ConceptMovedOutOfHierarchy;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.ConceptMovedToNode;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.ConceptRemovedFromNode;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.ConceptRemovedFromOntology;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.NodeConceptChange;
import edu.njit.cs.saboc.blu.core.abn.diff.explain.HierarchicalChanges;
import edu.njit.cs.saboc.blu.core.abn.diff.explain.OntologyChanges;
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
 * 
 * @param <NODE_T>
 * @param <ABN_T>
 */
public class DiffAbstractionNetworkGenerator {

    public AbstractionNetworkDiffResult diff(
            Ontology fromOnt, 
            AbstractionNetwork<Node> fromAbN, 
            Ontology toOnt, 
            AbstractionNetwork<Node> toAbN) {

        HierarchicalChanges ontDifferences = this.findHierarchicalChanges(
                fromOnt, 
                toOnt, 
                fromAbN, 
                toAbN);
        
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

        Map<Node, DiffNode> diffNodes = new HashMap<>();
        
        Map<Node, Set<Node>> parentNodes = new HashMap<>();

        removedNodes.forEach( (removedNode) -> {

            Set<NodeConceptChange> conceptChanges = new HashSet<>();

            removedNode.getConcepts().forEach((concept) -> {
                if (ontDifferences.getRemovedOntConcepts().contains(concept)) {
                    conceptChanges.add(new ConceptRemovedFromOntology(removedNode, concept));
                } else {
                    if(toConceptNodes.containsKey(concept)) {
                        Set<Node> currentConceptNodes = toConceptNodes.get(concept);
                        Set<Node> previousConceptNodes = fromConceptNodes.get(concept);
                        
                        Set<Node> newConceptNodes = SetUtilities.getSetDifference(currentConceptNodes, previousConceptNodes);
                        
                        if(newConceptNodes.isEmpty()) {
                            conceptChanges.add(new ConceptRemovedFromNode(removedNode, concept, currentConceptNodes));
                        } else {
                            newConceptNodes.forEach((newNode) -> {
                                conceptChanges.add(new ConceptMovedToNode(removedNode, newNode, concept));
                            });
                        }
                        
                    } else {
                        conceptChanges.add(new ConceptMovedOutOfHierarchy(removedNode, concept));
                    }
                }
            });
            
            Set<ChildOfChange> childOfChanges = new HashSet<>();
            
            parentNodes.put(removedNode, new HashSet<>());
            
            fromAbN.getNodeHierarchy().getParents(removedNode).forEach( (parentNode) -> {
                childOfChanges.add(new ChildOfChange(removedNode, parentNode, ChangeState.Removed));
                parentNodes.get(removedNode).add(parentNode);
            });

            RemovedNodeDetails removedNodeDetails = new RemovedNodeDetails(removedNode, conceptChanges, childOfChanges);

            diffNodes.put(removedNode, new RemovedNode(removedNode, removedNodeDetails));
        });

        introducedNodes.forEach( (introducedNode) -> {
            Set<NodeConceptChange> conceptChanges = new HashSet<>();
            
            introducedNode.getConcepts().forEach((concept) -> {
                if(ontDifferences.getAddedOntConcepts().contains(concept)) {
                    conceptChanges.add(new ConceptAddedToOntology(introducedNode, concept));
                } else {
                    if(fromConceptNodes.containsKey(concept)) {
                        Set<Node> currentConceptNodes = toConceptNodes.get(concept);
                        Set<Node> previousConceptNodes = fromConceptNodes.get(concept);
                        
                        Set<Node> oldConceptNodes = SetUtilities.getSetDifference(previousConceptNodes, currentConceptNodes);
                        
                        if(oldConceptNodes.isEmpty()) {
                            conceptChanges.add(new ConceptAddedToNode(introducedNode, concept, currentConceptNodes));
                        } else {
                            oldConceptNodes.forEach((oldNode) -> {
                                conceptChanges.add(new ConceptMovedFromNode(introducedNode, oldNode, concept));
                            });
                        }
                    } else {
                        conceptChanges.add(new ConceptMovedIntoHierarchy(introducedNode, concept));
                    }
                }
            });
            
            Set<ChildOfChange> childOfChanges = new HashSet<>();
            
            parentNodes.put(introducedNode, new HashSet<>());
            
            toAbN.getNodeHierarchy().getParents(introducedNode).forEach( (parentNode) -> {
                childOfChanges.add(new ChildOfChange(introducedNode, parentNode, ChangeState.Introduced));
                parentNodes.get(introducedNode).add(parentNode);
            });

            IntroduceNodeDetails introducedNodeDetails = new IntroduceNodeDetails(introducedNode, conceptChanges, childOfChanges);
            
            diffNodes.put(introducedNode, new IntroducedNode(introducedNode, introducedNodeDetails));
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
            Set<Node> allParentNodes = SetUtilities.getSetUnion(fromParentNodes, toParentNodes);
 
            Set<ChildOfChange> childOfChanges = new HashSet<>();
            
            parentNodes.put(toNode, new HashSet<>());
            allParentNodes.forEach( (parentNode) -> {
               parentNodes.get(toNode).add(parentNode);
            });
            
            addedParentNodes.forEach( (parent) -> {
                childOfChanges.add(new ChildOfChange(toNode, parent, ChangeState.Introduced));
            });
            
            removedParentNodes.forEach( (parent) -> {
                childOfChanges.add(new ChildOfChange(toNode, parent, ChangeState.Removed));
            });
                        
            if (toNode.strictEquals(fromNode)) {
                if (childOfChanges.isEmpty()) {
                    UnmodifiedNodeDetails nodeUnmodifiedDetails = new UnmodifiedNodeDetails(toNode);

                    diffNodes.put(toNode, new UnmodifiedNode(toNode, nodeUnmodifiedDetails));
                } else {
                    ModifiedNodeDetails nodeModifiedChanges = new ModifiedNodeDetails(toNode, Collections.emptySet(), childOfChanges);

                    diffNodes.put(toNode, new ModifiedNode(fromNode, toNode, nodeModifiedChanges));
                }
            } else {
                Set<Concept> nodeRemovedConcepts = SetUtilities.getSetDifference(fromNode.getConcepts(), toNode.getConcepts());
                Set<Concept> nodeAddedConcepts = SetUtilities.getSetDifference(toNode.getConcepts(), fromNode.getConcepts());

                Set<NodeConceptChange> conceptChanges = new HashSet<>();

                nodeRemovedConcepts.forEach( (concept) -> {

                    if (ontDifferences.getRemovedOntConcepts().contains(concept)) {
                        conceptChanges.add(new ConceptRemovedFromOntology(toNode, concept));
                    } else {
                        if (toConceptNodes.containsKey(concept)) {
                            Set<Node> currentConceptNodes = toConceptNodes.get(concept);
                            Set<Node> previousConceptNodes = fromConceptNodes.get(concept);
                            
                            Set<Node> transferredConceptNodes = SetUtilities.getSetIntersection(currentConceptNodes, previousConceptNodes);
                            
                            if (transferredConceptNodes.isEmpty()) {
                                Set<Node> newConceptNodes = SetUtilities.getSetDifference(currentConceptNodes, previousConceptNodes);

                                newConceptNodes.forEach( (newNode) -> {
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
                    if (ontDifferences.getAddedOntConcepts().contains(concept)) {
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
                
                ModifiedNodeDetails nodeModifiedChanges = new ModifiedNodeDetails(toNode, conceptChanges, childOfChanges);
                
                diffNodes.put(toNode, new ModifiedNode(fromNode, toNode, nodeModifiedChanges));
            }
        });
        
        Set<DiffNode> diffNodeSet = new HashSet<>(diffNodes.values());
        
        Set<DiffNode> removedRoots = new HashSet<>();
        Set<DiffNode> introducedRoots = new HashSet<>();
        Set<DiffNode> transferredRoots = new HashSet<>();
        
        fromAbN.getNodeHierarchy().getRoots().forEach( (root) -> {
            if(removedNodes.contains(root)) {
                removedRoots.add(diffNodes.get(root));
            }
        });
        
        toAbN.getNodeHierarchy().getRoots().forEach( (root) -> {
            if(introducedNodes.contains(root)) {
                introducedRoots.add(diffNodes.get(root));
            } else {
                transferredRoots.add(diffNodes.get(root));
            }
        });
        
        DiffNodeHierarchy diffHierarchy = new DiffNodeHierarchy(removedRoots, introducedRoots, transferredRoots);
        
        diffNodeSet.forEach( (diffNode) -> {
            
            Set<Node> diffNodeParents = parentNodes.get(diffNode.getChangeDetails().getChangedNode());
            Set<ChildOfChange> childOfChanges = diffNode.getChangeDetails().getChildOfChanges();
            
            diffNodeParents.forEach( (parent) -> {
                DiffNode parentDiffNode = diffNodes.get(parent);
                
                Optional<ChildOfChange> relatedChange = childOfChanges.stream().filter( (change) -> {
                   return change.getParentNode().equals(parent);
                }).findAny();
                
                if(relatedChange.isPresent()) {
                    ChildOfChange childOfChange = relatedChange.get();
                    
                    if(childOfChange.getChangeState() == ChangeState.Introduced) {
                        diffHierarchy.addEdge(diffNode, parentDiffNode, DiffEdge.EdgeState.Added);
                    } else {
                        diffHierarchy.addEdge(diffNode, parentDiffNode, DiffEdge.EdgeState.Removed);
                    }
                } else {
                    diffHierarchy.addEdge(diffNode, parentDiffNode, DiffEdge.EdgeState.Unmodified);
                }
                
            });
        });
        
        return new AbstractionNetworkDiffResult(fromAbN, toAbN, ontDifferences, diffNodeSet, diffHierarchy);
    }
    
    protected HierarchicalChanges findHierarchicalChanges(
            Ontology fromOnt, 
            Ontology toOnt,
            AbstractionNetwork<Node> fromAbN,
            AbstractionNetwork<Node> toAbN) {

        HierarchicalChanges changes = new HierarchicalChanges(fromOnt, fromAbN.getSourceHierarchy(), toOnt, toAbN.getSourceHierarchy());
        
        return changes;
    }
}

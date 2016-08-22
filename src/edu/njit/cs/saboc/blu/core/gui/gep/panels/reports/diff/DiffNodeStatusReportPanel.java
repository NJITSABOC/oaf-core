package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.diff;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.diff.DiffAbstractionNetworkInstance;
import edu.njit.cs.saboc.blu.core.abn.diff.DiffNodeInstance;
import edu.njit.cs.saboc.blu.core.abn.diff.change.ChangeState;
import edu.njit.cs.saboc.blu.core.abn.diff.utils.SetUtilities;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractEntityList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.AbNReportPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry.diff.DiffNodeStatusReport;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry.diff.DiffNodeStatusReport.DiffNodeStatus;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.models.diff.DiffNodeStatusTableModel;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class DiffNodeStatusReportPanel extends AbNReportPanel<AbstractionNetwork<SinglyRootedNode>> {
    
    private final AbstractEntityList<DiffNodeStatusReport> diffNodeStatusReportPanel; 
    
    public DiffNodeStatusReportPanel(
            AbNConfiguration config, 
            OAFAbstractTableModel<DiffNodeStatusReport> model) {
        
        super(config);
        
        this.setLayout(new BorderLayout());
        
        this.diffNodeStatusReportPanel = new AbstractEntityList<DiffNodeStatusReport> (model) {
                    
            public String getBorderText(Optional<ArrayList<DiffNodeStatusReport>> reports) {
               return "[DIFF NODE STATUS BORDER]";
            }
        };

        this.add(diffNodeStatusReportPanel, BorderLayout.CENTER);
    }
    
    public DiffNodeStatusReportPanel(AbNConfiguration config) {
        this(config, new DiffNodeStatusTableModel(config));
    }
    
    private class NodeMovedDetails {
        
        private final SinglyRootedNode from;
        private final SinglyRootedNode to;
        
        public NodeMovedDetails(SinglyRootedNode from, SinglyRootedNode to) {
            this.from = from;
            this.to = to;
        }
        
        public SinglyRootedNode getFrom() {
            return from;
        }
        
        public SinglyRootedNode getTo() {
            return to;
        }
        
        public boolean equals(Object o) {
            if(o instanceof NodeMovedDetails) {
                NodeMovedDetails other = (NodeMovedDetails)o;
                
                return this.getFrom().equals(other.getFrom()) && this.getTo().equals(other.getTo());
            }
            
            return false;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 71 * hash + Objects.hashCode(this.from);
            hash = 71 * hash + Objects.hashCode(this.to);
            
            return hash;
        }

    }
    
    @Override
    public void displayAbNReport(AbstractionNetwork<SinglyRootedNode> abn) {
        
        DiffAbstractionNetworkInstance<AbstractionNetwork<SinglyRootedNode>> diffAbN = 
                (DiffAbstractionNetworkInstance<AbstractionNetwork<SinglyRootedNode>>)abn;

        Map<ChangeState, Set<SinglyRootedNode>> diffNodesByType = new HashMap<>();
        
        abn.getNodes().forEach( (node) -> {
            DiffNodeInstance diffNode = (DiffNodeInstance)node;
            
            ChangeState changeState = diffNode.getDiffNode().getChangeDetails().getNodeState();

            if (!diffNodesByType.containsKey(changeState)) {
                diffNodesByType.put(changeState, new HashSet<>());
            }
            
            diffNodesByType.get(changeState).add(node);
        });
        
        Set<NodeMovedDetails> movedRoots = new HashSet<>();
        
        Set<SinglyRootedNode> removedRoots = new HashSet<>();
        Set<SinglyRootedNode> addedRoots = new HashSet<>();
        
        diffNodesByType.get(ChangeState.Introduced).forEach( (node) -> {
            
            Optional<SinglyRootedNode> matchingNode = diffNodesByType.get(ChangeState.Removed).stream().filter((otherNode) -> {
                return node.getRoot().equals(otherNode.getRoot());
            }).findAny();

            if(matchingNode.isPresent()) {
                movedRoots.add(new NodeMovedDetails(matchingNode.get(), node));
            } else {
                addedRoots.add(node);
            }
        });
        
        diffNodesByType.get(ChangeState.Removed).forEach( (node) -> {
            
            Optional<SinglyRootedNode> matchingNode = diffNodesByType.get(ChangeState.Introduced).stream().filter((otherNode) -> {
                return node.getRoot().equals(otherNode.getRoot());
            }).findAny();

            if(!matchingNode.isPresent()) {
                removedRoots.add(node);
            }
        });
        
        Set<NodeMovedDetails> movedExactly = new HashSet<>();
        Set<NodeMovedDetails> movedSubset = new HashSet<>();
        Set<NodeMovedDetails> movedSuperset = new HashSet<>();
        Set<NodeMovedDetails> movedDifferent = new HashSet<>();
        
        movedRoots.forEach( (moveDetails) -> {
            SinglyRootedNode from = moveDetails.getFrom();
            SinglyRootedNode to = moveDetails.getTo();
            
            Set<Concept> fromConcepts = from.getConcepts();
            Set<Concept> toConcepts = to.getConcepts();
            
            if(fromConcepts.equals(toConcepts)) {
                movedExactly.add(moveDetails);
            } else if(toConcepts.containsAll(fromConcepts)) {
                movedSuperset.add(moveDetails);
            } else if(fromConcepts.containsAll(toConcepts)) {
                movedSubset.add(moveDetails);
            } else {
                movedDifferent.add(moveDetails);
            }
        });

        Set<SinglyRootedNode> fromOnlyNewClasses = new HashSet<>();
        Set<SinglyRootedNode> fromOneNode = new HashSet<>();
        Set<SinglyRootedNode> fromMultipleNodes = new HashSet<>();
        
        addedRoots.forEach( (node) -> {
            Set<Concept> concepts = node.getConcepts();
            
            if(diffAbN.getOntologyDifferences().getAddedConcepts().containsAll(concepts)) {
                fromOnlyNewClasses.add(node);
            } else {
                Set<SinglyRootedNode> previousNodes = new HashSet<>();
                
                diffAbN.getFrom().getNodes().forEach( (fromNode) -> {
                    Set<Concept> fromNodeConcepts = fromNode.getConcepts();
                    
                    if(SetUtilities.getSetIntersection(concepts, fromNodeConcepts).isEmpty()) {
                        previousNodes.add(fromNode);
                    }
                });
                
                if(previousNodes.size() > 1) {
                    fromOneNode.add(node);
                } else {
                    fromMultipleNodes.add(node);
                }
            }
        });
        
        Set<SinglyRootedNode> removedFromOnt = new HashSet<>();
        Set<SinglyRootedNode> toOneNode = new HashSet<>();
        Set<SinglyRootedNode> toMultipleNodes = new HashSet<>();
        
        removedRoots.forEach( (node) -> {
            Set<Concept> concepts = node.getConcepts();
            
            if(diffAbN.getOntologyDifferences().getRemovedConcepts().containsAll(concepts)) {
                removedFromOnt.add(node);
            } else {
                Set<SinglyRootedNode> currentNodes = new HashSet<>();
                
                diffAbN.getTo().getNodes().forEach( (toNode) -> {
                    Set<Concept> toConcepts = toNode.getConcepts();
                    
                    if (!SetUtilities.getSetIntersection(concepts, toConcepts).isEmpty()) {
                        currentNodes.add(node);
                    }
                });
                
                if(currentNodes.size() > 1) {
                    toMultipleNodes.add(node);
                } else {
                    toOneNode.add(node);
                }
            }
        });
        
        ArrayList<DiffNodeStatusReport> reportEntries = new ArrayList<>();
        
        movedExactly.forEach( (details) -> {
            reportEntries.add(new DiffNodeStatusReport(details.getTo(), DiffNodeStatus.MovedExactly));
        });
        
        movedSubset.forEach( (details) -> {
            reportEntries.add(new DiffNodeStatusReport(details.getTo(), DiffNodeStatus.MovedSubset));
        });
        
        movedSuperset.forEach( (details) -> {
            reportEntries.add(new DiffNodeStatusReport(details.getTo(), DiffNodeStatus.MovedSuperset));
        });
        
        movedDifferent.forEach( (details) -> {
            reportEntries.add(new DiffNodeStatusReport(details.getTo(), DiffNodeStatus.MovedDifference));
        });
        
        fromOnlyNewClasses.forEach( (node) -> {
            reportEntries.add(new DiffNodeStatusReport(node, DiffNodeStatus.IntroducedFromNew));
        });
        
        fromOneNode.forEach( (node) -> {
            reportEntries.add(new DiffNodeStatusReport(node, DiffNodeStatus.IntroducedFromOneNode));
        });
        
        fromMultipleNodes.forEach( (node) -> {
            reportEntries.add(new DiffNodeStatusReport(node, DiffNodeStatus.IntroducedFromMultipleNodes));
        });
        
        removedFromOnt.forEach( (node) -> {
            reportEntries.add(new DiffNodeStatusReport(node, DiffNodeStatus.RemovedFromOnt));
        });
        
        toOneNode.forEach( (node) -> {
            reportEntries.add(new DiffNodeStatusReport(node, DiffNodeStatus.RemovedToOneNode));
        });
        
        toMultipleNodes.forEach( (node) -> {
            reportEntries.add(new DiffNodeStatusReport(node, DiffNodeStatus.RemovedToMultipleNodes));
        });
        
        reportEntries.sort( (a, b) -> {
            return a.getNode().getName().compareTo(b.getNode().getName());
        });
        
        diffNodeStatusReportPanel.setContents(reportEntries);
    }
}
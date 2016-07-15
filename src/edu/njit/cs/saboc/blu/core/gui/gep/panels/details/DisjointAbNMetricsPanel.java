package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry.OverlappingDetailsEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry.OverlappingNodeCombinationsEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry.OverlappingNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionAdapter;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OverlappingCombinationsTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OverlappingDetailsTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OverlappingNodeTableModel;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

/**
 *
 * @author Chris O
 */
public class DisjointAbNMetricsPanel extends BaseNodeInformationPanel {
    
    private class NodeOverlapMetrics {
        
        private final HashMap<Set<Node>, Set<DisjointNode>> intersectionGroups;
        
        private final Set<Concept> overlappingConcepts = new HashSet<>();
        
        private final Node overlappingGroup;
        
        private final DisjointNode disjointBasis;
        
        public NodeOverlapMetrics(
                Node overlappingGroup, 
                DisjointNode disjointBasis,
                HashMap<Set<Node>, Set<DisjointNode>> intersectionGroups) {
            
            this.intersectionGroups = intersectionGroups;
            this.overlappingGroup = overlappingGroup;
            this.disjointBasis = disjointBasis;
            
            Set<DisjointNode> disjointGroups = getAllIntersectionNodes();
            
            for(DisjointNode group : disjointGroups) {
                overlappingConcepts.addAll(group.getConcepts());
            }
        }
        
        public Set<Concept> getOverlappingConcepts() {
            return overlappingConcepts;
        }
        
        public Set<Set<Node>> getIntersections() {
            return intersectionGroups.keySet();
        }
        
        public Collection<Set<DisjointNode>> intersectionDisjointGroups() {
            return intersectionGroups.values();
        } 
        
        public Node getOverlappingGroup() {
            return overlappingGroup;
        }
        
        public DisjointNode getBasisDisjointGroup() {
            return disjointBasis;
        }
        
        public final Set<DisjointNode> getAllIntersectionNodes() {
            
            Set<DisjointNode> disjointGroups = new HashSet<>();
            
            for(Set<DisjointNode> groups : intersectionGroups.values()) {
                disjointGroups.addAll(groups);
            }
            
            return disjointGroups;
        }
        
        public Set<DisjointNode> groupsOverlapWith(Node group) {
            Set<DisjointNode> groups = new HashSet<>();
            
            for(Set<Node> overlaps : intersectionGroups.keySet()) {
                if(overlaps.contains(group)) {
                    groups.addAll(intersectionGroups.get(overlaps));
                }
            }
            
            return groups;
        }
    }
    
    private class DisjointAbNOverlapMetrics {

        private final HashMap<Node, NodeOverlapMetrics> groupMetrics = new HashMap<>();

        public DisjointAbNOverlapMetrics(DisjointAbstractionNetwork disjointAbN) {
            Set<Node> overlappingGroups = disjointAbN.getOverlappingNodes();

            Set<DisjointNode> disjointGroups = disjointAbN.getAllDisjointNodes();

            for (Node group : overlappingGroups) {

                DisjointNode disjointBasis = null;

                HashMap<Set<Node>, Set<DisjointNode>> intersectionGroups = new HashMap<>();

                for (DisjointNode disjointGroup : disjointGroups) {
                    if (disjointGroup.getOverlaps().contains(group)) {
                        if (disjointGroup.getOverlaps().size() == 1) {
                            disjointBasis = disjointGroup;
                        } else {
                            Set<Node> overlaps = disjointGroup.getOverlaps();

                            if (!intersectionGroups.containsKey(overlaps)) {
                                intersectionGroups.put(overlaps, new HashSet<>());
                            }

                            intersectionGroups.get(overlaps).add(disjointGroup);
                        }
                    }
                }

                groupMetrics.put(group, new NodeOverlapMetrics(group, disjointBasis, intersectionGroups));
            }
        }

        public HashMap<Node, NodeOverlapMetrics> getGroupMetrics() {
            return groupMetrics;
        }
    }

    private final AbstractEntityList<OverlappingNodeEntry> overlappingGroupTable;
    private final AbstractEntityList<OverlappingDetailsEntry> overlappingDetailsTable;
    private final AbstractEntityList<OverlappingNodeCombinationsEntry> overlappingCombinationsTable; 
    
    private final PartitionedAbNConfiguration configuration;
    
    private final JSplitPane splitPane;
    
    private Optional<DisjointAbNOverlapMetrics> currentMetrics = Optional.empty();

    public DisjointAbNMetricsPanel(PartitionedAbNConfiguration configuration) {

        this.configuration = configuration;
        
        this.setLayout(new BorderLayout());
        
        this.splitPane = NodeDetailsPanel.createStyledSplitPane(JSplitPane.VERTICAL_SPLIT);
        
        overlappingGroupTable = new AbstractEntityList<OverlappingNodeEntry>(new OverlappingNodeTableModel(configuration)) {
            @Override
            protected String getBorderText(Optional<ArrayList<OverlappingNodeEntry>> entities) {
                String base = String.format("Overlapping %s", configuration.getTextConfiguration().getNodeTypeName(true));
                
                if(entities.isPresent()) {
                    return String.format("%s (%d)", base, entities.get().size());
                } else {
                    return String.format("%s (0)", base);
                }
            }
        };
        
        overlappingGroupTable.addEntitySelectionListener(new EntitySelectionAdapter<OverlappingNodeEntry> () {

            @Override
            public void entityClicked(OverlappingNodeEntry entity) {
                if (currentMetrics.isPresent()) {
                    displayOverlappingDetailsFor(entity.getOverlappingNode());
                }
            }

            @Override
            public void noEntitySelected() {
                overlappingDetailsTable.clearContents();
            }
        });
        
        overlappingDetailsTable = new AbstractEntityList<OverlappingDetailsEntry>(new OverlappingDetailsTableModel(configuration)) {
            @Override
            protected String getBorderText(Optional<ArrayList<OverlappingDetailsEntry>> entities) {
                
                if(entities.isPresent()) {
                    return String.format("Overlaps With (%d)", entities.get().size());
                } else {
                    return "Overlaps With (0)";
                }
            }
        };
        
        overlappingCombinationsTable = new AbstractEntityList<OverlappingNodeCombinationsEntry>(new OverlappingCombinationsTableModel(configuration)) {
            @Override
            protected String getBorderText(Optional<ArrayList<OverlappingNodeCombinationsEntry>> entities) {
                if(entities.isPresent()) {
                    return String.format("Overlapping Combinations (%d)", entities.get().size());
                } else {
                    return "Overlapping Combinations (0)";
                }
            }
        };
        
        JTabbedPane overlapDetailsTabs = new JTabbedPane();
        overlapDetailsTabs.addTab("Individual Overlaps", overlappingDetailsTable);
        overlapDetailsTabs.addTab("Combinations", overlappingCombinationsTable);
        
        splitPane.setTopComponent(overlappingGroupTable);
        splitPane.setBottomComponent(overlapDetailsTabs);
        
        this.add(splitPane);
    }
    
    private void displayOverlappingDetailsFor(Node selectedNode) {
        DisjointAbNOverlapMetrics metrics = currentMetrics.get();

        NodeOverlapMetrics overlapMetrics = metrics.getGroupMetrics().get(selectedNode);

        Set<DisjointNode> intersectionNodes = overlapMetrics.getAllIntersectionNodes();

        HashMap<Node, Set<DisjointNode>> commonDisjointGroups = new HashMap<>();

        for (DisjointNode group : intersectionNodes) {
            Set<Node> overlappingGroups = group.getOverlaps();

            for (Node overlappingGroup : overlappingGroups) {
                if (!overlappingGroup.equals(selectedNode)) {
                    if (!commonDisjointGroups.containsKey(overlappingGroup)) {
                        commonDisjointGroups.put(overlappingGroup, new HashSet<>());
                    }

                    commonDisjointGroups.get(overlappingGroup).add(group);
                }
            }
        }

        ArrayList<OverlappingDetailsEntry> entries = new ArrayList<>();

        commonDisjointGroups.forEach((node, disjointNodes) -> {
            entries.add(new OverlappingDetailsEntry(node, disjointNodes));
        });

        Collections.sort(entries, (a, b) -> {
            int aCount = a.getDisjointGroups().size();
            int bCount = b.getDisjointGroups().size();
            
            if (aCount == bCount) {
                return a.getOverlappingNode().getName().compareToIgnoreCase(b.getOverlappingNode().getName());
            } else {
                return bCount - aCount;
            }
        });

        overlappingDetailsTable.setContents(entries);
        
        HashMap<Set<Node>, Set<DisjointNode>> disjointGroupsByOverlap = new HashMap<>();
        
        intersectionNodes.forEach( (disjointNode) -> {
            if(!disjointGroupsByOverlap.containsKey(disjointNode.getOverlaps())) {
                disjointGroupsByOverlap.put(disjointNode.getOverlaps(), new HashSet<>());
            }
            
            disjointGroupsByOverlap.get(disjointNode.getOverlaps()).add(disjointNode);
        });

        ArrayList<OverlappingNodeCombinationsEntry> combinationEntries = new ArrayList<>();
        
        disjointGroupsByOverlap.forEach((overlappingGroups, disjointNodeSet) -> {
            combinationEntries.add(new OverlappingNodeCombinationsEntry(selectedNode, disjointNodeSet));
        });
        
        Collections.sort(combinationEntries, (a, b) -> {
            if (a.getOtherOverlappingNodes().size() == b.getOtherOverlappingNodes().size()) {
                
                // Sort by name of overlaps
                if (a.getOverlappingConcepts().size() == b.getOverlappingConcepts().size()) { 
                    ArrayList<String> aOverlappingNames = new ArrayList<>();
                    
                    a.getOtherOverlappingNodes().forEach((overlappingGroup) -> {
                        aOverlappingNames.add(overlappingGroup.getName());
                    });
                    
                    ArrayList<String> bOverlappingNames = new ArrayList<>();
                    
                    b.getOtherOverlappingNodes().forEach((overlappingGroup) -> {
                        bOverlappingNames.add(overlappingGroup.getName());
                    });
                    
                    Collections.sort(aOverlappingNames);
                    Collections.sort(bOverlappingNames);
                    
                    for (int c = 0; c < aOverlappingNames.size(); c++) {
                        String aName = aOverlappingNames.get(c);
                        String bName = bOverlappingNames.get(c);
                        
                        int compare = aName.compareTo(bName);
                        
                        if (compare != 0) {
                            return compare;
                        }
                    }
                    
                    return 0; // Though this shouldn't happen...
                    
                } else {
                    return a.getOverlappingConcepts().size() - b.getOverlappingConcepts().size();
                }
            } else {
                return a.getOtherOverlappingNodes().size() - b.getOtherOverlappingNodes().size();
            }
        });
        
        overlappingCombinationsTable.setContents(combinationEntries);
    }
    
    @Override
    public void setContents(Node node) {
        PartitionedNode partitionedNode = (PartitionedNode)node;
        
        splitPane.setDividerLocation(300);
        
        DisjointAbstractionNetwork disjointAbN = configuration.getDisjointAbstractionNetworkFor(partitionedNode);
        
        ArrayList<Node> overlappingGroups = new ArrayList<>(disjointAbN.getOverlappingNodes());
        
        currentMetrics = Optional.of(new DisjointAbNOverlapMetrics(disjointAbN));
        
        DisjointAbNOverlapMetrics metrics = currentMetrics.get();

        Collections.sort(overlappingGroups, (a, b) -> {
            int aOverlapCount = metrics.getGroupMetrics().get(a).getOverlappingConcepts().size();
            int bOverlapCount = metrics.getGroupMetrics().get(b).getOverlappingConcepts().size();
            
            if (aOverlapCount == bOverlapCount) {
                return a.getName().compareToIgnoreCase(b.getName());
            }
            
            return bOverlapCount - aOverlapCount;
        });
        
        ArrayList<OverlappingNodeEntry> entries = new ArrayList<>();
        
        overlappingGroups.forEach((overlappingNode) -> {
            entries.add(new OverlappingNodeEntry(overlappingNode, metrics.getGroupMetrics().get(overlappingNode).getOverlappingConcepts()));
        });
        
        overlappingGroupTable.setContents(entries);
    }

    @Override
    public void clearContents() {
        currentMetrics = Optional.empty();
        
        overlappingGroupTable.clearContents();
        
        overlappingDetailsTable.clearContents();
        overlappingCombinationsTable.clearContents();
    }
}

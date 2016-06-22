package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import SnomedShared.generic.GenericConceptGroup;
import SnomedShared.generic.GenericGroupContainer;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUDisjointableConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry.OverlappingDetailsEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry.OverlappingNodeCombinationsEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry.OverlappingNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionAdapter;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.BLUAbstractOverlappingCombinationsTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OverlappingDetailsTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OverlappingNodeTableModel;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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
public class AbstractDisjointAbNMetricsPanel<
        CONTAINER_T extends GenericGroupContainer,
        DISJOINTABN_T extends DisjointAbstractionNetwork,
        GROUP_T extends GenericConceptGroup,
        DISJOINTGROUP_T extends DisjointNode,
        CONCEPT_T> extends BaseNodeInformationPanel<CONTAINER_T> {
    
    private class GroupOverlapMetrics {
        
        private final HashMap<HashSet<GROUP_T>, HashSet<DISJOINTGROUP_T>> intersectionGroups;
        
        private final HashSet<CONCEPT_T> overlappingConcepts = new HashSet<>();
        
        private final GROUP_T overlappingGroup;
        
        private final DISJOINTGROUP_T disjointBasis;
        
        public GroupOverlapMetrics(GROUP_T overlappingGroup, DISJOINTGROUP_T disjointBasis,
                HashMap<HashSet<GROUP_T>, HashSet<DISJOINTGROUP_T>> intersectionGroups) {
            
            this.intersectionGroups = intersectionGroups;
            this.overlappingGroup = overlappingGroup;
            this.disjointBasis = disjointBasis;
            
            HashSet<DISJOINTGROUP_T> disjointGroups = getAllIntersectionGroups();
            
            for(DISJOINTGROUP_T group : disjointGroups) {
                overlappingConcepts.addAll(group.getConceptsAsList());
            }
        }
        
        public HashSet<CONCEPT_T> getOverlappingConcepts() {
            return overlappingConcepts;
        }
        
        public Set<HashSet<GROUP_T>> getIntersections() {
            return intersectionGroups.keySet();
        }
        
        public Collection<HashSet<DISJOINTGROUP_T>> intersectionDisjointGroups() {
            return intersectionGroups.values();
        } 
        
        public GenericConceptGroup getOverlappingGroup() {
            return overlappingGroup;
        }
        
        public DisjointNode getBasisDisjointGroup() {
            return disjointBasis;
        }
        
        public HashSet<DISJOINTGROUP_T> getAllIntersectionGroups() {
            
            HashSet<DISJOINTGROUP_T> disjointGroups = new HashSet<>();
            
            for(HashSet<DISJOINTGROUP_T> groups : intersectionGroups.values()) {
                disjointGroups.addAll(groups);
            }
            
            return disjointGroups;
        }
        
        public HashSet<DISJOINTGROUP_T> groupsOverlapWith(GROUP_T group) {
            HashSet<DISJOINTGROUP_T> groups = new HashSet<>();
            
            for(HashSet<GROUP_T> overlaps : intersectionGroups.keySet()) {
                if(overlaps.contains(group)) {
                    groups.addAll(intersectionGroups.get(overlaps));
                }
            }
            
            return groups;
        }
    }
    
    private class DisjointAbNOverlapMetrics {

        private final HashMap<GROUP_T, GroupOverlapMetrics> groupMetrics = new HashMap<>();

        public DisjointAbNOverlapMetrics(DISJOINTABN_T disjointAbN) {
            HashSet<GROUP_T> overlappingGroups = disjointAbN.getOverlappingGroups();

            HashSet<DISJOINTGROUP_T> disjointGroups = disjointAbN.getDisjointGroups();

            for (GROUP_T group : overlappingGroups) {

                DISJOINTGROUP_T disjointBasis = null;

                HashMap<HashSet<GROUP_T>, HashSet<DISJOINTGROUP_T>> intersectionGroups = new HashMap<>();

                for (DISJOINTGROUP_T disjointGroup : disjointGroups) {
                    if (disjointGroup.getOverlaps().contains(group)) {
                        if (disjointGroup.getOverlaps().size() == 1) {
                            disjointBasis = disjointGroup;
                        } else {
                            HashSet<GROUP_T> overlaps = disjointGroup.getOverlaps();

                            if (!intersectionGroups.containsKey(overlaps)) {
                                intersectionGroups.put(overlaps, new HashSet<>());
                            }

                            intersectionGroups.get(overlaps).add(disjointGroup);
                        }
                    }
                }

                groupMetrics.put(group, new GroupOverlapMetrics(group, disjointBasis, intersectionGroups));
            }
        }

        public HashMap<GROUP_T, GroupOverlapMetrics> getGroupMetrics() {
            return groupMetrics;
        }
    }

    private final AbstractEntityList<OverlappingNodeEntry<GROUP_T, CONCEPT_T>> overlappingGroupTable;
    protected final OverlappingNodeTableModel<GROUP_T, CONCEPT_T> overlappingGroupTableModel;
    
    private final AbstractEntityList<OverlappingDetailsEntry<GROUP_T, DISJOINTGROUP_T>> overlappingDetailsTable;
    protected final OverlappingDetailsTableModel<GROUP_T, DISJOINTGROUP_T, CONCEPT_T> overlappingDetailsTableModel;
    
    private final AbstractEntityList<OverlappingNodeCombinationsEntry<GROUP_T, DISJOINTGROUP_T, CONCEPT_T>> overlappingCombinationsTable; 
    protected final BLUAbstractOverlappingCombinationsTableModel<GROUP_T, DISJOINTGROUP_T, CONCEPT_T> overlappingCombinationsTableModel;
    
    protected final BLUDisjointableConfiguration configuration;
    
    private final JSplitPane splitPane;
    
    private Optional<DisjointAbNOverlapMetrics> currentMetrics = Optional.empty();

    protected AbstractDisjointAbNMetricsPanel(
            OverlappingNodeTableModel<GROUP_T, CONCEPT_T> overlappingGroupTableModel,
            OverlappingDetailsTableModel<GROUP_T, DISJOINTGROUP_T, CONCEPT_T> overlappingDetailsTableModel,
            BLUAbstractOverlappingCombinationsTableModel<GROUP_T, DISJOINTGROUP_T, CONCEPT_T> overlappingCombinationsTableModel,
            BLUDisjointableConfiguration configuration) {
        
        this.overlappingGroupTableModel = overlappingGroupTableModel;
        this.overlappingDetailsTableModel = overlappingDetailsTableModel;
        this.overlappingCombinationsTableModel = overlappingCombinationsTableModel;
        this.configuration = configuration;
        
        this.setLayout(new BorderLayout());
        
        this.splitPane = NodeDetailsPanel.createStyledSplitPane(JSplitPane.VERTICAL_SPLIT);
        
        overlappingGroupTable = new AbstractEntityList<OverlappingNodeEntry<GROUP_T, CONCEPT_T>>(this.overlappingGroupTableModel) {
            @Override
            protected String getBorderText(Optional<ArrayList<OverlappingNodeEntry<GROUP_T, CONCEPT_T>>> entities) {
                if(entities.isPresent()) {
                    return String.format("Overlapping %s (%d)", configuration.getTextConfiguration().getGroupTypeName(true), entities.get().size());
                } else {
                    return String.format("Overlapping %s", configuration.getTextConfiguration().getGroupTypeName(true));
                }
            }
        };
        
        overlappingGroupTable.addEntitySelectionListener(new EntitySelectionAdapter<OverlappingNodeEntry<GROUP_T, CONCEPT_T>> () {

            @Override
            public void entityClicked(OverlappingNodeEntry<GROUP_T, CONCEPT_T> entity) {
                if (currentMetrics.isPresent()) {
                    displayOverlappingDetailsFor(entity.getOverlappingGroup());
                }
            }

            @Override
            public void noEntitySelected() {
                overlappingDetailsTable.clearContents();
            }
        });
        
        overlappingDetailsTable = new AbstractEntityList<OverlappingDetailsEntry<GROUP_T, DISJOINTGROUP_T>>(this.overlappingDetailsTableModel) {
            @Override
            protected String getBorderText(Optional<ArrayList<OverlappingDetailsEntry<GROUP_T, DISJOINTGROUP_T>>> entities) {
                if(entities.isPresent()) {
                    return String.format("Overlaps With (%d)", entities.get().size());
                } else {
                    return "Overlaps With";
                }
            }
        };
        
        overlappingCombinationsTable = new AbstractEntityList<OverlappingNodeCombinationsEntry<GROUP_T, DISJOINTGROUP_T, CONCEPT_T>>(this.overlappingCombinationsTableModel) {
            @Override
            protected String getBorderText(Optional<ArrayList<OverlappingNodeCombinationsEntry<GROUP_T, DISJOINTGROUP_T, CONCEPT_T>>> entities) {
                if(entities.isPresent()) {
                    return String.format("Overlapping Combinations (%d)", entities.get().size());
                } else {
                    return "Overlapping Combinations";
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
    
    private void displayOverlappingDetailsFor(GROUP_T selectedGroup) {
        DisjointAbNOverlapMetrics metrics = currentMetrics.get();

        GroupOverlapMetrics groupMetrics = metrics.getGroupMetrics().get(selectedGroup);

        HashSet<DISJOINTGROUP_T> intersectionGroups = groupMetrics.getAllIntersectionGroups();

        final HashMap<GROUP_T, HashSet<DISJOINTGROUP_T>> commonDisjointGroups = new HashMap<>();

        for (DISJOINTGROUP_T group : intersectionGroups) {
            HashSet<GROUP_T> overlappingGroups = group.getOverlaps();

            for (GROUP_T overlappingGroup : overlappingGroups) {
                if (!overlappingGroup.equals(selectedGroup)) {
                    if (!commonDisjointGroups.containsKey(overlappingGroup)) {
                        commonDisjointGroups.put(overlappingGroup, new HashSet<>());
                    }

                    commonDisjointGroups.get(overlappingGroup).add(group);
                }
            }
        }

        ArrayList<OverlappingDetailsEntry<GROUP_T, DISJOINTGROUP_T>> entries = new ArrayList<>();

        commonDisjointGroups.forEach((GROUP_T group, HashSet<DISJOINTGROUP_T> disjointGroups) -> {
            entries.add(new OverlappingDetailsEntry<>(group, disjointGroups));
        });

        Collections.sort(entries, new Comparator<OverlappingDetailsEntry<GROUP_T, DISJOINTGROUP_T>>() {
            public int compare(OverlappingDetailsEntry<GROUP_T, DISJOINTGROUP_T> a, OverlappingDetailsEntry<GROUP_T, DISJOINTGROUP_T> b) {
                int aCount = a.getDisjointGroups().size();
                int bCount = b.getDisjointGroups().size();

                if (aCount == bCount) {
                    return a.getOverlappingGroup().getRoot().getName().compareToIgnoreCase(b.getOverlappingGroup().getRoot().getName());
                } else {
                    return bCount - aCount;
                }
            }
        });

        overlappingDetailsTableModel.setContents(entries);
        
        HashMap<HashSet<GROUP_T>, HashSet<DISJOINTGROUP_T>> disjointGroupsByOverlap = new HashMap<>();
        
        intersectionGroups.forEach( (DISJOINTGROUP_T disjointGroup) -> {
            if(!disjointGroupsByOverlap.containsKey(disjointGroup.getOverlaps())) {
                disjointGroupsByOverlap.put(disjointGroup.getOverlaps(), new HashSet<>());
            }
            
            disjointGroupsByOverlap.get(disjointGroup.getOverlaps()).add(disjointGroup);
        });

        ArrayList<OverlappingNodeCombinationsEntry<GROUP_T, DISJOINTGROUP_T, CONCEPT_T>> combinationEntries = new ArrayList<>();
        
        disjointGroupsByOverlap.forEach((HashSet<GROUP_T> overlappingGroups, HashSet<DISJOINTGROUP_T> disjointGroups) -> {
            combinationEntries.add(new OverlappingNodeCombinationsEntry<>(selectedGroup, disjointGroups));
        });
        
        Collections.sort(combinationEntries, new Comparator<OverlappingNodeCombinationsEntry<GROUP_T, DISJOINTGROUP_T, CONCEPT_T>>() {
            public int compare(OverlappingNodeCombinationsEntry<GROUP_T, DISJOINTGROUP_T, CONCEPT_T> a, 
                    OverlappingNodeCombinationsEntry<GROUP_T, DISJOINTGROUP_T, CONCEPT_T> b) {

                if (a.getOtherOverlappingGroups().size() == b.getOtherOverlappingGroups().size()) {
                    if (a.getOverlappingConcepts().size() == b.getOverlappingConcepts().size()) {
                        ArrayList<String> aOverlappingNames = new ArrayList<>();

                        a.getOtherOverlappingGroups().forEach((GROUP_T overlappingGroup) -> {
                            aOverlappingNames.add(overlappingGroup.getRoot().getName());
                        });

                        ArrayList<String> bOverlappingNames = new ArrayList<>();

                        b.getOtherOverlappingGroups().forEach((GROUP_T overlappingGroup) -> {
                            bOverlappingNames.add(overlappingGroup.getRoot().getName());
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
                    return a.getOtherOverlappingGroups().size() - b.getOtherOverlappingGroups().size();
                }
            }
        });
        
        overlappingCombinationsTable.setContents(combinationEntries);
    }
    
    @Override
    public void setContents(CONTAINER_T container) {
        splitPane.setDividerLocation(300);
        
        DISJOINTABN_T disjointAbN = (DISJOINTABN_T)configuration.getDataConfiguration().createDisjointAbN(container);
        
        ArrayList<GROUP_T> overlappingGroups = new ArrayList<>(disjointAbN.getOverlappingGroups());
        
        currentMetrics = Optional.of(new DisjointAbNOverlapMetrics(disjointAbN));
        
        DisjointAbNOverlapMetrics metrics = currentMetrics.get();

        Collections.sort(overlappingGroups, new Comparator<GROUP_T>() {
            public int compare(GROUP_T a, GROUP_T b) {
                int aOverlapCount = metrics.getGroupMetrics().get(a).getOverlappingConcepts().size();
                int bOverlapCount = metrics.getGroupMetrics().get(b).getOverlappingConcepts().size();

                if (aOverlapCount == bOverlapCount) {
                    return a.getRoot().getName().compareToIgnoreCase(b.getRoot().getName());
                }

                return bOverlapCount - aOverlapCount;
            }
        });
        
        ArrayList<OverlappingNodeEntry<GROUP_T, CONCEPT_T>> entries = new ArrayList<>();
        
        overlappingGroups.forEach((GROUP_T overlappingGroup) -> {
            entries.add(new OverlappingNodeEntry<>(overlappingGroup, metrics.getGroupMetrics().get(overlappingGroup).getOverlappingConcepts()));
        });
        
        overlappingGroupTableModel.setContents(entries);
    }

    @Override
    public void clearContents() {
        currentMetrics = Optional.empty();
        
        overlappingGroupTable.clearContents();
        
        overlappingDetailsTable.clearContents();
        overlappingCombinationsTable.clearContents();
    }
}

package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import SnomedShared.generic.GenericConceptGroup;
import SnomedShared.generic.GenericGroupContainer;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.nodes.DisjointGenericConceptGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUDisjointableAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry.OverlappingDetailsEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry.OverlappingGroupEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.BLUAbstractOverlappingDetailsTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.BLUAbstractOverlappingGroupTableModel;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

/**
 *
 * @author Chris O
 */
public class AbstractDisjointAbNMetricsPanel<
        CONTAINER_T extends GenericGroupContainer,
        DISJOINTABN_T extends DisjointAbstractionNetwork,
        GROUP_T extends GenericConceptGroup,
        DISJOINTGROUP_T extends DisjointGenericConceptGroup,
        CONCEPT_T> extends AbNNodeInformationPanel<CONTAINER_T> {
    
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
        
        public DisjointGenericConceptGroup getBasisDisjointGroup() {
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

    private final JTable overlappingGroupTable;
    protected final BLUAbstractOverlappingGroupTableModel<GROUP_T, CONCEPT_T> overlappingGroupTableModel;
    
    private final JTable overlappingDetailsTable;
    protected final BLUAbstractOverlappingDetailsTableModel<GROUP_T, DISJOINTGROUP_T, CONCEPT_T> overlappingDetailsTableModel;
    
    protected final BLUDisjointableAbNConfiguration configuration;
    
    private final JSplitPane splitPane;
    
    private Optional<DisjointAbNOverlapMetrics> currentMetrics = Optional.empty();

    protected AbstractDisjointAbNMetricsPanel(
            BLUAbstractOverlappingGroupTableModel<GROUP_T, CONCEPT_T> overlappingGroupTableModel,
            BLUAbstractOverlappingDetailsTableModel<GROUP_T, DISJOINTGROUP_T, CONCEPT_T> overlappingDetailsTableModel,
            BLUDisjointableAbNConfiguration configuration) {
        
        this.overlappingGroupTableModel = overlappingGroupTableModel;
        this.overlappingDetailsTableModel = overlappingDetailsTableModel;
        this.configuration = configuration;
        
        this.setLayout(new BorderLayout());
        
        this.splitPane = AbstractNodeDetailsPanel.createStyledSplitPane(JSplitPane.VERTICAL_SPLIT);
        
        overlappingGroupTable = new JTable(this.overlappingGroupTableModel);
        
        overlappingGroupTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = overlappingGroupTable.getSelectedRow();
                
                if(selectedRow >= 0) {
                    OverlappingGroupEntry<GROUP_T, CONCEPT_T> entry = overlappingGroupTableModel.getItemAtRow(selectedRow);
                    
                    if(currentMetrics.isPresent()) {
                        displayOverlappingDetailsFor(entry.getOverlappingGroup());
                    }
                } else {
                    overlappingDetailsTableModel.setContents(new ArrayList<>());
                }
            }
        });

        overlappingDetailsTable = new JTable(this.overlappingDetailsTableModel);
        
        JTabbedPane overlapDetailsTabs = new JTabbedPane();
        overlapDetailsTabs.addTab("Individual", new JScrollPane(overlappingDetailsTable));
        overlapDetailsTabs.addTab("Combinations", new JPanel());
        
        splitPane.setTopComponent(new JScrollPane(overlappingGroupTable));
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
    }
    
    @Override
    public void setContents(CONTAINER_T container) {
        splitPane.setDividerLocation(300);
        
        DISJOINTABN_T disjointAbN = (DISJOINTABN_T)configuration.createDisjointAbN(container);
        
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
        
        ArrayList<OverlappingGroupEntry<GROUP_T, CONCEPT_T>> entries = new ArrayList<>();
        
        overlappingGroups.forEach((GROUP_T overlappingGroup) -> {
            entries.add(new OverlappingGroupEntry<>(overlappingGroup, metrics.getGroupMetrics().get(overlappingGroup).getOverlappingConcepts()));
        });
        
        overlappingGroupTableModel.setContents(entries);
    }

    @Override
    public void clearContents() {
        currentMetrics = Optional.empty();
        
        overlappingGroupTableModel.setContents(new ArrayList<>());
        overlappingDetailsTableModel.setContents(new ArrayList<>());
    }
}

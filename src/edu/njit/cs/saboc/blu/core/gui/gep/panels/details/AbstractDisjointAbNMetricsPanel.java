package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import SnomedShared.generic.GenericConceptGroup;
import SnomedShared.generic.GenericGroupContainer;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.nodes.DisjointGenericConceptGroup;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Chris O
 */
public abstract class AbstractDisjointAbNMetricsPanel<CONTAINER_T extends GenericGroupContainer,
        DISJOINTABN_T extends DisjointAbstractionNetwork,
        GROUP_T extends GenericConceptGroup,
        DISJOINTGROUP_T extends DisjointGenericConceptGroup,
        CONCEPT_T> extends AbNNodeInformationPanel<CONTAINER_T> {
    

    //private final IndividualOverlapDetailsTableModel overlappingDetailsModel;
    
    protected AbstractDisjointAbNMetricsPanel(DISJOINTABN_T disjointAbN) {
        /*
        super(new GridLayout(2, 1));
        
        ArrayList<GROUP_T> overlappingGroups = new ArrayList<>(disjointAbN.getOverlappingGroups());
         
        /*
        Collections.sort(overlappingGroups, new Comparator<GROUP_T>() {
            public int compare(GROUP_T a, GROUP_T b) {
                int aOverlapCount = metrics.getGroupMetrics().get(a).getOverlappingConceptCount();
                int bOverlapCount = metrics.getGroupMetrics().get(b).getOverlappingConceptCount();
                
                if(aOverlapCount == bOverlapCount) {
                    return a.getRoot().getName().compareToIgnoreCase(b.getRoot().getName());
                }
                
                return bOverlapCount - aOverlapCount;
            }
        });
 
        final JTable overlappingGroupTable = new JTable(overlappingGroupModel);
        
        overlappingGroupTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = overlappingGroupTable.getSelectedRow();
                
                overlappingDetailsModel.update(overlappingGroupModel.getSelectedGroupMetrics(selectedRow));
            }
        });
                

        JPanel overlappingGroupsPanel = new JPanel(new BorderLayout());
        overlappingGroupsPanel.setBorder(BorderFactory.createTitledBorder("Overlapping Groups"));
        //overlappingGroupsPanel.add(new JScrollPane(overlappingGroupTable), BorderLayout.CENTER);
        
        this.add(overlappingGroupsPanel);
        
        this.overlappingDetailsModel = new IndividualOverlapDetailsTableModel();
        
        final JTable overlapDetailsTable = new JTable(overlappingDetailsModel);
        
        JPanel overlappingDetailsPanel = new JPanel(new BorderLayout());
        overlappingDetailsPanel.setBorder(BorderFactory.createTitledBorder("Overlap Details"));
        
        JTabbedPane overlapDetailsTabs = new JTabbedPane();
        overlapDetailsTabs.addTab("Individual", new JScrollPane(overlapDetailsTable));
        overlapDetailsTabs.addTab("Combinations", new JPanel());
        
        overlappingDetailsPanel.add(overlapDetailsTabs);
        
        this.add(overlappingDetailsPanel);
        */
    }
    
    protected abstract String getDisjointGroupRootName(DisjointGenericConceptGroup group);
    
    protected abstract String [] getOverlapGroupColumnNames();

    private class GroupOverlapMetrics {
        
        private HashMap<HashSet<GROUP_T>, HashSet<DISJOINTGROUP_T>> intersectionGroups;
        
        private GROUP_T overlappingGroup;
        
        private DISJOINTGROUP_T disjointBasis;
        
        private int overlappingConceptCount;
        
        public GroupOverlapMetrics(GROUP_T overlappingGroup, DISJOINTGROUP_T disjointBasis,
                HashMap<HashSet<GROUP_T>, HashSet<DISJOINTGROUP_T>> intersectionGroups) {
            
            this.intersectionGroups = intersectionGroups;
            this.overlappingGroup = overlappingGroup;
            this.disjointBasis = disjointBasis;
            
            HashSet<DISJOINTGROUP_T> disjointGroups = getAllIntersectionGroups();
            
            int overlappingConceptCount = 0;
            
            for(DisjointGenericConceptGroup group : disjointGroups) {
                overlappingConceptCount += group.getConceptCount();
            }
            
            this.overlappingConceptCount = overlappingConceptCount;
        }
        
        public int getOverlappingConceptCount() {
            return overlappingConceptCount;
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
        
        private HashMap<GROUP_T, GroupOverlapMetrics> groupMetrics = new HashMap<>();
        
        public DisjointAbNOverlapMetrics(DISJOINTABN_T disjointAbN) {
            HashSet<GROUP_T> overlappingGroups = disjointAbN.getOverlappingGroups();
            
            HashSet<DISJOINTGROUP_T> disjointGroups = disjointAbN.getDisjointGroups();
            
            for(GROUP_T group : overlappingGroups) {
                
                DISJOINTGROUP_T disjointBasis = null;
                
                HashMap<HashSet<GROUP_T>, HashSet<DISJOINTGROUP_T>> intersectionGroups = new HashMap<>();
                
                for(DISJOINTGROUP_T disjointGroup : disjointGroups) {
                    if(disjointGroup.getOverlaps().contains(group)) {
                        if(disjointGroup.getOverlaps().size() == 1) {
                            disjointBasis = disjointGroup;
                        } else {
                            HashSet<GROUP_T> overlaps = disjointGroup.getOverlaps();
                            
                            if(!intersectionGroups.containsKey(overlaps)) {
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
    
    private class IndividualOverlapDetailsTableModel extends AbstractTableModel {

        private String[] columnNames;

        private Object[][] data = new Object[0][0];

        public IndividualOverlapDetailsTableModel() {
            this.columnNames = new String[]{"Overlapping Partial-area", "# Overlapping Concepts", "# Common Disjoint Groups"};
        }

        public final void update(GroupOverlapMetrics metrics) {
            
            HashSet<DISJOINTGROUP_T> intersectionGroups = metrics.getAllIntersectionGroups();
            
            final HashMap<GROUP_T, Integer> overlappingConceptCount = new HashMap<>();
            final HashMap<GROUP_T, Integer> commonDisjointGroupCount = new HashMap<>();

            for (DisjointGenericConceptGroup group : intersectionGroups) {
                HashSet<GROUP_T> overlappingGroups = group.getOverlaps();

                for (GROUP_T overlappingGroup : overlappingGroups) {
                    if (!overlappingGroup.equals(metrics.getOverlappingGroup())) {
                        if (!overlappingConceptCount.containsKey(overlappingGroup)) {
                            overlappingConceptCount.put(overlappingGroup, 0);
                            commonDisjointGroupCount.put(overlappingGroup, 1);
                        }
                        
                        overlappingConceptCount.put(overlappingGroup, overlappingConceptCount.get(overlappingGroup) + group.getConceptCount());
                        
                        commonDisjointGroupCount.put(overlappingGroup, commonDisjointGroupCount.get(overlappingGroup) + 1);
                    }
                }
            }
            
            ArrayList<GROUP_T> overlappingGroups = new ArrayList<>(overlappingConceptCount.keySet());
            
            Collections.sort(overlappingGroups, new Comparator<GROUP_T>() {
                public int compare(GROUP_T a, GROUP_T b) {
                    int aCount = overlappingConceptCount.get(a);
                    int bCount = overlappingConceptCount.get(b);
                    
                    if(aCount == bCount) {
                        return a.getRoot().getName().compareToIgnoreCase(b.getRoot().getName());
                    } else {
                        return bCount - aCount;
                    }
                }
            });
            
            this.data = new Object[overlappingGroups.size()][3];
            
            for(int r = 0; r < overlappingGroups.size(); r++) {
                GROUP_T overlappingGroup = overlappingGroups.get(r);
                
                data[r][0] = overlappingGroup.getRoot().getName();
                data[r][1] = overlappingConceptCount.get(overlappingGroup);
                data[r][2] = commonDisjointGroupCount.get(overlappingGroup);
            }
            
            this.fireTableDataChanged();
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
    }
    
}

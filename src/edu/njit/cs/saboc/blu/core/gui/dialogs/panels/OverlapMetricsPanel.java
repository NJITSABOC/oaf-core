package edu.njit.cs.saboc.blu.core.gui.dialogs.panels;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.nodes.DisjointGenericConceptGroup;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
 * @author Chris
 */
public abstract class OverlapMetricsPanel<T extends DisjointAbstractionNetwork> extends JPanel {
    
    private final T disjointAbN;
    
    private DisjointAbNOverlapMetrics metrics;
    
    private final OverlappingGroupTableModel overlappingGroupModel;
    
    private final IndividualOverlapDetailsTableModel overlappingDetailsModel;
    
    protected OverlapMetricsPanel(T disjointAbN) {
        super(new GridLayout(2, 1));
        
        this.disjointAbN = disjointAbN;
        this.metrics = new DisjointAbNOverlapMetrics(disjointAbN);

        ArrayList<GenericConceptGroup> overlappingGroups = new ArrayList<GenericConceptGroup>(disjointAbN.getOverlappingGroups());
          
        Collections.sort(overlappingGroups, new Comparator<GenericConceptGroup>() {
            public int compare(GenericConceptGroup a, GenericConceptGroup b) {
                int aOverlapCount = metrics.getGroupMetrics().get(a).getOverlappingConceptCount();
                int bOverlapCount = metrics.getGroupMetrics().get(b).getOverlappingConceptCount();
                
                if(aOverlapCount == bOverlapCount) {
                    return a.getRoot().getName().compareToIgnoreCase(b.getRoot().getName());
                }
                
                return bOverlapCount - aOverlapCount;
            }
        });
        
        this.overlappingGroupModel = new OverlappingGroupTableModel(getOverlapGroupColumnNames(), overlappingGroups, metrics);
 
        final JTable overlappingGroupTable = new JTable(overlappingGroupModel);
        
        overlappingGroupTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = overlappingGroupTable.getSelectedRow();
                
                overlappingDetailsModel.update(overlappingGroupModel.getSelectedGroupMetrics(selectedRow));
            }
        });

        JPanel overlappingGroupsPanel = new JPanel(new BorderLayout());
        overlappingGroupsPanel.setBorder(BorderFactory.createTitledBorder("Overlapping Groups"));
        overlappingGroupsPanel.add(new JScrollPane(overlappingGroupTable), BorderLayout.CENTER);
        
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
    }
    
    protected abstract String getDisjointGroupRootName(DisjointGenericConceptGroup group);
    
    protected abstract String [] getOverlapGroupColumnNames();
    
    public T getDisjointAbstractionNetwork() {
        return disjointAbN;
    }
    
    private class GroupOverlapMetrics {
        
        private HashMap<HashSet<GenericConceptGroup>, HashSet<DisjointGenericConceptGroup>> intersectionGroups;
        
        private GenericConceptGroup overlappingGroup;
        
        private DisjointGenericConceptGroup disjointBasis;
        
        private int overlappingConceptCount;
        
        public GroupOverlapMetrics(GenericConceptGroup overlappingGroup, DisjointGenericConceptGroup disjointBasis,
                HashMap<HashSet<GenericConceptGroup>, HashSet<DisjointGenericConceptGroup>> intersectionGroups) {
            
            this.intersectionGroups = intersectionGroups;
            this.overlappingGroup = overlappingGroup;
            this.disjointBasis = disjointBasis;
            
            HashSet<DisjointGenericConceptGroup> disjointGroups = getAllIntersectionGroups();
            
            int overlappingConceptCount = 0;
            
            for(DisjointGenericConceptGroup group : disjointGroups) {
                overlappingConceptCount += group.getConceptCount();
            }
            
            this.overlappingConceptCount = overlappingConceptCount;
        }
        
        public int getOverlappingConceptCount() {
            return overlappingConceptCount;
        }
        
        public Set<HashSet<GenericConceptGroup>> getIntersections() {
            return intersectionGroups.keySet();
        }
        
        public Collection<HashSet<DisjointGenericConceptGroup>> intersectionDisjointGroups() {
            return intersectionGroups.values();
        } 
        
        public GenericConceptGroup getOverlappingGroup() {
            return overlappingGroup;
        }
        
        public DisjointGenericConceptGroup getBasisDisjointGroup() {
            return disjointBasis;
        }
        
        public HashSet<DisjointGenericConceptGroup> getAllIntersectionGroups() {
            
            HashSet<DisjointGenericConceptGroup> disjointGroups = new HashSet<DisjointGenericConceptGroup>();
            
            for(HashSet<DisjointGenericConceptGroup> groups : intersectionGroups.values()) {
                disjointGroups.addAll(groups);
            }
            
            return disjointGroups;
        }
        
        public HashSet<DisjointGenericConceptGroup> groupsOverlapWith(GenericConceptGroup group) {
            HashSet<DisjointGenericConceptGroup> groups = new HashSet<DisjointGenericConceptGroup>();
            
            for(HashSet<GenericConceptGroup> overlaps : intersectionGroups.keySet()) {
                if(overlaps.contains(group)) {
                    groups.addAll(intersectionGroups.get(overlaps));
                }
            }
            
            return groups;
        }

    }
    
    private class DisjointAbNOverlapMetrics {
        
        private HashMap<GenericConceptGroup, GroupOverlapMetrics> groupMetrics = 
                new HashMap<GenericConceptGroup, GroupOverlapMetrics>();
        
        public DisjointAbNOverlapMetrics(T disjointAbN) {
            HashSet<GenericConceptGroup> overlappingGroups = disjointAbN.getOverlappingGroups();
            
            HashSet<DisjointGenericConceptGroup> disjointGroups = disjointAbN.getDisjointGroups();
            
            for(GenericConceptGroup group : overlappingGroups) {
                
                DisjointGenericConceptGroup disjointBasis = null;
                
                HashMap<HashSet<GenericConceptGroup>, HashSet<DisjointGenericConceptGroup>> intersectionGroups
                         = new HashMap<HashSet<GenericConceptGroup>, HashSet<DisjointGenericConceptGroup>>();
                
                for(DisjointGenericConceptGroup disjointGroup : disjointGroups) {
                    if(disjointGroup.getOverlaps().contains(group)) {
                        if(disjointGroup.getOverlaps().size() == 1) {
                            disjointBasis = disjointGroup;
                        } else {
                            HashSet<GenericConceptGroup> overlaps = disjointGroup.getOverlaps();
                            
                            if(!intersectionGroups.containsKey(overlaps)) {
                                intersectionGroups.put(overlaps, new HashSet<DisjointGenericConceptGroup>());
                            }
                            
                            intersectionGroups.get(overlaps).add(disjointGroup);
                        }
                    }
                }
                
                
                groupMetrics.put(group, new GroupOverlapMetrics(group, disjointBasis, intersectionGroups));                
            }
        }
        
        public HashMap<GenericConceptGroup, GroupOverlapMetrics> getGroupMetrics() {
            return groupMetrics;
        }
    }
        
    private class OverlappingGroupTableModel extends AbstractTableModel {

        private String[] columnNames;
        
        private Object[][] data = new Object[0][0];
        
        private final ArrayList<GroupOverlapMetrics> metricsEntries = new ArrayList<GroupOverlapMetrics>();

        public OverlappingGroupTableModel(String [] columnNames, ArrayList<GenericConceptGroup> groups, DisjointAbNOverlapMetrics metrics) {
            this.columnNames = columnNames;
            
            update(groups, metrics);
        }

        public final void update(ArrayList<GenericConceptGroup> groups, DisjointAbNOverlapMetrics metrics) {
            data = new Object[groups.size()][3];

            for (int r = 0; r < groups.size(); r++) {

                GenericConceptGroup group = groups.get(r);

                data[r][0] = group.getRoot().getName();
                data[r][1] = group.getConceptCount();
                data[r][2] = metrics.getGroupMetrics().get(group).getOverlappingConceptCount();
                
                metricsEntries.add(metrics.getGroupMetrics().get(group));
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
        
        public GroupOverlapMetrics getSelectedGroupMetrics(int index) {
            return metricsEntries.get(index);
        }
    }
    
    private class IndividualOverlapDetailsTableModel extends AbstractTableModel {

        private String[] columnNames;

        private Object[][] data = new Object[0][0];

        public IndividualOverlapDetailsTableModel() {
            this.columnNames = new String[]{"Overlapping Partial-area", "# Overlapping Concepts", "# Common Disjoint Groups"};
        }

        public final void update(GroupOverlapMetrics metrics) {
            
            HashSet<DisjointGenericConceptGroup> intersectionGroups = metrics.getAllIntersectionGroups();
            
            final HashMap<GenericConceptGroup, Integer> overlappingConceptCount = new HashMap<GenericConceptGroup, Integer>();
            final HashMap<GenericConceptGroup, Integer> commonDisjointGroupCount = new HashMap<GenericConceptGroup, Integer>();

            for (DisjointGenericConceptGroup group : intersectionGroups) {
                HashSet<GenericConceptGroup> overlappingGroups = group.getOverlaps();

                for (GenericConceptGroup overlappingGroup : overlappingGroups) {
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
            
            ArrayList<GenericConceptGroup> overlappingGroups = new ArrayList<GenericConceptGroup>(overlappingConceptCount.keySet());
            
            Collections.sort(overlappingGroups, new Comparator<GenericConceptGroup>() {
                public int compare(GenericConceptGroup a, GenericConceptGroup b) {
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
                GenericConceptGroup overlappingGroup = overlappingGroups.get(r);
                
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


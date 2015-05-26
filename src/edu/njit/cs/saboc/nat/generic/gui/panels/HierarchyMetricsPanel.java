package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.GenericNATBrowser;
import edu.njit.cs.saboc.nat.generic.data.HierarchyMetrics;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.table.AbstractTableModel;


/**
 *
 * @author Chris
 */
public class HierarchyMetricsPanel extends JPanel {
    
    private class HierarchyMetricsTableModel extends AbstractTableModel {
                
        private String[] columnNames = new String[]{"Metric", "#"};
        private Object[][] data;

        public HierarchyMetricsTableModel() {
            clearData();
        }
        
        public final void clearData() {
            this.data = new Object[1][2];
            
            this.data[0][0] = "";
            this.data[0][1] = "";
            
            this.fireTableDataChanged();
        }
        
        public final void setData(HierarchyMetrics metrics) {
            this.data = new Object[5][2];
                        
            this.data[1][0] = "Ancestors";
            this.data[1][1] = metrics.getAncestorCount();
            
            this.data[2][0] = "Descendants";
            this.data[2][1] = metrics.getDescendantCount();
            
            this.data[3][0] = "Parents";
            this.data[3][1] = metrics.getParentCount();
            
            this.data[4][0] = "Children";
            this.data[4][1] = metrics.getChildCount();
            
            this.data[5][0] = "Siblings";
            this.data[5][1] = metrics.getSiblingCount();
            
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

    private MultiNavPanel navPanel;
    
    public HierarchyMetricsPanel(final GenericNATBrowser mainPanel, ConceptBrowserDataSource dataSource) {

        this.setLayout(new BorderLayout());
        
        this.setBackground(mainPanel.getNeighborhoodBGColor());

       /*
        
        inferredTabbedPane.addTab("Metrics", createMetricsPanel(HierarchyType.Inferred));
        
        inferredTabbedPane.addTab("Ancestors", createAncestorsPanel(HierarchyType.Inferred, inferredTabbedPane));
        
        inferredTabbedPane.addTab("Descendants", createDescendantsPanel(HierarchyType.Inferred, inferredTabbedPane));
        
        inferredTabbedPane.addTab("Paths", createAllPathsPanel(HierarchyType.Inferred, inferredTabbedPane));
        
        if(dataSource.supportsStatedRelationships()) {
            statedTabbedPane.addTab("Metrics", createMetricsPanel(HierarchyType.Stated));
            
            statedTabbedPane.addTab("Ancestors", createAncestorsPanel(HierarchyType.Stated, statedTabbedPane));
            
            statedTabbedPane.addTab("Descendants", createDescendantsPanel(HierarchyType.Stated, statedTabbedPane));
        }
        
        tabbedPane.add("Inferred Hierarchy", inferredTabbedPane);
        tabbedPane.add("Stated Hierarchy", statedTabbedPane);

        this.add(tabbedPane, BorderLayout.CENTER);
               */
    }

    /*
    private BaseNavPanel createMetricsPanel(final HierarchyType type) {
        
        final HierarchyMetricsTableModel metricsModel = new HierarchyMetricsTableModel();
        
        final FocusConcept.Fields field = (type == HierarchyType.Inferred) ? 
                FocusConcept.Fields.HIERARCHYMETRICS : FocusConcept.Fields.STATEDHIERARCHYMETRICS;
        
        JTable table = new JTable(metricsModel);
        
        BaseNavPanel metricsPanel = new BaseNavPanel(mainPanel, dataSource) {

            public void dataPending() {
                metricsModel.clearData();
            }

            public void dataEmpty() {
                metricsModel.clearData();
            }

            public void dataReady() {
                HierarchyMetrics metrics = (HierarchyMetrics)focusConcept.getConceptList(field);
                metricsModel.setData(metrics);
            }
        };

        metricsPanel.setLayout(new BorderLayout());
        metricsPanel.add(table, BorderLayout.CENTER);

        focusConcept.addDisplayPanel(field, metricsPanel);

        return metricsPanel;
    }

    private BaseNavPanel createAncestorsPanel(final HierarchyType type, final JTabbedPane parentPane) {

        final SCTFilterableList ancestorsList = new SCTFilterableList(mainPanel.getFocusConcept(), mainPanel.getOptions(), true, true);
        
        final FocusConcept.Fields field = (type == HierarchyType.Inferred) ? 
                FocusConcept.Fields.ALLANCESTORS : FocusConcept.Fields.STATEDANCESTORS;

        BaseNavPanel ancestorsPanel = new BaseNavPanel(mainPanel, dataSource) {
            public void dataPending() {
                parentPane.setTitleAt(ANCESTOR_IDX, "Ancestors");
                ancestorsList.showPleaseWait();
            }

            public void dataEmpty() {
                parentPane.setTitleAt(ANCESTOR_IDX, "Ancestors");
                ancestorsList.showDataEmpty();
            }

            public void dataReady() {
                ArrayList<Concept> allAncestors = (ArrayList<Concept>)focusConcept.getConceptList(field);

                ArrayList<Filterable> conceptEntries = new ArrayList<Filterable>();

                for(Concept c : allAncestors) {
                    
                    if(type == HierarchyType.Inferred) {
                        conceptEntries.add(new FilterableConceptEntry(c));
                    } else {
                        conceptEntries.add(new FilterableStatedAncestorEntry((LocalSCTConceptStated)c));
                    }
                }
                
                ancestorsList.setContents(conceptEntries);
                
                parentPane.setTitleAt(ANCESTOR_IDX, String.format("Ancestors (%d)", allAncestors.size()));
            }
        };

        ancestorsPanel.setLayout(new BorderLayout());
        ancestorsPanel.add(ancestorsList);

        focusConcept.addDisplayPanel(field, ancestorsPanel);

        return ancestorsPanel;
    }
    
    
    private BaseNavPanel createDescendantsPanel(final HierarchyType type, final JTabbedPane parentPane) {

        final SCTFilterableList descendantList = new SCTFilterableList(mainPanel.getFocusConcept(), mainPanel.getOptions(), true, true);
        
        final FocusConcept.Fields field = (type == HierarchyType.Inferred) ? 
                FocusConcept.Fields.ALLDESCENDANTS : FocusConcept.Fields.STATEDDESCENDANTS;

        BaseNavPanel descendantsPanel = new BaseNavPanel(mainPanel, dataSource) {
            public void dataPending() {
                parentPane.setTitleAt(DESCENDANT_IDX, "Descendants");
                descendantList.showPleaseWait();
            }

            public void dataEmpty() {
                parentPane.setTitleAt(DESCENDANT_IDX, "Descendants");
                descendantList.showDataEmpty();
            }

            public void dataReady() {
                ArrayList<Concept> allDescendants = (ArrayList<Concept>) focusConcept.getConceptList(field);
                
                ArrayList<Filterable> conceptEntries = new ArrayList<Filterable>();
                
                if(allDescendants.size() > 2000) {
                    
                    if (type == HierarchyType.Inferred) {
                        for (Concept descendant : allDescendants) {

                            if (dataSource.getConceptChildren(descendant).size() >= 10) {
                                conceptEntries.add(new FilterableConceptEntry(descendant));
                            }
                        }
                    } else {
                        SCTLocalDataSourceWithStated statedDS = (SCTLocalDataSourceWithStated)dataSource;
                        
                        for (Concept descendant : allDescendants) {

                            if (statedDS.getStatedChildren(descendant).size() >= 10) {
                                conceptEntries.add(new FilterableStatedAncestorEntry((LocalSCTConceptStated)descendant));
                            }
                        }
                    }
                         
                    parentPane.setTitleAt(DESCENDANT_IDX, String.format("Descendants (%d, Showing %d)", allDescendants.size(), conceptEntries.size()));
                    descendantList.showDataEmpty();
                    
                } else {
     
                    for (Concept c : allDescendants) {
                        
                        if(type == HierarchyType.Inferred) {
                            conceptEntries.add(new FilterableConceptEntry(c));
                        } else {
                            conceptEntries.add(new FilterableStatedAncestorEntry((LocalSCTConceptStated)c));
                        }
                    }

                    parentPane.setTitleAt(DESCENDANT_IDX, String.format("Descendants (%d)", allDescendants.size()));
                }
                
                descendantList.setContents(conceptEntries);
            }
        };

        descendantsPanel.setLayout(new BorderLayout());
        descendantsPanel.add(descendantList);

        focusConcept.addDisplayPanel(field, descendantsPanel);

        return descendantsPanel;
    }

    private BaseNavPanel createAllPathsPanel(final HierarchyType type, final JTabbedPane parentPane) {
        final SCTFilterableList pathList = new SCTFilterableList(mainPanel.getFocusConcept(), mainPanel.getOptions(), false, true);
        
         final FocusConcept.Fields field = (type == HierarchyType.Inferred) ? 
                FocusConcept.Fields.ALLPATHS : null;
        
        BaseNavPanel inferredPathsPanel = new BaseNavPanel(mainPanel, dataSource) {
            public void dataPending() {
                parentPane.setTitleAt(PATH_IDX, "Paths");
                pathList.showPleaseWait();
            }

            public void dataEmpty() {
                parentPane.setTitleAt(PATH_IDX, "Paths");
                pathList.showDataEmpty();
            }

            public void dataReady() {
                ArrayList<ArrayList<Concept>> allPaths = (ArrayList<ArrayList<Concept>>)focusConcept.getConceptList(field);
                
                ArrayList<Filterable> pathEntries = new ArrayList<Filterable>();
                
                for(ArrayList<Concept> path : allPaths) {
                    pathEntries.add(new FilterablePathEntry(path));
                }
                
                pathList.setContents(pathEntries);
                
                parentPane.setTitleAt(PATH_IDX, String.format("Paths (%d)", allPaths.size()));
            }
        };
        
        
        JButton deriveBtn = new JButton("Derive Path Subtaxonomy");
        deriveBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                List<Filterable> selectedPaths = pathList.getSelectedValues();
                
                if(!selectedPaths.isEmpty()) {
                    ArrayList<ArrayList<Concept>> paths = new ArrayList<ArrayList<Concept>>();
                    
                    for(Filterable selectedPath : selectedPaths) {
                        paths.add(((FilterablePathEntry)selectedPath).getPath());
                    }
                    
                    deriveAndDisplayPathSubtaxonomy(paths);
                }
            }
        });
        
        pathList.addListMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    PathListDialog.show(mainPanel, ((FilterablePathEntry)pathList.getSelectedValues().get(0)).getPath());
                }
            }
        });
        

        inferredPathsPanel.setLayout(new BorderLayout());
        
        inferredPathsPanel.add(deriveBtn, BorderLayout.NORTH);
        
        inferredPathsPanel.add(pathList, BorderLayout.CENTER);

        focusConcept.addDisplayPanel(field, inferredPathsPanel);

        return inferredPathsPanel;
    }
    
    */
}

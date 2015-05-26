package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.GenericNATBrowser;
import edu.njit.cs.saboc.nat.generic.data.BrowserConcept;
import edu.njit.cs.saboc.nat.generic.gui.listeners.DataLoadedListener;
import java.awt.BorderLayout;
import java.util.ArrayList;

/**
 * A class that displays lateral relationships of the Focus Concept.
 * Siblings have their own tab on this panel.  Concept relationships can be
 * further deconstructed into Term Relationships, which also have their own
 * tab.
 */
public class RelationshipPanel extends NATLayoutPanel {

    private MultiNavPanel multiNavPanel;

    public RelationshipPanel(final GenericNATBrowser mainPanel, ConceptBrowserDataSource dataSource) {
 
        multiNavPanel = new MultiNavPanel(mainPanel);

        setBackground(mainPanel.getNeighborhoodBGColor());
        
        setLayout(new BorderLayout());
 
        multiNavPanel.addNavPanel(mainPanel.getFocusConcept().COMMON_DATA_FIELDS.SIBLINGS, 
                new ConceptListPanel(mainPanel, mainPanel.getFocusConcept().COMMON_DATA_FIELDS.SIBLINGS, dataSource,
                    new DataLoadedListener<ArrayList<BrowserConcept>>() {
                        public void dataLoaded(ArrayList<BrowserConcept> data) {
                            multiNavPanel.updateTabTitle(mainPanel.getFocusConcept().COMMON_DATA_FIELDS.SIBLINGS, 
                                    String.format("Siblings (%d)", data.size()));
                        }
                    }, false
                ), "Siblings");
        
        this.add(multiNavPanel, BorderLayout.CENTER);

        /*

        // Concept Relationships Panel
        conRelPanel = new BaseNavPanel(mainPanel, dataSource) {
            @Override
            public void dataPending() {
                tabbedPane.setTitleAt(CON_REL_IDX, "INFERRED ATTRIBUTE RELATIONSHIPS");
                conRelList.showPleaseWait();
            }

            public void dataEmpty() {
                tabbedPane.setTitleAt(CON_REL_IDX, "INFERRED ATTRIBUTE RELATIONSHIPS");
                conRelList.showDataEmpty();
            }

            @Override
            public void dataReady() {
                ArrayList<Filterable> entries = new ArrayList<Filterable>();

                ArrayList<OutgoingLateralRelationship> relationships =
                        (ArrayList<OutgoingLateralRelationship>)focusConcept.getConceptList(
                        FocusConcept.Fields.CONCEPTREL);

                for(OutgoingLateralRelationship olr : relationships) {
                    entries.add(new FilterableLateralRelationshipEntry(olr));
                }

                conRelList.setContents(entries);
                int relCount = relationships.size();
                tabbedPane.setTitleAt(CON_REL_IDX, "INFERRED ATTRIBUTE RELATIONSHIPS (" + relCount + ")");
            }
        };
        
        conRelPanel.setLayout(new BorderLayout());
        conRelPanel.add(conRelList, BorderLayout.CENTER);
        
        statedConRelPanel = new BaseNavPanel(mainPanel, dataSource) {
            @Override
            public void dataPending() {
                tabbedPane.setTitleAt(STATED_CON_REL_IDX, "STATED ATTRIBUTE RELATIONSHIPS");
                statedConRelList.showPleaseWait();
            }

            public void dataEmpty() {
                tabbedPane.setTitleAt(STATED_CON_REL_IDX, "STATED ATTRIBUTE RELATIONSHIPS");
                statedConRelList.showDataEmpty();
            }

            @Override
            public void dataReady() {
                ArrayList<Filterable> entries = new ArrayList<Filterable>();

                ArrayList<OutgoingLateralRelationship> relationships =
                        (ArrayList<OutgoingLateralRelationship>)focusConcept.getConceptList(
                        FocusConcept.Fields.STATEDCONCEPTRELS);

                for(OutgoingLateralRelationship olr : relationships) {
                    entries.add(new FilterableLateralRelationshipEntry(olr));
                }

                statedConRelList.setContents(entries);
                
                int relCount = relationships.size();
                tabbedPane.setTitleAt(STATED_CON_REL_IDX, "STATED ATTRIBUTE RELATIONSHIPS (" + relCount + ")");
            }
        };
        
        statedConRelPanel.setLayout(new BorderLayout());
        statedConRelPanel.add(statedConRelList, BorderLayout.CENTER);

        // Siblings Panel
        siblingPanel = new BaseNavPanel(mainPanel, dataSource) {
            @Override
            public void dataPending() {
                tabbedPane.setTitleAt(SIBLING_IDX, "INFERRED SIBLINGS");
                siblingList.showPleaseWait();
            }

            public void dataEmpty() {
                tabbedPane.setTitleAt(SIBLING_IDX, "INFERRED SIBLINGS");
                siblingList.showDataEmpty();
            }

            @Override
            public void dataReady() {
                FocusConcept.Fields field;

                field = FocusConcept.Fields.SIBLINGS;
                
                int count = ((ArrayList<String>)focusConcept.getConceptList(field)).size();
                tabbedPane.setTitleAt(SIBLING_IDX, "INFERRED SIBLINGS (" + count + ")");

                ArrayList<Concept> siblings = (ArrayList<Concept>)focusConcept.getConceptList(field);
                ArrayList<Filterable> conceptEntries = new ArrayList<Filterable>();

                for(Concept c : siblings) {
                    conceptEntries.add(new FilterableConceptEntry(c));
                }
                
                siblingList.setContents(conceptEntries);
            }
        };
        siblingPanel.setLayout(new BorderLayout());
        siblingPanel.add(siblingList, BorderLayout.CENTER);
        
        // Stated siblings Panel
        statedSiblingsPanel = new BaseNavPanel(mainPanel, dataSource) {
            @Override
            public void dataPending() {
                tabbedPane.setTitleAt(STATED_SIBLING_IDX, "STATED SIBLINGS");
                statedSiblingsList.showPleaseWait();
            }

            public void dataEmpty() {
                tabbedPane.setTitleAt(STATED_SIBLING_IDX, "STATED SIBLINGS");
                statedSiblingsList.showDataEmpty();
            }

            @Override
            public void dataReady() {
                ArrayList<Concept> siblings = (ArrayList<Concept>)focusConcept.getConceptList(FocusConcept.Fields.STATEDSIBLINGS);
                
                 tabbedPane.setTitleAt(STATED_SIBLING_IDX, "STATED SIBLINGS (" + siblings.size() + ")");
                
                ArrayList<Filterable> conceptEntries = new ArrayList<Filterable>();

                for(Concept c : siblings) {
                    conceptEntries.add(new FilterableConceptEntry(c));
                }
                
                statedSiblingsList.setContents(conceptEntries);
            }
        };
        
        statedSiblingsPanel.setLayout(new BorderLayout());
        statedSiblingsPanel.add(statedSiblingsList, BorderLayout.CENTER);

        // Tabbed Pane
        tabbedPane = new JTabbedPane();
        
        final ButtonTabbedPaneUI tabbedUI = new ButtonTabbedPaneUI() {

            protected TabButton createFilterTabButton(int tabIndex) {
                TabButton button = new TabButton(tabIndex);
                button.setIcon(IconManager.getIconManager().getIcon("filter.png"));
                button.setPreferredSize(new Dimension(16, 16));
                button.setPadding(new Insets(0, 2, 3, 0));
                button.setBorder(null);
                button.setContentAreaFilled(false);
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        ((TabButton)e.getSource()).setContentAreaFilled(true);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        ((TabButton)e.getSource()).setContentAreaFilled(false);
                    }
                });

                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if(tabbedPane.getSelectedIndex() == CON_REL_IDX) {
                            conRelList.toggleFilterPanel();
                        } else if(tabbedPane.getSelectedIndex() == STATED_CON_REL_IDX) {
                            statedConRelList.toggleFilterPanel();
                        } else if(tabbedPane.getSelectedIndex() == SIBLING_IDX) {
                            siblingList.toggleFilterPanel();
                        }
                    }
                });

                return button;
            }
        };
        
        tabbedPane.setUI(tabbedUI);

        tabbedPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
        });

        tabbedPane.addTab("INFERRED ATTRIBUTE RELATIONSHIPS", conRelPanel);
        tabbedPane.addTab("INFERRED SIBLINGS", siblingPanel);
        
        focusConcept.addDisplayPanel(FocusConcept.Fields.CONCEPTREL, conRelPanel);
        focusConcept.addDisplayPanel(FocusConcept.Fields.SIBLINGS, siblingPanel);

        add(tabbedPane, BorderLayout.CENTER);
                
        */
    }
}

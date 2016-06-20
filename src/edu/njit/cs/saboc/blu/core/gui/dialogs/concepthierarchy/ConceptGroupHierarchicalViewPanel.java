package edu.njit.cs.saboc.blu.core.gui.dialogs.concepthierarchy;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.SingleRootedConceptHierarchy;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import javax.swing.JPanel;
import javax.swing.JViewport;

/**
 *
 * @author Chris
 */
public abstract class ConceptGroupHierarchicalViewPanel extends JPanel {

    private SingleRootedConceptHierarchy hierarchy;
    
    protected AbstractionNetwork abstractionNetwork;
    protected SinglyRootedNode group;

    private boolean initialized = false;
    private boolean loading = false;
    
    private HashMap<Concept, ConceptEntry> conceptEntryMap;

    private ArrayList<ArrayList<ConceptEntry>> conceptEntries;
    
    private String hierarchyGroupType;
    
    private String conceptType;
    
    private ConceptPainter conceptPainter;

    public ConceptGroupHierarchicalViewPanel(
            final AbstractionNetwork abstractionNetwork,
            final String hierarchyGroupType,
            final String conceptType, 
            final ConceptPainter conceptPainter,
            final HierarchyPanelClickListener clickListener) {
                
        this.abstractionNetwork = abstractionNetwork;
        
        this.hierarchyGroupType = hierarchyGroupType;
        this.conceptType = conceptType;
        
        this.conceptPainter = conceptPainter;
        
        this.setLayout(null);
        this.setPreferredSize(new Dimension(this.getWidth(), 2000));
        
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                if(!initialized || loading) {
                    return;
                }

                ConceptEntry clickedEntry = getConceptEntryAt(e.getX(), e.getY());

                if(e.getClickCount() >= 1 && e.getButton() == MouseEvent.BUTTON1) {

                    for (ArrayList<ConceptEntry> level : conceptEntries) {
                        for (ConceptEntry entry : level) {
                            entry.resetState();
                        }
                    }

                    if(clickedEntry != null) {
                        if (e.getClickCount() >= 2) {
                            clickListener.conceptDoubleClicked(clickedEntry.getConcept());
                        }

                        clickedEntry.setSelected(true);

                        Concept concept = clickedEntry.getConcept();

                        Set<Concept> parents = hierarchy.getParents(concept);
                        Set<Concept> children = hierarchy.getChildren(concept);

                        if(parents != null) {
                            for(Concept parent : parents) {
                                conceptEntryMap.get(parent).setFilledAsParent(true);
                            }
                        }

                        if(children != null) {
                            for(Concept child : children) {
                                conceptEntryMap.get(child).setFilledAsChild(true);
                            }
                        }
                    }
                }

                repaint();
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                
                if(!initialized || loading) {
                    return;
                }

                ConceptEntry mousedOverEntry = getConceptEntryAt(e.getX(), e.getY());
                
                for (ArrayList<ConceptEntry> level : conceptEntries) {
                    for (ConceptEntry entry : level) {
                        entry.setHighlighted(false);
                    }
                }

                if(mousedOverEntry != null) {
                    mousedOverEntry.setHighlighted(true);
                }

                repaint();
            }
        });
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        BufferedImage bi = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics bufferedGraphics = bi.getGraphics();

        bufferedGraphics.setColor(Color.WHITE);
        bufferedGraphics.fillRect(0, 0, this.getWidth(), this.getHeight());

        if(!initialized) {
            bufferedGraphics.setColor(Color.WHITE);
            
            bufferedGraphics.fillRect(0, 0, getWidth(), getHeight());
            
            bufferedGraphics.setColor(Color.BLACK);
            bufferedGraphics.setFont(new Font("Ariel", Font.BOLD, 18));
            bufferedGraphics.drawString("LOADING... PLEASE WAIT...", 200, 100);

            if(group != null && !loading) {
                new Thread(getHierarchyLoader()).start();
            }
        } else {
            
            JViewport parent = (JViewport)this.getParent();
            
            final int startX = 20;

            int xPos = startX;
            int yPos = 16;
                  
            bufferedGraphics.setColor(Color.BLACK);
            bufferedGraphics.setFont(new Font("Ariel", Font.BOLD, 14));

            for(int l = 0; l < conceptEntries.size(); l++) {
                xPos = startX;
                
                if (parent.getViewRect().contains(xPos, yPos)) {
                    String title = "";

                    if (l == 0) {
                        title = String.format("%s Root %s (NOTE: Longest path is shown)",
                                this.hierarchyGroupType, this.conceptType);

                    } else if (l == 1) {
                        title = String.format("Children of Root %s, # Concepts: %d", conceptType, conceptEntries.get(l).size());
                    } else {
                        title = String.format("Depth: %d, # Concepts: %d", l, conceptEntries.get(l).size());
                    }

                    bufferedGraphics.drawString(title, xPos, yPos);
                }

                yPos += 16;

                int fit = this.getWidth() / (ConceptEntry.CONCEPT_WIDTH + 8);
                int current = 0;

                for(ConceptEntry ce : conceptEntries.get(l)) {
                    
                    if(parent.getViewRect().contains(xPos, yPos)) {
                         ce.drawConceptAt(conceptPainter, (Graphics2D)bufferedGraphics, xPos, yPos);
                    }

                    xPos += (ConceptEntry.CONCEPT_WIDTH + 8);

                    current++;

                    if(current == fit) {
                        xPos = startX;
                        yPos += (ConceptEntry.CONCEPT_HEIGHT + 8);
                        current = 0;
                    }
                }

                yPos += ConceptEntry.CONCEPT_HEIGHT + 50;
            }
            
                    
            this.setPreferredSize(new Dimension(this.getWidth(), yPos));
            getParent().revalidate();
        }

        g.drawImage(bi, 0, 0, null);
    }
    
    private ConceptEntry getConceptEntryAt(int x, int y) {
        for(ArrayList<ConceptEntry> level : conceptEntries) {
            for(ConceptEntry entry : level) {
                if(entry.getBounds().contains(x, y)) {
                    return entry;
                }
            }
        }

        return null;
    }
    
    public abstract ConceptGroupHierarchyLoader<Concept, SingleRootedConceptHierarchy, ? extends GenericConceptGroup> getHierarchyLoader();
    
    public void initialize(SingleRootedConceptHierarchy hierarchy, 
            ArrayList<ArrayList<ConceptEntry>> conceptEntries, 
            HashMap<Concept, ConceptEntry> conceptEntryMap) {
        
        this.hierarchy = hierarchy;
        this.conceptEntries = conceptEntries;
        this.conceptEntryMap = conceptEntryMap;
        
        this.initialized = true;
        
        this.repaint();
    }
    
    public void setGroup(SinglyRootedNode group) {
        this.initialized = false;
        this.loading = false;
        
        this.group = group;
    }
}

package edu.njit.cs.saboc.blu.core.gui.dialogs;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphEdge;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author Chris
 */
public class GenericGroupEditMenu extends JPopupMenu {
    private final BluGraph graph;
    
    private Node group;

    public GenericGroupEditMenu(BluGraph graph) {
        this.graph = graph;
        
        this.setFocusable(true);
        this.addMouseListener(new MenuItemListener());
        
        createEdgeMenu();
    }
    
    public void setCurrentGroup(Node group) {
        this.group = group;
    }
    
    private final void createEdgeMenu() {
        JMenuItem drawParEdgesMenu = new JMenuItem("Draw All Parent Edges");

        drawParEdgesMenu.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent me) {

                HashSet<Integer> pareaParents = group.getParentIds();

                for (int pid : pareaParents) {
                    if (graph.getNodeEntries().containsKey(pid) && !graph.edgeAlreadyDrawn(group.getId(), pid)
                            && graph.getNodeEntries().get(pid).getGroupLevelParent().getParentPartition().isVisible()) {

                        graph.drawRoutedEdge(group.getId(), pid);
                    }
                }

                requestFocusInWindow();
            }
        });

        drawParEdgesMenu.addMouseListener(new MenuItemListener());
        this.add(drawParEdgesMenu);

        JMenuItem drawChdEdgesMenu = new JMenuItem("Draw All Child Edges");
        drawChdEdgesMenu.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent me) {
                int currentSummaryId = group.getId();

                HashSet<GenericConceptGroup> pareaChildren = graph.getAbstractionNetwork().getChildGroups(group);

                if (pareaChildren != null) {
                    for (GenericConceptGroup child : pareaChildren) {
                        if (graph.getNodeEntries().containsKey(child.getId()) && !graph.edgeAlreadyDrawn(child.getId(), currentSummaryId)
                                && graph.getNodeEntries().get(child.getId()).getGroupLevelParent().getParentPartition().isVisible()) {
                            
                            graph.drawRoutedEdge(child.getId(), currentSummaryId);
                        }
                    }
                }

                requestFocusInWindow();
            }
        });

        drawChdEdgesMenu.addMouseListener(new MenuItemListener());
        this.add(drawChdEdgesMenu);

        JMenuItem deleteEdgesMenu = new JMenuItem("Delete All Parent Edges");
        deleteEdgesMenu.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                ArrayList<GraphEdge> removeEdges = new ArrayList<GraphEdge>();
                ArrayList<GraphEdge> edges = graph.getEdges();

                for (GraphEdge temp : edges) {
                    if (temp.getSource() == group.getId()) {
                        removeEdges.add(temp);
                    }
                }

                edges.removeAll(removeEdges);
                graph.removeEdges(removeEdges);
                graph.redrawEdges();
                
                requestFocusInWindow();
            }
        });

        deleteEdgesMenu.addMouseListener(new MenuItemListener());
        this.add(deleteEdgesMenu);

        JMenuItem deleteIncomingEdges = new JMenuItem("Delete All Child Edges");
        deleteIncomingEdges.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {

                ArrayList<GraphEdge> removeEdges = new ArrayList<GraphEdge>();

                ArrayList<GraphEdge> edges = graph.getEdges();

                for (GraphEdge temp : edges) {
                    if (temp.getTarget() == group.getId()) {
                        removeEdges.add(temp);
                    }
                }

                edges.removeAll(removeEdges);
                graph.removeEdges(removeEdges);
                graph.redrawEdges();
                requestFocusInWindow();
            }
        });

        deleteIncomingEdges.addMouseListener(new MenuItemListener());
        this.add(deleteIncomingEdges);
    }
    
    
    
    class MenuItemListener implements MouseListener {

        public void mouseEntered(MouseEvent e) {
            if (e.getSource() instanceof JMenuItem) {
                ((JMenuItem) e.getSource()).setBackground(Color.lightGray);
            }
        }

        public void mouseExited(MouseEvent e) {
            if (e.getSource() instanceof JMenuItem) {
                ((JMenuItem) (e.getSource())).setBackground(new Color(238, 238, 238));
            }
        }

        public void mouseReleased(MouseEvent e) {
            if (e.getSource() instanceof JMenuItem) {
                ((JMenuItem) (e.getSource())).setBackground(new Color(238, 238, 238));
            }
        }

        public void mousePressed(MouseEvent e) {
            if (e.getSource() instanceof JMenuItem) {
                ((JMenuItem) (e.getSource())).setBackground(new Color(138, 138, 138));
            }
        }

        public void mouseClicked(MouseEvent e) {
            GenericGroupEditMenu.this.setVisible(false);
        }
    }
}

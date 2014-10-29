package edu.njit.cs.saboc.blu.core.gui.disjointabn;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.nodes.DisjointGenericConceptGroup;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import javax.swing.JPanel;

/**
 *
 * @author Chris
 */
public abstract class DisjointAbstractionNetworkGraph<T extends DisjointAbstractionNetwork, 
        V extends DisjointGenericConceptGroup, U extends DisjointGroupEntry> extends JPanel { //implements MouseListener {
    
    private T disjointAbN;
    
    private HashMap<V, U> disjointGroupEntryMap = new HashMap<V, U>();

    private ArrayList<ArrayList<U>> disjointGroupEntries = new ArrayList<ArrayList<U>>();
    
    private final DisjointAbNGraphActionListener<U> actionListener;

    public DisjointAbstractionNetworkGraph(final T disjointAbN, final DisjointAbNGraphActionListener<U> actionListener) { // TODO: Add click action listener
        
        this.disjointAbN = disjointAbN;
        
        this.actionListener = actionListener;
        
        this.createEntries(disjointAbN);

        this.setLayout(null);
        
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                U clickedEntry = getDisjointEntryAt(e.getX(), e.getY());

                if(e.getButton() == MouseEvent.BUTTON1) {
                    resetAllEntries();

                    if(clickedEntry != null) {

                        if (e.getClickCount() >= 2) {
                            actionListener.disjointEntryDoubleClicked(clickedEntry);
                        }

                        clickedEntry.setState(DisjointGroupEntry.State.Selected);

                        V group = (V)clickedEntry.getDisjointGroup();

                        HashSet<V> parents = disjointAbN.getHierarchy().getParents(group);
                        HashSet<V> children = disjointAbN.getHierarchy().getChildren(group);

                        for (V parent : parents) {
                            disjointGroupEntryMap.get(parent).setState(DisjointGroupEntry.State.HighlightedAsParent);
                        }

                        for (V child : children) {
                            disjointGroupEntryMap.get(child).setState(DisjointGroupEntry.State.HighlightedAsChild);
                        }
                    }
                }

                repaint();
            }
        });
        
        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {

                U mousedOverEntry = getDisjointEntryAt(e.getX(), e.getY());
                
                for (ArrayList<U> level : disjointGroupEntries) {
                    for (U entry : level) {
                        entry.setMousedOver(false);
                    }
                }

                if(mousedOverEntry != null) {
                    mousedOverEntry.setMousedOver(true);
                }

                repaint();
            }
        });
        
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int width = getWidth();

                int height = 0;

                int maxColumns = width / (DisjointGroupEntry.ENTRY_WIDTH + 8);

                for (int l = 0; l < disjointGroupEntries.size(); l++) {
                    int rows = disjointGroupEntries.get(l).size() / maxColumns;

                    height += (rows * (DisjointGroupEntry.ENTRY_HEIGHT + 8)) + DisjointGroupEntry.ENTRY_HEIGHT + 50;
                }

                setPreferredSize(new Dimension(width, height));
            }
        });
    }
    
    public T getDisjointAbstractionNetwork() {
        return disjointAbN;
    }

    /***
     * Returns the Disjoint PArea GUI object at the given x,y coordinate. Returns null if no object exists at that position.
     * @param x
     * @param y
     * @return 
     */
    private U getDisjointEntryAt(int x, int y) {
        for(ArrayList<U> level : disjointGroupEntries) {
            for(U entry : level) {
                if(entry.getBounds().contains(x, y)) {
                    return entry;
                }
            }
        }

        return null;
    }
    
    private void resetAllEntries() {
        for (ArrayList<U> level : disjointGroupEntries) {
            for (U entry : level) {
                entry.setState(DisjointGroupEntry.State.Unselected);
            }
        }
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        BufferedImage bi = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics bufferedGraphics = bi.getGraphics();

        bufferedGraphics.setColor(Color.WHITE);
        bufferedGraphics.fillRect(0, 0, this.getWidth(), this.getHeight());

        final int startX = 20;

        int xPos = startX;
        int yPos = 16;
        
        int fit = this.getWidth() / (DisjointGroupEntry.ENTRY_WIDTH + 8);

        for (int l = 0; l < disjointGroupEntries.size(); l++) {
            ((Graphics2D) (bufferedGraphics)).setTransform(AffineTransform.getTranslateInstance(0, 0));

            xPos = startX;
            
            int current = 0;

            for (U disjointEntry : disjointGroupEntries.get(l)) {
                disjointEntry.drawAt((Graphics2D) bufferedGraphics, xPos, yPos);
                xPos += (DisjointGroupEntry.ENTRY_WIDTH + 8);

                current++;

                if (current == fit) {
                    xPos = startX;
                    yPos += (DisjointGroupEntry.ENTRY_HEIGHT + 8);
                    current = 0;
                }
            }

            xPos = startX;
            yPos += DisjointGroupEntry.ENTRY_HEIGHT + 50;
        }

        g.drawImage(bi, 0, 0, null);
    }
    
    /**
     * Creates the GUI objects for the disjoint partial-areas 
     * @param djpaTaxonomy 
     */
    private void createEntries(T disjointAbN) {

        int maxOverlap = disjointAbN.getLevelCount();
        
        HashSet<V> disjointGroups = disjointAbN.getDisjointGroups();
     
        for (int overlapSize = 1; overlapSize <= maxOverlap; overlapSize++) {
            ArrayList<U> levelEntries = new ArrayList<U>();

            for (V disjointGroup : disjointGroups) {
                if (disjointGroup.getOverlaps().size() == overlapSize) {
                    U entry = createDisjointGroupEntry(disjointGroup);
                    levelEntries.add(entry);
                    
                    disjointGroupEntryMap.put(disjointGroup, entry);
                }
            }

            Collections.sort(levelEntries, new Comparator<U>() {
                public int compare(U a, U b) {
                    return b.getDisjointGroup().getConceptCount() - a.getDisjointGroup().getConceptCount();
                }
            });

            if (overlapSize > 1) {
                ArrayList<U> topLevel = disjointGroupEntries.get(0);
                levelEntries = doDisjointPAreaSort(levelEntries, topLevel, 0, maxOverlap);
            }

            disjointGroupEntries.add(levelEntries);
        }
        
        int colorsNeeded = disjointGroupEntries.get(0).size();
        
        Color [] colors = this.createColors();

        /*
        Color[] colors = {
            new Color(255, 0, 0),
            new Color(0, 153, 153),
            new Color(200, 240, 0),
            new Color(166, 0, 0),
            new Color(221, 0, 150),
            new Color(20, 209, 0),
            new Color(255, 252, 0),
            new Color(80, 14, 173),
            new Color(255, 0, 76),
            new Color(51, 204, 204),
            new Color(0, 178, 92),
            new Color(255, 142, 0),
            new Color(148, 0, 141),
            new Color(255, 200, 50),
            new Color(234, 252, 113),
            new Color(255, 120, 90),
            new Color(166, 75, 0)
        };
        */
        

        HashMap<GenericConceptGroup, Color> colorMap = new HashMap<GenericConceptGroup, Color>();

        int colorId = 0;

        for (U dpae : disjointGroupEntries.get(0)) {
            GenericConceptGroup overlapGroup = (GenericConceptGroup)dpae.getDisjointGroup().getOverlaps().iterator().next();

            if (colorId >= colors.length) {
                colorMap.put(overlapGroup, Color.gray);
            } else {
                colorMap.put(overlapGroup, colors[colorId]);
                colorId++;
            }

            dpae.setColorSet(new Color[]{colorMap.get(overlapGroup)});
        }

        for (int i = 1; i < disjointGroupEntries.size(); i++) {
            for (U disjointGroup : disjointGroupEntries.get(i)) {
                ArrayList<GenericConceptGroup> summaries = new ArrayList<GenericConceptGroup>(disjointGroup.getDisjointGroup().getOverlaps());

                Collections.sort(summaries, new Comparator<GenericConceptGroup>() {
                    public int compare(GenericConceptGroup a, GenericConceptGroup b) {
                        return b.getConceptCount() - a.getConceptCount();
                    }
                });

                Color[] dpaColors = new Color[summaries.size()];

                for (int c = 0; c < dpaColors.length; c++) {
                    dpaColors[c] = colorMap.get(summaries.get(c));
                }

                disjointGroup.setColorSet(dpaColors);
            }
        }
    }
    
    private Color [] createColors() {
        
        int iterations = 4;
        
        Color [] seedColors = new Color[] {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.PINK, Color.ORANGE, Color.CYAN, Color.LIGHT_GRAY, Color.MAGENTA};
        
        Color [] colors = new Color[seedColors.length * iterations];
        
        int index = 0;
        
        for(index = 0; index < seedColors.length; index++) {
            colors[index] = seedColors[index];
        }
        
        double scale = 0.8;
        
        for(int c = 0; c < iterations - 1; c++) {
            for(Color color : seedColors) {
                int r = (int)(color.getRed() * scale);
                int g = (int)(color.getGreen() * scale);
                int b = (int)(color.getBlue() * scale);
                
                colors[index++] = new Color(r,g,b);
            }
            
            scale -= 0.2;
        }

        return colors;
        
    }

    /**
     * Sorts, and then recursively subsorts up to maxLevel number of times, based on the size of the basis disjoint partial-areas
     * @param entries
     * @param topLevel
     * @param currentLevel
     * @param maxLevel
     * @return 
     */
    private ArrayList<U> doDisjointPAreaSort(ArrayList<U> entries,
            ArrayList<U> topLevel, int currentLevel, int maxLevel) {

        if(currentLevel >= maxLevel) {
            return entries;
        }

        ArrayList<ArrayList<U>> sortedEntries = new ArrayList<ArrayList<U>>();

        HashSet<U> processed = new HashSet<U>();

        for (int c = currentLevel; c < topLevel.size(); c++) {
            GenericConceptGroup overlap = (GenericConceptGroup)topLevel.get(c).getDisjointGroup().getOverlaps().iterator().next();

            ArrayList<U> sorted = new ArrayList<U>();

            for (U entry : entries) {
                if (!processed.contains(entry)) {
                    if (entry.getDisjointGroup().getOverlaps().contains(overlap)) {
                        sorted.add(entry);
                        processed.add(entry);
                    }
                }
            }

            sorted = doDisjointPAreaSort(sorted, topLevel, c + 1, maxLevel);

            sortedEntries.add(sorted);
        }

        ArrayList<U> finalSortedEntries = new ArrayList<U>();

        for (ArrayList<U> entry : sortedEntries) {
            finalSortedEntries.addAll(entry);
        }

        return finalSortedEntries;
    }
    
    protected abstract U createDisjointGroupEntry(V group);
}

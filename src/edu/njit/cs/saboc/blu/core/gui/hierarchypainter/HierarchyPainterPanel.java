package edu.njit.cs.saboc.blu.core.gui.hierarchypainter;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.MultiRootedHierarchy;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public class HierarchyPainterPanel<T> extends JPanel {
    
    private BufferedImage conceptHierarchyImage = null; 

    public HierarchyPainterPanel() {
        this.setLayout(null);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D)g;
        
        g2d.setColor(Color.WHITE);
        
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

        if(conceptHierarchyImage != null) {           
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            g2d.drawImage(conceptHierarchyImage, 0, 0, this.getWidth(), this.getHeight(), null);
        }
    }

    private class LevelMetrics {
        public int level;

        public int conceptCount;

        public int rows;
        public int cols;

        public LevelMetrics(int level, int conceptCount, int rows, int cols) {
            this.level = level;
            this.conceptCount = conceptCount;
            this.rows = rows;
            this.cols = cols;
        }
    }

    public void paintHierarchy(MultiRootedHierarchy<T> hierarchy) {
        ArrayList<ArrayList<T>> conceptsByLevel = getConceptsByLevel(hierarchy);

        final int CONCEPT_WIDTH = 20;
        final int CONCEPT_HEIGHT = 20;

        final int COL_DIVIDE_WIDTH = CONCEPT_WIDTH;
        final int ROW_DIVIDE_HEIGHT = CONCEPT_HEIGHT * 2;
        final int LEVEL_DIVIDE_HEIGHT = 40;
        
        final int MAX_CONCEPTS_PER_ROW = 6000 / (CONCEPT_WIDTH + COL_DIVIDE_WIDTH);
        
        ArrayList<LevelMetrics> metrics = new ArrayList<LevelMetrics>();
        
        int c = 0;
        
        for(ArrayList<T> level : conceptsByLevel) {
            int levelConceptCount = level.size();
            
            int colCount = levelConceptCount;
            int rowCount = 1;
            
            if(levelConceptCount > MAX_CONCEPTS_PER_ROW) {
                colCount = MAX_CONCEPTS_PER_ROW;
                rowCount = levelConceptCount / MAX_CONCEPTS_PER_ROW;
            }
            
            metrics.add(new LevelMetrics(c++, levelConceptCount, rowCount, colCount));
        }
        
        int maxCols = metrics.get(0).cols;
        
        int imageHeight = metrics.get(0).rows * (ROW_DIVIDE_HEIGHT + LEVEL_DIVIDE_HEIGHT);
        
        for(int i = 1; i < metrics.size(); i++) {
            if(metrics.get(i).cols > maxCols) {
                maxCols = metrics.get(i).cols;
            }
            
            imageHeight += metrics.get(i).rows * (ROW_DIVIDE_HEIGHT + LEVEL_DIVIDE_HEIGHT);
        }
        
        int imageWidth = maxCols * (CONCEPT_WIDTH + COL_DIVIDE_WIDTH);
        
        HashMap<T, Point> conceptDrawLocation = new HashMap<T, Point>();
        
        int conceptYPos = 0;
        
        for(int r = 0; r < metrics.size(); r++) {
            ArrayList<T> levelConcepts = conceptsByLevel.get(r);
            LevelMetrics metric = metrics.get(r);
            
            int processedConcepts = 0;
           
            for(int levelRow = 0; levelRow <= metric.rows; levelRow++) {
                for(int levelCol = 0; levelCol < metric.cols; levelCol++) {
                    int conceptXPos = levelCol * (CONCEPT_WIDTH + COL_DIVIDE_WIDTH);
                    
                    if(processedConcepts < levelConcepts.size()) {
                        conceptDrawLocation.put(levelConcepts.get(processedConcepts), new Point(conceptXPos, conceptYPos));
                        
                        processedConcepts++; 
                    } else {
                        break;
                    }
                }
                
                conceptYPos += (CONCEPT_HEIGHT + ROW_DIVIDE_HEIGHT);
            }
            
            conceptYPos += LEVEL_DIVIDE_HEIGHT;
        }
        
        imageHeight = conceptYPos;
        
        BufferedImage hierarchyImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
                
        Graphics2D g = (Graphics2D)hierarchyImage.getGraphics();
        
        g.setColor(Color.WHITE);
        
        g.fillRect(0, 0, imageWidth, imageHeight);
        
        Color [] levelColors = {
            Color.BLUE,
            Color.RED,
            Color.GREEN,
            Color.ORANGE,
            Color.MAGENTA,
            Color.CYAN,
            Color.BLACK,
            new Color(181, 31, 31),
            new Color(104, 189, 132)
        };
        
        
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        for(int r = 0; r < metrics.size(); r++) {
            ArrayList<T> levelConcepts = conceptsByLevel.get(r);
            
            Color levelColor = levelColors[r % levelColors.length];
            
            
            g.setColor(new Color(levelColor.getRed(), levelColor.getGreen(), levelColor.getBlue(), 210));
            
            for(T concept : levelConcepts) {
                Point childDrawPoint = conceptDrawLocation.get(concept);
                
                Set<T> parents = hierarchy.getParents(concept);
                
                for (T parent : parents) {
                    Point parentDrawPoint = conceptDrawLocation.get(parent);

                    if (parentDrawPoint != null) {
                        g.drawLine(childDrawPoint.x + CONCEPT_WIDTH / 2,
                                childDrawPoint.y,
                                parentDrawPoint.x + CONCEPT_WIDTH / 2,
                                parentDrawPoint.y + CONCEPT_HEIGHT);
                    }
                }
                
            }
        }
        
        g.setColor(Color.BLACK);
        
        for(int r = 0; r < metrics.size(); r++) {
            ArrayList<T> levelConcepts = conceptsByLevel.get(r);
            
            for(T concept : levelConcepts) {
                Point drawPoint = conceptDrawLocation.get(concept);
                
                if(drawPoint != null) {
                    g.setColor(Color.WHITE);
                    g.fillRect(drawPoint.x, drawPoint.y, CONCEPT_WIDTH, CONCEPT_HEIGHT);
                    
                    g.setColor(Color.BLACK);
                    g.drawRect(drawPoint.x, drawPoint.y, CONCEPT_WIDTH, CONCEPT_HEIGHT);
                }
            }
        }
        
        this.conceptHierarchyImage = hierarchyImage;

//        try {
//            ImageIO.write(hierarchyImage, "png", new File("C:\\Users\\Den\\Desktop\\test.png"));
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        }
    }

    private ArrayList<ArrayList<T>> getConceptsByLevel(MultiRootedHierarchy<T> hierarchy) {
        
        ArrayList<ArrayList<T>> conceptLevels = new ArrayList<ArrayList<T>>();

        Set<T> roots = hierarchy.getRoots();
        
        HashMap<T, Integer> parentCount = new HashMap<T, Integer>();
        
        Set<T> concepts = hierarchy.getNodesInHierarchy();
        
        for(T concept : concepts) {
            parentCount.put(concept, hierarchy.getParents(concept).size());
        }
        
        Queue<T> queue = new LinkedList<T>();
        queue.addAll(roots);
        
        HashMap<T, Integer> conceptDepth = new HashMap<T, Integer>();

        while(!queue.isEmpty()) {
            T concept = queue.remove();
            
            Set<T> parents = hierarchy.getParents(concept);
            
            int maxParentDepth = -1;
            
            for(T parent : parents) {
                int parentDepth = conceptDepth.get(parent);
                
                if(parentDepth > maxParentDepth) {
                    maxParentDepth = parentDepth;
                }
            }
            
            int depth = maxParentDepth + 1;
            
            conceptDepth.put(concept, depth);
            
            if(conceptLevels.size() < depth + 1) {
                conceptLevels.add(new ArrayList<T>());
            }
            
            conceptLevels.get(depth).add(concept);

            Set<T> children = hierarchy.getChildren(concept);
            
            for(T child : children) {
                int childParentCount = parentCount.get(child) - 1;
                
                if(childParentCount == 0) {
                    queue.add(child);
                } else {
                    parentCount.put(child, childParentCount);
                }
            }
        }
        
        return conceptLevels;
    }
}

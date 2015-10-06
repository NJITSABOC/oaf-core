package edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing;

import edu.njit.cs.saboc.blu.core.graph.nodes.AbNNodeEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericContainerEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericGroupEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericPartitionEntry;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

/**
 *
 * @author Chris
 */
public class AbNPainter {
    public void paintContainerAtPoint(Graphics2D g2d, GenericContainerEntry entry, Point p, double scale) {
        Stroke savedStroke = g2d.getStroke();
        
        g2d.setPaint(entry.getBackground());
        g2d.fillRect(p.x, p.y, (int)(entry.getWidth() * scale), (int)(entry.getHeight() * scale));
        
        g2d.setStroke(new BasicStroke(2));
        g2d.setPaint(Color.BLACK);
        g2d.drawRect(p.x, p.y, (int)(entry.getWidth() * scale), (int)(entry.getHeight() * scale));
        
        g2d.setStroke(savedStroke);
    }
    
    public void paintPartitionAtPoint(Graphics2D g2d, GenericPartitionEntry partition, Point p, double scale) {
        Stroke savedStroke = g2d.getStroke();

        g2d.setColor(partition.getBackground());
        
        g2d.fillRect(p.x, p.y, (int)(partition.getWidth() * scale), (int)(partition.getHeight() * scale));

        switch (partition.getHighlightState()) {
            case Selected:
                if (partition.isMousedOver()) {
                    g2d.setStroke(new BasicStroke(5));
                    g2d.setPaint(Color.YELLOW);
                } else {
                    g2d.setStroke(new BasicStroke(3));
                    g2d.setPaint(Color.YELLOW);
                }

                break;
            default:
                if (partition.isMousedOver()) {
                    g2d.setStroke(new BasicStroke(2));
                    g2d.setPaint(Color.CYAN);
                } else {
                    g2d.setStroke(new BasicStroke(1));
                    g2d.setPaint(Color.BLACK);
                }
        }

        g2d.drawRect(p.x, p.y, (int)(partition.getWidth() * scale), (int)(partition.getHeight() * scale));

        g2d.setStroke(savedStroke);
    }
    
    public void paintGroupAtPoint(Graphics2D g2d, GenericGroupEntry group, Point p, double scale) {
        
        Color bgColor;
        
        switch(group.getHighlightState()) {
                
            case Parent:
                bgColor = new Color(200, 200, 255);
                break;
                
            case Child:
                bgColor = new Color(255, 200, 255);
                break;
                
            case Selected:
                bgColor = new Color(255, 255, 100);
                break;
                
            case SearchResult:
                bgColor = new Color(255, 150, 150);
                break;
                
            default:
                bgColor = Color.WHITE;
        }
        
        if(group.isMousedOver()) {
            bgColor = bgColor.brighter();
        }
        
        g2d.setPaint(bgColor);
        
        g2d.fillRect(p.x, p.y, (int)(group.getWidth() * scale), (int)(group.getHeight() * scale));

        Stroke savedStroke = g2d.getStroke();
        
        Color outlineColor;
        
        if(group.isMousedOver()) {
            g2d.setStroke(new BasicStroke(2));
            outlineColor = Color.CYAN;
        } else {
            if(group.getHighlightState().equals(AbNNodeEntry.HighlightState.Selected)) {
                g2d.setStroke(new BasicStroke(2));
            } else {
                g2d.setStroke(new BasicStroke(1));
            }
            
            outlineColor = Color.BLACK;
        }

        g2d.setPaint(outlineColor);

        g2d.drawRect(p.x, p.y, (int)(group.getWidth() * scale), (int)(group.getHeight() * scale));

        g2d.setStroke(savedStroke);
    }
}

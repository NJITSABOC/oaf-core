package edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing;

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
        
        switch(partition.getState()) {
            case MousedOver:
                g2d.setStroke(new BasicStroke(2));
                g2d.setPaint(Color.CYAN);
                
                break;
            case Selected: 
                g2d.setStroke(new BasicStroke(3));
                g2d.setPaint(Color.YELLOW);
                
                break;
            case Default:
                g2d.setStroke(new BasicStroke(1));
                g2d.setPaint(Color.BLACK);

                break;
        }

        g2d.drawRect(p.x, p.y, (int)(partition.getWidth() * scale), (int)(partition.getHeight() * scale));

        g2d.setStroke(savedStroke);
    }
    
    public void paintGroupAtPoint(Graphics2D g2d, GenericGroupEntry group, Point p, double scale) {
        g2d.setPaint(group.getBackground());
        
        g2d.fillRect(p.x, p.y, (int)(group.getWidth() * scale), (int)(group.getHeight() * scale));

        Stroke savedStroke = g2d.getStroke();
        
        if (!group.getBackground().equals(Color.WHITE)) {
            g2d.setStroke(new BasicStroke(2));
        } else {
            g2d.setStroke(new BasicStroke(1));
        }

        g2d.setPaint(Color.BLACK);

        g2d.drawRect(p.x, p.y, (int)(group.getWidth() * scale), (int)(group.getHeight() * scale));

        g2d.setStroke(savedStroke);
    }
}

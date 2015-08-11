package edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing;

import edu.njit.cs.saboc.blu.core.abn.reduced.ReducingGroup;
import edu.njit.cs.saboc.blu.core.graph.nodes.AbNNodeEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericGroupEntry;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

/**
 *
 * @author Chris O
 */
public class ReducedAbNPainter extends AbNPainter {
    
    private final int EDGE_RADIUS = 24;
    
    public void paintGroupAtPoint(Graphics2D g2d, GenericGroupEntry group, Point p, double scale) {
        
        ReducingGroup reducedGroup = (ReducingGroup)(group.getGroup());
        
        Color bgColor;
        
        switch(group.getHighlightState()) {
                
            case Parent:
                bgColor = new Color(150, 150, 255);
                break;
                
            case Child:
                bgColor = new Color(255, 150, 255);
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
        
        if (reducedGroup.getReducedGroups().size() == 1) {
            g2d.fillRect(p.x, p.y, (int) (group.getWidth() * scale), (int) (group.getHeight() * scale));
        } else {
            g2d.fillRoundRect(p.x, p.y, (int) (group.getWidth() * scale), (int) (group.getHeight() * scale), EDGE_RADIUS, EDGE_RADIUS);
        }
        
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
        
        if (reducedGroup.getReducedGroups().size() == 1) {
            g2d.drawRect(p.x, p.y, (int) (group.getWidth() * scale), (int) (group.getHeight() * scale));
        } else {
            g2d.drawRoundRect(p.x, p.y, (int) (group.getWidth() * scale), (int) (group.getHeight() * scale), EDGE_RADIUS, EDGE_RADIUS);
        }

        g2d.setStroke(savedStroke);
    }
}

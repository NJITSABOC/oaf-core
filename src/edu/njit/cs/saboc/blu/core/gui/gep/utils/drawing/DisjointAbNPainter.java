
package edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing;

import edu.njit.cs.saboc.blu.core.graph.disjointabn.BluDisjointGroupEntry;
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
 * @author Chris O
 */
public class DisjointAbNPainter extends AbNPainter {
    public void paintContainerAtPoint(Graphics2D g2d, GenericContainerEntry entry, Point p, double scale) {

    }
    
    public void paintPartitionAtPoint(Graphics2D g2d, GenericPartitionEntry partition, Point p, double scale) {

    }
    
    public void paintGroupAtPoint(Graphics2D g2d, GenericGroupEntry group, Point p, double scale) {
        
        BluDisjointGroupEntry disjointGroup = (BluDisjointGroupEntry)group;
        
        Color [] colorSet = disjointGroup.getColorSet();
        
        int colorWidth = (int)((BluDisjointGroupEntry.DISJOINT_GROUP_WIDTH / colorSet.length) * scale);

        int totalDrawn = 0;
        
        for (int c = 0; c < colorSet.length; c++) {
            g2d.setColor(colorSet[c]);
            
            int drawWidth;
            
            if(c < colorSet.length - 1) {
                drawWidth = colorWidth;
                totalDrawn += drawWidth;
            } else {
                drawWidth = ((int)(BluDisjointGroupEntry.DISJOINT_GROUP_WIDTH * scale)) - totalDrawn;
            }
            
            g2d.fillRect(p.x + c * colorWidth, p.y, drawWidth, (int)(BluDisjointGroupEntry.DISJOINT_GROUP_HEIGHT * scale));

        }

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
        
        Point labelOffset = group.getLabelOffset();
        
        int textAreaX = p.x + (int)(scale * labelOffset.x);
        int textAreaY = p.y + (int)(scale * labelOffset.y);
        
        g2d.setPaint(bgColor);
        
        g2d.fillRect(textAreaX, textAreaY, (int)(GenericGroupEntry.ENTRY_WIDTH * scale), (int)(GenericGroupEntry.ENTRY_HEIGHT* scale));

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
        g2d.drawRect(textAreaX, textAreaY, (int)(GenericGroupEntry.ENTRY_WIDTH * scale), (int)(GenericGroupEntry.ENTRY_HEIGHT* scale));

        g2d.setStroke(savedStroke);
    }
}

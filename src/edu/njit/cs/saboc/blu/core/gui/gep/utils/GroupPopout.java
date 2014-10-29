package edu.njit.cs.saboc.blu.core.gui.gep.utils;

import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.TextDrawingUtilities;
import edu.njit.cs.saboc.blu.core.gui.gep.Viewport;
import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericGroupEntry;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 *
 * @author Chris
 */
public class GroupPopout {
    private Rectangle originalBounds;
    private Rectangle bounds;

    private boolean dead = false;

    private BufferedImage textImage;

    private GenericGroupEntry groupEntry;

    private Color bgColor;
    
    private BluGraph graph;

    public GroupPopout(BluGraph graph, GenericGroupEntry groupEntry) {
        this.graph = graph;
        
        this.groupEntry = groupEntry;
        this.bgColor = groupEntry.getBackground();
        
        GenericConceptGroup group = groupEntry.getGroup();

        originalBounds = new Rectangle(groupEntry.getAbsoluteX(), groupEntry.getAbsoluteY(),
                groupEntry.getWidth(), groupEntry.getHeight());

        bounds = (Rectangle)originalBounds.clone();

        textImage = new BufferedImage(bounds.width * 2, (int)(bounds.height * 4.5), BufferedImage.TYPE_INT_ARGB);

        Graphics2D textGraphics = textImage.createGraphics();
        textGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

        textGraphics.setColor(new Color(0, 0, 0, 0));
        textGraphics.fillRect(0, 0, bounds.width * 2, (int)(bounds.height * 4.5));

        textGraphics.setColor(Color.BLACK);

        TextDrawingUtilities.drawTextWithNewlines(textGraphics, group.getRoot().getName(), originalBounds, 10, 10);

        String conceptTxt = String.format("%d %s", group.getConceptCount(), 
                (group.getConceptCount() == 1 ? "Concept" : "Concepts"));

        textGraphics.drawString(conceptTxt,
                bounds.width - (conceptTxt.length() * 5) / 2,
                (int)(bounds.height * 4.5) - 8);
    }

    public Rectangle getCurrentBounds() {
        return bounds;
    }

    public boolean isDead() {
        return dead;
    }
    
    public void setBackgroundColor(Color color) {
        this.bgColor = color;
    }

    public void doExpand() {
        
        if(bounds.width > 2 * originalBounds.width) {
            return;
        }

        if(dead) {
            dead = false;
            setBackgroundColor(groupEntry.getBackground());
        }

        bounds.x -= 8;
        bounds.y -= 8;
        bounds.width += 16;
        bounds.height += 16;
    }

    public void doContract() {
        
        if(bounds.width - 16 < originalBounds.width) {
            dead = true;
            bounds = (Rectangle)originalBounds.clone();
            return;
        }

        bounds.x += 8;
        bounds.y += 8;
        bounds.width -= 16;
        bounds.height -= 16;
    }

    public void drawPopout(Graphics g, Viewport viewport) {
        Point scaledSize = viewport.getScaledSize(groupEntry.getWidth(), groupEntry.getHeight());
        
        int dW = bounds.width - scaledSize.x;
        int dH = bounds.height - scaledSize.y;

        int x = groupEntry.getAbsoluteX() - viewport.region.x;
        int y = groupEntry.getAbsoluteY() - viewport.region.y;
        
        Point scaledLocation = viewport.getScaledPoint(new Point(x, y));

        g.setColor(bgColor);
        g.fillRoundRect(scaledLocation.x - (dW / 2),
                scaledLocation.y - (dH / 2),
                bounds.width,
                bounds.height,
                20,
                20);

        g.setColor(Color.BLACK);
        g.drawRoundRect(scaledLocation.x - (dW / 2),
                scaledLocation.y - (dH / 2),
                bounds.width,
                bounds.height,
                20,
                20);

        g.drawImage(textImage, scaledLocation.x + 2 - (dW / 2), scaledLocation.y + 2 - (dH / 2), (int) (bounds.width) - 4,
                (int) (bounds.height) - 4, null);
    }
}

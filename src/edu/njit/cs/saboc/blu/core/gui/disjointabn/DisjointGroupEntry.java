package edu.njit.cs.saboc.blu.core.gui.disjointabn;

import edu.njit.cs.saboc.blu.core.abn.disjoint.nodes.DisjointGenericConceptGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.TextDrawingUtilities;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;

/**
 *
 * @author cro3
 */
public abstract class DisjointGroupEntry<T extends DisjointGenericConceptGroup> {
    public static enum State {
        Unselected,
        Selected,
        HighlightedAsParent,
        HighlightedAsChild
    }

    public static final int ENTRY_WIDTH = 120;
    public static final int ENTRY_HEIGHT = 50;

    private static final int ENTRY_TEXT_WIDTH = 100;
    private static final int ENTRY_TEXT_HEIGHT = 30;

    private final T disjointGroup;
    private Rectangle currentBounds = new Rectangle();

    private boolean selected = false;
    private boolean mousedOver = false;

    private Color[] colorSet;
    
    private State state;

    public DisjointGroupEntry(T disjointGroup) {
        this.disjointGroup = disjointGroup;
        this.state = State.Unselected;
    }
    
    public State getState() {
        return state;
    }
    
    public void setState(State state) {
        this.state = state;
    }

    public void setColorSet(Color[] colors) {
        this.colorSet = colors;
    }
    
    public void setMousedOver(boolean isMousedOver) {
        this.mousedOver = isMousedOver;
    }
    
    public boolean isMousedOver() {
        return this.mousedOver;
    }

    public T getDisjointGroup() {
        return disjointGroup;
    }
    
    public void setSelected(boolean isSelected) {
        this.selected = isSelected;
        
        if(selected) {
            setState(State.Selected);
        } else {
            setState(State.Unselected);
        }
    }
    
    public boolean isSelected() {
        return selected;
    }

    public void drawAt(Graphics2D g2d, int x, int y) {
        Rectangle bounds = new Rectangle(x, y, ENTRY_WIDTH, ENTRY_HEIGHT);
        currentBounds = bounds;

        AffineTransform transform = AffineTransform.getTranslateInstance(bounds.x, bounds.y);

        g2d.setTransform(transform);

        int colorWidth = ENTRY_WIDTH / colorSet.length;

        for (int c = 0; c < colorSet.length; c++) {
            g2d.setColor(colorSet[c]);
            g2d.fillRect(c * colorWidth, 0, colorWidth, bounds.height);
        }

        Stroke savedStroke = g2d.getStroke();
        
        if (mousedOver) {
            g2d.setStroke(new BasicStroke(2));
            g2d.setPaint(Color.CYAN);
        } else {
            if (selected) {
                g2d.setStroke(new BasicStroke(2));
            }

            g2d.setPaint(Color.BLACK);
        }

        g2d.drawRect(0, 0, bounds.width, bounds.height);

        g2d.setStroke(savedStroke);

        String text = getRootName();

        //text = text.substring(0, text.lastIndexOf("("));

        if (text.length() > 20) {
            text = text.substring(0, 20) + "...";
        }

        text += "(" + disjointGroup.getConceptCount() + ")";

        Font savedFont = g2d.getFont();

        g2d.setFont(new Font("Tahoma", Font.PLAIN, 11));

        switch(state) {
            case Selected :
                g2d.setColor(Color.YELLOW);
                break;
            case HighlightedAsParent:
                g2d.setColor(Color.BLUE);
                break;
            case HighlightedAsChild:
                g2d.setColor(Color.MAGENTA);
                break;
            default: 
                g2d.setColor(Color.WHITE);
        }
        
        g2d.fillRect(10, 10, ENTRY_TEXT_WIDTH, ENTRY_TEXT_HEIGHT);

        if (mousedOver) {
            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(Color.CYAN);
        } else {
            if (selected) {
                g2d.setStroke(new BasicStroke(2));
            }

            g2d.setColor(Color.BLACK);
        }

        g2d.drawRect(10, 10, ENTRY_TEXT_WIDTH, ENTRY_TEXT_HEIGHT);

        g2d.setStroke(savedStroke);
        g2d.setColor(Color.BLACK);

        TextDrawingUtilities.drawTextWithinBounds(g2d, text,
                new Rectangle(2, 10, ENTRY_TEXT_WIDTH - 4, ENTRY_TEXT_HEIGHT - 4),
                new Rectangle(0, 0, ENTRY_WIDTH, ENTRY_HEIGHT));

        g2d.setFont(savedFont);
    }
    
    public Rectangle getBounds() {
        return currentBounds;
    }
    
    protected abstract String getRootName();

    public String toString() {
        return getRootName();
    }
}

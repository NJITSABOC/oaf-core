package edu.njit.cs.saboc.blu.core.gui.gep;

import edu.njit.cs.saboc.blu.core.graph.nodes.AbNNodeEntry;
import java.awt.Point;
import java.util.Optional;

/**
 *
 * @author Chris O
 */
public class ViewportAutoScroller {
    private Optional<Point> targetPoint = Optional.empty();
    
    private Viewport viewport;
    
    public ViewportAutoScroller() {
        
    }
    
    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }
    
    public void navigateToPoint(Point thePoint) {
        this.targetPoint = Optional.of(thePoint);
    }
    
    public void navigateToNodeEntry(AbNNodeEntry entry) {
        int entryXPos = entry.getAbsoluteX();
        int entryYPos = entry.getAbsoluteY();
        
        int xBufferArea = (int)(entry.getWidth() * viewport.scale * 2);
        int yBufferArea = (int)(entry.getHeight() * viewport.scale * 2);
        
        int destX = viewport.region.x;
        int destY = viewport.region.y;
        
        int graphWidth = viewport.getGraph().getAbNWidth();
        int graphHeight = viewport.getGraph().getAbNHeight();
        
        if (entryXPos + entry.getWidth() <= viewport.region.x + xBufferArea
                || entryXPos >= viewport.region.x + viewport.region.width - xBufferArea) {
            
            destX = entry.getAbsoluteX() + entry.getWidth() / 2 - viewport.region.width / 2;
            
            if (destX < 0) {
                destX = 0;
            } else if (destX > graphWidth - viewport.region.width) {
                destX = graphWidth - viewport.region.width;
            }
        }
        
        if (entryYPos + entry.getHeight() <= viewport.region.y + yBufferArea ||
                entryYPos >= viewport.region.y + viewport.region.height - yBufferArea) {
            
            destY = entry.getAbsoluteY() + entry.getHeight() / 2 - viewport.region.height / 2;
            
            if (destY < 0) {
                destY = 0;
            } else if (destY > graphHeight - viewport.region.height) {
                destY = graphHeight - viewport.region.height;
            }
        }
        
        if (viewport.scale > 0.6) {
            targetPoint = Optional.of(new Point(destX, destY));
        }
    }
    
    public void update() {
        if (targetPoint.isPresent()) {
            Point thePoint = targetPoint.get();
            
            int dx = thePoint.x - viewport.region.x;
            int dy = thePoint.y - viewport.region.y;

            final int MOVE_SPEED = 196;
            final int CLOSEST_DISTANCE = 128;

            int distSquared = dx * dx + dy * dy;

            if (Math.sqrt(distSquared) <= CLOSEST_DISTANCE) {
                viewport.setLocation(thePoint);
                
                targetPoint = Optional.empty();
            } else {
                double angle = Math.atan2(dy, dx);
                viewport.moveScaled(new Point((int) (MOVE_SPEED * Math.cos(angle)), (int) (MOVE_SPEED * Math.sin(angle))));
            }
        }
    }
}

package edu.njit.cs.saboc.blu.core.gui.gep;

import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphEdge;
import edu.njit.cs.saboc.blu.core.graph.nodes.PartitionedNodeEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericPartitionEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AbNDrawingUtilities;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.GraphMouseStateMonitor;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.GraphSelectionStateMonitor;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AbNPainter;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import javax.swing.JPanel;
import javax.swing.Timer;


/**
 *
 * @author Chris O
 */
public class AbNDisplayPanel extends JPanel {
    
    public interface AbNEntitySelectionListener {
        public void nodeEntrySelected(SinglyRootedNodeEntry nodeEntry);
        public void partitionEntrySelected(GenericPartitionEntry entry);
        public void noEntriesSelected();
    }
    
    public interface ZoomFactorChangedListener {
        public void zoomFactorChanged(int zoomFactor);
    }
    
    private enum DisplayState {
        Uninitialized,
        Initializing,
        Alive,
        Paused,
        Loading,
        Dead
    }

    private DisplayState panelState = DisplayState.Uninitialized;
    
    private BluGraph graph;
    private Viewport viewport;
    

    private final ArrayList<AbNDisplayWidget> widgets = new ArrayList<>();
    
    private final ArrayList<UpdateableEntity> updateableEntities = new ArrayList<>();
    
    private int currentTick = 0;
    
    private final Timer updateTimer = new Timer(50, (ae) -> {
        if (panelState == DisplayState.Alive) {
            
            updateableEntities.forEach((entity) -> {
               entity.update(currentTick);
            });

            updateViewportMovementByMouse(currentTick);
            
            currentTick++;
        }
    });
    
    private volatile boolean doDraw = false;
    
    public void requestRedraw() {
        this.doDraw = true;
    }
    
    private final Thread drawThread = new Thread(() -> {
        long lastDraw = System.currentTimeMillis();
        
        while(panelState != DisplayState.Dead) {
            
            long currentDraw = System.currentTimeMillis();
            
            while (currentDraw - lastDraw < 50 && !doDraw) { // Draw a frame every 20ms or so (if neccessary)
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    
                }
                
                currentDraw = System.currentTimeMillis();
            }
            
            repaint();
            lastDraw = currentDraw;
            
            doDraw = false;
        }
    });

    private final GraphMouseStateMonitor mouseStateMonitor = new GraphMouseStateMonitor();
    
    private GraphSelectionStateMonitor selectionStateMonitor;
    
    private final ViewportAutoScroller autoScroller  = new ViewportAutoScroller();
    
    
    private AbNPainter painter;

    private final ArrayList<AbNEntitySelectionListener> selectionListeners = new ArrayList<>();
    private final ArrayList<ZoomFactorChangedListener> zoomFactorChangedListeners = new ArrayList<>();

    public AbNDisplayPanel() {
        this.setLayout(null);
        
        initializeFixedListeners();

        setFocusable(true);
        
        drawThread.start();
    }
    
    public ViewportAutoScroller getAutoScroller() {
        return autoScroller;
    }
    
    public Viewport getViewport() {
        return viewport;
    }
    
    public final void addWidget(AbNDisplayWidget widget) {
        widgets.add(widget);
        
        addUpdateableEntity(widget);
        
        this.add(widget);
    }
    
    public final void removeWidget(AbNDisplayWidget widget) {
        widgets.remove(widget);
        
        removeUpdateableEntity(widget);
        
        this.remove(widget);
    }
    
    public final void addUpdateableEntity(UpdateableEntity entity) {
        this.updateableEntities.add(entity);
    }
    
    public final void removeUpdateableEntity(UpdateableEntity entity) {
        this.updateableEntities.remove(entity);
    }
    
    public void addAbNSelectionListener(AbNEntitySelectionListener listener) {
        selectionListeners.add(listener);
    }
    
    public void removeAbNSelectionListener(AbNEntitySelectionListener listener) {
        selectionListeners.remove(listener);
    }
    
    public void addZoomFactorChangedListener(ZoomFactorChangedListener listener) {
        zoomFactorChangedListeners.add(listener);
    }
    
    public void removeZoomFactorChangedListener(ZoomFactorChangedListener listener) {
        zoomFactorChangedListeners.remove(listener);
    }
        
    public void initialize(BluGraph graph, AbNPainter painter, AbNInitialDisplayAction initialDisplayAction) {
        updateTimer.stop();
        
        this.panelState = DisplayState.Initializing;

        reset();
        
        this.graph = graph;
        this.painter = painter;
        
        this.viewport = new Viewport(graph);
        this.selectionStateMonitor = new GraphSelectionStateMonitor(graph);
        
        this.autoScroller.setViewport(viewport);
                
        addUpdateableEntity(autoScroller);

        viewport.setParentFrameSize(this.getSize());
        
        setZoomFactor(100);
        
        this.panelState = DisplayState.Alive;
        
        updateTimer.start();
        
        initialDisplayAction.doInitialDisplay(this);
    }
    
    public void setZoomFactor(int zoomLevel) {
        viewport.setZoom(zoomLevel);
        
        zoomFactorChanged(zoomLevel);
    }
    
    private void zoomFactorChanged(int zoomValue) {
        zoomFactorChangedListeners.forEach( (listener) -> {
           listener.zoomFactorChanged(zoomValue); 
        });
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (panelState == DisplayState.Alive) {
            viewport.setSizeScaled(getWidth(), getHeight());

            drawAbstractionNetwork(g2d, viewport);

            drawLocationIndicators(g2d, Color.RED);

            if (mouseStateMonitor.mouseDragging()) {
                drawNavigationPipper(g2d);
            }
        } else if (panelState == DisplayState.Loading || panelState == DisplayState.Initializing || panelState == DisplayState.Uninitialized) {

            if (graph != null) {
                drawAbstractionNetwork(g2d, viewport);
            }

            Color fadeOutColor = new Color(0, 0, 0, 200);

            g2d.setColor(fadeOutColor);
            g2d.fillRect(0, 0, getWidth(), getHeight());

            drawShortMessage(g2d, "Loading, please wait...");
        }
    }
    
    public void kill() {
        this.panelState = DisplayState.Dead;
        updateTimer.stop();
    }
    
    public void doLoading() {
        this.panelState = DisplayState.Loading;
    }

    private void reset() {
        autoScroller.cancelAutoNavigation();
        
        selectionListeners.clear();
        zoomFactorChangedListeners.clear();
        
        updateableEntities.clear();
        
        widgets.forEach( (widget) -> {
            AbNDisplayPanel.this.remove(widget);
        });
        
        widgets.clear();
    }
    
    private void initializeFixedListeners() {

        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (panelState == DisplayState.Alive) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        mouseStateMonitor.setClickedLocation(e.getPoint());

                        AbNDisplayPanel.this.requestRedraw();
                    }
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (panelState == DisplayState.Alive) {

                    if (e.getButton() == MouseEvent.BUTTON1) {
                        mouseStateMonitor.setClickedLocation(null);
                        mouseStateMonitor.setCurrentDraggedLocation(null);

                        AbNDisplayPanel.this.requestRedraw();
                    }
                }
            }

            public void mouseClicked(MouseEvent e) {

                AbNDisplayPanel.this.requestFocus();

                if (panelState == DisplayState.Alive) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        int clickCount = e.getClickCount();

                        Point pointOnGraph = viewport.getPointOnGraph(e.getPoint());

                        SinglyRootedNodeEntry groupEntry = getGroupEntryAtPoint(pointOnGraph);

                        if (groupEntry != null) {
                            selectionStateMonitor.setSelectedGroup(groupEntry);

                            if (clickCount == 1) {
                                handleSingleClickOnGroupEntry(groupEntry);
                            } else if (clickCount == 2) {

                            }

                        } else {

                            final GenericPartitionEntry partition = getContainerPartitionAtPoint(pointOnGraph);

                            if (partition != null) {
                                selectionStateMonitor.setSelectedPartition(partition);

                                if (clickCount == 1) {
                                    handleSingleClickOnPartitionEntry(partition);
                                } else if (clickCount == 2) {

                                }

                            } else {
                                selectionStateMonitor.resetAll();

                                handleClickOutsideAnyEntry();
                            }
                        }

                        AbNDisplayPanel.this.requestRedraw();
                    }
                }
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (panelState == DisplayState.Alive) {
                    if (mouseStateMonitor.mouseDragging()) {
                        mouseStateMonitor.setCurrentDraggedLocation(e.getPoint());
                        mouseStateMonitor.setCurrentMouseLocation(e.getPoint());
                    } else {
                        if (e.getPoint().distance(mouseStateMonitor.getClickedLocation()) > 16) {
                            mouseStateMonitor.setCurrentDraggedLocation(e.getPoint());
                            mouseStateMonitor.setCurrentMouseLocation(e.getPoint());
                        }
                    }

                    AbNDisplayPanel.this.requestRedraw();
                }
            }

            public void mouseMoved(MouseEvent e) {
                if (panelState == DisplayState.Alive) {
                    Point mouseLocation = viewport.getPointOnGraph(e.getPoint());

                    mouseStateMonitor.setCurrentMouseLocation(e.getPoint());

                    SinglyRootedNodeEntry group = getGroupEntryAtPoint(mouseLocation);

                    if (group != null) {
                        selectionStateMonitor.setMousedOverGroup(group);
                    } else {

                        GenericPartitionEntry partition = getContainerPartitionAtPoint(mouseLocation);

                        if (partition != null && partition.isVisible()) {

                            // Only highlight if mouse at the top of the partition.
                            if (partition.doHighlight(-1, mouseLocation.y)) {
                                selectionStateMonitor.setMousedOverPartition(partition);
                            }
                        } else {
                            selectionStateMonitor.resetMousedOver();
                        }
                    }

                    AbNDisplayPanel.this.requestRedraw();
                }
            }
        });

        this.addComponentListener(new ComponentAdapter() {
            
            public void componentResized(ComponentEvent e) {
                if (panelState == DisplayState.Alive) {
                    viewport.setParentFrameSize(getSize());

                    AbNDisplayPanel.this.requestRedraw();
                }
            }
        });
        
        this.addMouseWheelListener(new MouseWheelListener() {
            public void mouseWheelMoved(MouseWheelEvent e) {
                
                if (panelState == DisplayState.Alive) {
                    if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
                        int zoomLevelChange = -e.getUnitsToScroll() / 3;
                        int currentZoomValue = viewport.getZoomFactor();

                        int newZoomLevel = currentZoomValue + zoomLevelChange * 10;

                        setZoomFactor(newZoomLevel);

                        AbNDisplayPanel.this.requestRedraw();
                    }
                }
            }
        });

        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {

                if (panelState == DisplayState.Alive) {
                    
                    final int MOVE_SPEED = 64;
                    
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                            viewport.moveVertical(-MOVE_SPEED);
                            break;
                        case KeyEvent.VK_DOWN:
                            viewport.moveVertical(MOVE_SPEED);
                            break;
                        case KeyEvent.VK_LEFT:
                            viewport.moveHorizontal(-MOVE_SPEED);
                            break;
                        case KeyEvent.VK_RIGHT:
                            viewport.moveHorizontal(MOVE_SPEED);
                            break;
                    }

                    AbNDisplayPanel.this.requestRedraw();
                }
            }
        });
    }
    
    public BluGraph getGraph() {
        return graph;
    }
    
    private SinglyRootedNodeEntry getGroupEntryAtPoint(Point point) {
        Component containerLevel = graph.getComponentAt(point);

        if (containerLevel != null && containerLevel instanceof PartitionedNodeEntry) {
            Component containerPartitionLevel = containerLevel.getComponentAt(
                    point.x - containerLevel.getX(),
                    point.y - containerLevel.getY());

            if (containerPartitionLevel != null && containerPartitionLevel instanceof GenericPartitionEntry) {
                Component groupLevel = containerPartitionLevel.getComponentAt(
                        point.x - containerLevel.getX() - containerPartitionLevel.getX(),
                        point.y - containerLevel.getY() - containerPartitionLevel.getY());

                if (groupLevel != null && groupLevel instanceof SinglyRootedNodeEntry) {
                    SinglyRootedNodeEntry group = (SinglyRootedNodeEntry) groupLevel;

                    return group;
                }
            }
        }

        return null;
    }

    private GenericPartitionEntry getContainerPartitionAtPoint(Point point) {
        Component containerLevel = graph.getComponentAt(point);

        if (containerLevel != null && containerLevel instanceof PartitionedNodeEntry) {
            Component containerPartitionLevel = containerLevel.getComponentAt(
                    point.x - containerLevel.getX(),
                    point.y - containerLevel.getY());

            if (containerPartitionLevel != null && containerPartitionLevel instanceof GenericPartitionEntry) {
                return (GenericPartitionEntry)containerPartitionLevel;
            }
        }

        return null;
    }

    public void highlightEntriesForSearch(Set<SinglyRootedNode> nodes) {       
        selectionStateMonitor.setSearchResults(nodes);
    }

    private void drawAbstractionNetwork(Graphics2D g2d, Viewport viewport) {
               
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        Collection<? extends PartitionedNodeEntry> containerEntries = graph.getContainerEntries().values();
        
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        
        if (viewport.getViewScale() > 0.2) {
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }

        for(PartitionedNodeEntry container : containerEntries) {
            if(viewport.getViewRegion().intersects(container.getBounds())) {
                AbNDrawingUtilities.paintContainer(painter, g2d, container, viewport, graph.getLabelManager());
            }
        }
        
        ArrayList<GraphEdge> graphEdges = graph.getEdges();
        
        for(GraphEdge edge : graphEdges) {
            for(JPanel segment : edge.getSegments()) {
                if(viewport.getViewRegion().intersects(segment.getBounds())) {
                    AbNDrawingUtilities.paintEdge(g2d, edge, viewport);
                    break;
                }
            }
        }
    }
    
    public BufferedImage getCurrentViewImage() {
        Rectangle viewportRegion = viewport.getViewRegion();
        
        BufferedImage image = new BufferedImage(
                Math.min(viewportRegion.width, graph.getWidth()),
                Math.min(viewportRegion.height, graph.getHeight()), BufferedImage.TYPE_INT_RGB);
        
        Rectangle drawRegion = new Rectangle(viewportRegion);
        
        drawRegion.x = Math.max(viewportRegion.x, 0);
        drawRegion.y = Math.max(viewportRegion.y, 0);
        
        Viewport drawingViewport = new Viewport(graph);
        drawingViewport.setLocation(drawRegion.getLocation());
        drawingViewport.setSizeAbsolute(image.getWidth(), image.getHeight());

        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.WHITE);

        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        
        drawAbstractionNetwork(graphics, drawingViewport);
        
        return image;
    }
    
    private void drawLocationIndicators(Graphics2D g2d, Color color) {
        g2d.setColor(color);
        
        Rectangle viewportRegion = viewport.getViewRegion();

        int xStartPoint = (int)(((double)viewportRegion.x / graph.getWidth()) * getWidth());
        int xEndPoint = xStartPoint + (int)(((double)viewportRegion.width / graph.getWidth()) * getWidth());

        g2d.fill3DRect(xStartPoint, getHeight() - 20, 2, 20, true);
        g2d.fill3DRect(xEndPoint - 2, getHeight() - 20, 2, 20, true);

        int yStartPoint = (int)(((double)viewportRegion.y / graph.getHeight()) * getHeight());
        int yEndPoint = yStartPoint + (int)(((double)viewportRegion.height / graph.getHeight()) * getHeight());

        g2d.fill3DRect(getWidth() - 20, yStartPoint, 20, 2, true);
        g2d.fill3DRect(getWidth() - 20, yEndPoint - 2, 20, 2, true);
    }

    private void drawNavigationPipper(Graphics2D g2d) {
        Point lastClickedPoint = mouseStateMonitor.getClickedLocation();
        Point currentMousePoint = mouseStateMonitor.getCurrentMouseLocation();
        
        Stroke savedStroke = g2d.getStroke();

        g2d.setStroke(new BasicStroke(4));

        g2d.setColor(new Color(32, 32, 32, 128));
        g2d.fillOval(lastClickedPoint.x - 4, lastClickedPoint.y - 4, 10, 10);

        g2d.drawOval(lastClickedPoint.x - 74, lastClickedPoint.y - 74, 150, 150);

        int deltaX = lastClickedPoint.x - currentMousePoint.x;
        int deltaY = lastClickedPoint.y - currentMousePoint.y;

        int radius = (int) Math.min(Math.sqrt(deltaX * deltaX + deltaY * deltaY), 75);

        Color base = new Color(100, 100, 255);

        final int LEVEL_TRANSPARENCY = 100;

        if (radius > 0) {
            if (radius >= 25) {
                g2d.setColor(new Color(base.getRed(), base.getGreen(), base.getBlue(), LEVEL_TRANSPARENCY));
                g2d.fillOval(lastClickedPoint.x - 25, lastClickedPoint.y - 25, 50, 50);
            } else {
                int transparency = (int) (((double) radius / 25.0) * LEVEL_TRANSPARENCY);
                g2d.setColor(new Color(base.getRed(), base.getGreen(), base.getBlue(), transparency));
                g2d.fillOval(lastClickedPoint.x - radius, lastClickedPoint.y - radius, 2 * radius, 2 * radius);
            }

            if (radius >= 50) {
                g2d.setColor(new Color(base.getRed(), base.getGreen(), base.getBlue(), LEVEL_TRANSPARENCY));
                g2d.fillOval(lastClickedPoint.x - 50, lastClickedPoint.y - 50, 100, 100);
            } else if (radius > 25) {
                int transparency = (int) (((double) (radius - 25) / 25.0) * LEVEL_TRANSPARENCY);
                g2d.setColor(new Color(base.getRed(), base.getGreen(), base.getBlue(), transparency));
                g2d.fillOval(lastClickedPoint.x - radius, lastClickedPoint.y - radius, 2 * radius, 2 * radius);
            }

            if (radius > 50) {
                int transparency = (int) (((double) (radius - 50) / 25.0) * LEVEL_TRANSPARENCY);
                g2d.setColor(new Color(base.getRed(), base.getGreen(), base.getBlue(), transparency));
                g2d.fillOval(lastClickedPoint.x - radius, lastClickedPoint.y - radius, 2 * radius, 2 * radius);
            }
        }

        g2d.setColor(Color.BLACK);

        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(lastClickedPoint.x, lastClickedPoint.y, currentMousePoint.x, currentMousePoint.y);

        g2d.setStroke(new BasicStroke(6));
        g2d.drawOval(lastClickedPoint.x - 75, lastClickedPoint.y - 75, 150, 150);
        g2d.fillOval(lastClickedPoint.x - 6, lastClickedPoint.y - 6, 12, 12);

        g2d.setStroke(new BasicStroke(4));

        g2d.setColor(base);
        g2d.fillOval(lastClickedPoint.x - 5, lastClickedPoint.y - 5, 10, 10);
        g2d.drawOval(lastClickedPoint.x - 75, lastClickedPoint.y - 75, 150, 150);

        g2d.setStroke(savedStroke);
    }
        
    private void drawShortMessage(Graphics2D g2d, String message) {
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        
        final int BUBBLE_WIDTH = 600;
        final int BUBBLE_HEIGHT = 340;
        
        final int BORDER_OFFSET = 4;
        
        g2d.setColor(Color.WHITE);
        
        int drawX = (panelWidth / 2) - BUBBLE_WIDTH / 2;
        int drawY = (panelHeight / 2) - BUBBLE_HEIGHT / 2;
        
        g2d.fillOval(drawX, drawY, BUBBLE_WIDTH, BUBBLE_HEIGHT);
        
        g2d.setColor(new Color(100, 100, 255));

        g2d.fillOval(drawX + BORDER_OFFSET, drawY + BORDER_OFFSET, BUBBLE_WIDTH - 2 * BORDER_OFFSET, BUBBLE_HEIGHT - 2 * BORDER_OFFSET);

        g2d.setColor(Color.WHITE);
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 50));
        
        int strSize = g2d.getFontMetrics().stringWidth(message);
        
        int strX = (panelWidth / 2) - (strSize / 2);

        g2d.drawString(message, strX, drawY + BUBBLE_HEIGHT / 2);
        
    }

    private void updateViewportMovementByMouse(int tick) {
        
        if (mouseStateMonitor.mouseDragging()) {
            
            Point currentMousePoint = mouseStateMonitor.getCurrentMouseLocation();
            Point lastClickedPoint = mouseStateMonitor.getClickedLocation();
            
            Point delta = new Point(
                    (currentMousePoint.x - lastClickedPoint.x) / 5,
                    (currentMousePoint.y - lastClickedPoint.y) / 5);

            viewport.moveScaled(delta);
            
            AbNDisplayPanel.this.requestRedraw();
        }
    }
     
    
    private void handleSingleClickOnGroupEntry(SinglyRootedNodeEntry nodeEntry) {
        selectionListeners.forEach( (listener) -> {
            listener.nodeEntrySelected(nodeEntry);
        });
    }

    private void handleSingleClickOnPartitionEntry(GenericPartitionEntry entry) {
        selectionListeners.forEach((listener) -> {
            listener.partitionEntrySelected(entry);
        });
    }

    private void handleClickOutsideAnyEntry() {
        selectionListeners.forEach((listener) -> {
            listener.noEntriesSelected();
        });
    }
}

package edu.njit.cs.saboc.blu.core.gui.gep;

import SnomedShared.generic.GenericConceptGroup;
import SnomedShared.generic.GenericGroupContainer;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphEdge;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericContainerEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericGroupEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericPartitionEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.ui.BLUPartitionedAbNUIConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AbNDrawingUtilities;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.GraphMouseStateMonitor;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.GraphSelectionStateMonitor;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn.AbstractAbNDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractNodeDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractNodePanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.loading.LoadingPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.GroupPopout;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AbNPainter;
import edu.njit.cs.saboc.blu.core.gui.iconmanager.IconManager;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 *
 * @author Chris
 */
public class EnhancedGraphExplorationPanel extends JPanel {
    
    private enum GEPState {
        Uninitialized,
        Initializing,
        Alive,
        Paused,
        Loading,
        Dead
    }

    private GEPState gepState = GEPState.Uninitialized;
    
    private BluGraph graph;
    private Viewport viewport;
         
    private Timer updateTimer = new Timer(50, new ActionListener() {
        public void actionPerformed(ActionEvent ae) {
            
            if (gepState == GEPState.Alive) {
                updateNavButtonPressed();
                updateViewportMovementByMouse();
                updatePopups();
                updateViewportMovement();
            }
            
        }
    });
    
    private volatile boolean doDraw = false;
    
    private final Thread drawThread = new Thread(new Runnable() {
        public void run() {
            
            long lastDraw = System.currentTimeMillis();
            
            while(gepState != GEPState.Dead) {
                
                long currentDraw = System.currentTimeMillis();
                
                while (currentDraw - lastDraw < 50 && !doDraw) { // Draw a frame every 20ms or so (if neccessary)
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        
                    }
                    
                    currentDraw = System.currentTimeMillis();
                }

                graphPanel.repaint();
                lastDraw = currentDraw;

                doDraw = false;
            }
        }
    });
    
    private void requestRedraw() {
        this.doDraw = true;
    }
    
    private JPanel graphPanel;
    
    private JSlider zoomSlider;
    private JButton moveUpBtn;
    private JButton moveDownBtn;
    private JButton moveLeftBtn;
    private JButton moveRightBtn;

    private JSplitPane splitPane;
    private JPanel detailsPanel;
    
    private final LoadingPanel loadingPanel;
    private AbstractAbNDetailsPanel abnDetailsPanel;
    private Optional<AbstractNodePanel> groupDetailsPanel;
    private Optional<AbstractNodePanel> containerDetailsPanel;

    private Point targetEntryPoint = null;

    private final Map<GenericGroupEntry, GroupPopout> groupPopouts = Collections.synchronizedMap(
            new HashMap<GenericGroupEntry, GroupPopout>());

    private boolean magnifyGroupMode = false;
       
    private final GraphMouseStateMonitor mouseStateMonitor = new GraphMouseStateMonitor();
    
    private GraphSelectionStateMonitor selectionStateMonitor;
    
    private AbNPainter painter;
    
    private BLUConfiguration configuration;
    
    public EnhancedGraphExplorationPanel() {
        intitializeFixedUIComponents();
        initializeFixedListeners();

        loadingPanel = new LoadingPanel();
        
        setFocusable(true);

        drawThread.start();
    }
    
    public void setContents(final BluGraph graph, AbNPainter painter, BLUConfiguration configuration) {
        updateTimer.stop();
        
        this.gepState = GEPState.Initializing;
        
        this.configuration = configuration;
        this.graph = graph;
        this.painter = painter;
        
        this.configuration.getUIConfiguration().setGEP(this);
        
        this.viewport = new Viewport(graph);
        this.selectionStateMonitor = new GraphSelectionStateMonitor(graph);
        
        this.initializeContentComponents(graph, painter, configuration);
        
        jumpToRoot();
        
        updateTimer.start();
        
        viewport.setZoom(100, graphPanel.getWidth(), graphPanel.getHeight());
        
        this.gepState = GEPState.Alive;
    }
    
    public void killGEP() {
        this.gepState = GEPState.Dead;
    }
    
    public void showLoading() {
        this.gepState = GEPState.Loading;
        
        this.setDetailsPanelContents(this.loadingPanel);
    }
    
    private void initializeContentComponents(final BluGraph graph, AbNPainter painter, BLUConfiguration configuration) {
        this.abnDetailsPanel = configuration.getUIConfiguration().createAbNDetailsPanel();
        
        if (abnDetailsPanel != null) {
            this.setDetailsPanelContents(abnDetailsPanel);
        }

        if (configuration.getUIConfiguration().hasGroupDetailsPanel()) {
            groupDetailsPanel = Optional.of(configuration.getUIConfiguration().createGroupDetailsPanel());
        } else {
            this.groupDetailsPanel = Optional.empty();
        }

        if (configuration.getUIConfiguration() instanceof BLUPartitionedAbNUIConfiguration) {
            BLUPartitionedAbNUIConfiguration config = (BLUPartitionedAbNUIConfiguration) configuration.getUIConfiguration();

            if (config.hasContainerDetailsPanel()) {
                containerDetailsPanel = Optional.of(config.createContainerDetailsPanel());
            } else {
                this.containerDetailsPanel = Optional.empty();
            }
        } else {
            this.containerDetailsPanel = Optional.empty();
        }
    }

    private void intitializeFixedUIComponents() {

        graphPanel = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (gepState == GEPState.Alive) {
                    viewport.setSizeScaled(graphPanel.getWidth(), graphPanel.getHeight());
                    
                    drawAbstractionNetwork(g2d, viewport);

                    drawPopups(g);

                    drawLocationIndicators(g2d, Color.RED);

                    if (mouseStateMonitor.mouseDragging()) {
                        drawNavigationPipper(g2d);
                    }
                } else if(gepState == GEPState.Loading || gepState == GEPState.Initializing || gepState == GEPState.Uninitialized) {
                    
                    if(graph != null) {
                        drawAbstractionNetwork(g2d, viewport);
                    }
                    
                    Color fadeOutColor = new Color(0, 0, 0, 200);
                    
                    g2d.setColor(fadeOutColor);
                    g2d.fillRect(0, 0, graphPanel.getWidth(), graphPanel.getHeight());
                    
                    drawShortMessage(g2d, "Loading, please wait...");
                }
            }
        };
        
        graphPanel.setLayout(null);

        zoomSlider = new JSlider(JSlider.VERTICAL, 10, 200, 100);
        zoomSlider.setBounds(10, 10, 40, 150);
        zoomSlider.setMajorTickSpacing(10);
        zoomSlider.setPaintTicks(true);
        zoomSlider.setSnapToTicks(true);
        zoomSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent ce) {
                if(!zoomSlider.getValueIsAdjusting()) {
                    viewport.setZoom(zoomSlider.getValue(), 
                            graphPanel.getWidth(), 
                            graphPanel.getHeight());
                    
                    EnhancedGraphExplorationPanel.this.requestRedraw();
                }
            }
        });
        
        graphPanel.add(zoomSlider);

        moveUpBtn = new JButton("\u2191");
        moveUpBtn.setBounds(100, 25, 50, 25);

        moveLeftBtn = new JButton("\u2190");
        moveLeftBtn.setBounds(50, 50, 50, 25);

        moveDownBtn = new JButton("\u2193");
        moveDownBtn.setBounds(100, 50, 50, 25);

        moveRightBtn = new JButton("\u2192");
        moveRightBtn.setBounds(150, 50, 50, 25);

        final JToggleButton toggle = new JToggleButton();
        toggle.setBounds(90, 90, 64, 64);
        toggle.setIcon(IconManager.getIconManager().getIcon("magnifyingglass.png"));
        toggle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                magnifyGroupMode = toggle.isSelected();
            }
        });

        graphPanel.add(moveUpBtn);
        graphPanel.add(moveLeftBtn);
        graphPanel.add(moveDownBtn);
        graphPanel.add(moveRightBtn);
        graphPanel.add(toggle);

        this.setLayout(new BorderLayout());
        
        splitPane = AbstractNodeDetailsPanel.createStyledSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        detailsPanel = new JPanel(new BorderLayout());
        
        splitPane.setLeftComponent(graphPanel);
        splitPane.setRightComponent(detailsPanel);

        this.add(splitPane, BorderLayout.CENTER);
    }
    
    private void initializeFixedListeners() {

        graphPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (gepState == GEPState.Alive) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        mouseStateMonitor.setClickedLocation(e.getPoint());

                        EnhancedGraphExplorationPanel.this.requestRedraw();
                    }
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (gepState == GEPState.Alive) {

                    if (e.getButton() == MouseEvent.BUTTON1) {
                        mouseStateMonitor.setClickedLocation(null);
                        mouseStateMonitor.setCurrentDraggedLocation(null);

                        EnhancedGraphExplorationPanel.this.requestRedraw();
                    }
                }
            }

            public void mouseClicked(MouseEvent e) {

                EnhancedGraphExplorationPanel.this.requestFocus();

                if (gepState == GEPState.Alive) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        targetEntryPoint = null;

                        int clickCount = e.getClickCount();

                        Point pointOnGraph = viewport.getPointOnGraph(e.getPoint());

                        GenericGroupEntry groupEntry = getGroupEntryAtPoint(pointOnGraph);

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

                        EnhancedGraphExplorationPanel.this.requestRedraw();
                    }
                }
            }
        });

        graphPanel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (gepState == GEPState.Alive) {
                    if (mouseStateMonitor.mouseDragging()) {
                        mouseStateMonitor.setCurrentDraggedLocation(e.getPoint());
                        mouseStateMonitor.setCurrentMouseLocation(e.getPoint());
                    } else {
                        if (e.getPoint().distance(mouseStateMonitor.getClickedLocation()) > 16) {
                            mouseStateMonitor.setCurrentDraggedLocation(e.getPoint());
                            mouseStateMonitor.setCurrentMouseLocation(e.getPoint());
                        }
                    }

                    EnhancedGraphExplorationPanel.this.requestRedraw();
                }
            }

            public void mouseMoved(MouseEvent e) {
                if (gepState == GEPState.Alive) {
                    Point mouseLocation = viewport.getPointOnGraph(e.getPoint());

                    mouseStateMonitor.setCurrentMouseLocation(e.getPoint());

                    GenericGroupEntry group = getGroupEntryAtPoint(mouseLocation);

                    if (group != null) {
                        selectionStateMonitor.setMousedOverGroup(group);

                        if (group.isVisible() && magnifyGroupMode && !groupPopouts.containsKey(group)) {
                            groupPopouts.put(group, new GroupPopout(graph, group, configuration));
                        }
                    } else {

                        GenericPartitionEntry partition = getContainerPartitionAtPoint(mouseLocation);

                        if (partition != null && partition.isVisible()) {
                            int yLocation = partition.getAbsoluteY();

                            // Only highlight if mouse at the top of the partition.
                            if (mouseLocation.y < yLocation + 30) {
                                selectionStateMonitor.setMousedOverPartition(partition);
                            }
                        } else {
                            selectionStateMonitor.resetMousedOver();
                        }
                    }

                    EnhancedGraphExplorationPanel.this.requestRedraw();
                }
            }
        });

        graphPanel.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {

                if (gepState == GEPState.Alive) {
                    viewport.setZoom(zoomSlider.getValue(),
                            graphPanel.getWidth(),
                            graphPanel.getHeight());

                    EnhancedGraphExplorationPanel.this.requestRedraw();
                }
            }
        });
        
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                splitPane.setDividerLocation(getWidth() - 500);
            }
        });

        graphPanel.addMouseWheelListener(new MouseWheelListener() {
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (gepState == GEPState.Alive) {
                    if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
                        int zoomLevelChange = -e.getUnitsToScroll() / 3;
                        int currentZoomValue = zoomSlider.getValue();

                        int newZoomLevel = currentZoomValue + zoomLevelChange * 10;

                        zoomSlider.setValue(newZoomLevel);

                        viewport.setZoom(newZoomLevel,
                                graphPanel.getWidth(),
                                graphPanel.getHeight());

                        EnhancedGraphExplorationPanel.this.requestRedraw();
                    }
                }
            }
        });

        graphPanel.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {

                if (gepState == GEPState.Alive) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                            viewport.moveVertical(-64);
                            break;
                        case KeyEvent.VK_DOWN:
                            viewport.moveVertical(64);
                            break;
                        case KeyEvent.VK_LEFT:
                            viewport.moveHorizontal(-64);
                            break;
                        case KeyEvent.VK_RIGHT:
                            viewport.moveHorizontal(64);
                            break;
                    }

                    EnhancedGraphExplorationPanel.this.requestRedraw();
                }
            }
        });
    }
    
    public BluGraph getGraph() {
        return graph;
    }
    
    private GenericGroupEntry getGroupEntryAtPoint(Point point) {
        Component containerLevel = graph.getComponentAt(point);

        if (containerLevel != null && containerLevel instanceof GenericContainerEntry) {
            Component containerPartitionLevel = containerLevel.getComponentAt(
                    point.x - containerLevel.getX(),
                    point.y - containerLevel.getY());

            if (containerPartitionLevel != null && containerPartitionLevel instanceof GenericPartitionEntry) {
                Component groupLevel = containerPartitionLevel.getComponentAt(
                        point.x - containerLevel.getX() - containerPartitionLevel.getX(),
                        point.y - containerLevel.getY() - containerPartitionLevel.getY());

                if (groupLevel != null && groupLevel instanceof GenericGroupEntry) {
                    GenericGroupEntry group = (GenericGroupEntry) groupLevel;

                    return group;
                }
            }
        }

        return null;
    }

    private GenericPartitionEntry getContainerPartitionAtPoint(Point point) {
        Component containerLevel = graph.getComponentAt(point);

        if (containerLevel != null && containerLevel instanceof GenericContainerEntry) {
            Component containerPartitionLevel = containerLevel.getComponentAt(
                    point.x - containerLevel.getX(),
                    point.y - containerLevel.getY());

            if (containerPartitionLevel != null && containerPartitionLevel instanceof GenericPartitionEntry) {
                return (GenericPartitionEntry)containerPartitionLevel;
            }
        }

        return null;
    }
    
    public void focusOnPoint(int x, int y) {
        viewport.focusOnPoint(x, y, graphPanel.getWidth(), graphPanel.getHeight());
        
        this.doDraw = true;
    }

    public void jumpToRoot() {
        if(graph.getAbstractionNetwork().getRootGroup() == null) {
            return;
        }
        
        GenericGroupEntry root = graph.getGroupEntries().get(graph.getAbstractionNetwork().getRootGroup().getId());

        if(root == null) {
            return;
        }
        
        viewport.focusOnPoint(root.getAbsoluteX(), 0, graphPanel.getWidth(), graphPanel.getHeight());
        
        this.requestRedraw();
    }
    
    public void centerOnRoot() {
    	GenericGroupEntry root = graph.getGroupEntries().get(graph.getAbstractionNetwork().getRootGroup().getId());
        
        centerOnEntry(root);
    }

    public void centerOnEntry(GenericGroupEntry entry) {
        if (entry == null) {
            return;
        }
        
        int entryXPos = entry.getAbsoluteX();
        int entryYPos = entry.getAbsoluteY();
        
        int xBufferArea = (int)(GenericGroupEntry.ENTRY_WIDTH * viewport.scale * 2);
        int yBufferArea = (int)(GenericGroupEntry.ENTRY_HEIGHT * viewport.scale * 2);
        
        int destX = viewport.region.x;
        int destY = viewport.region.y;
        
        if (entryXPos + entry.getWidth() <= viewport.region.x + xBufferArea
                || entryXPos >= viewport.region.x + viewport.region.width - xBufferArea) {
            
            destX = entry.getAbsoluteX() + GenericGroupEntry.ENTRY_WIDTH / 2 - viewport.region.width / 2;
            
            if (destX < 0) {
                destX = 0;
            } else if (destX > graph.getWidth() - viewport.region.width) {
                destX = graph.getWidth() - viewport.region.width;
            }
        }
        
        if (entryYPos + entry.getHeight() <= viewport.region.y + yBufferArea ||
                entryYPos >= viewport.region.y + viewport.region.height - yBufferArea) {
            
            destY = entry.getAbsoluteY() + GenericGroupEntry.ENTRY_HEIGHT / 2 - viewport.region.height / 2;
            
            if (destY < 0) {
                destY = 0;
            } else if (destY > graph.getHeight() - viewport.region.height) {
                destY = graph.getHeight() - viewport.region.height;
            }
        }
        
        if (viewport.scale > 0.6) { // Snap viewport
            targetEntryPoint = new Point(destX, destY);
        }
    }

    public void highlightEntriesForSearch(ArrayList<GenericConceptGroup> groups) {
        ArrayList<Integer> entryIds = new ArrayList<Integer>();
        
        for(GenericConceptGroup group : groups) {
            entryIds.add(group.getId());
        }
        
        selectionStateMonitor.setSearchResults(entryIds);
    }

    private void drawAbstractionNetwork(Graphics2D g2d, Viewport viewport) {
               
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, graphPanel.getWidth(), graphPanel.getHeight());
        
        Collection<? extends GenericContainerEntry> containerEntries = graph.getContainerEntries().values();
        
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        
        if (viewport.scale > 0.2) {
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }

        for(GenericContainerEntry container : containerEntries) {
            if(viewport.region.intersects(container.getBounds())) {
                AbNDrawingUtilities.paintContainer(painter, g2d, container, viewport, graph.getLabelManager());
            }
        }
        
        ArrayList<GraphEdge> graphEdges = graph.getEdges();
        
        for(GraphEdge edge : graphEdges) {
            for(JPanel segment : edge.getSegments()) {
                if(viewport.region.intersects(segment.getBounds())) {
                    AbNDrawingUtilities.paintEdge(g2d, edge, viewport);
                    break;
                }
            }
        }
    }
    
    public BufferedImage getCurrentViewImage() {
        Rectangle viewportRegion = viewport.region;
        
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
    
    private void drawPopups(Graphics g) {
        for (GroupPopout popout : groupPopouts.values()) {
            if (viewport.region.intersects(popout.getCurrentBounds()) && !popout.isDead()) {
                popout.drawPopout(g, viewport);
            }
        }
    }
    
    private void drawLocationIndicators(Graphics2D g2d, Color color) {
        g2d.setColor(color);

        int xStartPoint = (int)(((float)viewport.region.x / graph.getWidth()) * graphPanel.getWidth());
        int xEndPoint = xStartPoint + (int)(((float)viewport.region.width / graph.getWidth()) * graphPanel.getWidth());

        g2d.fill3DRect(xStartPoint, graphPanel.getHeight() - 20, 2, 20, true);
        g2d.fill3DRect(xEndPoint - 2, graphPanel.getHeight() - 20, 2, 20, true);

        int yStartPoint = (int)(((float)viewport.region.y / graph.getHeight()) * graphPanel.getHeight());
        int yEndPoint = yStartPoint + (int)(((float)viewport.region.height / graph.getHeight()) * graphPanel.getHeight());

        g2d.fill3DRect(graphPanel.getWidth() - 20, yStartPoint, 20, 2, true);
        g2d.fill3DRect(graphPanel.getWidth() - 20, yEndPoint - 2, 20, 2, true);
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
        int panelWidth = graphPanel.getWidth();
        int panelHeight = graphPanel.getHeight();
        
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
    
    private void updateNavButtonPressed() {
        boolean buttonPressed = false;
        
        if (moveUpBtn.getModel().isPressed()) {
            viewport.moveVertical(-16);
            buttonPressed = true;
        } else if (moveDownBtn.getModel().isPressed()) {
            viewport.moveVertical(16);
            buttonPressed = true;
        } else if (moveLeftBtn.getModel().isPressed()) {
            viewport.moveHorizontal(-16);
            buttonPressed = true;
        } else if (moveRightBtn.getModel().isPressed()) {
            viewport.moveHorizontal(16);
            buttonPressed = true;
        }
        
        if(buttonPressed) {
            EnhancedGraphExplorationPanel.this.requestRedraw();
        }
    }
    
    private void updateViewportMovementByMouse() {
        if (mouseStateMonitor.mouseDragging()) {
            
            Point currentMousePoint = mouseStateMonitor.getCurrentMouseLocation();
            Point lastClickedPoint = mouseStateMonitor.getClickedLocation();
            
            Point delta = new Point(
                    (currentMousePoint.x - lastClickedPoint.x) / 5,
                    (currentMousePoint.y - lastClickedPoint.y) / 5);

            viewport.moveScaled(delta);
            
            EnhancedGraphExplorationPanel.this.requestRedraw();
        }
    }
    
    private void updatePopups() {
        GenericGroupEntry mousedOverPArea = null;

        if (mouseStateMonitor.getCurrentMouseLocation() != null) {
            mousedOverPArea = selectionStateMonitor.getMouseOverGroupEntry();
        }

        ArrayList<GenericGroupEntry> keys = new ArrayList<GenericGroupEntry>(groupPopouts.keySet());

        for (GenericGroupEntry group : keys) {
            if (groupPopouts.get(group).isDead()) {
                groupPopouts.remove(group);
                
                continue;
            }

            if (group == mousedOverPArea) {
                groupPopouts.get(group).doExpand();
            } else {
                groupPopouts.get(group).doContract();
            }
            
            EnhancedGraphExplorationPanel.this.requestRedraw();
        }
    }
    
    private void updateViewportMovement() {
        if (targetEntryPoint == null) {
            return;
        }

        int dx = targetEntryPoint.x - viewport.region.x;
        int dy = targetEntryPoint.y - viewport.region.y;
        
        final int MOVE_SPEED = 196;
        
        int distSquared = dx * dx + dy * dy;

        if (Math.sqrt(distSquared) <= 64) {
            viewport.setLocation(targetEntryPoint);
            targetEntryPoint = null;
        } else {
            double angle = Math.atan2(dy, dx);
            viewport.moveScaled(new Point((int) (MOVE_SPEED * Math.cos(angle)), (int) (MOVE_SPEED * Math.sin(angle))));
        }
        
        this.requestRedraw();
    }
    
    private void setDetailsPanelContents(JPanel panel) {
        detailsPanel.removeAll();
        detailsPanel.add(panel, BorderLayout.CENTER);
        detailsPanel.revalidate();
        detailsPanel.repaint();
    }
     
    /**
     * Section for handling mouse clicks
     */
    private void handleSingleClickOnGroupEntry(GenericGroupEntry entry) {
        if (groupDetailsPanel.isPresent()) {
            
            Thread loadThread = new Thread(new Runnable() {
                public void run() {
                    setDetailsPanelContents(loadingPanel);
                    
                    groupDetailsPanel.get().clearContents();
                    groupDetailsPanel.get().setContents(entry.getGroup());
                    
                    if(containerDetailsPanel.isPresent()) {
                        containerDetailsPanel.get().clearContents();
                    }
                    
                    if (entry == selectionStateMonitor.getSelectedGroupEntry()) {
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                setDetailsPanelContents(groupDetailsPanel.get());
                            }
                        });
                    }
                }
            });
            
            loadThread.start();
        }
    }

     /**
     * Section for handling mouse clicks
     */
    private void handleSingleClickOnPartitionEntry(final GenericPartitionEntry entry) {

        if (containerDetailsPanel.isPresent()) {
            GenericContainerEntry parentEntry = entry.getParentContainer();
            GenericGroupContainer container = parentEntry.getGroupContainer();

            Thread loadThread = new Thread(new Runnable() {
                public void run() {
                    setDetailsPanelContents(loadingPanel);
                    
                    if(groupDetailsPanel.isPresent()) {
                        groupDetailsPanel.get().clearContents();
                    }
                    
                    containerDetailsPanel.get().clearContents();
                    
                    containerDetailsPanel.get().setContents(container);
                    
                    if (entry == selectionStateMonitor.getSelectedPartitionEntry()) {
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                setDetailsPanelContents(containerDetailsPanel.get());
                            }
                        });
                    }
                }
            });
            
            loadThread.start();
        }
    }

    private void handleClickOutsideAnyEntry() {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (groupDetailsPanel.isPresent()) {
                    groupDetailsPanel.get().clearContents();
                }

                if (containerDetailsPanel.isPresent()) {
                    containerDetailsPanel.get().clearContents();
                }

                setDetailsPanelContents(abnDetailsPanel);
            }
        });
        
        
        targetEntryPoint = null;
    }
}

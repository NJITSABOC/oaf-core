package edu.njit.cs.saboc.blu.core.gui.graphframe;

import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericContainerEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericGroupEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.EnhancedGraphExplorationPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AbNPainter;
import edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.PopupToggleButton;
import edu.njit.cs.saboc.blu.core.gui.iconmanager.IconManager;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.filechooser.FileFilter;

// Blugraph is the most generic, of which there are
// OWLPAreaBluGraph, PAreaBluGraph, and ClusterBluGraph -Vlad
public abstract class GenericInternalGraphFrame extends JInternalFrame {
    
    protected JFrame parentFrame;
    
       
    protected final EnhancedGraphExplorationPanel gep;

    protected final JTabbedPane tabbedPane = new JTabbedPane();
    
    
    protected final JScrollPane scroller;
    protected final JPanel scrollerContentPanel;
    
    protected BluGraph graph;
        
    protected JCheckBox chkHideGroups;
    
    protected JLabel hierarchyInfoLabel = new JLabel();
    
    protected JPanel menuPanel = new JPanel();
    
    private JPanel reportsPanel = new JPanel();
    
    private JPanel optionsPanel = new JPanel();
    
    private ArrayList<PopupToggleButton> toggleMenuButtons = new ArrayList<PopupToggleButton>();

    protected GenericInternalGraphFrame(JFrame parentFrame, String title) {
        super(title,
                true, //resizable
                true, //closable
                true, //maximizable
                true);//iconifiable
        
        this.parentFrame = parentFrame;
        
        this.scrollerContentPanel = new JPanel(new BorderLayout());
        this.scroller = new JScrollPane(scrollerContentPanel);
        
        this.gep = new EnhancedGraphExplorationPanel();
        this.gep.showLoading();

        tabbedPane.addTab("Explore", gep);
        tabbedPane.addTab("Edit", scroller);

        this.setSize(1024, 512);

        this.addInternalFrameListener(new InternalFrameAdapter() {
            public void internalFrameClosing(InternalFrameEvent e) {
                for(PopupToggleButton button : toggleMenuButtons) {
                    button.disposePopup();
                }
                
                gep.killGEP();
            }
            public void internalFrameDeactivated(InternalFrameEvent e) {
                for(PopupToggleButton button : toggleMenuButtons) {
                    if(button.isSelected()) {
                        button.setSelected(false);
                        button.setIcon(null);
                        button.hidePopup();
                    }
                }
            }
            public void internalFrameIconified(InternalFrameEvent e) {
                for(PopupToggleButton button : toggleMenuButtons) {
                    if(button.isSelected()) {
                        button.setSelected(false);
                        button.setIcon(null);
                        button.hidePopup();
                    }
                }
            }
        });
        
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized (ComponentEvent e) {
                JInternalFrame frame = (JInternalFrame) e.getSource();
                
                for(PopupToggleButton button : toggleMenuButtons) {
                    if(button.isSelected()) {
                        button.updatePopupLocation(frame.getSize());
                    }
                }
            }
            public void componentMoved (ComponentEvent e) {
                JInternalFrame frame = (JInternalFrame) e.getSource();
                
                for(PopupToggleButton button : toggleMenuButtons) {
                    if(button.isSelected()) {
                        button.updatePopupLocation(frame.getSize());
                    }
                }
            }
        });
        
        chkHideGroups = new JCheckBox("Collapse All"); // TODO: Hide all what?

        chkHideGroups.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                handleHideGroupClick();
            }
        });

        menuPanel.add(chkHideGroups);
        
        menuPanel.add(new JSeparator(SwingConstants.VERTICAL));
        menuPanel.add(Box.createHorizontalStrut(5));
        
        menuPanel.add(reportsPanel);
        
        menuPanel.add(new JSeparator(SwingConstants.VERTICAL));
        menuPanel.add(Box.createHorizontalStrut(5));
        
        menuPanel.add(hierarchyInfoLabel);
        
        menuPanel.add(new JSeparator(SwingConstants.VERTICAL));
        menuPanel.add(Box.createHorizontalStrut(5));
                
        JButton goToRootBtn = new JButton("Return to Root");
        
        goToRootBtn.setToolTipText("Click to return to the root of this abstraction network.");
        
        goToRootBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (tabbedPane.getSelectedIndex() == 0) {
                    GenericGroupEntry entry = graph.getGroupEntries().get(graph.getAbstractionNetwork().getRootGroup().getId());

                    scroller.getViewport().setViewPosition(
                            new Point(entry.getAbsoluteX() - GenericInternalGraphFrame.this.getWidth() / 2, 0));
                } else {
                    gep.centerOnRoot();
                    gep.repaint();
                }
            }
        });
        
        final JButton saveButton = new JButton("Save As Image");
        saveButton.setToolTipText("Save the current screen as a PNG image. Available only in Graph Exploration mode.");

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                saveCurrentView();
            }
        });
        
        menuPanel.add(goToRootBtn);
        menuPanel.add(saveButton);
        
        menuPanel.add(new JSeparator(SwingConstants.VERTICAL));
        menuPanel.add(Box.createHorizontalStrut(5));
        
        menuPanel.add(optionsPanel);

        tabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent ce) {
                if (tabbedPane.getSelectedIndex() == 1) {
                    saveButton.setEnabled(true);
                    saveButton.setToolTipText("Save the current screen as a PNG image.");
                } else {
                    saveButton.setEnabled(false);
                    saveButton.setToolTipText("Save the current screen as a PNG image. "
                            + "Available only in Graph Exploration mode.");
                }
            }
        });

        saveButton.setEnabled(true);
        
        add(menuPanel, BorderLayout.NORTH);
        
        add(tabbedPane);
        
        setVisible(true);
    }
      
    public BluGraph getGraph() {
        return graph;
    }
    
    public EnhancedGraphExplorationPanel getEnhancedGraphExplorationPanel() {
        return gep;
    }
    
    protected void addReportButtonToMenu(final JButton button) {
        reportsPanel.add(button);
        reportsPanel.revalidate();
        reportsPanel.repaint();
    }
    
    protected void removeReportButtonFromMenu(final JButton button) {
        reportsPanel.remove(button);
        reportsPanel.revalidate();
        reportsPanel.repaint();
    }
    
    protected void addToggleableButtonToMenu(final PopupToggleButton button) {
        toggleMenuButtons.add(button);
        
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
               handleTogglableButtonPressed(button);
              
            }
        });
        
        optionsPanel.add(button);
    }
    
    private void handleTogglableButtonPressed(PopupToggleButton pressedButton) {
        
        for(PopupToggleButton button : toggleMenuButtons) {
            if(button != pressedButton) {
                button.setSelected(false);
                button.setIcon(null);
                button.hidePopup();
            } else {
                if (button.isSelected()) {
                    button.setIcon(IconManager.getIconManager().getIcon("cancel.png"));
                } else {
                    button.setIcon(null);
                }
            }
        }
    }
    
    public void setAllGroupsHidden(boolean allGroupsHidden) {
        this.chkHideGroups.setSelected(allGroupsHidden);
    }
    
    public void focusOnComponent(final Component c) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                int x = c.getX();
                int y = c.getY();

                if (c instanceof GenericGroupEntry) {
                    GenericGroupEntry group = ((GenericGroupEntry) c);

                    x = group.getAbsoluteX();
                    y = group.getAbsoluteY();
                }

                int width = c.getWidth();
                int height = c.getHeight();

                int midPointX = x + width / 2;
                int midPointY = y + height / 2;

                int scrollXPos = midPointX - getWidth() / 2;
                int scrollYPos = midPointY - getHeight() / 2;

                if (scrollXPos < 0) {
                    scrollXPos = 0;
                } else if (scrollXPos > graph.getWidth() - getWidth()) {
                    scrollXPos = graph.getWidth() - getWidth();
                }

                if (scrollYPos < 0) {
                    scrollYPos = 0;
                } else if (scrollYPos > graph.getHeight() - getHeight()) {
                    scrollYPos = graph.getHeight() - getHeight();
                }

                scroller.getViewport().setViewPosition(new Point(scrollXPos, scrollYPos));

                graph.repaint();
                gep.focusOnPoint(midPointX, midPointY);
                gep.repaint();
            }
        });
    }
    
    private void handleHideGroupClick() {
        if (chkHideGroups.isSelected()) {
            Component[] components = graph.getComponents();

            for (Component c : components) {
                if (c instanceof GenericContainerEntry) {
                    if (!((GenericContainerEntry) c).isCollapsed()) {
                        ((GenericContainerEntry) c).collapseContainer();
                    }
                }
            }
        } else {
            Component[] components = graph.getComponents();

            for (Component c : components) {
                if (c instanceof GenericContainerEntry) {
                    if (((GenericContainerEntry) c).isCollapsed()) {
                        ((GenericContainerEntry) c).expandContainer();
                    }
                }
            }
        }
    }
    
    protected void setHierarchyInfoText(String text) {
        hierarchyInfoLabel.setText(text);
    }
    
    protected void displayAbstractionNetwork(BluGraph graph, AbNPainter painter, BLUConfiguration gepConfiguration) {
        
        this.graph = graph;
        
        gep.showLoading();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                gep.setContents(graph, painter, gepConfiguration);
                
                scrollerContentPanel.add(graph, BorderLayout.CENTER);
                
                scroller.revalidate();
                scroller.repaint();

                initializeTabs(graph, gepConfiguration);

                tabbedPane.validate();

                tabbedPane.repaint();
            }
        });
    }
    
    protected void initializeTabs(BluGraph graph, BLUConfiguration gepConfiguration) {

        String abnTypeName = gepConfiguration.getTextConfiguration().getAbNTypeName(false);

        tabbedPane.setTitleAt(0, String.format("Explore %s", abnTypeName));
        tabbedPane.setTitleAt(1, String.format("Edit %s", abnTypeName));
        
        tabbedPane.setToolTipTextAt(0,
                String.format("<html><b>Explore %s</b> allows you to quickly<br>"
                        + "explore the %s. Additional information is displayed by selecting the different %s elements.",
                        abnTypeName,
                        abnTypeName.toLowerCase(),
                        abnTypeName.toLowerCase()));

        tabbedPane.setToolTipTextAt(1,
                String.format("<html><b>Edit %s Graph</b> allows you to edit some of the %s's layout and draw edges.", abnTypeName, 
                        abnTypeName.toLowerCase()));
    }

    private void saveCurrentView() {
        final JFileChooser chooser = new JFileChooser();
        
        chooser.setFileFilter(new FileFilter() {
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }

                if (f.getName().endsWith(".png")) {
                    return true;
                } else {
                    return false;
                }
            }

            public String getDescription() {
                return "Portal Network Graphics (.png) Images";
            }
        });
        
        int returnVal = chooser.showSaveDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            BufferedImage graphImage = gep.getCurrentViewImage();

            try {
                String file = chooser.getSelectedFile().getAbsolutePath();

                if (!file.toLowerCase().endsWith(".png")) {
                    file += ".png";
                }

                ImageIO.write(graphImage, "png", new File(file));
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

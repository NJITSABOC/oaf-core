
package edu.njit.cs.saboc.blu.core.gui.gep.panels;

import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericGroupEntry;
import edu.njit.cs.saboc.blu.core.gui.iconmanager.IconManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 *
 * @author Chris
 */
public class GroupOptionsPanel extends JPanel {

    private JLabel elementLabel = new JLabel("", SwingConstants.CENTER);

    private enum MoveDirection {
        Up,
        Down
    }

    private boolean initialized = false;

    private boolean hidden = true;

    private boolean moving = false;
    private MoveDirection moveDirection = null;

    private final int MOVE_BOUND = -36;

    private Timer moveTimer = new Timer(10, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if(moving) {
                final Rectangle bounds = GroupOptionsPanel.this.getBounds();

                if (moveDirection == MoveDirection.Down) {
                    bounds.y += 1;

                    if (bounds.y == 0) {
                        moving = false;
                        hidden = false;
                    }
                } else {
                    bounds.y -= 1;

                    if (bounds.y == MOVE_BOUND) {
                        moving = false;
                        hidden = true;
                    }
                }
                
                GroupOptionsPanel.this.setLocation(bounds.x, bounds.y);
            } else {
                moveDirection = null;
                moveTimer.stop();
            }
        }
    });

    private GenericGroupEntry entry;

    public GroupOptionsPanel(final BluGraph graph) {
        this.setLayout(null);
        this.setBounds(200, -36, 600, 40);

        super.setOpaque(false);

        elementLabel.setBounds(150, 4, 300, 24);
        
        this.add(elementLabel);

        final JButton option1Button = createPanelButton("groupDetailsIcon.png", "Get information about this partial-area");
        option1Button.setBounds(elementLabel.getX() - 100, 4, 30, 30);

        final JButton option2Button = createPanelButton("viewRootInConceptBrowserIcon.png", "View root of this Partial-area in the Concept Browser");
        option2Button.setBounds(elementLabel.getX() - 60, 4, 30, 30);

        final JButton option3Button = createPanelButton("createRootConstrainedIcon.png", "Create Root-constrained Partial-area Subtaxonomy");
        option3Button.setBounds(elementLabel.getX() + elementLabel.getWidth() + 20, 4, 30, 30);

        final JButton option4Button = createPanelButton("viewPAreaInHybridIcon.png", "View Partial-area in Hybrid Browser");
        option4Button.setBounds(elementLabel.getX() + elementLabel.getWidth() + 54, 4, 30, 30);
        
        final JButton option5Button = createPanelButton("deriveTanIcon.png", "Derive a Tribal Abstraction Network");
        option5Button.setBounds(elementLabel.getX() + elementLabel.getWidth() + 88, 4, 30, 30);

//        if(graph instanceof PAreaBluGraph) {
//
//        } else if (graph instanceof ClusterBluGraph) {
//            option3Button.setEnabled(false);
//            option4Button.setEnabled(false);
//        } else {
//            option2Button.setEnabled(false);
//            option3Button.setEnabled(false);
//            option4Button.setEnabled(false);
//            option5Button.setEnabled(false);
//        }

        option1Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
//                if (graph instanceof PAreaBluGraph) {
//                    ConceptGroupDetailsDialog dialog = new ConceptGroupDetailsDialog(entry.getGroup(), 
//                            (SCTAbstractionNetwork)graph.getAbstractionNetwork(),
//                            ConceptGroupDetailsDialog.DialogType.PartialArea);
//                } else if (graph instanceof ClusterBluGraph) {
//                    ConceptGroupDetailsDialog dialog = new ConceptGroupDetailsDialog(entry.getGroup(), 
//                            (SCTAbstractionNetwork)graph.getAbstractionNetwork(),
//                            ConceptGroupDetailsDialog.DialogType.Cluster);
//                } else if (graph instanceof OWLPAreaBluGraph) {
//                    OWLPAreaDetailsDialog dialog = new OWLPAreaDetailsDialog((OWLPArea) entry.getGroup(),
//                            (OWLPAreaTaxonomy) graph.getAbstractionNetwork());
//                }
            }
        });

        option2Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
//                SCTAbstractionNetwork abn = (SCTAbstractionNetwork)graph.getAbstractionNetwork();
//                
//                MainToolFrame.getMainFrame().addNewBrowserFrame(entry.getGroup().getRoot(), 
//                        abn.getSCTDataSource());
            }
        });

        option3Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
//                final MainToolFrame mainFrame = MainToolFrame.getMainFrame();
//
//                final SwingWorker t = new SwingWorker() {
//
//                    public Object doInBackground() {
//                        PAreaTaxonomy origTaxonomy = (PAreaTaxonomy)graph.getAbstractionNetwork();
//                        
//                        PAreaTaxonomy subtaxonomy = origTaxonomy.getRootSubtaxonomy((PAreaSummary)entry.getGroup());
//                        
//                        mainFrame.addNewPAreaGraphFrame(
//                                subtaxonomy,
//                                graph.getIsAreaGraph(),
//                                graph.showingConceptCountLabels());
//
//                        return new Object();
//                    }
//                };
//                
//                t.execute();
//                requestFocusInWindow();
            }
        });

        option4Button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
//                try {
//                    graph.getParentInternalFrame().viewInTextBrowser((PAreaSummary)entry.getGroup());
//                } catch (Exception e) {
//                }
            }
        });
        
        option5Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
//                SCTConceptHierarchy hierarchy = null;
//                SCTAbstractionNetwork abn = (SCTAbstractionNetwork)graph.getAbstractionNetwork();
//                
//                if(graph instanceof PAreaBluGraph) {
//                    PAreaSummary parea = (PAreaSummary)entry.getGroup();
//
//                    hierarchy = abn.getSCTDataSource().getPAreaConceptHierarchy(
//                            (PAreaTaxonomy)abn, parea);
//                
//                } else if (graph instanceof ClusterBluGraph) {
//                    ClusterSummary cluster = (ClusterSummary) entry.getGroup();
//
//                    hierarchy = abn.getSCTDataSource().getClusterConceptHierarchy(
//                            (TribalAbstractionNetwork) abn, cluster);
//                } else {
//                    // What happened here?
//                }
//                
//                TribalAbstractionNetwork chd = TANUtilities.createTANFromConceptHierarchy(
//                        entry.getGroup().getRoot(), 
//                        abn.getVersion(),
//                        hierarchy);
//                
//                MainToolFrame.getMainFrame().addNewClusterGraphFrame(chd, true, false);
            }
        });

        this.add(option1Button);
        this.add(option2Button);
        this.add(option3Button);
        this.add(option4Button);
        this.add(option5Button);
    }
    
    private JButton createPanelButton(final String iconFile, final String description) {
        JButton button = new JButton();
        
        button.setBackground(new Color(120, 120, 255));
        button.setBorder(BorderFactory.createEtchedBorder());
        button.setIcon(IconManager.getIconManager().getIcon(iconFile));
        
        button.addMouseListener(new MouseAdapter() {
            
            public void mouseEntered(MouseEvent e) {
                    setLabelText(new Font("Tahoma", Font.BOLD, 11), Color.RED,
                            description);
            }

            public void mouseExited(MouseEvent e) {
                setLabelText(new Font("Tahoma", Font.BOLD, 11), Color.BLACK,
                        getGroupTextForLabel());
            }
        });
        
        return button;
    }

    public void setCurrentGroup(GenericGroupEntry entry) {
        this.entry = entry;

        if(entry == null) {
            elementLabel.setText("");
            return;
        }

        setLabelText(new Font("Tahoma", Font.BOLD, 11), Color.BLACK, getGroupTextForLabel());
    }

    private String getGroupTextForLabel() {
        if(entry == null) {
            return "";
        }

        String text = entry.getGroup().getRoot().getName();

        int width = elementLabel.getFontMetrics(elementLabel.getFont()).stringWidth(text);

        if (width > 400) {
            int charWidth = elementLabel.getFontMetrics(elementLabel.getFont()).charWidth('A');

            int charCount = 400 / charWidth;

            text = text.substring(0, charCount - 3) + "...";
        }

        return text;
    }

    private void setLabelText(Font font, Color color, String text) {
        this.elementLabel.setFont(font);
        this.elementLabel.setForeground(color);
        this.elementLabel.setText(text);
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        BufferedImage bi = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);        

        Graphics2D g2d = bi.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.BLACK);

        g2d.fillRect(40, 0, 520, 40);
        g2d.fillOval(0, -40, 80, 80);
        g2d.fillOval(520, -40, 80, 80);

        g2d.setColor(new Color(100, 100, 255));
        
        g2d.fillRect(40, 0, 520, 38);
        g2d.fillOval(2, -42, 80, 80);
        g2d.fillOval(518, -42, 80, 80);

        Rectangle labelBounds = elementLabel.getBounds();

        g2d.setColor(Color.BLACK);

        g2d.fillRect(labelBounds.x, labelBounds.y - 1, labelBounds.width, labelBounds.height + 2);

        g2d.fillOval(elementLabel.getX() - elementLabel.getHeight() / 2 - 1, elementLabel.getY() - 1,
                elementLabel.getHeight() + 1, elementLabel.getHeight() + 2);

        g2d.fillOval(elementLabel.getX() + elementLabel.getWidth() - elementLabel.getHeight() / 2 + 1,
                elementLabel.getY() - 1, elementLabel.getHeight(), elementLabel.getHeight() + 2);


        g2d.setColor(Color.WHITE);
        g2d.fillRect(150, 4, 300, 24);
        
        g2d.fillOval(elementLabel.getX() - elementLabel.getHeight() / 2, elementLabel.getY(),
                elementLabel.getHeight(), elementLabel.getHeight());

        g2d.fillOval(elementLabel.getX() + elementLabel.getWidth() - elementLabel.getHeight() / 2,
                elementLabel.getY(), elementLabel.getHeight(), elementLabel.getHeight());

        g.drawImage(bi, 0, 0, null);
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean value) {
        this.initialized = value;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void doHide() {

        if(this.getY() == MOVE_BOUND) {
            return;
        }

        moveTimer.stop();

        this.moving = true;
        this.moveDirection = MoveDirection.Up;

        moveTimer.start();
    }

    public void doShow() {
        moveTimer.stop();

        this.moving = true;
        this.moveDirection = MoveDirection.Down;

        moveTimer.start();
    }
}

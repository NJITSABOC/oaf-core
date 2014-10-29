/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.njit.cs.saboc.blu.core.gui.gep.panels;

import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericPartitionEntry;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 *
 * @author harsh
 */
public class PartitionOptionsPanel extends JPanel {
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
                final Rectangle bounds = PartitionOptionsPanel.this.getBounds();

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
                
                PartitionOptionsPanel.this.setLocation(bounds.x, bounds.y);
            } else {
                moveDirection = null;
                moveTimer.stop();
            }
        }
    });

    private GenericPartitionEntry entry;
    
    public PartitionOptionsPanel(final BluGraph graph) {
        this.setLayout(null);
        this.setBounds(200, -36, 600, 40);

        super.setOpaque(false);
        

        //added by Vladimir
        elementLabel.setBounds(150, 4, 300, 24);
        
        this.add(elementLabel);
        
        //changed by Harsh:
        /*this.add(option3Button);
        this.add(option4Button);
        */
        
        //previously deleted by Harsh, does't affect code:
                
        
        
        /*
        final JButton option1Button = new JButton();
        option1Button.setBounds(elementLabel.getX() - 100, 4, 30, 30);

        final JButton option2Button = new JButton();
        option2Button.setBounds(elementLabel.getX() - 60, 4, 30, 30);

        final JButton option3Button = new JButton();
        option3Button.setBounds(elementLabel.getX() + elementLabel.getWidth() + 30, 4, 30, 30);

        final JButton option4Button = new JButton();
        option4Button.setBounds(elementLabel.getX() + elementLabel.getWidth() + 70, 4, 30, 30);

        option1Button.setBackground(new Color(120, 120, 255));
        option2Button.setBackground(new Color(120, 120, 255));
        option3Button.setBackground(new Color(120, 120, 255));
        option4Button.setBackground(new Color(120, 120, 255));

        option1Button.setBorder(BorderFactory.createEtchedBorder());
        option2Button.setBorder(BorderFactory.createEtchedBorder());
        option3Button.setBorder(BorderFactory.createEtchedBorder());
        option4Button.setBorder(BorderFactory.createEtchedBorder());

        option1Button.setIcon(new ImageIcon(MainToolFrame.class.getResource("groupDetailsIcon.png")));
        option2Button.setIcon(new ImageIcon(MainToolFrame.class.getResource("viewRootInConceptBrowserIcon.png")));
        option3Button.setIcon(new ImageIcon(MainToolFrame.class.getResource("createRootConstrainedIcon.png")));
        option4Button.setIcon(new ImageIcon(MainToolFrame.class.getResource("viewPAreaInHybridIcon.png")));

        if(graph instanceof PAreaBluGraph) {

        } else if (graph instanceof ClusterBluGraph) {
            option3Button.setEnabled(false);
            option4Button.setEnabled(false);
        } else {
            option1Button.setEnabled(false);
            option2Button.setEnabled(false);
            option3Button.setEnabled(false);
            option4Button.setEnabled(false);
        }

        option1Button.addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent e) {
                if(option1Button.isEnabled()) {
                    setLabelText(new Font("Tahoma", Font.BOLD, 11), Color.RED,
                            "Get detailed information about this Partial-area");
                }
            }

            public void mouseExited(MouseEvent e) {
                setLabelText(new Font("Tahoma", Font.BOLD, 11), Color.BLACK,
                        getGroupTextForLabel());
            }
        });

        option2Button.addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent e) {
                if(option2Button.isEnabled()) {
                    setLabelText(new Font("Tahoma", Font.BOLD, 11), Color.RED,
                            "View root of this Partial-area in the Concept Browser");
                }
            }

            public void mouseExited(MouseEvent e) {
                setLabelText(new Font("Tahoma", Font.BOLD, 11), Color.BLACK,
                        getGroupTextForLabel());
            }
        });

        option3Button.addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent e) {
                if(option3Button.isEnabled()) {
                    setLabelText(new Font("Tahoma", Font.BOLD, 11), Color.RED,
                            "Create Root-constrained Partial-area Subtaxonomy");
                }
            }

            public void mouseExited(MouseEvent e) {
                setLabelText(new Font("Tahoma", Font.BOLD, 11), Color.BLACK,
                        getGroupTextForLabel());
            }
        });

        option4Button.addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent e) {
                if(option4Button.isEnabled()) {
                    setLabelText(new Font("Tahoma", Font.BOLD, 11), Color.RED,
                            "View Partial-area in Hybrid Browser");
                }
            }

            public void mouseExited(MouseEvent e) {
                setLabelText(new Font("Tahoma", Font.BOLD, 11), Color.BLACK,
                        getGroupTextForLabel());
            }
        });

        option1Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new ConceptGroupDetailsDialog(entry.getGroup(), graph.getHierarchyData(),
                        ConceptGroupDetailsDialog.DialogType.PartialArea);
            }
        });

        option2Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                MainToolFrame.getMainFrame().addNewBrowserFrame(entry.getGroup().getRoot(), 
                        graph.getHierarchyData().getVersion());
            }
        });

        option3Button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                final MainToolFrame mainFrame = MainToolFrame.getMainFrame();
                JProgressBar progress = new JProgressBar();
                progress.setIndeterminate(true);

                final Popup waitPopup = PopupFactory.getSharedInstance().getPopup(mainFrame, progress, 200, 100);
                waitPopup.show();

                final SwingWorker t = new SwingWorker() {

                    public Object doInBackground() {
                        mainFrame.addNewPAreaGraphFrame(
                                ((PAreaHierarchyData)graph.getHierarchyData()).getHierarchyRootedAt(
                                    (PAreaSummary)entry.getGroup()),
                                graph.getIsAreaGraph(),
                                graph.showingConceptCountLabels());

                        return new Object();
                    }

                    @Override
                    public void done() {
                        waitPopup.hide();
                    }
                };
                t.execute();
                requestFocusInWindow();
            }
        });

        option4Button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                try {
                    graph.getParentInternalFrame().viewInTextBrowser((PAreaSummary)entry.getGroup());
                } catch (Exception e) {
                }
            }
        });

        this.add(option1Button);
        this.add(option2Button);
        this.add(option3Button);
        this.add(option4Button);
        
        */

        
        
        
        
    }

    
    public void setCurrentPartition(GenericPartitionEntry entry) {
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
        
        String text = entry.getPartitionName();
        
        
        
        
//added by Harsh, disabled by Vlad
//        this.elementLabel.setText("<html> <center>" + text + "</center> </html>");
//end
        

//        int width = elementLabel.getFontMetrics(elementLabel.getFont()).stringWidth(text);
//
//        if (width > 400) {
//            int charWidth = elementLabel.getFontMetrics(elementLabel.getFont()).charWidth('A');
//
//            int charCount = 400 / charWidth;
//
//            text = text.substring(0, charCount - 3) + "...";
//        }

        return text;
    }

    private void setLabelText(Font font, Color color, String text) {
        this.elementLabel.setFont(font);
        this.elementLabel.setForeground(color);
        this.elementLabel.setText(text);
    }
        //g2d.setColor(new Color(0, 204, 0));

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        BufferedImage bi = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);        

        Graphics2D g2d = bi.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.BLACK);

        g2d.fillRect(40, 0, 520, 40);
        g2d.fillOval(0, -40, 80, 80);
        g2d.fillOval(520, -40, 80, 80);

        g2d.setColor(new Color(0, 204, 0));
        
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

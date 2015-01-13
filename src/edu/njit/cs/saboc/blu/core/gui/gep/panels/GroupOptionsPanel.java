
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
    
    private JButton [] optionButtons = new JButton[6];

    public GroupOptionsPanel(final BluGraph graph, final GroupOptionsPanelConfiguration configuration) {
        this.setLayout(null);
        this.setBounds(200, -36, 600, 40);

        super.setOpaque(false);

        elementLabel.setBounds(150, 4, 300, 24);
        
        this.add(elementLabel);

        optionButtons[0] = createPanelButton("groupDetailsIcon.png", "Get information about this partial-area");
        optionButtons[0].setBounds(elementLabel.getX() - 120, 4, 30, 30);

        optionButtons[1] = createPanelButton("viewRootInConceptBrowserIcon.png", "View root of this Partial-area in the Concept Browser");
        optionButtons[1].setBounds(elementLabel.getX() - 85, 4, 30, 30);
        
        optionButtons[2] = createPanelButton("viewPAreaInHybridIcon.png", "View Partial-area in Hybrid Browser");
        optionButtons[2].setBounds(elementLabel.getX() - 50, 4, 30, 30);

        optionButtons[3] = createPanelButton("createRootConstrainedIcon.png", "Create Root-constrained Partial-area Subtaxonomy");
        optionButtons[3].setBounds(elementLabel.getX() + elementLabel.getWidth() + 20, 4, 30, 30);

        optionButtons[4] = createPanelButton("deriveTanIcon.png", "Derive a Tribal Abstraction Network");
        optionButtons[4].setBounds(elementLabel.getX() + elementLabel.getWidth() + 55, 4, 30, 30);
        
        optionButtons[5] = createPanelButton("filter.png", "Expand Reduced Partial-area");
        optionButtons[5].setBounds(elementLabel.getX() + elementLabel.getWidth() + 90, 4, 30, 30);
        
        for (int c = 0; c < optionButtons.length; c++) {
            if (configuration.isButtonEnabled(c)) {
                final int index = c;
                
                optionButtons[c].setEnabled(true);
                optionButtons[c].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        if(entry != null) {
                            configuration.getAction(index).actionPerformedOn(entry.getGroup());
                        }
                    }
                });
            } else {
                optionButtons[c].setEnabled(false);
            }
            
            this.add(optionButtons[c]);
        }
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

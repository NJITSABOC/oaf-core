/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.njit.cs.saboc.blu.core.gui.gep.panels;

import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericGroupEntry;
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
 * Displays information metrics (Number of Parents, Children & Descendants)
 * for the selected Partial-area.
 * @author harsh
 */
public class GroupMetricsPanel extends JPanel {
    
    private JLabel elementLabel1 = new JLabel("<html><B>Parents: </B></html>", SwingConstants.CENTER);
    private JLabel elementLabel2 = new JLabel("", SwingConstants.CENTER);
    private JLabel elementLabel3 = new JLabel("<html><B>Children: </B></html>", SwingConstants.CENTER);
    private JLabel elementLabel4 = new JLabel("", SwingConstants.CENTER);
    private JLabel elementLabel5 = new JLabel("<html><B>Descendants: </B></html>", SwingConstants.CENTER);
    private JLabel elementLabel6 = new JLabel("", SwingConstants.CENTER);
    

    private enum MoveDirection {
        Left,
        Right
    }

    private boolean initialized = false;

    private boolean hidden = true;

    private boolean moving = false;
    private MoveDirection moveDirection = null;

    private int MOVE_BOUND;
    
    private GenericGroupEntry entry;

    private Timer moveTimer = new Timer(10, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if(moving) {
                final Rectangle bounds = GroupMetricsPanel.this.getBounds();

                if (moveDirection == MoveDirection.Left) {
                    bounds.x -= 5;

                    if (bounds.x == (MOVE_BOUND - 134)) {
                        moving = false;
                        hidden = false;
                    }
                } else {
                    bounds.x += 5;

                    if (bounds.x == (MOVE_BOUND - 4)) {
                        moving = false;
                        hidden = true;
                    }
                }
                
                GroupMetricsPanel.this.setLocation(bounds.x, bounds.y);
            } else {
                moveDirection = null;
                moveTimer.stop();
            }
        }
    });
    
    BluGraph currentGraph;
    public GroupMetricsPanel(final BluGraph graph) {
        currentGraph = graph;
        
        this.setLayout(null);
        this.setBounds(this.MOVE_BOUND - 4, 20, 140, 200);
        super.setOpaque(false);
        
        elementLabel1.setBounds(30, 10, 80, 20);
        elementLabel1.setForeground(new Color(0xffffff));
        this.add(elementLabel1);
        
        elementLabel2.setBounds(30, 30, 80, 20);
        this.add(elementLabel2);
        
        elementLabel3.setBounds(30, 60, 80, 20);
        elementLabel3.setForeground(new Color(0xffffff));
        this.add(elementLabel3);
        
        elementLabel4.setBounds(30, 80, 80, 20);
        this.add(elementLabel4);
        
        elementLabel5.setBounds(20, 110, 100, 20);
        elementLabel5.setForeground(new Color(0xffffff));
        this.add(elementLabel5);
        
        elementLabel6.setBounds(30, 130, 80, 20);
        this.add(elementLabel6);

        super.setOpaque(false);        
    }
    
    public void setCurrentGroup(GenericGroupEntry entry) {
        
        this.entry = entry;
        
        if(entry == null) {
            elementLabel2.setText("");
            elementLabel4.setText("");
            elementLabel6.setText("");
            return;
        }
        
        setLabelText(elementLabel2, new Font("Tahoma", Font.BOLD, 11), Color.BLACK, getGroupTextForLabel("parent"));
        setLabelText(elementLabel4, new Font("Tahoma", Font.BOLD, 11), Color.BLACK, getGroupTextForLabel("children"));
        setLabelText(elementLabel6, new Font("Tahoma", Font.BOLD, 11), Color.BLACK, getGroupTextForLabel("descendants"));
    }
    
    private String getGroupTextForLabel(String labelType) {
        if(entry == null) {
            return "";
        }

        String text = "";
        if(labelType.equals("parent")) {
            text = Integer.toString(entry.getGroup().getParentIds().size());
        }
        else if(labelType.equals("children")) {
            text = Integer.toString(currentGraph.getAbstractionNetwork().getGroupChildren(entry.getGroup().getId()).size());
        }
        else if(labelType.equals("descendants")) {            
            text = Integer.toString(currentGraph.getAbstractionNetwork().getGroupDescendants(entry.getGroup().getId()).size());
        }

        return text;
    }
    
    private void setLabelText(JLabel myLabel, Font font, Color color, String text) {
        myLabel.setFont(font);       
        myLabel.setForeground(color);        
        myLabel.setText(text);
    }
    
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        BufferedImage bi = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);        

        Graphics2D g2d = bi.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.BLACK);

        g2d.fillOval(0, 0, 30, 30);        
        g2d.fillOval(0, 140, 30, 30);
        g2d.fillRect(0, 15, 15, 140);
        g2d.fillRect(15, 0, 125, 170);

        g2d.setColor(new Color(100, 100, 255));
        
        g2d.fillOval(2, 2, 30, 30);        
        g2d.fillOval(2, 138, 30, 30);
        g2d.fillRect(2, 15, 15, 140);
        g2d.fillRect(15, 2, 125, 166);
        
        drawLabelBackground(g2d, elementLabel2);
        drawLabelBackground(g2d, elementLabel4);
        drawLabelBackground(g2d, elementLabel6);
        
        g.drawImage(bi, 0, 0, null);

    }
    
    public void drawLabelBackground(Graphics2D g2d, JLabel myLabel) {
        Rectangle labelBounds = myLabel.getBounds();

        g2d.setColor(Color.BLACK);

        g2d.fillRect(labelBounds.x, labelBounds.y - 1, labelBounds.width, labelBounds.height + 2);

        g2d.fillOval(myLabel.getX() - myLabel.getHeight() / 2 - 1, myLabel.getY() - 1,
                myLabel.getHeight() + 1, myLabel.getHeight() + 2);

        g2d.fillOval(myLabel.getX() + myLabel.getWidth() - myLabel.getHeight() / 2 + 1,
                myLabel.getY() - 1, myLabel.getHeight(), myLabel.getHeight() + 2);


        g2d.setColor(Color.WHITE);
        g2d.fillRect(labelBounds.x, labelBounds.y, labelBounds.width, labelBounds.height);
        
        g2d.fillOval(myLabel.getX() - myLabel.getHeight() / 2, myLabel.getY(),
                myLabel.getHeight(), myLabel.getHeight());

        g2d.fillOval(myLabel.getX() + myLabel.getWidth() - myLabel.getHeight() / 2,
                myLabel.getY(), myLabel.getHeight(), myLabel.getHeight());
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean value) {
        this.initialized = value;
    }
    
    public void setMoveBounds(int explorationPanelBounds) {
        this.MOVE_BOUND = explorationPanelBounds;        
    }

    public boolean isHidden() {
        return hidden;
    }
    
    public void setHidden(boolean isItHidden) {
        this.hidden = isItHidden;
    }

    public void doHide() {

        if(this.getX() == MOVE_BOUND - 4) {
            return;
        }

        moveTimer.stop();

        this.moving = true;
        this.moveDirection = MoveDirection.Right;

        moveTimer.start();
    }

    public void doShow() {
        moveTimer.stop();

        this.moving = true;
        this.moveDirection = MoveDirection.Left;

        moveTimer.start();
    }
}

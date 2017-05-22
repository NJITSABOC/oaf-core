package edu.njit.cs.saboc.blu.core.gui.gep.panels;

import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayWidget;
import edu.njit.cs.saboc.blu.core.gui.iconmanager.ImageManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public class NavigationTutorialPanel extends AbNDisplayWidget {
    
    private final JEditorPane descriptionPane;
    
    private int currentTutorialLocation = 0;
    
    private final Dimension panelSize = new Dimension(350, 120);
    
    public NavigationTutorialPanel(AbNDisplayPanel panel) {
        
        super(panel);
        
        this.setLayout(new BorderLayout());
        
        this.setBorder(BorderFactory.createEtchedBorder());
        
        String navigateTutorial = "<html><b>Navigation: </b>Use the arrow buttons at the top left, "
                + "the scroll bars on the edges of this display, or by clicking and dragging. "
                + "Zoom in and out using the mouse wheel or using the slider bar at the top left.";
        
        String selectionTutorial = "<html><b>View details: </b>Left click on a node to view its "
                + "details in the Dashboard panel (on the left, by default). "
                + "Click outside of any node to view the details of the abstraction network.";
        
        String minimapTutorial = "<html><b>Minimap: </b> Shown at the top right when available, "
                + "provides a high level view of the current abstraction network. Clicking on the "
                + "minimap will navigate you to that location in the abstraction network.";
        
        String aggregationTutorial = "<html><b>Aggregation: </b> Most abstraction networks can be "
                + "<i>aggregated</i> using the aggregation menu at the bottom right. Aggregation "
                + "combines small nodes into their ancestor nodes, simplfiying the view.";
        
        String [] tutorialText = {
            navigateTutorial, 
            selectionTutorial,
            minimapTutorial,
            aggregationTutorial
        };
        
        JButton nextBtn = new JButton("Got it!");

        nextBtn.addActionListener((ae) -> {

            if (currentTutorialLocation < tutorialText.length - 1) {
                currentTutorialLocation++;
                
                displayText(tutorialText[currentTutorialLocation]);
                
                if(currentTutorialLocation == tutorialText.length) {
                    nextBtn.setVisible(false);
                }
            } else {
                this.setVisible(false);
            }
        });
        
        JButton closeBtn = new JButton(ImageManager.getImageManager().getIcon("cancel.png"));
        closeBtn.addActionListener( (ae) -> { 
           this.setVisible(false);
        });
        
        this.descriptionPane = new JEditorPane();
        
        descriptionPane.setContentType("text/html");
        descriptionPane.setEnabled(true);
        descriptionPane.setEditable(false);
        
        descriptionPane.setSelectionStart(0);
        descriptionPane.setSelectionEnd(0);
        
        descriptionPane.setBorder(BorderFactory.createTitledBorder("Navigation and Display Tutorial"));
        
        this.add(descriptionPane, BorderLayout.CENTER);
        
        
        this.setBackground(new Color(220, 220, 255));
        
        JPanel nextPanel = new JPanel();
        nextPanel.setLayout(new BoxLayout(nextPanel, BoxLayout.Y_AXIS));
        
        nextPanel.add(closeBtn);
        nextPanel.add(Box.createVerticalStrut(30));
        nextPanel.add(nextBtn);
        

        
        nextPanel.setOpaque(false);
        
        this.add(nextPanel, BorderLayout.EAST);
                
        displayText(tutorialText[currentTutorialLocation]);
    }
    
    private void displayText(String text) {
        descriptionPane.setText(text);
    }
    
    @Override
    public void displayPanelResized(AbNDisplayPanel displayPanel) {
        
        this.setBounds(
                displayPanel.getWidth() - 600, 
                8, 
                panelSize.width, 
                panelSize.height);
    }
}

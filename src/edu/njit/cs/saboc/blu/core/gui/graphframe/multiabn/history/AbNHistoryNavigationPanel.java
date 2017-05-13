package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.history;

import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivationParser;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.MultiAbNGraphFrame;
import edu.njit.cs.saboc.blu.core.gui.iconmanager.ImageManager;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public class AbNHistoryNavigationPanel extends JPanel {

    private final JButton backBtn;
    private final JButton forwardBtn;
   
    private final AbNHistoryButton viewHistoryBtn;
    
    private final AbNDerivationHistory derivationHistory;
    
    private final AbNHistoryNavigationManager historyNavigationManager;

    public AbNHistoryNavigationPanel(
            MultiAbNGraphFrame graphFrame,
            AbNDerivationHistory derivationHistory,
            AbNDerivationParser abnParser) {
        
        this.derivationHistory = derivationHistory;
        
        historyNavigationManager = new AbNHistoryNavigationManager(derivationHistory);
        
        backBtn = new JButton();
        backBtn.setIcon(ImageManager.getImageManager().getIcon("left-arrow.png"));
        backBtn.addActionListener((ae) -> {
            historyNavigationManager.goBack();
            
            updateNavigationButtons();
        });
        
        backBtn.setPreferredSize(new Dimension(60, 24));

        forwardBtn = new JButton();
        forwardBtn.setIcon(ImageManager.getImageManager().getIcon("right-arrow.png"));
        forwardBtn.addActionListener((ae) -> {
            historyNavigationManager.goForward();
            
            updateNavigationButtons();
        });
        
        forwardBtn.setPreferredSize(new Dimension(60, 24));
        
        derivationHistory.addHistoryChangedListener( () -> {
            updateNavigationButtons();
        });

        this.viewHistoryBtn = new AbNHistoryButton(graphFrame, derivationHistory, abnParser);
        
        this.add(backBtn);
        this.add(Box.createHorizontalStrut(4));
        this.add(viewHistoryBtn);
        this.add(Box.createHorizontalStrut(4));
        this.add(forwardBtn);
    }
    
    public void refreshHistoryDisplay() {
        this.viewHistoryBtn.displayHistory(derivationHistory);
    }

    private void updateNavigationButtons() {
        backBtn.setEnabled(historyNavigationManager.canGoBack());
        forwardBtn.setEnabled(historyNavigationManager.canGoForward());
    }
}

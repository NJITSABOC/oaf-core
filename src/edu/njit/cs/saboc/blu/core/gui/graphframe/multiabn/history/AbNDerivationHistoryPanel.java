package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.history;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionListener;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public class AbNDerivationHistoryPanel extends JPanel {
    
    private final AbNDerivationHistoryList derivationList;
    
    private AbNDerivationHistoryEntry selectedEntry;
        
    public AbNDerivationHistoryPanel() {
        
        this.setLayout(new BorderLayout());

        this.derivationList = new AbNDerivationHistoryList();
        this.derivationList.addEntitySelectionListener(new EntitySelectionListener<AbNDerivationHistoryEntry>() {

            @Override
            public void entityClicked(AbNDerivationHistoryEntry entity) {
                selectedEntry = entity;
            }

            @Override
            public void entityDoubleClicked(AbNDerivationHistoryEntry entity) {
                entity.displayEntry();
            }

            @Override
            public void noEntitySelected() {
                
            }
        });
        
        this.add(derivationList, BorderLayout.CENTER);
    }
    
    public void showHistory(AbNDerivationHistory history) {
        ArrayList<AbNDerivationHistoryEntry> historyList = new ArrayList<>(history.getHistory());
        
        historyList.sort( (a, b) -> a.getDate().compareTo(b.getDate()));
        
        this.derivationList.setContents(historyList);
    }
      
    public AbNDerivationHistoryEntry getSelectedEntry() {
        return selectedEntry;
    } 
}

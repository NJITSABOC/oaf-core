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
    
    private final ArrayList<AbNDerivationHistoryEntry> entries = new ArrayList<>();
    
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
    
    public void addEntry(AbNDerivationHistoryEntry entry) {
        entries.add(entry);
        System.out.println("New Derivation:");

        System.out.println(entry.getDerivation().serializeToJSON().toJSONString());
        
        derivationList.setContents(entries);
    }
    
    public ArrayList<AbNDerivationHistoryEntry> getEntries(){
        return entries;
    } 
      
    public AbNDerivationHistoryEntry getSelectedEntry(){
        return selectedEntry;
    } 
    
}

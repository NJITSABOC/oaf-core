package edu.njit.cs.saboc.blu.core.abn.provenance;

import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public class AbNDerivationHistory {
    private final ArrayList<AbNDerivationHistoryEntry> historyEntries = new ArrayList<>();
    
    public AbNDerivationHistory() {
        
    }
    
    public AbNDerivationHistory(ArrayList<AbNDerivationHistoryEntry> historyEntries) {
        this.historyEntries.addAll(historyEntries);
    }
    
    public void addHistoryEntry(AbNDerivationHistoryEntry entry) {
        historyEntries.add(entry);
    }
   
    public ArrayList<AbNDerivationHistoryEntry> getHistory() {
        return historyEntries;
    }
}

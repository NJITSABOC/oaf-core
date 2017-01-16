package edu.njit.cs.saboc.blu.core.abn.provenance;

import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public class AbNDerivationHistory {
    private final ArrayList<AbNDerivation> historyEntries = new ArrayList<>();
    
    public AbNDerivationHistory() {
        
    }
    
    public AbNDerivationHistory(ArrayList<AbNDerivation> historyEntries) {
        this.historyEntries.addAll(historyEntries);
    }
    
    public void addHistoryEntry(AbNDerivation entry) {
        historyEntries.add(entry);
    }
   
    public ArrayList<AbNDerivation> getHistory() {
        return historyEntries;
    }
}

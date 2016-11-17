package edu.njit.cs.saboc.blu.core.abn.provenance;

import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public class AbNProvenance {
    private final ArrayList<AbNDerivationHistoryEntry> historyEntries = new ArrayList<>();
    
    public AbNProvenance() {
        
    }
    
    public AbNProvenance(ArrayList<AbNDerivationHistoryEntry> historyEntries) {
        this.historyEntries.addAll(historyEntries);
    }
    
    public void addHistoryEntry(AbNDerivationHistoryEntry entry) {
        historyEntries.add(entry);
    }
   
    public ArrayList<AbNDerivationHistoryEntry> getProvenance() {
        return historyEntries;
    }
}

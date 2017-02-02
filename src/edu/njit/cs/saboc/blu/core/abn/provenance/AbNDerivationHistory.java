package edu.njit.cs.saboc.blu.core.abn.provenance;

import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public class AbNDerivationHistory {
    private final ArrayList<AbNDerivation> historyEntries = new ArrayList<>();
    
    public AbNDerivationHistory() {
        System.out.println("New History:");
        
    }
    
    public AbNDerivationHistory(ArrayList<AbNDerivation> historyEntries) {
        this.historyEntries.addAll(historyEntries);
    }
    
    public void addHistoryEntry(AbNDerivation entry) {
        System.out.println("New Derivation:");
        historyEntries.add(entry);
        System.out.println(entry.serializeToJSON().toJSONString());
    }
   
    public ArrayList<AbNDerivation> getHistory() {
        System.out.println("get history");
        return historyEntries;
    }
    
    public void dumpHistory() {
        
        System.out.println();
        
        historyEntries.forEach( (entry) -> {
            System.out.println(entry.getDescription());
        });
    }
}

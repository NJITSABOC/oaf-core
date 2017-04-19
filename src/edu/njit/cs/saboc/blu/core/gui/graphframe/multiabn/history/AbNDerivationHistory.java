package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.history;

import java.util.ArrayList;
import org.json.simple.JSONArray;

/**
 *
 * @author Chris O
 */
public class AbNDerivationHistory {
    
    private final ArrayList<AbNDerivationHistoryEntry> entries = new ArrayList<>();
    
    public AbNDerivationHistory() {
        
    }
    
    public void addEntry(AbNDerivationHistoryEntry entry) {
        entries.add(entry);
    }
    
    public ArrayList<AbNDerivationHistoryEntry> getHistory() {
        return entries;
    } 
    
    public JSONArray toJSON() {
        JSONArray arr = new JSONArray();
        
        entries.forEach( (entry) -> {
            arr.add(entry.toJSON());
        });
        
        return arr;
    }
}

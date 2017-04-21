package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.history;

import java.util.ArrayList;
import org.json.simple.JSONArray;

/**
 *
 * @author Chris O
 */
public class AbNDerivationHistory {
    
    private ArrayList<AbNDerivationHistoryEntry> entries;
    
    public AbNDerivationHistory() {
        setHistory(new ArrayList<>());
    }
    
    public AbNDerivationHistory(ArrayList<AbNDerivationHistoryEntry> entries) {
        setHistory(entries);
    }
    
    public final void setHistory(ArrayList<AbNDerivationHistoryEntry> entries) {
        this.entries = entries;
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

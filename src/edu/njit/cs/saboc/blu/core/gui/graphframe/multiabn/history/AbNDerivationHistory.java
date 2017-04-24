package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.history;

import java.util.ArrayList;
import org.json.simple.JSONArray;

/**
 *
 * @author Chris O
 */
public class AbNDerivationHistory {
    
    public interface AbNDerivationHistoryListener {
        public void historyChanged();
    }
    
    private final ArrayList<AbNDerivationHistoryEntry> entries = new ArrayList<>();
    
    private final ArrayList<AbNDerivationHistoryListener> historyChangedListeners = new ArrayList<>();;
    
    public AbNDerivationHistory() {
        
    }
    
    public AbNDerivationHistory(ArrayList<AbNDerivationHistoryEntry> entries) {
        this.entries.addAll(entries);
    }
    
    public void addHistoryChangedListener(AbNDerivationHistoryListener listener) {
        this.historyChangedListeners.add(listener);
    }
    
    public void removeHistoryChangedListener(AbNDerivationHistoryListener listener) {
        this.historyChangedListeners.remove(listener);
    }
    
    public final void setHistory(ArrayList<AbNDerivationHistoryEntry> entries) {
        
        this.entries.clear();
        this.entries.addAll(entries);

        historyChangedListeners.forEach((listener) -> {
            listener.historyChanged();
        });
    }
    
    public void addEntry(AbNDerivationHistoryEntry entry) {
        entries.add(entry);
        
        historyChangedListeners.forEach( (listener) -> {
            listener.historyChanged();
        });
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

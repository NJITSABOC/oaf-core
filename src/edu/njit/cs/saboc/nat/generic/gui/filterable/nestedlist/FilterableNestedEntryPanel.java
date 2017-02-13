package edu.njit.cs.saboc.nat.generic.gui.filterable.nestedlist;

import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 * @param <T>
 */
public abstract class FilterableNestedEntryPanel<T extends FilterableNestedEntry> extends JPanel {
    
    private final T entry;
    
    private final ArrayList<FilterableEntryPanel> subPanels = new ArrayList<>();
    
    public FilterableNestedEntryPanel(T entry) {
        this.entry = entry;
    }
    
    public void addSubEntryPanel(FilterableEntryPanel panel) {
        subPanels.add(panel);
    }
    
    public ArrayList<FilterableEntryPanel> getSubPanels() {
        return subPanels;
    }
    
    public T getEntry() {
        return entry;
    }
}

package edu.njit.cs.saboc.nat.generic.gui.filterable.nestedlist;

import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import java.awt.Color;
import java.util.Optional;
import javax.swing.JPanel;

/**
 * The filterable entries that are displayed within a nested filterable entry.
 * The user interacts with these sub panels.
 * 
 * @author Chris O
 * @param <T>
 */
public abstract class FilterableEntryPanel<T extends Filterable> extends JPanel {
    
    private final T entry;
    private boolean isSelected = false;
    
    private final Optional<String> filter;
    
    public FilterableEntryPanel(T entry, Optional<String> filter) {
        this.entry = entry;
        
        this.filter = filter;
        
        this.setOpaque(false);
    }
    
    public T getEntry() {
        return entry;
    }
    
    protected Optional<String> getFilter() {
        return filter;
    }
    
    public void setSelected(boolean value) {
        this.isSelected = value;
        
        if(value) {
            this.setOpaque(true);
            this.setBackground(new Color(220, 220, 255));
        } else {
            this.setOpaque(false);
            this.setBackground(Color.WHITE);
        }
    }
    
    public boolean isSelected() {
        return isSelected;
    }
}

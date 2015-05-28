package edu.njit.cs.saboc.blu.core.utils.filterable.list;

/**
 *
 * @author Chris O
 */
public abstract class Filterable<T> {
    
    public abstract String getFilterText(String filter);
    public abstract String getInitialText();
    
    public abstract boolean containsFilter(String filter);
    
    public abstract T getObject();
    
    public String filter(String text, String filter) {
        String filtered = text.replace("<", "&lt;");

        if(!filter.isEmpty()) {
            filter = filter.replace("<", "&lt;").toLowerCase();
            String tag1 = "<font style=\"BACKGROUND-COLOR: yellow\">";
            String tag2 = "</font>";
            String lower = filtered.toLowerCase();
            
            int i = lower.indexOf(filter, 0);
            
            while(i != -1) {
                int j = i + filter.length();
                
                filtered = filtered.substring(0, j) + tag2 + filtered.substring(j);
                filtered = filtered.substring(0, i) + tag1 + filtered.substring(i);
                
                i = lower.indexOf(filter, j + tag1.length() + tag2.length());
            }
        }

        return filtered;
    }
    
    public String getToolTipText() {
        return getInitialText();
    }
}

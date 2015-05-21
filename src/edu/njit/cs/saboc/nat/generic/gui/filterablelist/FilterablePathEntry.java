package edu.njit.cs.saboc.nat.generic.gui.filterablelist;

import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.data.BrowserConcept;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public class FilterablePathEntry extends Filterable<ArrayList<BrowserConcept>> {
    
    private ArrayList<BrowserConcept> path;
    
    private String pathStr;
    
    public FilterablePathEntry(ArrayList<BrowserConcept> path) {
        this.path = path;
        
        String pathStr = path.get(0).getName();

        if (path.size() > 1) {
            pathStr += " ... ";
        }

        if (path.size() > 2) {
            pathStr += path.get(path.size() - 2).getName() + ", ";
        }

        pathStr += path.get(path.size() - 1).getName();
        pathStr += " (Path Length: " + path.size() +")";
        
        this.pathStr = pathStr;
    }
    
    public ArrayList<BrowserConcept> getObject() {
        return path;
    }
    
    public String getInitialText() {
        return pathStr;
    }
    
    public String getFilterText(String filter) {
        return getInitialText();
    }
    
    public ArrayList<BrowserConcept> getPath() {
        return path;
    }
}

package edu.njit.cs.saboc.nat.generic.history;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.json.simple.JSONArray;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class BookmarkManager<T extends Concept> {
    
    private final ArrayList<T> bookmarks = new ArrayList<>();
    
    public BookmarkManager() {
        
    }
    
    public BookmarkManager(ArrayList<T> bookmarks){ 
        this.setBookmarks(bookmarks);
    }
    
    public final void setBookmarks(ArrayList<T> bookmarks) {
        this.bookmarks.addAll(bookmarks);
    }
    
    public void addBookmark(T concept) {
        bookmarks.add(concept);
    }
    
    public ArrayList<T> getBookmarks() {
        return bookmarks;
    }
    
    public JSONArray toJSON() {
        JSONArray arr = new JSONArray();
        
        this.bookmarks.forEach( (concept) -> {
            arr.add(concept.getIDAsString());
        });
        
        return arr;
    }
}

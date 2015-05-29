package edu.njit.cs.saboc.nat.generic.data;

import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author Chris O
 */
public interface ConceptBrowserDataSource<T> {

    public ArrayList<T> getConceptParents(T concept);
    
    public ArrayList<T> getConceptChildren(T concept);
    
    public ArrayList<String> getConceptSynonyms(T concept);
    
    public ArrayList<T> getConceptSiblings(T concept);
          
    public ArrayList<BrowserSearchResult<T>> searchExact(String term);

    public ArrayList<BrowserSearchResult<T>> searchAnywhere(String term);

    public ArrayList<BrowserSearchResult<T>> searchStarting(String term);
    
    public T getRoot();
    
    public String getConceptName(T concept);
            
    public String getConceptId(T concept);
    
    public Optional<T> getConceptFromId(String id);
}

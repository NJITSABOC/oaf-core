package edu.njit.cs.saboc.nat.generic.data;

import java.util.ArrayList;
import java.util.Optional;

/**
 * An interface that defines high level functionality that must be implemented by every instance of a NAT
 * @author Chris O
 */
public interface ConceptBrowserDataSource<T> {

    /**
     * 
     * @param concept The concept
     * @return The parents of the concept
     */
    public ArrayList<T> getConceptParents(T concept);
    
    /**
     * 
     * @param concept The concept
     * @return The children of the concept
     */
    public ArrayList<T> getConceptChildren(T concept);
    
    /**
     * 
     * @param concept The concept
     * @return The synonyms of the concept
     */
    public ArrayList<String> getConceptSynonyms(T concept);
    
    /**
     * 
     * @param concept The concept
     * @return The siblings (children of parent(s)) of the concept
     */
    public ArrayList<T> getConceptSiblings(T concept);
          
    /**
     * 
     * @param term The search term
     * @return The concepts which have a name that exactly matches the search term
     */
    public ArrayList<BrowserSearchResult<T>> searchExact(String term);

    /**
     * 
     * @param term The search term
     * @return The concepts which have the search term anywhere in their name
     */
    public ArrayList<BrowserSearchResult<T>> searchAnywhere(String term);

    /**
     * 
     * @param term The search term
     * @return The concepts which have a name that begins with the search term
     */
    public ArrayList<BrowserSearchResult<T>> searchStarting(String term);
    
    /**
     * 
     * @return The root concept of the ontology
     */
    public T getRoot();
    
    /**
     * 
     * @param concept The concept
     * @return The fully name of the concept
     */
    public String getConceptName(T concept);
            
    /**
     * 
     * @param concept The concept
     * @return The unique ID of the concept
     */
    public String getConceptId(T concept);
    
    /**
     * 
     * @param id The unique id of the concept
     * @return Optional which will contain the concept with the unique id, if such a concept exists
     */
    public Optional<T> getConceptFromId(String id);
}

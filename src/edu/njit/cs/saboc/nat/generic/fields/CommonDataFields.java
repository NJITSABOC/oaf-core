package edu.njit.cs.saboc.nat.generic.fields;

import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public class CommonDataFields<T> {
    public final NATDataField<T, T> CONCEPT;
    
    public final NATDataField<T, ArrayList<String>> SYNONYMS;
    
    public final NATDataField<T, ArrayList<T>> PARENTS;
    
    public final NATDataField<T, ArrayList<T>> CHILDREN;
    
    public final NATDataField<T, ArrayList<T>> SIBLINGS;
    
    public final NATDataField<T, String> HISTORY;
    
    public CommonDataFields(ConceptBrowserDataSource dataSource) {
        this.CONCEPT = new NATDataField<T, T>("Concept", new DataSourceDataRetriever<T, T>(dataSource) {
            public T retrieveData(T concept) {
                return concept;
            }
        });
        
        this.SYNONYMS = new NATDataField<T, ArrayList<String>>("Synonyms", new DataSourceDataRetriever<T, ArrayList<String>>(dataSource) {
            public ArrayList<String> retrieveData(T concept) {
                return dataSource.getConceptSynonyms(concept);
            }
        });
        
        this.PARENTS = new NATDataField<T, ArrayList<T>>("Parents", new DataSourceDataRetriever<T, ArrayList<T>>(dataSource) {
            public ArrayList<T> retrieveData(T concept) {
                return dataSource.getConceptParents(concept);
            }
        });
        
        this.CHILDREN = new NATDataField<T, ArrayList<T>>("Children", new DataSourceDataRetriever<T, ArrayList<T>>(dataSource) {
            public ArrayList<T> retrieveData(T concept) {
                return dataSource.getConceptChildren(concept);
            }
        });
        
        this.SIBLINGS = new NATDataField<T, ArrayList<T>>("Siblings", new DataSourceDataRetriever<T, ArrayList<T>>(dataSource) {
            public ArrayList<T> retrieveData(T concept) {
                return dataSource.getConceptSiblings(concept);
            }
        });
        
        this.HISTORY = new NATDataField<T, String>("History", new DataSourceDataRetriever<T, String>(dataSource) {
            public String retrieveData(T concept) {
                return "";
            }
        });
    }
}

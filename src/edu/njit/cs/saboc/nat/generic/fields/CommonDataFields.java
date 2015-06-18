package edu.njit.cs.saboc.nat.generic.fields;

import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import java.util.ArrayList;

/**
 * A container class for holding generic data fields that can be used across all NAT implementations
 * @author Chris O
 */
public class CommonDataFields<T> {
    /**
     * The focus concept data field
     */
    public final NATDataField<T, T> CONCEPT;
    
    /**
     * The synonyms data field
     */
    public final NATDataField<T, ArrayList<String>> SYNONYMS;
    
    /**
     * The parents data field
     */
    public final NATDataField<T, ArrayList<T>> PARENTS;
    
    /**
     * The children data field
     */
    public final NATDataField<T, ArrayList<T>> CHILDREN;
    
    /**
     * The siblings data field
     */
    public final NATDataField<T, ArrayList<T>> SIBLINGS;
    
    /**
     * The history data field
     */
    public final NATDataField<T, String> HISTORY;
    
    /**
     * 
     * @param dataSource The data source to be used by the data fields
     */
    public CommonDataFields(ConceptBrowserDataSource<T> dataSource) {
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

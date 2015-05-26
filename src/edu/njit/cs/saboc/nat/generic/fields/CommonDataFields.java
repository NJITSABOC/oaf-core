package edu.njit.cs.saboc.nat.generic.fields;

import edu.njit.cs.saboc.nat.generic.data.BrowserConcept;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public class CommonDataFields {
    public final NATDataField CONCEPT;
    
    public final NATDataField SYNONYMS;
    
    public final NATDataField PARENTS;
    
    public final NATDataField CHILDREN;
    
    public final NATDataField SIBLINGS;
    
    public CommonDataFields(ConceptBrowserDataSource dataSource) {
        this.CONCEPT = new NATDataField<BrowserConcept>("Concept", new DataSourceDataRetriever<BrowserConcept>(dataSource) {
            public BrowserConcept retrieveData(BrowserConcept concept) {
                return concept;
            }
        });
        
        this.SYNONYMS = new NATDataField<ArrayList<String>>("Synonyms", new DataSourceDataRetriever<ArrayList<String>>(dataSource) {
            public ArrayList<String> retrieveData(BrowserConcept concept) {
                return dataSource.getConceptSynonyms(concept);
            }
        });
        
        this.PARENTS = new NATDataField<ArrayList<BrowserConcept>>("Parents", new DataSourceDataRetriever<ArrayList<BrowserConcept>>(dataSource) {
            public ArrayList<BrowserConcept> retrieveData(BrowserConcept concept) {
                return dataSource.getConceptParents(concept);
            }
        });
        
        this.CHILDREN = new NATDataField<ArrayList<BrowserConcept>>("Children", new DataSourceDataRetriever<ArrayList<BrowserConcept>>(dataSource) {
            public ArrayList<BrowserConcept> retrieveData(BrowserConcept concept) {
                return dataSource.getConceptChildren(concept);
            }
        });
        
        this.SIBLINGS = new NATDataField<ArrayList<BrowserConcept>>("Siblings", new DataSourceDataRetriever<ArrayList<BrowserConcept>>(dataSource) {
            public ArrayList<BrowserConcept> retrieveData(BrowserConcept concept) {
                return dataSource.getConceptSiblings(concept);
            }
        });
    }
}

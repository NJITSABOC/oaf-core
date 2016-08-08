package edu.njit.cs.saboc.nat.generic.fields;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import java.util.ArrayList;
import java.util.Set;

/**
 * A container class for holding generic data fields that can be used across all NAT implementations
 * @author Chris O
 */
public class CommonDataFields {

    public final NATDataField<Concept> CONCEPT;
    
    public final NATDataField<ArrayList<Concept>> PARENTS;

    public final NATDataField<ArrayList<Concept>> CHILDREN;

    public final NATDataField<ArrayList<Concept>> SIBLINGS;

    public final NATDataField<ArrayList<Concept>> STRICT_SIBLINGS;
    
    public final NATDataField<String> HISTORY;
    
    /**
     * 
     * @param dataSource The data source to be used by the data fields
     */
    public CommonDataFields(ConceptBrowserDataSource dataSource) {
        
        
        this.CONCEPT = new NATDataField<>("Concept", new DataSourceDataRetriever<Concept>(dataSource) {
            public Concept retrieveData(Concept concept) {
                return concept;
            }
        });

        this.PARENTS = new NATDataField<>("Parents", new DataSourceDataRetriever<ArrayList<Concept>>(dataSource) {
            public ArrayList<Concept> retrieveData(Concept concept) {
                return getSortedConceptList(
                        dataSource.getOntology().getConceptHierarchy().getParents(concept)
                );
            }
        });
        
        this.CHILDREN = new NATDataField<>("Children", new DataSourceDataRetriever<ArrayList<Concept>>(dataSource) {
            public ArrayList<Concept> retrieveData(Concept concept) {
                return getSortedConceptList(
                        dataSource.getOntology().getConceptHierarchy().getChildren(concept)
                );
            }
        });
        
        this.SIBLINGS = new NATDataField<>("Siblings", new DataSourceDataRetriever<ArrayList<Concept>>(dataSource) {
            public ArrayList<Concept> retrieveData(Concept concept) {
                return getSortedConceptList(
                        dataSource.getOntology().getConceptHierarchy().getSiblings(concept)
                );
            }
        });
        
        this.STRICT_SIBLINGS = new NATDataField<>("Strict Siblings", new DataSourceDataRetriever<ArrayList<Concept>>(dataSource) {
            public ArrayList<Concept> retrieveData(Concept concept) {
                return getSortedConceptList(
                        dataSource.getOntology().getConceptHierarchy().getStrictSiblings(concept)
                );
            }
        });
        
        this.HISTORY = new NATDataField<>("History", new DataSourceDataRetriever<String>(dataSource) {
            public String retrieveData(Concept concept) {
                return "";
            }
        });
    }
    
    private ArrayList<Concept> getSortedConceptList(Set<Concept> concepts) {
        ArrayList<Concept> sortedConcepts = new ArrayList<>(concepts);
        sortedConcepts.sort( (a, b) -> {
            return a.getName().compareToIgnoreCase(b.getName());
        });
        
        return sortedConcepts;
    }
}

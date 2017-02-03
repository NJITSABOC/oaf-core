package edu.njit.cs.saboc.nat.generic.gui.panels.dataretrievers;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.comparators.ConceptNameComparator;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.panels.ResultPanel.DataRetriever;
import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class CommonBrowserDataRetrievers {
    
    public static <T extends Concept> DataRetriever<T, ArrayList<T>> getChildrenRetriever(ConceptBrowserDataSource<T> dataSource) {
        
        return new DataRetriever<T, ArrayList<T>>() {

            @Override
            public ArrayList<T> getData(T concept) {
                return getSortedConceptList(dataSource.getOntology().getConceptHierarchy().getChildren(concept));
            }

            @Override
            public String getDataType() {
                return "Children";
            }
            
        };
    }
    
    public static <T extends Concept> DataRetriever<T, ArrayList<T>> getParentsRetriever(ConceptBrowserDataSource<T> dataSource) {
        
        return new DataRetriever<T, ArrayList<T>>() {

            @Override
            public ArrayList<T> getData(T concept) {
                return getSortedConceptList(dataSource.getOntology().getConceptHierarchy().getParents(concept));
            }

            @Override
            public String getDataType() {
                return "Parents";
            }
        };
    }
    
    public static <T extends Concept> DataRetriever<T, ArrayList<T>> getSiblingsRetriever(ConceptBrowserDataSource<T> dataSource) {
        
        return new DataRetriever<T, ArrayList<T>>() {

            @Override
            public ArrayList<T> getData(T concept) {
                return getSortedConceptList(dataSource.getOntology().getConceptHierarchy().getSiblings(concept));
            }

            @Override
            public String getDataType() {
                return "Siblings";
            }
        };
    }
    
    public static <T extends Concept> DataRetriever<T, ArrayList<T>> getStrictSiblingsRetriever(ConceptBrowserDataSource<T> dataSource) {
        
        return new DataRetriever<T, ArrayList<T>>() {

            @Override
            public ArrayList<T> getData(T concept) {
                return getSortedConceptList(dataSource.getOntology().getConceptHierarchy().getStrictSiblings(concept));
            }

            @Override
            public String getDataType() {
                return "Strict Siblings";
            }
        };
    }
    
    private static <T extends Concept> ArrayList<T> getSortedConceptList(Set<T> conceptSet) {
        ArrayList<T> concepts = new ArrayList<>(conceptSet);

        concepts.sort(new ConceptNameComparator());

        return concepts;
    }
}

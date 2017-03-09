package edu.njit.cs.saboc.nat.generic.gui.filterable.list;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.data.NATConceptSearchResult;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A filterable entry for displaying a search result
 * 
 * @author Chris O
 * @param <T>
 */
public class FilterableSearchResultEntry<T extends Concept> extends Filterable<NATConceptSearchResult<T>> 
        implements NavigableEntry<T> {

    private final NATConceptSearchResult<T> searchResult;
    private final ConceptBrowserDataSource<T> dataSource;

    public FilterableSearchResultEntry(
            NATConceptSearchResult<T> concept, 
            ConceptBrowserDataSource<T> dataSource) {
        
        this.searchResult = concept;
        this.dataSource = dataSource;
    }

    @Override
    public NATConceptSearchResult<T> getObject() {
        return searchResult;
    }

    @Override
    public T getNavigableConcept() {
        return getObject().getConcept();
    }
    
    @Override
    public boolean containsFilter(String filter) {
        
        if(searchResult.getConcept().getName().toLowerCase().contains(filter)) {
            return true;
        }
        
        if(searchResult.getConcept().getIDAsString().toLowerCase().contains(filter)) {
            return true;
        }
        
        return searchResult.getMatchedTerms().stream().anyMatch( (term) -> {
           return term.toLowerCase().contains(filter);
        });
    }
    
    @Override
    public String getClipboardText() {
        
       return "";
    }

    @Override
    public String getToolTipText() {
        String filter = searchResult.getQuery().toLowerCase();
        
        ArrayList<String> matchedTerms = new ArrayList<>(searchResult.getMatchedTerms());
        Collections.sort(matchedTerms);

        String matchedTermsStr = "";

        for (String term : matchedTerms) {
            String filtered = Filterable.filter(term, filter);

            matchedTermsStr += filtered + "<br>";
        }
                
        matchedTermsStr = matchedTermsStr.substring(0, matchedTermsStr.length() - 4);
        
        return String.format("<html><font size = 5><b>Matched Terms</b><br>%s", matchedTermsStr);
    }
}

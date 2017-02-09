package edu.njit.cs.saboc.nat.generic;

import edu.njit.cs.saboc.nat.generic.history.FocusConceptHistory;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;

import java.util.ArrayList;
import java.util.Optional;


public class FocusConceptManager<T extends Concept> {
    
    public interface FocusConceptChangedListener<T extends Concept> {
        public void focusConceptChanged(T concept);
    }
    
    private final ConceptBrowserDataSource<T> dataSource;
    private final NATBrowserPanel<T> browser;
    
    
    private final ArrayList<FocusConceptChangedListener<T>> listeners = new ArrayList<>();

    private final FocusConceptHistory<T> history = new FocusConceptHistory<>();
    
    private Optional<T> activeFocusConcept = Optional.empty();
   
    public FocusConceptManager(
            NATBrowserPanel<T> browser, 
            ConceptBrowserDataSource<T> dataSource) {
        
        this.browser = browser;
        this.dataSource = dataSource;
    }

    public FocusConceptHistory<T> getHistory() {
        return history;
    }
    
    public void addFocusConceptListener(FocusConceptChangedListener<T> fcl) {
        listeners.add(fcl);
    }

    public void navigateToRoot() {
        navigateTo(dataSource.getOntology().getConceptHierarchy().getRoot());
    }

    public void navigateTo(T concept) {
        navigateTo(concept, true);
    }
    
    public void navigateTo(T concept, boolean addHistoryEntry) {
        activeFocusConcept = Optional.of(concept);

        listeners.forEach( (listener) -> {
            listener.focusConceptChanged(concept);
        });
        
        if(addHistoryEntry) {
            history.addHistoryEntry(concept);
        }
    }
    
    public NATBrowserPanel<T> getNATBrowserPanel() {
        return browser;
    }

    public T getActiveFocusConcept() {
        return activeFocusConcept.get();
    }
}

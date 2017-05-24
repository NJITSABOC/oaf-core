package edu.njit.cs.saboc.nat.generic;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;

/**
 *
 * @author Chris Ochs
 * @param <T>
 */
public interface DataSourceChangeListener<T extends Concept> {
    
    public void dataSourceLoaded(ConceptBrowserDataSource<T> dataSource);
    public void dataSourceRemoved();
    
}

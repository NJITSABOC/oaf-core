package edu.njit.cs.saboc.nat.generic.fields;

import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;

/**
 *
 * @author Chris O
 */
public abstract class DataSourceDataRetriever<T> implements FieldDataRetriever<T>  {
    protected ConceptBrowserDataSource dataSource;
    
    public DataSourceDataRetriever(ConceptBrowserDataSource dataSource) {
        this.dataSource = dataSource;
    }
}

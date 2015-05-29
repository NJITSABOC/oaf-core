package edu.njit.cs.saboc.nat.generic.fields;

import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;

/**
 *
 * @author Chris O
 */
public abstract class DataSourceDataRetriever<T, V> implements FieldDataRetriever<T, V>  {
    protected ConceptBrowserDataSource<T> dataSource;
    
    public DataSourceDataRetriever(ConceptBrowserDataSource<T> dataSource) {
        this.dataSource = dataSource;
    }
}

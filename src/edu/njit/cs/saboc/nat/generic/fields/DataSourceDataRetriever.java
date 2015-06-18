package edu.njit.cs.saboc.nat.generic.fields;

import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;

/**
 * Generic class for retrieving some data for the NAT via a concept browser data source
 * @author Chris O
 */
public abstract class DataSourceDataRetriever<T, V> implements FieldDataRetriever<T, V>  {
    protected ConceptBrowserDataSource<T> dataSource;
    
    public DataSourceDataRetriever(ConceptBrowserDataSource<T> dataSource) {
        this.dataSource = dataSource;
    }
}

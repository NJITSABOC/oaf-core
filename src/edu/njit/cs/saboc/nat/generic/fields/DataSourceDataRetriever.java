package edu.njit.cs.saboc.nat.generic.fields;

import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;

/**
 * Generic class for retrieving some data for the NAT via a concept browser data source
 * @author Chris O
 */
public abstract class DataSourceDataRetriever<V> implements FieldDataRetriever<V>  {
    
    private final ConceptBrowserDataSource dataSource;
    
    public DataSourceDataRetriever(ConceptBrowserDataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public ConceptBrowserDataSource getDataSource() {
        return dataSource;
    }
}

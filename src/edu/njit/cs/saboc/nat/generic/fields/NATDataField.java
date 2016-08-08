package edu.njit.cs.saboc.nat.generic.fields;

import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 * Generic class which represents a data field. A data field handles information about
 * a piece of information for a given concept, e.g., its parents, siblings, relationships, etc.
 * @author Chris O
 */
public class NATDataField<V> {
    
    /**
     * The unique name of the data field (MUST BE UNIQUE!)
     */
    private final String fieldName;
    
    /**
     * The data retriever for this field
     */
    private final FieldDataRetriever<V> retriever;
    
    /**
     * 
     * @param fieldName The unique name of the field (MUST BE UNIQUE AND MEANINGFUL!)
     * @param retriever The data retriever for this field
     */
    public NATDataField(String fieldName, FieldDataRetriever<V> retriever) {
        this.fieldName = fieldName;
        this.retriever = retriever;
    }
    
    public int hashCode() {
        return fieldName.toLowerCase().hashCode();
    }
    
    public boolean equals(Object o) {
        if(o instanceof NATDataField) {
            return ((NATDataField)o).fieldName.equalsIgnoreCase(fieldName);
        }
        
        return false;
    }
    
    /**
     * 
     * @param concept The concept
     * @return The data for the concept
     */
    public V getData(Concept concept) {
        return retriever.retrieveData(concept);
    }
    
    /**
     * 
     * @return The unique name of the data field
     */
    public String getFieldName() {
        return fieldName;
    }
    
    public String toString() {
        return String.format("NATDataField: %s", fieldName);
    }
}

package edu.njit.cs.saboc.nat.generic.fields;

import edu.njit.cs.saboc.nat.generic.data.BrowserConcept;

/**
 *
 * @author Chris O
 */
public class NATDataField<T> {
    
    private String fieldName;
    
    private FieldDataRetriever<T> retriever;
    
    public NATDataField(String fieldName, FieldDataRetriever<T> retriever) {
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
    
    public T getData(BrowserConcept concept) {
        return retriever.retrieveData(concept);
    }
    
    public String toString() {
        return String.format("NATDataField: %s", fieldName);
    }
}

package edu.njit.cs.saboc.nat.generic.fields;

/**
 *
 * @author Chris O
 */
public class NATDataField<T, V> {
    
    private String fieldName;
    
    private FieldDataRetriever<T, V> retriever;
    
    public NATDataField(String fieldName, FieldDataRetriever<T, V> retriever) {
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
    
    public V getData(T concept) {
        return retriever.retrieveData(concept);
    }
    
    public String getFieldName() {
        return fieldName;
    }
    
    public String toString() {
        return String.format("NATDataField: %s", fieldName);
    }
}

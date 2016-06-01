

package edu.njit.cs.saboc.blu.core.ontology;

/**
 *  A generic class representing a property or relationship that can be inherited
 * 
 * @author Chris O
 * 
 * @param <ID_T> The type of the id (e.g., an integer or an iri)
 * @param <TYPE_T> The type of the inheritable property (e.g., a kind of attribute relationship or object property)
 */
public abstract class InheritableProperty<ID_T, TYPE_T> {
    private final ID_T id;
    private final TYPE_T propertyType;
    
    public InheritableProperty(ID_T id, TYPE_T propertyType) {
        this.id = id;
        this.propertyType = propertyType;
    }
    
    public final ID_T getID() {
        return id;
    }
    
    public final TYPE_T getType() {
        return propertyType;
    }
    
    public boolean equals(Object o) {
        if(o instanceof InheritableProperty) {
            InheritableProperty other = (InheritableProperty)o;
            
            return this.id.equals(other.id);
        }
        
        return false;
    }
    
    public abstract String getName();
}

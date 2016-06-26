package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.node.SimilarityNode;
import java.util.Set;

/**
 * Represents a region of an area in a partial-area taxonomy
 * @author Chris O
 */
public abstract class Region extends SimilarityNode {
    
    private final Set<InheritableProperty> relationships;
    
    public Region(Set<PArea> pareas, Set<InheritableProperty> relationships) {
        super(pareas);
        
        this.relationships = relationships;
    }
    
    public Set<InheritableProperty> getRelationships() {
        return relationships;
    }

    public Set<PArea> getPAreas() { 
        return (Set<PArea>)(Set<?>)super.getInternalNodes();
    }
    
    public boolean isPAreaInRegion(PArea parea) {        
        return getPAreas().contains(parea);
    }
    
    public int hashCode() {
        return relationships.hashCode();
    }
    
    public boolean equals(Object o) {
        if(o instanceof Region) {
            Region other = (Region)o;
            
            return relationships.stream().allMatch( (rel) -> {
                return other.relationships.stream().anyMatch( (otherRel) -> {
                    return rel.equalsIncludingInheritance(otherRel);
                });
            });
        }
        
        return false;
    }
}

package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.node.SimilarityNode;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 *  Represents an area in a partial-area taxonomy
 * 
 * @author Chris O
 */
public class Area extends SimilarityNode {

    private final Set<InheritableProperty> relationships;
    
    private final Set<Region> regions;
    
    public Area(Set<PArea> pareas, Set<InheritableProperty> relationships) {
        super(pareas);
        
        this.relationships = relationships;
        
        HashMap<Set<InheritableProperty>, Set<PArea>> regionPartition = new HashMap<>();
        
        pareas.forEach( (parea) -> {
            final Set<InheritableProperty> pareaRelationships = parea.getRelationships();
            
            Optional<Set<InheritableProperty>> matchedRegion = regionPartition.keySet().stream().filter( (region) -> {
                
                return region.stream().allMatch( (relationship) -> {
                    return pareaRelationships.stream().anyMatch( (pareaRelationship) -> {
                        return relationship.equalsIncludingInheritance(pareaRelationship);
                    });
                });
            }).findAny();

            if(matchedRegion.isPresent()) {
                regionPartition.get(matchedRegion.get()).add(parea);
            } else {
                regionPartition.put(pareaRelationships, new HashSet<>());
                regionPartition.get(pareaRelationships).add(parea);
            }
        });
        
        this.regions = new HashSet<>();
    }
    
    public Set<InheritableProperty> getRelationships() {
        return relationships;
    }
    
    public Set<Region> getRegions() {
        return regions;
    }
    
    public Set<PArea> getPAreas() {
        return (Set<PArea>)(Set<?>)super.getInternalNodes();
    }

    public String getName() {
        return "";
    }
    
    public String getName(String separator) {
        return "";
    }
    
    public int hashCode() {
        return relationships.hashCode();
    }
    
    public boolean equals(Object o) {
        if(o instanceof Area) {
            Area otherArea = (Area)o;
            
            return this.getRelationships().equals(otherArea.getRelationships());
        }
        
        return false;
    }

}

package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.node.SimilarityNode;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.ArrayList;
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
    
    public HashSet<Concept> getRoots() {
        HashSet<Concept> roots = new HashSet<>();
        
        Set<PArea> pareas = getPAreas();
        
        pareas.forEach( (parea) -> {
            roots.add(parea.getHierarchy().getRoot());
        });
        
        return roots;
    }
    
    public HashSet<Concept> getConcepts() {
        HashSet<Concept> concepts = new HashSet<>();
        
        HashSet<PArea> pareas = this.getPAreas();
        
        pareas.forEach((parea) -> {
            concepts.addAll(parea.getConcepts());
        });
        
        return concepts;
    }
    
    public HashSet<PArea> getPAreas() {
        return new HashSet<>((Set<PArea>)super.getInternalNodes());
    }
    
    public ArrayList<PArea> getPAreasSorted() {
        ArrayList<PArea> pareas = new ArrayList<>(getPAreas());
        
        pareas.sort( (a, b) -> {
            if (a.getConceptCount() == b.getConceptCount()) {
                    return a.getRoot().getName().compareToIgnoreCase(b.getRoot().getName());
                }

                return a.getConceptCount() - b.getConceptCount();
        });

        return pareas;
    }
    
    public boolean hasOverlappingConcepts() {
        HashSet<Concept> concepts = new HashSet<>();
        
        Set<PArea> pareas = this.getPAreas();
        
        for(PArea parea : pareas) {
            Set<Concept> pareaConcepts = parea.getConcepts();
            
            for(Concept concept : pareaConcepts) {
                if(concepts.contains(concept)) {
                    return true;
                } else {
                    concepts.add(concept);
                }
            }
        }
        
        return false;
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

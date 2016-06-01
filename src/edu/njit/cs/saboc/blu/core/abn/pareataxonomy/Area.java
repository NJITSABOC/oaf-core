package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.OverlappingConceptResult;
import edu.njit.cs.saboc.blu.core.abn.node.SimilarityNode;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.InheritableProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *  Represents an area in a partial-area taxonomy
 * 
 * @author Chris O
 */
public abstract class Area extends SimilarityNode {

    private final Set<InheritableProperty> relationships;
    
    private final HashSet<Region> regions;
    
    public Area(Set<Area> parents, Set<PArea> pareas, Set<InheritableProperty> relationships) {
        super(parents, pareas);
        
        this.relationships = relationships;
    }
    
    public Set<InheritableProperty> getRelationships() {
        return relationships;
    }
    
    public HashSet<Region> getRegions() {
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

}

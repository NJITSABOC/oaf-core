package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.node.NodeHierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.ConceptHierarchy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

/**
 *
 * @author Chris O
 */
public class PAreaTaxonomyGenerator {
    
    /**
     * Builds a partial-area taxonomy from the given source hierarchy. 
     * 
     * This process is done by first initializing temporary data structures
     * with the area/parea/parea taxonomy dependencies and then 
     * injecting them into the proper data structures at the end.
     * 
     * @param factory
     * @param sourceHierarchy
     * @return 
     */
    public PAreaTaxonomy derivePAreaTaxonomy(
            final PAreaTaxonomyFactory factory, 
            final ConceptHierarchy<Concept> sourceHierarchy) {
        
        HashMap<Concept, Set<InheritableProperty>> conceptRelationships = new HashMap<>();

        Set<Concept> concepts = sourceHierarchy.getConceptsInHierarchy();
        
        concepts.forEach((concept) -> {
            conceptRelationships.put(concept, factory.getRelationships(concept));
        });
        
        Set<Concept> pareaRoots = new HashSet<>();

        for (Concept concept : concepts) {
            Set<Concept> parents = sourceHierarchy.getParents(concept);

            Set<InheritableProperty> rels = conceptRelationships.get(concept);

            boolean equalsParent = false;

            for (Concept parent : parents) {
                Set<InheritableProperty> parentRels = conceptRelationships.get(parent);
                
                if (rels.equals(parentRels)) {
                    equalsParent = true;
                    break;
                }
            }

            if (parents.isEmpty() || !equalsParent) {
                pareaRoots.add(concept);
            }
        }

        HashMap<Concept, ConceptHierarchy<Concept>> pareaConceptHierarchy = new HashMap<>();
        
        HashMap<Concept, Set<Concept>> parentPAreaRoots = new HashMap<>();
        HashMap<Concept, Set<Concept>> childPAreaRoots = new HashMap<>();

        // Initialize the partial-area data structures
        pareaRoots.forEach( (root) -> {
            pareaConceptHierarchy.put(root, new ConceptHierarchy(root));
            
            parentPAreaRoots.put(root, new HashSet<>());
            childPAreaRoots.put(root, new HashSet<>());
        });

        HashMap<Concept, HashSet<Concept>> conceptPAreas = new HashMap<>();

        Stack<Concept> stack = new Stack<>();
        
        // For all of the roots, find the concepts in the associated partial area. Establish CHILD OF links.
        pareaRoots.forEach( (root) -> {
            stack.add(root);
            
            HashSet<Concept> processedConcepts = new HashSet<>();
            processedConcepts.add(root);
            
            while (!stack.isEmpty()) {
                Concept concept = stack.pop();
                processedConcepts.add(concept);

                ConceptHierarchy<Concept> pareaHierarchy = pareaConceptHierarchy.get(root);

                if (!conceptPAreas.containsKey(concept)) {
                    conceptPAreas.put(concept, new HashSet<>());
                }

                conceptPAreas.get(concept).add(root);

                Set<Concept> children = sourceHierarchy.getChildren(concept);

                children.forEach( (child) -> {
                    if (pareaRoots.contains(child)) {
                        parentPAreaRoots.get(child).add(root);
                        childPAreaRoots.get(root).add(child);
                    } else {
                        if(conceptRelationships.get(root).equals(conceptRelationships.get(child))) {
                            if(!processedConcepts.contains(child) && !stack.contains(child)) {
                                stack.add(child);
                            }
                            
                            pareaHierarchy.addIsA(child, concept);
                        }
                    }
                    
                });
                
            } // end while
        });
        
        Set<PArea> pareas = new HashSet<>();
        
        HashMap<Set<InheritableProperty>, Set<PArea>> pareasByRelationships = new HashMap<>();
        HashMap<Concept, PArea> pareasByRoot = new HashMap<>();

        PArea rootPArea = null;

        for (Concept root : pareaRoots) {
            PArea parea = new PArea(pareaConceptHierarchy.get(root), conceptRelationships.get(root));

            if (root.equals(sourceHierarchy.getRoot())) {
                rootPArea = parea;
            }
            
            if(!pareasByRelationships.containsKey(parea.getRelationships())) {
                pareasByRelationships.put(parea.getRelationships(), new HashSet<>());
            }
            
            pareasByRoot.put(root, parea);
            pareasByRelationships.get(parea.getRelationships()).add(parea);
            
            pareas.add(parea);
        }
                
        NodeHierarchy<PArea> pareaHierarchy = new NodeHierarchy<>(rootPArea);
        
        pareas.forEach((parea) -> {
            Concept root = parea.getHierarchy().getRoot();

            Set<Concept> parents = sourceHierarchy.getParents(root);

            parents.forEach((parent) -> {
                Set<Concept> parentPAreas = conceptPAreas.get(parent);

                parentPAreas.forEach((parentPAreaRoot) -> {
                    pareaHierarchy.addIsA(parea, pareasByRoot.get(parentPAreaRoot));
                });
            });
        });
        
        Set<Area> areas = new HashSet<>();
        
        Area rootArea = null;
        
        HashMap<Set<InheritableProperty>, Area> areasByRelationships = new HashMap<>();
        
        for(Entry<Set<InheritableProperty>, Set<PArea>> entry : pareasByRelationships.entrySet()) {
            Area area = new Area(entry.getValue(), entry.getKey());
            
            areas.add(area);
            
            if(area.getPAreas().contains(rootPArea)) {
                rootArea = area;
            }
            
            areasByRelationships.put(area.getRelationships(), area);
        }
        
        NodeHierarchy<Area> areaHierarchy = new NodeHierarchy<>(rootArea);
        
        areas.forEach((area) -> {
            if (!area.equals(areaHierarchy.getRoot())) {
                area.getPAreas().forEach((parea) -> {
                    Area parentArea = areasByRelationships.get(parea.getRelationships());
                    areaHierarchy.addIsA(area, parentArea);
                });
            }
        });
        
        AreaTaxonomy areaTaxonomy = new AreaTaxonomy(areaHierarchy, sourceHierarchy);
        PAreaTaxonomy pareaTaxonomy = new PAreaTaxonomy(areaTaxonomy, pareaHierarchy, sourceHierarchy);

        return pareaTaxonomy;
    }
    
    public PAreaTaxonomy createTaxonomyFromPAreas(NodeHierarchy<PArea> pareaHierarchy) {
                     
        HashMap<Set<InheritableProperty>, Set<PArea>> pareasByRelationships = new HashMap<>();
        
        ConceptHierarchy<Concept> conceptHierarchy = new ConceptHierarchy<>(pareaHierarchy.getRoot().getRoot());
                
        pareaHierarchy.getNodesInHierarchy().forEach( (parea) -> {
            Set<InheritableProperty> properties = parea.getRelationships();
            
            if(!pareasByRelationships.containsKey(properties)) {
                pareasByRelationships.put(properties, new HashSet<>());
            }
            
            pareasByRelationships.get(properties).add(parea);
            
            conceptHierarchy.addAllHierarchicalRelationships(parea.getHierarchy());
        });
        
        HashMap<Set<InheritableProperty>, Area> areasByRelationships = new HashMap<>();
        
        Area rootArea = null;
        
        for (Entry<Set<InheritableProperty>, Set<PArea>> entry : pareasByRelationships.entrySet()) {
            Area area = new Area(entry.getValue(), entry.getKey());

            if (area.getPAreas().contains(pareaHierarchy.getRoot())) {
                rootArea = area;
            }

            areasByRelationships.put(area.getRelationships(), area);
        }
        
        NodeHierarchy<Area> areaHierarchy = new NodeHierarchy<>(rootArea);
 
        areasByRelationships.values().forEach((area) -> {
            if (!area.equals(areaHierarchy.getRoot())) {
                area.getPAreas().forEach((parea) -> {
                    Area parentArea = areasByRelationships.get(parea.getRelationships());
                    areaHierarchy.addIsA(area, parentArea);
                });
            }
        });
        
        AreaTaxonomy areaTaxonomy = new AreaTaxonomy(areaHierarchy, conceptHierarchy);
        PAreaTaxonomy pareaTaxonomy = new PAreaTaxonomy(areaTaxonomy, pareaHierarchy, conceptHierarchy);
   
        return pareaTaxonomy;
    }
}

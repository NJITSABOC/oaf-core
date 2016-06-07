package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.GroupHierarchy;
import edu.njit.cs.saboc.blu.core.abn.ParentNodeInformation;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateableConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.node.NodeHierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.SingleRootedConceptHierarchy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 *
 * @author Chris O
 */
public abstract class PAreaTaxonomyGenerator {
    
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
            final SingleRootedConceptHierarchy sourceHierarchy) {
        
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

        HashMap<Concept, SingleRootedConceptHierarchy> pareaConceptHierarchy = new HashMap<>();
        
        HashMap<Concept, Set<Concept>> parentPAreaRoots = new HashMap<>();
        HashMap<Concept, Set<Concept>> childPAreaRoots = new HashMap<>();

        // Initialize the partial-area data structures
        pareaRoots.forEach( (root) -> {
            pareaConceptHierarchy.put(root, new SingleRootedConceptHierarchy(root));
            
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

                SingleRootedConceptHierarchy pareaHierarchy = pareaConceptHierarchy.get(root);

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

        PArea rootPArea = null;

        for (Concept root : pareaRoots) {
            PArea parea = new PArea(pareaConceptHierarchy.get(root), conceptRelationships.get(root));

            if (root.equals(sourceHierarchy.getRoot())) {
                rootPArea = parea;
            }
            
            if(!pareasByRelationships.containsKey(parea.getRelationships())) {
                pareasByRelationships.put(parea.getRelationships(), new HashSet<>());
            }
            
            pareasByRelationships.get(parea.getRelationships()).add(parea);
            
            pareas.add(parea);
        }
                
        NodeHierarchy<PArea> pareaHierarchy = new NodeHierarchy<>(rootPArea);
        
        for(PArea parea : pareas) {
            Concept root = parea.getHierarchy().getRoot();
            
            Set<Concept> parents = sourceHierarchy.getParents(root);

            
            for(Concept parent : parents) {
                Set<Concept> parentPAreas = conceptPAreas.get(parent);
                
                for(Concept parentPArea : parentPAreas) {
                    
                    //pareaHierarchy.addIsA(parea, pareas.get(parentPAreaId));
                }
            }
        }
        
        final PArea finalRootPArea = rootPArea;
        
        Set<Area> areas = new HashSet<>();
        
        Area rootArea = null;
        
        pareasByRelationships.forEach( (rels, pareasWithRels) -> {
            areas.add(new Area(pareasWithRels, rels));
            

        });
        
        NodeHierarchy<Area> areaHierarchy;
        
        

        return createPAreaTaxonomy(conceptHierarchy, rootPArea, areas, pareas, pareaHierarchy);
    }
    
    protected abstract HIERARCHY_T getConceptHierarchy();
    
    protected abstract RelationshipEquality<REL_T> getRelationshipEquality();
    
    protected abstract HashSet<REL_T> getDefiningConceptRelationships(CONCEPT_T concept);
    
    protected abstract HIERARCHY_T initPAreaConceptHierarchy(CONCEPT_T root);
    
    protected abstract PAREA_T createPArea(int id, HIERARCHY_T pareaHierarchy, HashSet<Integer> parentIds, 
             HashSet<REL_T> relationships);
    
    protected abstract AREA_T createArea(int id, HashSet<REL_T> relationships);
    
    protected abstract TAXONOMY_T createPAreaTaxonomy(
            HIERARCHY_T conceptHierarchy, PAREA_T rootPArea, 
            ArrayList<AREA_T> areas, HashMap<Integer, PAREA_T> pareas, 
            GroupHierarchy<PAREA_T> pareaHierarchy);
    
    public TAXONOMY_T createTaxonomyFromPAreas(
            HashMap<Integer, PAREA_T> pareas, 
            GroupHierarchy<PAREA_T> pareaHierarchy) {
        
        ArrayList<AREA_T> subhierarchyAreas = new ArrayList<AREA_T>();
             
        int areaId = 0;
        
        HashMap<HashSet<REL_T>, AREA_T> areaMap = new HashMap<HashSet<REL_T>, AREA_T>();
        
        for(PAREA_T parea : pareas.values()) {
            AREA_T area;
            
            if(!areaMap.containsKey(parea.getRelsWithoutInheritanceInfo())) {
                area = (AREA_T)createArea(areaId++, parea.getRelsWithoutInheritanceInfo());
                
                areaMap.put(area.getRelationships(), area);
                
                subhierarchyAreas.add(area);
            } else {
                area = areaMap.get(parea.getRelsWithoutInheritanceInfo());
            }
            
            area.addPArea(parea);
            
            HashSet<ParentNodeInformation<CONCEPT_T, PAREA_T>> reducedParentInfo = new HashSet<ParentNodeInformation<CONCEPT_T, PAREA_T>>();
            
            PAREA_T originalRoot;
            
            if(parea instanceof AggregateableConceptGroup) {
                originalRoot = (PAREA_T)((AggregateableConceptGroup)parea).getAggregatedGroupHierarchy().getRoots().iterator().next();
            } else {
                originalRoot = parea;
            }

            HashSet<ParentNodeInformation<CONCEPT_T, PAREA_T>> originalParents = originalRoot.getParentPAreaInfo();
                        
            for(ParentNodeInformation<CONCEPT_T, PAREA_T> originalParent : originalParents) {
                if(pareas.containsKey(originalParent.getParentGroup().getId())) {
                    reducedParentInfo.add(new ParentNodeInformation<CONCEPT_T, PAREA_T>(originalParent.getParentConcept(), 
                            pareas.get(originalParent.getParentGroup().getId())));
                } else {
                    for(PAREA_T otherPArea : pareas.values()) {
                        
                        if (otherPArea instanceof AggregateableConceptGroup) {
                            AggregateableConceptGroup aggregateGroup = (AggregateableConceptGroup) otherPArea;

                            if (aggregateGroup.getAllGroupsConcepts().contains(originalParent.getParentConcept())) {
                                reducedParentInfo.add(new ParentNodeInformation<CONCEPT_T, PAREA_T>(originalParent.getParentConcept(), otherPArea));
                                break;
                            }
                        }
                        
                    }
                }
            }

            parea.setParentPAreaInfo(reducedParentInfo);
        }

        TAXONOMY_T reducedTaxonomy = (TAXONOMY_T)createPAreaTaxonomy(getConceptHierarchy(), 
                pareaHierarchy.getRoots().iterator().next(), 
                subhierarchyAreas, 
                pareas, 
                pareaHierarchy);
        
        return reducedTaxonomy;
    }
}

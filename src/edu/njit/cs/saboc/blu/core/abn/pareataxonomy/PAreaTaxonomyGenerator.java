package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.GenericParentGroupInfo;
import edu.njit.cs.saboc.blu.core.abn.GroupHierarchy;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateableConceptGroup;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 *
 * @author Chris O
 */
public abstract class PAreaTaxonomyGenerator<
        TAXONOMY_T extends GenericPAreaTaxonomy<TAXONOMY_T, PAREA_T, AREA_T, REGION_T, CONCEPT_T, REL_T, HIERARCHY_T>,
        PAREA_T extends GenericPArea<CONCEPT_T, REL_T, HIERARCHY_T, PAREA_T>,
        AREA_T extends GenericArea<CONCEPT_T, REL_T, HIERARCHY_T, PAREA_T, REGION_T>,
        REGION_T extends GenericRegion<CONCEPT_T, REL_T, HIERARCHY_T, PAREA_T>,
        CONCEPT_T, // Concept type
        REL_T,// Relationship type
        HIERARCHY_T extends SingleRootedHierarchy<CONCEPT_T, HIERARCHY_T>> {
    
    public TAXONOMY_T derivePAreaTaxonomy() {
        
        HashMap<CONCEPT_T, HashSet<REL_T>> conceptRelationships = new HashMap<CONCEPT_T, HashSet<REL_T>>();
        
        HIERARCHY_T conceptHierarchy = this.getConceptHierarchy();
        
        RelationshipEquality<REL_T> relEquality = this.getRelationshipEquality();

        HashSet<CONCEPT_T> concepts = conceptHierarchy.getNodesInHierarchy();
        
        CONCEPT_T hierarchyRoot = conceptHierarchy.getRoot();

        // Initialize relationships
        for (CONCEPT_T concept : concepts) {
            conceptRelationships.put(concept, new HashSet<REL_T>());
        }

        // Get each concept's defining relationships
        for (CONCEPT_T concept : concepts) {
            conceptRelationships.put(concept, this.getDefiningConceptRelationships(concept));
        }
        
        HashSet<CONCEPT_T> partialAreaRoots = new HashSet<CONCEPT_T>();

        for (CONCEPT_T concept : concepts) {
            Set<CONCEPT_T> parents = conceptHierarchy.getParents(concept);

            HashSet<REL_T> rels = conceptRelationships.get(concept);

            boolean equalsParent = false;

            for (CONCEPT_T parent : parents) {
                if (relEquality.equalsNoInheritance(rels, conceptRelationships.get(parent))) {
                    equalsParent = true;
                    break;
                }
            }

            if (parents.isEmpty() || !equalsParent) {
                partialAreaRoots.add(concept);
            }
        }

        int pareaId = 0;

        // Partial area collections
        HashMap<CONCEPT_T, Integer> partialAreaIds = new HashMap<CONCEPT_T, Integer>();
        HashMap<CONCEPT_T, HIERARCHY_T> partialAreas = new HashMap<CONCEPT_T, HIERARCHY_T>();
        HashMap<CONCEPT_T, Set<CONCEPT_T>> parentPartialAreas = new HashMap<CONCEPT_T, Set<CONCEPT_T>>();
        HashMap<CONCEPT_T, Set<CONCEPT_T>> childPartialAreas = new HashMap<CONCEPT_T, Set<CONCEPT_T>>();

        // Initialize them
        for (CONCEPT_T root : partialAreaRoots) {
            partialAreaIds.put(root, pareaId++);
            
            partialAreas.put(root, initPAreaConceptHierarchy(root));
            parentPartialAreas.put(root, new HashSet<>());
            childPartialAreas.put(root, new HashSet<>());
        }

        HashMap<CONCEPT_T, HashSet<CONCEPT_T>> conceptPAreas = new HashMap<>();

        Stack<CONCEPT_T> stack = new Stack<>();
        
        // For all of the roots, find the concepts in the associated partial area. Establish CHILD OF links.
        for (CONCEPT_T root : partialAreaRoots) {
            stack.add(root);
            
            HashSet<CONCEPT_T> processedConcepts = new HashSet<>();
            processedConcepts.add(root);
            
            while (!stack.isEmpty()) {
                CONCEPT_T concept = stack.pop();
                processedConcepts.add(concept);

                HIERARCHY_T pareaHierarchy = partialAreas.get(root);

                if (!conceptPAreas.containsKey(concept)) {
                    conceptPAreas.put(concept, new HashSet<>());
                }

                conceptPAreas.get(concept).add(root);

                Set<CONCEPT_T> children = conceptHierarchy.getChildren(concept);

                for (CONCEPT_T child : children) {
                    if (partialAreaRoots.contains(child)) {
                        parentPartialAreas.get(child).add(root);
                        childPartialAreas.get(root).add(child);
                    } else {
                        if(relEquality.equalsNoInheritance(conceptRelationships.get(root), conceptRelationships.get(child))) {
                            if(!processedConcepts.contains(child) && !stack.contains(child)) {
                                stack.add(child);
                            }
                            
                            pareaHierarchy.addIsA(child, concept);
                        }
                    }
                    
                } // end for each
                
            } // end while
            
        } // For each root
        
        HashMap<Integer, PAREA_T> pareas = new HashMap<Integer, PAREA_T>();

        ArrayList<AREA_T> areas = new ArrayList<AREA_T>();

        int areaId = 0;

        PAREA_T rootPArea = null;

        for (CONCEPT_T root : partialAreaRoots) {
            int id = partialAreaIds.get(root);

            HashSet<Integer> parentIds = new HashSet<Integer>();

            Set<CONCEPT_T> parentPAreas = parentPartialAreas.get(root);

            for (CONCEPT_T parentPArea : parentPAreas) {
                parentIds.add(partialAreaIds.get(parentPArea));
            }

            Set<CONCEPT_T> childPAreas = childPartialAreas.get(root);

            HashSet<Integer> childIds = new HashSet<Integer>();

            for (CONCEPT_T childPArea : childPAreas) {
                childIds.add(partialAreaIds.get(childPArea));
            }

            PAREA_T parea = createPArea(id, partialAreas.get(root), parentIds, conceptRelationships.get(root));

            if (root.equals(hierarchyRoot)) {
                rootPArea = parea;
            }

            pareas.put(id, parea);

            boolean areaFound = false;

            for (AREA_T area : areas) {
                if (relEquality.equalsNoInheritance(area.getRelationships(), parea.getRelationships())) {
                    area.addPArea(parea);
                    areaFound = true;
                    break;
                }
            }

            if (!areaFound) {
                AREA_T area = createArea(areaId++, parea.getRelsWithoutInheritanceInfo());

                area.addPArea(parea);
                areas.add(area);
            }
        }
        
        GroupHierarchy<PAREA_T> pareaHierarchy = new GroupHierarchy<>(rootPArea);
        
        for(PAREA_T parea : pareas.values()) {
            CONCEPT_T root = parea.getHierarchy().getRoot();
            
            HashSet<CONCEPT_T> parents = conceptHierarchy.getParents(root);
            
            HashSet<GenericParentGroupInfo<CONCEPT_T, PAREA_T>> parentPAreaInfo = new HashSet<GenericParentGroupInfo<CONCEPT_T, PAREA_T>>();
            
            for(CONCEPT_T parent : parents) {
                HashSet<CONCEPT_T> parentPAreaRoots = conceptPAreas.get(parent);
                
                for(CONCEPT_T parentPAreaRoot : parentPAreaRoots) {
                    int parentPAreaId = partialAreaIds.get(parentPAreaRoot);
                    
                    pareaHierarchy.addIsA(parea, pareas.get(parentPAreaId));
                    
                    parentPAreaInfo.add(new GenericParentGroupInfo<CONCEPT_T, PAREA_T>(parent, pareas.get(parentPAreaId)));
                }
            }
            
            parea.setParentPAreaInfo(parentPAreaInfo);
        }

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
            
            HashSet<GenericParentGroupInfo<CONCEPT_T, PAREA_T>> reducedParentInfo = new HashSet<GenericParentGroupInfo<CONCEPT_T, PAREA_T>>();
            
            PAREA_T originalRoot;
            
            if(parea instanceof AggregateableConceptGroup) {
                originalRoot = (PAREA_T)((AggregateableConceptGroup)parea).getAggregatedGroupHierarchy().getRoots().iterator().next();
            } else {
                originalRoot = parea;
            }

            HashSet<GenericParentGroupInfo<CONCEPT_T, PAREA_T>> originalParents = originalRoot.getParentPAreaInfo();
                        
            for(GenericParentGroupInfo<CONCEPT_T, PAREA_T> originalParent : originalParents) {
                if(pareas.containsKey(originalParent.getParentGroup().getId())) {
                    reducedParentInfo.add(new GenericParentGroupInfo<CONCEPT_T, PAREA_T>(originalParent.getParentConcept(), 
                            pareas.get(originalParent.getParentGroup().getId())));
                } else {
                    for(PAREA_T otherPArea : pareas.values()) {
                        
                        if (otherPArea instanceof AggregateableConceptGroup) {
                            AggregateableConceptGroup aggregateGroup = (AggregateableConceptGroup) otherPArea;

                            if (aggregateGroup.getAllGroupsConcepts().contains(originalParent.getParentConcept())) {
                                reducedParentInfo.add(new GenericParentGroupInfo<CONCEPT_T, PAREA_T>(originalParent.getParentConcept(), otherPArea));
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

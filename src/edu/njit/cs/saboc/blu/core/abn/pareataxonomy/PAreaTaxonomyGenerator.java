package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

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
        PAREA_T extends GenericPArea<CONCEPT_T, REL_T>,
        AREA_T extends GenericArea<CONCEPT_T, REL_T, PAREA_T, REGION_T>,
        REGION_T extends GenericRegion<CONCEPT_T, REL_T, PAREA_T>,
        CONCEPT_T, // Concept type
        REL_T> // Relationship type

{
    
    public GenericPAreaTaxonomy<PAREA_T, AREA_T, REGION_T, CONCEPT_T, REL_T> derivePAreaTaxonomy() {
        
        HashMap<CONCEPT_T, HashSet<REL_T>> conceptRelationships = new HashMap<CONCEPT_T, HashSet<REL_T>>();
        
        SingleRootedHierarchy<CONCEPT_T> conceptHierarchy = this.getConceptHierarchy();

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

            Set<REL_T> rels = conceptRelationships.get(concept);

            boolean equalsParent = false;

            for (CONCEPT_T parent : parents) {
                if (rels.equals(conceptRelationships.get(parent))) {
                    
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
        
        HashMap<CONCEPT_T, SingleRootedHierarchy<CONCEPT_T>> partialAreas = new HashMap<CONCEPT_T, SingleRootedHierarchy<CONCEPT_T>>();
        
        HashMap<CONCEPT_T, Set<CONCEPT_T>> parentPartialAreas = new HashMap<CONCEPT_T, Set<CONCEPT_T>>();
        HashMap<CONCEPT_T, Set<CONCEPT_T>> childPartialAreas = new HashMap<CONCEPT_T, Set<CONCEPT_T>>();

        // Initialize them
        for (CONCEPT_T root : partialAreaRoots) {
            partialAreaIds.put(root, pareaId++);
            
            partialAreas.put(root, initPAreaConceptHierarchy(root));
            parentPartialAreas.put(root, new HashSet<CONCEPT_T>());
            childPartialAreas.put(root, new HashSet<CONCEPT_T>());
        }

        HashMap<CONCEPT_T, HashSet<CONCEPT_T>> conceptPAreas = new HashMap<CONCEPT_T, HashSet<CONCEPT_T>>();

        Stack<CONCEPT_T> stack = new Stack<CONCEPT_T>();

        // For all of the roots, find the concepts in the associated partial area. Establish CHILD OF links.
        for (CONCEPT_T root : partialAreaRoots) {
            stack.add(root);

            while (!stack.isEmpty()) {
                CONCEPT_T concept = stack.pop();

                SingleRootedHierarchy<CONCEPT_T> pareaHierarchy = partialAreas.get(root);

                if (!conceptPAreas.containsKey(concept)) {
                    conceptPAreas.put(concept, new HashSet<CONCEPT_T>());
                }

                conceptPAreas.get(concept).add(root);

                Set<CONCEPT_T> children = conceptHierarchy.getChildren(concept);

                for (CONCEPT_T child : children) {
                    if (partialAreaRoots.contains(child)) {
                        
                        parentPartialAreas.get(child).add(root);
                        childPartialAreas.get(root).add(child);
                        
                    } else {
                        if (!stack.contains(child) && conceptRelationships.get(root).equals(conceptRelationships.get(child))) {
                            
                            pareaHierarchy.addIsA(child, concept);
                            
                            stack.add(child);
                        }
                    }
                    
                } // end for each
                
            } // end while
            
        } // For each root

        HashMap<Integer, PAREA_T> pareas = new HashMap<Integer, PAREA_T>();

        HashMap<Integer, HashSet<Integer>> pareaHierarchy = new HashMap<Integer, HashSet<Integer>>();

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

            pareaHierarchy.put(id, childIds);

            PAREA_T parea = createPArea(id, partialAreas.get(root), parentIds, conceptRelationships.get(root));

            if (root.equals(hierarchyRoot)) {
                rootPArea = parea;
            }

            pareas.put(id, parea);

            boolean areaFound = false;

            for (AREA_T area : areas) {
                if (area.getRelationships().equals(parea.getRelsWithoutInheritanceInfo())) {
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

        return createPAreaTaxonomy(conceptHierarchy, rootPArea, areas, pareas, pareaHierarchy);
    }
    
    protected abstract SingleRootedHierarchy<CONCEPT_T> getConceptHierarchy();
    
    protected abstract HashSet<REL_T> getDefiningConceptRelationships(CONCEPT_T concept);
    
    protected abstract SingleRootedHierarchy<CONCEPT_T> initPAreaConceptHierarchy(CONCEPT_T root);
    
    protected abstract PAREA_T createPArea(int id, SingleRootedHierarchy<CONCEPT_T> pareaHierarchy, HashSet<Integer> parentIds, HashSet<REL_T> relationships);
    
    protected abstract AREA_T createArea(int id, HashSet<REL_T> relationships);
    
    protected abstract GenericPAreaTaxonomy<PAREA_T, AREA_T, REGION_T, CONCEPT_T, REL_T> createPAreaTaxonomy(
            SingleRootedHierarchy<CONCEPT_T> conceptHierarchy, PAREA_T rootPArea, 
            ArrayList<AREA_T> areas, HashMap<Integer, PAREA_T> pareas, 
            HashMap<Integer, HashSet<Integer>> pareaHierarchy);
}

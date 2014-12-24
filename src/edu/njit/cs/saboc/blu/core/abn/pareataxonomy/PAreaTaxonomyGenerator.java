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
        P extends GenericPArea<T, R>,
        A extends GenericArea<T, R, P, REGION_T>,
        REGION_T extends GenericRegion<T, R, P>,
        T, // Concept type
        R> // Relationship type

{
    
    public GenericPAreaTaxonomy<P, A, REGION_T, T, R> derivePAreaTaxonomy() {
        
        HashMap<T, HashSet<R>> conceptRelationships = new HashMap<T, HashSet<R>>();
        
        SingleRootedHierarchy<T> conceptHierarchy = this.getConceptHierarchy();

        HashSet<T> concepts = conceptHierarchy.getNodesInHierarchy();
        
        T hierarchyRoot = conceptHierarchy.getRoot();

        // Initialize relationships
        for (T concept : concepts) {
            conceptRelationships.put(concept, new HashSet<R>());
        }

        // Get each concept's defining relationships
        for (T concept : concepts) {
            conceptRelationships.put(concept, this.getDefiningConceptRelationships(concept));
        }
        
        HashSet<T> partialAreaRoots = new HashSet<T>();

        for (T concept : concepts) {
            Set<T> parents = conceptHierarchy.getParents(concept);

            Set<R> rels = conceptRelationships.get(concept);

            boolean equalsParent = false;

            for (T parent : parents) {
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
        HashMap<T, Integer> partialAreaIds = new HashMap<T, Integer>();
        
        HashMap<T, SingleRootedHierarchy<T>> partialAreas = new HashMap<T, SingleRootedHierarchy<T>>();
        
        HashMap<T, Set<T>> parentPartialAreas = new HashMap<T, Set<T>>();
        HashMap<T, Set<T>> childPartialAreas = new HashMap<T, Set<T>>();

        // Initialize them
        for (T root : partialAreaRoots) {
            partialAreaIds.put(root, pareaId++);
            
            partialAreas.put(root, initPAreaConceptHierarchy(root));
            parentPartialAreas.put(root, new HashSet<T>());
            childPartialAreas.put(root, new HashSet<T>());
        }

        HashMap<T, HashSet<T>> conceptPAreas = new HashMap<T, HashSet<T>>();

        Stack<T> stack = new Stack<T>();

        // For all of the roots, find the concepts in the associated partial area. Establish CHILD OF links.
        for (T root : partialAreaRoots) {
            stack.add(root);

            while (!stack.isEmpty()) {
                T concept = stack.pop();

                SingleRootedHierarchy<T> pareaHierarchy = partialAreas.get(root);

                if (!conceptPAreas.containsKey(concept)) {
                    conceptPAreas.put(concept, new HashSet<T>());
                }

                conceptPAreas.get(concept).add(root);

                Set<T> children = conceptHierarchy.getChildren(concept);

                for (T child : children) {
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

        HashMap<Integer, P> pareas = new HashMap<Integer, P>();

        HashMap<Integer, HashSet<Integer>> pareaHierarchy = new HashMap<Integer, HashSet<Integer>>();

        ArrayList<A> areas = new ArrayList<A>();

        int areaId = 0;

        P rootPArea = null;

        for (T root : partialAreaRoots) {
            int id = partialAreaIds.get(root);

            HashSet<Integer> parentIds = new HashSet<Integer>();

            Set<T> parentPAreas = parentPartialAreas.get(root);

            for (T parentPArea : parentPAreas) {
                parentIds.add(partialAreaIds.get(parentPArea));
            }

            Set<T> childPAreas = childPartialAreas.get(root);

            HashSet<Integer> childIds = new HashSet<Integer>();

            for (T childPArea : childPAreas) {
                childIds.add(partialAreaIds.get(childPArea));
            }

            pareaHierarchy.put(id, childIds);

            P parea = createPArea(id, partialAreas.get(root), parentIds, conceptRelationships.get(root));

            if (root.equals(hierarchyRoot)) {
                rootPArea = parea;
            }

            pareas.put(id, parea);

            boolean areaFound = false;

            for (A area : areas) {
                if (area.getRelationships().equals(parea.getRelsWithoutInheritanceInfo())) {
                    area.addPArea(parea);
                    areaFound = true;
                    break;
                }
            }

            if (!areaFound) {
                A area = createArea(areaId++, parea.getRelsWithoutInheritanceInfo());

                area.addPArea(parea);
                areas.add(area);
            }
        }

        return createPAreaTaxonomy(conceptHierarchy, rootPArea, areas, pareas, pareaHierarchy);
    }
    
    protected abstract SingleRootedHierarchy<T> getConceptHierarchy();
    
    protected abstract HashSet<R> getDefiningConceptRelationships(T concept);
    
    protected abstract SingleRootedHierarchy<T> initPAreaConceptHierarchy(T root);
    
    protected abstract P createPArea(int id, SingleRootedHierarchy<T> pareaHierarchy, HashSet<Integer> parentIds, HashSet<R> relationships);
    
    protected abstract A createArea(int id, HashSet<R> relationships);
    
    protected abstract GenericPAreaTaxonomy<P, A, REGION_T, T, R> createPAreaTaxonomy(
            SingleRootedHierarchy<T> conceptHierarchy, P rootPArea, 
            ArrayList<A> areas, HashMap<Integer, P> pareas, 
            HashMap<Integer, HashSet<Integer>> pareaHierarchy);
}

package edu.njit.cs.saboc.blu.core.abn.targetbased;

import edu.njit.cs.saboc.blu.core.abn.node.NodeHierarchy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.ConceptHierarchy;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 *
 * @author Chris O
 */
public abstract class TargetAbstractionNetworkGenerator {
    
    public TargetAbstractionNetwork deriveTargetAbstractionNetwork(
            TargetAbstractionNetworkFactory factory,
            ConceptHierarchy sourceHierarchy, 
            InheritableProperty relationshipType, 
            ConceptHierarchy targetHierarchy) {
        
        Set<InheritableProperty> relTypes = new HashSet<>();
        relTypes.add(relationshipType);
        
        return this.deriveTargetAbstractionNetwork(factory, sourceHierarchy, relTypes, targetHierarchy);
    }
    
    public TargetAbstractionNetwork deriveTargetAbstractionNetwork(
            TargetAbstractionNetworkFactory factory,
            ConceptHierarchy sourceHierarchy, 
            Set<InheritableProperty> relationshipTypes, 
            ConceptHierarchy targetHierarchy) {
        
        Map<Concept, Set<RelationshipTriple>> relationshipsToTargetHierarchy = new HashMap<>();
        
        Map<Concept, Set<RelationshipTriple>> relationshipsFromSourceHierarchy = new HashMap<>();
        
        Set<Concept> uniqueTargets = new HashSet<>();
        
        sourceHierarchy.getConceptsInHierarchy().forEach( (concept) -> {
            Set<RelationshipTriple> relationships = factory.getRelationshipsToTargetHierarchyFor(concept, relationshipTypes, targetHierarchy);
            
            relationshipsToTargetHierarchy.put(concept, relationships);
            
            relationships.forEach( (rel) -> {
                uniqueTargets.add(rel.getTarget());
                
                if(!relationshipsFromSourceHierarchy.containsKey(rel.getTarget())) {
                    relationshipsFromSourceHierarchy.put(rel.getTarget(), new HashSet<>());
                }
                
                relationshipsFromSourceHierarchy.get(rel.getTarget()).add(rel);
            });
        });

        Map<Concept, Set<Concept>> lowestNontargetAncestors = getLowestNonTargetAncestor(uniqueTargets, targetHierarchy);
        
        Set<Concept> targetGroupRoots = new HashSet<>(Collections.singleton(targetHierarchy.getRoot()));
        
        lowestNontargetAncestors.values().forEach((lowestNontargetConcepts) -> {
            targetGroupRoots.addAll(lowestNontargetConcepts);
        });
       
        // The target groups a concept belongs to
        HashMap<Concept, Set<Concept>> conceptsGroups = new HashMap<>();

        // The subhierarchy of (all!) concepts summarized by a target group, includes non-targets
        HashMap<Concept, ConceptHierarchy> conceptsInGroup = new HashMap<>();
        
        HashMap<Concept, Integer> parentCounts = new HashMap<>();

        HashMap<Concept, Set<RelationshipTriple>> groupIncomingRelationships = new HashMap<>();
        
        for (Concept concept : targetHierarchy.getConceptsInHierarchy()) {
            conceptsGroups.put(concept, new HashSet<>());
            
            parentCounts.put(concept, targetHierarchy.getParents(concept).size());
            
            groupIncomingRelationships.put(concept, new HashSet<>());

            if (targetGroupRoots.contains(concept)) {
                conceptsGroups.get(concept).add(concept);

                conceptsInGroup.put(concept, new ConceptHierarchy(concept));
            }
        }

        Queue<Concept> queue = new LinkedList<>();
        
        queue.add(targetHierarchy.getRoot()); // Start from the root of the hierarchy...

        while (!queue.isEmpty()) {
            Concept concept = queue.remove();

            Set<Concept> parents = targetHierarchy.getParents(concept);
            
            // Add concepts to the target group. These may be targets or not.
            if (!targetGroupRoots.contains(concept)) {
                parents.forEach((parent) -> {
                    Set<Concept> parentGroups = conceptsGroups.get(parent);

                    conceptsGroups.get(concept).addAll(parentGroups);

                    parentGroups.forEach((parentGroupRoot) -> {
                        conceptsInGroup.get(parentGroupRoot).addIsA(concept, parent);
                    });
                });
            }
            
            // Add any incoming relationships
            conceptsGroups.get(concept).forEach( (root) -> {
                groupIncomingRelationships.get(root).addAll(relationshipsFromSourceHierarchy.get(concept));
            });

            Set<Concept> children = targetHierarchy.getChildren(concept);

            children.forEach((child) -> {
                int childParentCount = parentCounts.get(child) - 1;

                if (childParentCount == 0) {
                    queue.add(child);
                } else {
                    parentCounts.put(child, childParentCount);
                }
            });
        }
        
        HashMap<Concept, TargetGroup> targetGroups = new HashMap<>();
        
        // Create the target groups...
        targetGroupRoots.forEach((root) -> {
            targetGroups.put(root, 
                    new TargetGroup(conceptsInGroup.get(root), 
                    new IncomingRelationshipDetails(groupIncomingRelationships.get(root))));
        });
        
        NodeHierarchy<TargetGroup> nodeHierarchy = new NodeHierarchy<>(targetGroups.get(targetHierarchy.getRoot()));
        
        targetGroups.values().forEach( (group) -> {
            Concept root = group.getRoot();
            
            Set<Concept> parents = targetHierarchy.getParents(root);
            
            parents.forEach( (parent) -> {
                Set<Concept> parentGroupRoots = conceptsGroups.get(parent);
                
                parentGroupRoots.forEach( (parentGroupRoot) -> {
                    nodeHierarchy.addIsA(group, targetGroups.get(parentGroupRoot));
                });
            });
        });
        
        return new TargetAbstractionNetwork(nodeHierarchy, targetHierarchy);
    }
    
    private Map<Concept, Set<Concept>> getLowestNonTargetAncestor(Set<Concept> targets, ConceptHierarchy hierarchy) {
        
        Map<Concept, Set<Concept>> lowestNontargetConcepts = new HashMap<>();
        
        for(Concept target : targets) {
            Set<Concept> lowestAncestorsWithoutIncoming = new HashSet<>();
            
            Stack<Concept> stack = new Stack<>();
            
            stack.addAll(hierarchy.getParents(target));
            
            while(!stack.isEmpty()) {
                Concept concept = stack.pop();
                
                if(targets.contains(concept)) {
                    for(Concept parent : hierarchy.getParents(concept)) {
                        if(!stack.contains(parent) && !targets.contains(parent)) {
                            stack.add(parent);
                        }
                    }
                } else {
                    lowestAncestorsWithoutIncoming.add(concept);
                }
            }
            
            lowestNontargetConcepts.put(target, lowestAncestorsWithoutIncoming);
        }
        
        return lowestNontargetConcepts;
    }
}

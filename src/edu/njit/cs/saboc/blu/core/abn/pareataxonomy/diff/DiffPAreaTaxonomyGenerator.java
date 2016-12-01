package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.AbstractionNetworkDiffResult;
import edu.njit.cs.saboc.blu.core.abn.diff.DiffAbstractionNetworkGenerator;
import edu.njit.cs.saboc.blu.core.abn.diff.DiffNode;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.explain.InheritablePropertyChanges;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class DiffPAreaTaxonomyGenerator {

    public DiffPAreaTaxonomy createDiffPAreaTaxonomy(
            DiffPAreaTaxonomyFactory factory,
            Ontology fromOnt, 
            PAreaTaxonomy fromTaxonomy, 
            Ontology toOnt, 
            PAreaTaxonomy toTaxonomy) {
        
        DiffAbstractionNetworkGenerator diffGenerator = new DiffAbstractionNetworkGenerator();
        
        AbstractionNetworkDiffResult areaDiffResult = diffGenerator.diff(fromOnt, fromTaxonomy.getAreaTaxonomy(), toOnt, toTaxonomy.getAreaTaxonomy());
        
        AbstractionNetworkDiffResult pareaDiffResult = diffGenerator.diff(fromOnt, fromTaxonomy, toOnt, toTaxonomy);
        
        Map<DiffNode, DiffPArea> diffPAreas = new HashMap<>();
        
        Map<Set<InheritableProperty>, Set<DiffPArea>> diffPAreasByArea = new HashMap<>();
        
        pareaDiffResult.getDiffNodes().forEach( (diffNode) -> {
            PArea changedPArea = (PArea)diffNode.getChangeDetails().getChangedNode();
            
            DiffPArea diffPArea = new DiffPArea(diffNode, changedPArea);
            
            diffPAreas.put(diffNode, diffPArea);
            
            Set<InheritableProperty> properties = changedPArea.getRelationships();
            
            if(!diffPAreasByArea.containsKey(properties)) {
                diffPAreasByArea.put(properties, new HashSet<>());
            }

            diffPAreasByArea.get(properties).add(diffPArea);
        });
        
        Map<DiffNode, DiffArea> diffAreas = new HashMap<>();
        
        areaDiffResult.getDiffNodes().forEach( (diffNode) -> {
            Area changedArea = (Area)diffNode.getChangeDetails().getChangedNode();

            Set<DiffPArea> diffAreaPAreas = diffPAreasByArea.get(changedArea.getRelationships());
            
            DiffArea diffArea = new DiffArea(
                    diffNode, 
                    changedArea.getRelationships(), 
                    diffAreaPAreas);
            
            diffAreas.put(diffNode, diffArea);
        });
        
        Set<DiffArea> rootAreas = new HashSet<>();
        
        areaDiffResult.getDiffNodeHierarchy().getRoots().forEach( (diffRoot) -> {
            rootAreas.add(diffAreas.get(diffRoot));
        });
        
        Hierarchy<DiffArea> diffAreaHierarchy = new Hierarchy<>(rootAreas);
        
        areaDiffResult.getDiffNodeHierarchy().getEdges().forEach( (diffEdge) -> {
            DiffArea from = diffAreas.get(diffEdge.getFrom());
            DiffArea to = diffAreas.get(diffEdge.getTo());
            
            diffAreaHierarchy.addEdge(from, to);
        });
        
        Set<DiffPArea> rootPAreas = new HashSet<>();
        
        pareaDiffResult.getDiffNodeHierarchy().getRoots().forEach( (diffRoot) -> {
            rootPAreas.add(diffPAreas.get(diffRoot));
        });
        
        Hierarchy<DiffPArea> diffPAreaHierarchy = new Hierarchy<>(rootPAreas);
        
        pareaDiffResult.getDiffNodeHierarchy().getEdges().forEach( (diffEdge) -> {
            DiffPArea from = diffPAreas.get(diffEdge.getFrom());
            DiffPArea to = diffPAreas.get(diffEdge.getTo());
            
            diffPAreaHierarchy.addEdge(from, to);
        });
        
        DiffAreaTaxonomy diffAreaTaxonomy = factory.createDiffAreaTaxonomy(
                (InheritablePropertyChanges)areaDiffResult.getOntologyDifferences(),
                fromTaxonomy.getAreaTaxonomy(), 
                toTaxonomy.getAreaTaxonomy(), 
                diffAreaHierarchy);
        
        DiffPAreaTaxonomy diffPAreaTaxonomy = factory.createDiffPAreaTaxonomy(
                diffAreaTaxonomy, 
                fromTaxonomy, 
                toTaxonomy, 
                diffPAreaHierarchy);
        
        return diffPAreaTaxonomy;
    }
}

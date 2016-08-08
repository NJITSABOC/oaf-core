package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.AbstractionNetworkDiffResult;
import edu.njit.cs.saboc.blu.core.abn.diff.DiffAbstractionNetworkGenerator;
import edu.njit.cs.saboc.blu.core.abn.diff.change.ChangeState;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Chris O
 */
public class DiffPAreaTaxonomyGenerator {

//    public DiffPAreaTaxonomy createDiffPAreaTaxonomy(Ontology fromOnt, PAreaTaxonomy fromTaxonomy, Ontology toOnt, PAreaTaxonomy toTaxonomy) {
//        DiffAbstractionNetworkGenerator diffGenerator = new DiffAbstractionNetworkGenerator();
//        
//        AbstractionNetworkDiffResult areaDiff = diffGenerator.diff(fromOnt, fromTaxonomy.getAreaTaxonomy(), toOnt, toTaxonomy.getAreaTaxonomy());
//        
//        AbstractionNetworkDiffResult pareaDiff = diffGenerator.diff(fromOnt, fromTaxonomy, toOnt, toTaxonomy);
//
//        Map<Node, DiffPArea> diffPAreas = new HashMap<>();
//        
//        pareaDiff.getNodeChanges().forEach((node, changes) -> {
//            ChangeState state = changes.getNodeState();
//            
//            
//        });
//    }
}

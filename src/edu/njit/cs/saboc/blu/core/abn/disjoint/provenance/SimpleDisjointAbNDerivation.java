
package edu.njit.cs.saboc.blu.core.abn.disjoint.provenance;

import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbNFactory;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivation;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Chris O
 */
public class SimpleDisjointAbNDerivation<T extends SinglyRootedNode> extends DisjointAbNDerivation<T> {
    
    private final DisjointAbNFactory factory;
    private final AbNDerivation<PartitionedAbstractionNetwork> parentAbNDerivation;
    private final Set<Concept> sourceNodeRoots;
    
    public SimpleDisjointAbNDerivation(
            DisjointAbNFactory factory,
            AbNDerivation<PartitionedAbstractionNetwork> parentAbNDerivation, 
            Set<Concept> sourceNodeRoots) {
        
        super(factory, parentAbNDerivation);
        
        this.factory = factory;
        this.parentAbNDerivation = parentAbNDerivation;
        this.sourceNodeRoots = sourceNodeRoots;
    }
    
    public SimpleDisjointAbNDerivation(SimpleDisjointAbNDerivation derivedAbN) {
        this(derivedAbN.getFactory(), derivedAbN.getParentAbNDerivation(), derivedAbN.getSourceNodeRoots());
    }
    
    public Set<Concept> getSourceNodeRoots() {
        return sourceNodeRoots;
    }

    @Override
    public String getDescription() {
        return String.format("Disjointed %d", sourceNodeRoots.size());
    }

    @Override
    public DisjointAbstractionNetwork getAbstractionNetwork() {
        PartitionedAbstractionNetwork partitionedAbN = parentAbNDerivation.getAbstractionNetwork();
        
        Set<T> overlappingNodes = new HashSet<>();
        
        this.sourceNodeRoots.forEach( (concept) -> {
            overlappingNodes.addAll(partitionedAbN.getNodesWith(concept));
        });

        DisjointAbNGenerator generator = new DisjointAbNGenerator<>();

        return generator.generateDisjointAbstractionNetwork(
                factory,
                partitionedAbN,
                overlappingNodes);
    }
    
    @Override
    public JSONArray serializeToJSON() {
        JSONArray result = new JSONArray();
        result.add("SimpleDisjointAbNDerivation");
        
        //serialzie parentAbNDerivation
        JSONObject obj_parentAbNDerivation = new JSONObject();
        obj_parentAbNDerivation.put("BaseDerivation", parentAbNDerivation.serializeToJSON());   
        result.add(obj_parentAbNDerivation);

        //serialize sourceNodeRoots
        JSONObject obj_sourceNodeRoots = new JSONObject();
        JSONArray arr = new JSONArray();
        sourceNodeRoots.forEach(root ->{
            arr.add(root.getIDAsString());
        });        
        obj_sourceNodeRoots.put("RootIDs", arr);
        result.add(obj_sourceNodeRoots);
        
        return result;
        
    }
    
}

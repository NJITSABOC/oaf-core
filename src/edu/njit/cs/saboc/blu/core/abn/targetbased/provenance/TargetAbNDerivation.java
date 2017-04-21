package edu.njit.cs.saboc.blu.core.abn.targetbased.provenance;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetworkFactory;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetworkGenerator;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetGroup;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Stores the arguments needed to create a target abstraction network 
 * between two hierarchies
 * 
 * @author Chris O
 */
public class TargetAbNDerivation extends AbNDerivation<TargetAbstractionNetwork> {
    
    private final TargetAbstractionNetworkFactory factory;
    
    private final Concept sourceHierarchyRoot;
    private final InheritableProperty propertyType;
    private final Concept targetHierarchyRoot;
    
    public TargetAbNDerivation(Ontology ont, 
            TargetAbstractionNetworkFactory factory,
            Concept sourceHierarchyRoot, 
            InheritableProperty propertyType, 
            Concept targetHierarchyRoot) {
        
        super(ont);
        
        this.factory = factory;
        
        this.sourceHierarchyRoot = sourceHierarchyRoot;
        this.propertyType = propertyType;
        this.targetHierarchyRoot = targetHierarchyRoot;
    }
    
    public TargetAbNDerivation(TargetAbNDerivation targetAbN) {
        this(targetAbN.getSourceOntology(), 
                targetAbN.getFactory(), 
                targetAbN.getSourceHierarchyRoot(), 
                targetAbN.getPropertyType(), 
                targetAbN.getTargetHierarchyRoot());
    }

    public TargetAbstractionNetworkFactory getFactory() {
        return factory;
    }
    
    public Concept getSourceHierarchyRoot() {
        return sourceHierarchyRoot;
    }

    public InheritableProperty getPropertyType() {
        return propertyType;
    }

    public Concept getTargetHierarchyRoot() {
        return targetHierarchyRoot;
    }

    @Override
    public String getDescription() {
        return "Derived target abstraction network";
    }

    @Override
    public TargetAbstractionNetwork getAbstractionNetwork() {
        TargetAbstractionNetworkGenerator generator = new TargetAbstractionNetworkGenerator();
        
        TargetAbstractionNetwork<TargetGroup> targetAbN = generator.deriveTargetAbstractionNetwork(
                factory, 
                getSourceOntology().getConceptHierarchy().getSubhierarchyRootedAt(sourceHierarchyRoot), 
                propertyType, 
                getSourceOntology().getConceptHierarchy().getSubhierarchyRootedAt(targetHierarchyRoot));
        
        return targetAbN;
    }

    @Override
    public String getName() {
        return String.format("%s %s", propertyType.getName(), getAbstractionNetworkTypeName());
    }

    @Override
    public String getAbstractionNetworkTypeName() {
        return "Target Abstraction Network";
    }

    public JSONArray serializeToJSON() {
        JSONArray result = new JSONArray();
        //serialize class
        JSONObject obj_class = new JSONObject();
        obj_class.put("ClassName","TargetAbNDerivation");       
        result.add(obj_class);
        
        //serialzie sourceHierarchyRoot
        JSONObject obj_sourceHierarchyRoot = new JSONObject();
        obj_sourceHierarchyRoot.put("SourceRootID", sourceHierarchyRoot.getIDAsString());   
        result.add(obj_sourceHierarchyRoot);

        //serialize propertyType
        JSONObject obj_propertyType = new JSONObject();
        obj_propertyType.put("PropertyID", propertyType.getIDAsString());
        result.add(obj_propertyType);
        
        //serialize targetHierarchyRoot
        JSONObject obj_targetHierarchyRoot = new JSONObject();
        obj_targetHierarchyRoot.put("TargetRootID", targetHierarchyRoot.getIDAsString());
        result.add(obj_targetHierarchyRoot);
        
        return result;
    }
}

package edu.njit.cs.saboc.blu.core.abn.targetbased.provenance;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetworkFactory;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetworkGenerator;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetGroup;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;

/**
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
}

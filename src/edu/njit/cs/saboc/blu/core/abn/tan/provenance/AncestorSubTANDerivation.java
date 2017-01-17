
package edu.njit.cs.saboc.blu.core.abn.tan.provenance;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.provenance.RootedSubAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class AncestorSubTANDerivation extends ClusterTANDerivation 
    implements RootedSubAbNDerivation<ClusterTANDerivation> {
    
    private final ClusterTANDerivation base;
    private final Concept clusterRootConcept;
    
    public AncestorSubTANDerivation(ClusterTANDerivation base, Concept clusterRootConcept) {
        super(base);
        
        this.base = base;
        this.clusterRootConcept = clusterRootConcept;
    }
    
    public AncestorSubTANDerivation(AncestorSubTANDerivation deriveTaxonomy) {
        this(deriveTaxonomy.getSuperAbNDerivation(), deriveTaxonomy.getSelectedRoot());
    }

    @Override
    public Concept getSelectedRoot() {
        return clusterRootConcept;
    }

    @Override
    public ClusterTANDerivation getSuperAbNDerivation() {
        return base;
    }
    
    @Override
    public String getDescription() {
        return String.format("Derived ancestor Sub TAN (Cluster: %s)", clusterRootConcept.getName());
    }

    @Override
    public ClusterTribalAbstractionNetwork getAbstractionNetwork() {
        ClusterTribalAbstractionNetwork tan = base.getAbstractionNetwork();
        
        Set<Cluster> clusters = tan.getNodesWith(clusterRootConcept);
        
        return tan.createAncestorTAN(clusters.iterator().next());
    }
}

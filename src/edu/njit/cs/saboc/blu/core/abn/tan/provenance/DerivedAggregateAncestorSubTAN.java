package edu.njit.cs.saboc.blu.core.abn.tan.provenance;

import edu.njit.cs.saboc.blu.core.abn.provenance.DerivedAggregateAbN;
import edu.njit.cs.saboc.blu.core.abn.provenance.DerivedRootedSubAbN;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class DerivedAggregateAncestorSubTAN extends DerivedClusterTAN 
    implements DerivedRootedSubAbN<DerivedClusterTAN>, DerivedAggregateAbN<DerivedClusterTAN> {
    
    private final DerivedClusterTAN aggregateBase;
    private final int minBound;
    private final Concept selectedAggregateClusterRoot;
    
    public DerivedAggregateAncestorSubTAN(
            DerivedClusterTAN aggregateBase, 
            int minBound,
            Concept selectedAggregateClusterRoot) {
        
        super(aggregateBase);
        
        this.aggregateBase = aggregateBase;
        this.minBound = minBound;
        this.selectedAggregateClusterRoot = selectedAggregateClusterRoot;
    }
    
    public DerivedAggregateAncestorSubTAN(DerivedAggregateAncestorSubTAN deriveTaxonomy) {
        this(deriveTaxonomy.getSuperAbNDerivation(), 
                deriveTaxonomy.getBound(), 
                deriveTaxonomy.getSelectedRoot());
    }

    @Override
    public Concept getSelectedRoot() {
        return selectedAggregateClusterRoot;
    }

    @Override
    public DerivedClusterTAN getSuperAbNDerivation() {
        return aggregateBase;
    }

    @Override
    public int getBound() {
        return minBound;
    }

    @Override
    public DerivedClusterTAN getNonAggregateSourceDerivation() {
        DerivedAggregateAbN<DerivedClusterTAN> derivedAggregate = (DerivedAggregateAbN<DerivedClusterTAN>)this.getSuperAbNDerivation();
        
        return derivedAggregate.getNonAggregateSourceDerivation();
    }
  
    @Override
    public String getDescription() {
        return String.format("Derived aggregate ancestors subtaxonomy (PArea: %s)", selectedAggregateClusterRoot.getName());
    }

    @Override
    public ClusterTribalAbstractionNetwork getAbstractionNetwork() {
        ClusterTribalAbstractionNetwork sourceTAN = this.getSuperAbNDerivation().getAbstractionNetwork();
        
        Set<Cluster> clusters = sourceTAN.getNodesWith(selectedAggregateClusterRoot);
        
        return sourceTAN.createAncestorTAN(clusters.iterator().next());
    }
}


package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.tan.Band;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.TribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.graph.tan.DisjointTANFactory;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;

/**
 *
 * @author Chris O
 */
public class TANConfiguration extends PartitionedAbNConfiguration {
    
    public TANConfiguration(TribalAbstractionNetwork tan) {
        super(tan);
    }
    
    public void setUIConfiguration(TANUIConfiguration uiConfig) {
        super.setUIConfiguration(uiConfig);
    }
    
    public void setTextConfiguration(TANTextConfiguration textConfig) {
        super.setTextConfiguration(textConfig);
    }
    
    public TribalAbstractionNetwork getAbstractionNetwork() {
        return (TribalAbstractionNetwork)super.getAbstractionNetwork();
    }
    
    public TribalAbstractionNetwork getTribalAbstractionNetwork() {
        return this.getAbstractionNetwork();
    }

    public TANUIConfiguration getUIConfiguration() {
        return (TANUIConfiguration)super.getUIConfiguration();
    }

    public TANTextConfiguration getTextConfiguration() {
        return (TANTextConfiguration)super.getTextConfiguration();
    }

    @Override
    public int getPartitionedNodeLevel(PartitionedNode node) {
        Band band = (Band)node;
        
        return band.getPatriarchs().size();
    }

    @Override
    public DisjointAbstractionNetwork<?, ?> getDisjointAbstractionNetworkFor(PartitionedNode node) {
        return this.getDisjointTANFor((Band)node);
    }
    
    public DisjointAbstractionNetwork<TribalAbstractionNetwork, Cluster> getDisjointTANFor(Band band) {
        DisjointAbNGenerator<TribalAbstractionNetwork, Cluster> generator = new DisjointAbNGenerator<>();
        
        DisjointAbstractionNetwork<TribalAbstractionNetwork, Cluster> disjointTaxonomy = 
                generator.generateDisjointAbstractionNetwork(
                        new DisjointTANFactory(), 
                        getTribalAbstractionNetwork(), 
                        band.getClusters());

        return disjointTaxonomy;
    }
}

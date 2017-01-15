package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.TANConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetbased.configuration.TargetAbNConfiguration;

/**
 *
 * @author Chris O
 */
public interface AbNGraphFrameInitializers {
    public GraphFrameInitializer<PAreaTaxonomy, PAreaTaxonomyConfiguration> getPAreaTaxonomyInitializer();
    public GraphFrameInitializer<ClusterTribalAbstractionNetwork, TANConfiguration> getTANInitializer();
    public GraphFrameInitializer<TargetAbstractionNetwork, TargetAbNConfiguration> getTargetAbNInitializer();
    
    public GraphFrameInitializer<DisjointAbstractionNetwork, DisjointAbNConfiguration> getDisjointPAreaTaxonomyInitializer();
    public GraphFrameInitializer<DisjointAbstractionNetwork, DisjointAbNConfiguration> getDisjointTANInitializer();
}

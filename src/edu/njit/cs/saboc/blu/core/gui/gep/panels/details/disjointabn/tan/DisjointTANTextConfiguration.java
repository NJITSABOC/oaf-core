package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn.tan;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.OntologyEntityNameConfiguration;

/**
 *
 * @author Chris O
 */
public abstract class DisjointTANTextConfiguration extends DisjointAbNTextConfiguration<DisjointNode<Cluster>> {

    private final DisjointAbstractionNetwork<DisjointNode<Cluster>, ClusterTribalAbstractionNetwork<Cluster>, Cluster> disjointTAN;
    
    public DisjointTANTextConfiguration(
            OntologyEntityNameConfiguration ontologyEntityNameConfig, 
            DisjointAbstractionNetwork<DisjointNode<Cluster>, ClusterTribalAbstractionNetwork<Cluster>, Cluster> disjointTAN) {
        
        super(ontologyEntityNameConfig, disjointTAN);

        this.disjointTAN = disjointTAN;
    }

    @Override
    public String getAbNTypeName(boolean plural) {
        if (plural) {
            return "Disjoint Tribal Abstraction Networks";
        } else {
            return "Disjoint Tribal Abstraction Network";
        }
    }

    @Override
    public String getNodeTypeName(boolean plural) {
        if (plural) {
            return "Disjoint Clusters";
        } else {
            return "Disjoint Cluster";
        }
    }

    @Override
    public String getOverlappingNodeTypeName(boolean plural) {
        if (plural) {
            return "Clusters";
        } else {
            return "Cluster";
        }
    }

    @Override
    public String getNodeHelpDescription(DisjointNode<Cluster> node) {
        StringBuilder helpDescription = new StringBuilder();

        helpDescription.append("A <b>disjoint clusters</b> represents a summary of a disjoint hierarchy of concepts within cluster tribal abstraction network band. ");

        helpDescription.append("A <b>basis</b> (non-overlapping) disjoint cluster is a disjoint cluster that summarizes all of the concepts "
                + "that are summarized by exactly one cluster in the complete cluster tribal abstraction network. "
                + "Basis disjoint clusters are assigned one color and are named after this cluster.");

        helpDescription.append("An <b>overlapping</b> disjoint cluster is a disjoint cluster that summarizes a set of concepts that are summarized "
                + "by two or more clusters in the complete cluster tribal abstraction network. Overlapping disjoint clusters are color coded according to the "
                + "clusters in which their concepts overlap and are named after the concept which is the point of intersection between the clusters. "
                + "There may be multiple points of intersection, thus, there may be many similarly color coded overlapping disjoint clusters.");

        return helpDescription.toString();
    }

    
    @Override
    public String getAbNName() {
        return disjointTAN.getDerivation().getName();
    }

    @Override
    public String getAbNHelpDescription() {

        String description = "[DISJOINT TAN DESCRIPTION]";
        

        return description;
    }
}

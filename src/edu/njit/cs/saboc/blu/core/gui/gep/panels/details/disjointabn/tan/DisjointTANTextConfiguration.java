package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn.tan;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.OntologyEntityNameConfiguration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public abstract class DisjointTANTextConfiguration extends DisjointAbNTextConfiguration<DisjointNode<Cluster>> {

    private final DisjointAbstractionNetwork<DisjointNode<Cluster>, ClusterTribalAbstractionNetwork<Cluster>, Cluster> disjointTAN;

    public DisjointTANTextConfiguration(
            OntologyEntityNameConfiguration ontologyEntityNameConfig, 
            DisjointAbstractionNetwork<DisjointNode<Cluster>, ClusterTribalAbstractionNetwork<Cluster>, Cluster> disjointTaxonomy) {
        
        super(ontologyEntityNameConfig);
        
        this.disjointTAN = disjointTaxonomy;
    }

    @Override
    public String getAbNTypeName(boolean plural) {
        if (plural) {
            return "Disjoint Cluster Tribal Abstraction Networks";
        } else {
            return "Disjoint Cluster Tribal Abstraction Network";
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

        if (disjointTAN.isAggregated()) {
            helpDescription.append("[AGGREGATE DISJOINT TAN DESCRIPTION TEXT]");
            
        } else {
            helpDescription.append("A <b>disjoint clusters</b> represents a summary of a disjoint hierarchy of concepts within cluster tribal abstraction network band. ");

            helpDescription.append("A <b>basis</b> (non-overlapping) disjoint cluster is a disjoint cluster that summarizes all of the concepts "
                    + "that are summarized by exactly one cluster in the complete cluster tribal abstraction network. "
                    + "Basis disjoint clusters are assigned one color and are named after this cluster.");

            helpDescription.append("An <b>overlapping</b> disjoint cluster is a disjoint cluster that summarizes a set of concepts that are summarized "
                    + "by two or more clusters in the complete cluster tribal abstraction network. Overlapping disjoint clusters are color coded according to the "
                    + "clusters in which their concepts overlap and are named after the concept which is the point of intersection between the clusters. "
                    + "There may be multiple points of intersection, thus, there may be many similarly color coded overlapping disjoint clusters.");
        }

        return helpDescription.toString();
    }

    
    @Override
    public String getAbNName() {
        return disjointTAN.getDerivation().getName();
    }

    @Override
    public String getAbNSummary() {
        
        Cluster cluster = disjointTAN.getOverlappingNodes().iterator().next();
        String bandName = disjointTAN.getParentAbstractionNetwork().getPartitionNodeFor(cluster).getName();

        int overlappingClusterCount = disjointTAN.getOverlappingNodes().size();

        int disjointClusterCount = disjointTAN.getAllDisjointNodes().size();

        int conceptCount = 0;

        Set<DisjointNode<Cluster>> disjointClusters = disjointTAN.getAllDisjointNodes();

        Map<Integer, Set<DisjointNode<Cluster>>> levels = new HashMap<>();

        for (DisjointNode<Cluster> disjointCluster : disjointClusters) {
            int level = disjointCluster.getOverlaps().size();

            if (!levels.containsKey(level)) {
                levels.put(level, new HashSet<>());
            }

            levels.get(level).add(disjointCluster);

            conceptCount += disjointCluster.getConceptCount();
        }

        ArrayList<Integer> sortedLevels = new ArrayList<>(levels.keySet());
        Collections.sort(sortedLevels);

        String summary = String.format("The {%s} disjoint cluster tribal abstraction network, "
                + "summarizes the overlap between %d partial-areas. "
                + "The disjoint cluster TAN summarizes %d concepts in %d disjoint clusters.",
                bandName,
                overlappingClusterCount,
                conceptCount,
                disjointClusterCount);

        summary += "<p><b>Overlapping Concept Distribution:</b><br>";
        
        for (int level : sortedLevels) {

            if (level == 1) {
                continue;
            }

            Set<DisjointNode<Cluster>> levelDisjointPAreas = levels.get(level);

            int levelClassCount = 0;

            for (DisjointNode<Cluster> levelDisjointPArea : levelDisjointPAreas) {
                levelClassCount += levelDisjointPArea.getConceptCount();
            }

            summary += String.format("Level %d: %d Disjoint Clusters:, %d %s<br>", 
                    level, 
                    levelDisjointPAreas.size(), 
                    levelClassCount, 
                    this.getOntologyEntityNameConfiguration().getConceptTypeName(true));
        }

        summary += "<p><b>Help / Description:</b><br>";

        summary += getAbNHelpDescription();

        return summary;
    }

    @Override
    public String getAbNHelpDescription() {

        String description;

        if (disjointTAN.isAggregated()) {
            description = "[AGGREGATE DISJOINT TAN DESCRIPTION]";
        } else {
            description = "[DISJOINT TAN DESCRIPTION]";
        }

        return description;
    }
}

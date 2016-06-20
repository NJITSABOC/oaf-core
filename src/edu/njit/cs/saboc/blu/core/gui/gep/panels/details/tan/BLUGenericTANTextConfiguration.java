package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan;

import edu.njit.cs.saboc.blu.core.abn.tan.TribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.nodes.Band;
import edu.njit.cs.saboc.blu.core.abn.tan.nodes.Cluster;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.text.BLUPartitionedAbNTextConfiguration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public abstract class BLUGenericTANTextConfiguration<
        TAN_T extends TribalAbstractionNetwork,
        BAND_T extends Band,
        CLUSTER_T extends Cluster,
        CONCEPT_T>
            implements BLUPartitionedAbNTextConfiguration<TAN_T, BAND_T, CLUSTER_T, CONCEPT_T> {

    private final TAN_T tan;

    public BLUGenericTANTextConfiguration(TAN_T tan) {
        this.tan = tan;
    }
    
    public TAN_T getTAN() {
        return tan;
    }
    
    @Override
    public String getAbNTypeName(boolean plural) {
        if(plural) {
            return "Tribal Abstraction Networks";
        } else {
            return "Tribal Abstraction Network";
        }
    }
    
    @Override
    public String getContainerTypeName(boolean plural) {
        if (plural) {
            return "Bands";
        } else {
            return "Band";
        }
    }

    private String getCommaSeparatedBandName(HashSet<CONCEPT_T> tribes) {

        if (tribes.isEmpty()) {
            return "";
        }

        ArrayList<String> tribeNames = new ArrayList<>();

        tribes.forEach((CONCEPT_T tribe) -> {
            tribeNames.add(getConceptName(tribe));
        });

        Collections.sort(tribeNames);

        String bandName = tribeNames.get(0);

        for (int c = 1; c < tribeNames.size(); c++) {
            bandName += ", " + tribeNames.get(c);
        }

        return bandName;
    }

    @Override
    public String getContainerName(BAND_T band) {
        HashSet<CONCEPT_T> tribes = band.getPatriarchs();

        if (tribes.isEmpty()) {
            return "";
        } else {
            return getCommaSeparatedBandName(tribes);
        }
    }

    @Override
    public String getGroupsContainerName(CLUSTER_T group) {
        HashSet<CONCEPT_T> patriarchs = group.getPatriarchs();

        if (patriarchs.isEmpty()) {
            return "";
        } else {
            return getCommaSeparatedBandName(patriarchs);
        }
    }

    @Override
    public String getGroupTypeName(boolean plural) {
        if (plural) {
            return "Clusters";
        } else {
            return "Cluster";
        }
    }

    @Override
    public String getGroupName(CLUSTER_T cluster) {
        return cluster.getRoot().getName();
    }

    @Override
    public String getContainerHelpDescription(BAND_T container) {
        StringBuilder helpDescription = new StringBuilder();
        
        String pluralConceptName = this.getConceptTypeName(true).toLowerCase();
        String singularConceptName = this.getConceptTypeName(false).toLowerCase();
        
        String text = String.format("A <b>band</b> summarizes the set of all %s which belong to the intersection of the exact same subhierarchies. "
                + "That is, the %s summarized by a band are all descendants of the same patriarch %s (shown below)."
                + "Each %s is summarized by exactly one band.",
                
                pluralConceptName, pluralConceptName, pluralConceptName, singularConceptName);

        helpDescription.append(text);

        return helpDescription.toString();
    }

    @Override
    public String getGroupHelpDescriptions(CLUSTER_T group) {
        StringBuilder helpDescription = new StringBuilder();
        
        String pluralConceptName = this.getConceptTypeName(true).toLowerCase();
        
        String text = String.format("A <b>cluster</b> summarizes the subhierarchy of %s at a "
                + "specific point of intersection between two or more subhierarchies.", pluralConceptName);

        helpDescription.append(text);

        return helpDescription.toString();
    }

    @Override
    public String getAbNName() {
        return tan.getTANName() + " Tribal Abstraction Network (TAN)";
    }

    @Override
    public String getAbNSummary() {
        String tanName = tan.getTANName();

        int conceptCount = tan.getSourceConceptHierarchy().getNodesInHierarchy().size();

        int bandCount = tan.getBands().size();
        int clusterCount = tan.getClusters().values().size();

        HashSet<CONCEPT_T> intersectionConcepts = new HashSet<>();
        
        HashMap<CONCEPT_T, HashSet<CLUSTER_T>> intersectingPatriarchClusters = new HashMap<>();
        
        tan.getClusters().values().forEach((Object o) -> {
            CLUSTER_T cluster = (CLUSTER_T)o;
            
            HashSet<CONCEPT_T> patriarchs = cluster.getPatriarchs();
            
            if (patriarchs.size() > 1) {
                intersectionConcepts.addAll(cluster.getConcepts());
                
                patriarchs.forEach( (p) -> {
                    if(!intersectingPatriarchClusters.containsKey(p)) {
                        intersectingPatriarchClusters.put(p, new HashSet<>());
                    }
                    
                    intersectingPatriarchClusters.get(p).add(cluster);
                });
            }
        });

        ArrayList<CLUSTER_T> patriarchClusters = tan.getPatriarchClusters();

        HashSet<CLUSTER_T> nonintersectingPatriarchClusters = tan.getNonOverlappingPatriarchClusters();
        
        String pluralConceptName = this.getConceptTypeName(true).toLowerCase();
        
        String result = String.format("The <b>%s</b> Tribal Abstraction Network (TAN) summarizes %d %s in %d band(s) and %d cluster(s). "
                + "There are a total of %d patriarch clusters that intersect. A total of %d %s are descendants "
                + "of more than one patriarch and belong to multiple tribes.<p>",
                
                tanName,
                conceptCount,
                pluralConceptName,
                bandCount,
                clusterCount,
                (patriarchClusters.size() - nonintersectingPatriarchClusters.size()),
                intersectionConcepts.size(),
                pluralConceptName
        );
        
        if(!intersectingPatriarchClusters.isEmpty()) {
            ArrayList<String> intersectingClusterNames = new ArrayList<>();
            
            intersectingPatriarchClusters.keySet().forEach( (p) -> {
                intersectingClusterNames.add(String.format("%s (%d clusters total)", 
                        this.getConceptName(p), 
                        intersectingPatriarchClusters.get(p).size()));
            });
            
            Collections.sort(intersectingClusterNames);
            
            String intersectingSummaryStr = intersectingClusterNames.get(0);
            
            for(int c = 1; c < intersectingClusterNames.size(); c++) {
                intersectingSummaryStr += String.format("<br>%s", intersectingClusterNames.get(c));
            }
            
            result += String.format("<p>The following %d patriarchs intersect with other patriarchs: <p> %s",
                    intersectingPatriarchClusters.keySet().size(), intersectingSummaryStr);
        }
        
        if (!nonintersectingPatriarchClusters.isEmpty()) {
            ArrayList<String> nonOverlappingClusterNames = new ArrayList<>();

            nonintersectingPatriarchClusters.forEach((CLUSTER_T cluster) -> {
                nonOverlappingClusterNames.add(
                        String.format("%s (%d)", cluster.getRoot().getName(), cluster.getConceptCount()));
            });

            Collections.sort(nonOverlappingClusterNames);

            String nonOverlappingClustersStr = nonOverlappingClusterNames.get(0);

            for (int c = 1; c < nonOverlappingClusterNames.size(); c++) {
                nonOverlappingClustersStr += String.format("<br>%s", nonOverlappingClusterNames.get(c));
            }

            result += String.format("<p>The following %d patriarch cluster(s) don't intersect with any other patriarch cluster: <p> %s",
                    nonintersectingPatriarchClusters.size(), nonOverlappingClustersStr);
        }

        result += "<p><b>Help / Description:</b><br>";
        result += this.getAbNHelpDescription();

        return result;
    }

    @Override
    public String getAbNHelpDescription() {
        
        String pluralConceptName = this.getConceptTypeName(true).toLowerCase();
        String singularConceptName = this.getConceptTypeName(true).toLowerCase();
        
        String result = String.format("A <b>Tribal Abstraction Network (TAN)</b> is an abstraction network which summarizes the major points of intersection within "
                + "a hierarchy of %s. Given a hierarchy of %s, the children of the root of the hierarchy are defined as <i>patriarchs</i>. "
                + "Patriarchs are root %s of subhierarchies within the overall hierarchy. The subhierarchies rooted at the patriarchs may intersect. "
                + "A %s in the hierarchy may be a descendant of multiple patriarchs."
                + "<p>"
                + "The TAN is composed of two kinds of nodes: Bands and Clusters.<p>"
                + "A <b>band</b> summarizes the set of all %s that are descendants of the exact same patriarchs. Bands are organized into "
                + "color coded levels according to the number of patriarchs their %s are descendnats of (e.g., green is two). Bands are labeled "
                + "with this set of patriarchs, the total number of %s which belong to the band, and the total number of clusters in the band."
                + "<p>"
                + "A <b>cluster</b> summarizes the subhierarchy of %s that exists at one intersection point between two or more subhierarchies. "
                + "Clusters are shown as white boxes within each band. Clusters are named after the %s which is at the exact point of intersection. "
                + "The total number of %s summarized by a cluster is shown in parenthesis."
                + "<p>"
                + "A TAN also captures the subsumption relationships between %s. Clicking on a cluster will show you the root's parents (in blue) "
                + "and child clusters, which summarize descendants of one or more additional patriarchs, in purple.",
                    pluralConceptName,
                    pluralConceptName, 
                    pluralConceptName, 
                    singularConceptName, 
                    pluralConceptName, 
                    pluralConceptName,
                    pluralConceptName, 
                    pluralConceptName, 
                    singularConceptName, 
                    pluralConceptName, 
                    pluralConceptName);

        return result;
    }
}

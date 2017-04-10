package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.OntologyEntityNameConfiguration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public abstract class DisjointPAreaTaxonomyTextConfiguration extends DisjointAbNTextConfiguration<DisjointNode<PArea>> {

    private final DisjointAbstractionNetwork<DisjointNode<PArea>, PAreaTaxonomy<PArea>, PArea> disjointTaxonomy;

    public DisjointPAreaTaxonomyTextConfiguration(
            OntologyEntityNameConfiguration ontologyEntityNameConfig, 
            DisjointAbstractionNetwork<DisjointNode<PArea>, PAreaTaxonomy<PArea>, PArea> disjointTaxonomy) {
        
        super(ontologyEntityNameConfig);
        
        this.disjointTaxonomy = disjointTaxonomy;
    }

    @Override
    public String getAbNTypeName(boolean plural) {
        if (plural) {
            return "Disjoint Partial-area Taxonomies";
        } else {
            return "Disjoint Partial-area Taxonomy";
        }
    }

    @Override
    public String getNodeTypeName(boolean plural) {
        if (plural) {
            return "Disjoint Partial-areas";
        } else {
            return "Disjoint Partial-area";
        }
    }

    @Override
    public String getOverlappingNodeTypeName(boolean plural) {
        if (plural) {
            return "Partial-areas";
        } else {
            return "Partial-area";
        }
    }

    @Override
    public String getNodeHelpDescription(DisjointNode<PArea> node) {
        StringBuilder helpDescription = new StringBuilder();

        if (disjointTaxonomy.isAggregated()) {
            helpDescription.append("[AGGREGATE DISJOINT PAREA DESCRIPTION TEXT]");
            
        } else {
            helpDescription.append("A <b>disjoint partial-area</b> represents a summary of a disjoint hierarchy of concepts within a partial-area taxonomy area. ");

            helpDescription.append("A <b>basis</b> (non-overlapping) disjoint partial-area is a disjoint partial-area that summarizes all of the concepts "
                    + "that are summarized by exactly one partial-area in the complete partial-area taxonomy. "
                    + "Basis disjoint partial-areas are assigned one color and are named after this partial-area.");

            helpDescription.append("An <b>overlapping</b> disjoint partial-area is a disjoint partial-area that summarizes a set of concepts that are summarized "
                    + "by two or more partial-areas in the complete partial-area taxonomy. Overlapping disjoint partial-areas are color coded according to the "
                    + "partial-areas in which their concepts overlap and are named after the concept which is the point of intersection between the partial-areas. "
                    + "There may be multiple points of intersection, thus, there may be many similarly color coded overlapping disjoint partial-areas.");
        }

        return helpDescription.toString();
    }

    
    @Override
    public String getAbNName() {
        return disjointTaxonomy.getDerivation().getName();
    }

    @Override
    public String getAbNSummary() {
        
        PArea aPArea = disjointTaxonomy.getOverlappingNodes().iterator().next();

        String areaName = disjointTaxonomy.getParentAbstractionNetwork().getPartitionNodeFor(aPArea).getName();

        String parentPAreaTaxonomyName = disjointTaxonomy.getParentAbstractionNetwork().getPAreaHierarchy().getRoot().getName();

        int overlappingPAreaCount = disjointTaxonomy.getOverlappingNodes().size();

        int disjointPAreaCount = disjointTaxonomy.getAllDisjointNodes().size();

        int conceptCount = 0;

        Set<DisjointNode<PArea>> disjointPAreas = disjointTaxonomy.getAllDisjointNodes();

        HashMap<Integer, HashSet<DisjointNode<PArea>>> levels = new HashMap<>();

        for (DisjointNode<PArea> disjointPArea : disjointPAreas) {
            int level = disjointPArea.getOverlaps().size();

            if (!levels.containsKey(level)) {
                levels.put(level, new HashSet<>());
            }

            levels.get(level).add(disjointPArea);

            conceptCount += disjointPArea.getConceptCount();
        }

        ArrayList<Integer> sortedLevels = new ArrayList<>(levels.keySet());
        Collections.sort(sortedLevels);

        String summary = String.format("The {%s} disjoint partial-area taxonomy, "
                + "derived from the %s partial-area taxonomy, "
                + "summarizes the overlap between %d partial-areas. "
                + "The disjoint partial-area taxonomy summarizes %d concepts in %d disjoint partial-areas.",
                areaName,
                parentPAreaTaxonomyName,
                overlappingPAreaCount,
                conceptCount,
                disjointPAreaCount);

        summary += "<p><b>Overlapping Concept Distribution:</b><br>";
        
        for (int level : sortedLevels) {

            if (level == 1) {
                continue;
            }

            HashSet<DisjointNode<PArea>> levelDisjointPAreas = levels.get(level);

            int levelClassCount = 0;

            for (DisjointNode<PArea> levelDisjointPArea : levelDisjointPAreas) {
                levelClassCount += levelDisjointPArea.getConceptCount();
            }

            summary += String.format("Level %d: %d Disjoint Partial-areas, %d Concepts<br>", level, levelDisjointPAreas.size(), levelClassCount);
        }

        summary += "<p><b>Help / Description:</b><br>";

        summary += getAbNHelpDescription();

        return summary;
    }

    @Override
    public String getAbNHelpDescription() {

        String description;

        if (disjointTaxonomy.isAggregated()) {
            description = "[AGGREGATE DISJOINT PAREA TAXONOMY DESCRIPTION]";
        } else {
            description = "A disjoint partial-area taxonomy is an abstraction network, derived from an area in a complete "
                    + "partial-area taxonomy, that partitions the selected area's concepts into disjoint groups called disjoint partial-areas. "
                    + "The disjoint partial-areas are organized into a hierarchy based on the underlying <i>Is a</i> hierarchy. In comparison to the "
                    + "partial-area taxonomy, the disjoint partial-area taxonomy provides a more accurate picture of the concept hierarchy within an area,"
                    + "since concepts may be summarized by multiple partial-areas (called <i>overlapping concepts</i>). We note that partial-areas "
                    + "that do not have any overlapping concepts are not considered when creating the disjoint partial-area taxonomy."
                    + "<p>"
                    + "Disjoint partial-areas that do not contain any overlapping concepts (called basis disjoint partial-areas, or non-overlapping disjoint partial-areas), "
                    + "are give a single color and are located at the top of the disjoint partial-area taxonomy abstraction network. Overlapping disjoint partial-areas,"
                    + "which summarize one or more overlapping concepts, are color coded according to which partial-areas their concepts overlap between. Overlapping "
                    + "disjoint partial-areas are organized into levels according to their degree of overlap, i.e., how many partial-areas their concepts are summarized by.";
        }

        return description;
    }
}

package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.factory;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateNode;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNTextFormatter;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class NodeSummaryTextFactory<T extends Node> {
    
    private final AbNConfiguration config;
    private final AbNTextFormatter formatter;
    
    public NodeSummaryTextFactory(AbNConfiguration config) {
        this.config = config;
        this.formatter = new AbNTextFormatter(config.getTextConfiguration());
    }
    
    public AbNConfiguration getConfiguration() {
        return config;
    }
    
    public String createNodeSummaryText(T node) {
        
        String result = "<html><b><nodeName></b> is a <nodeTypeName> "
                + "that summarizes <font color='RED'><conceptCount></font> "
                + "<conceptTypeName count=<conceptCount>>.";
        
        result = result.replaceAll("<nodeName>", node.getName());
        result = result.replaceAll("<conceptCount>", Integer.toString(node.getConceptCount()));
        
        AbstractionNetwork<Node> abn = config.getAbstractionNetwork();

        int parentCount = abn.getNodeHierarchy().getParents(node).size();       
        int childCount = abn.getNodeHierarchy().getChildren(node).size();

        Set<Node> descendentNodes = abn.getNodeHierarchy().getDescendants(node);

        Set<Concept> descendantConcepts = new HashSet<>();

        descendentNodes.forEach((descendentPArea) -> {
            descendantConcepts.addAll(descendentPArea.getConcepts());
        });
        
        result += "<br>";
        
        String parentStr;
        
        if(parentCount > 0) {
            parentStr = "It has <font color='RED'><parentNodeCount></font> parent "
                    + "<nodeTypeName count=<parentNodeCount>> and ";
            
            parentStr = parentStr.replaceAll("<parentNodeCount>", Integer.toString(parentCount));
            
        } else {
            parentStr = "It has no parent <nodeTypeName count=0> and ";
        }
        
        result += ("<p>" + parentStr);
        
        String childStr;
        
        if(childCount > 0) {
            childStr = "<font color = 'RED'><childNodeCount></font> child <nodeTypeName count=<childNodeCount>>.";
            childStr = childStr.replaceAll("<childNodeCount>", Integer.toString(childCount));
        } else {
            childStr = "no child <nodeTypeName count=0>.";
        }
        
        result += childStr;

        if (!descendentNodes.isEmpty()) {

            String descendantStr = "There are a total of <font color='RED'><descendantNodeCount></font>"
                    + " descendant <nodeTypeName count=<descendantNodeCount>> that summarize a total of "
                    + "<font color='RED'><descendantConceptCount></font> <conceptTypeName count=<descendantConceptCount>>.";
                    
            descendantStr = descendantStr.replaceAll("<descendantNodeCount>", Integer.toString(descendentNodes.size()));
            descendantStr = descendantStr.replaceAll("<descendantConceptCount>", Integer.toString(descendantConcepts.size()));
                        
            result += ("<p>" + descendantStr);
        }
        
        if (node instanceof AggregateNode) {
            AggregateNode<?> aNode = (AggregateNode) node;

            if (!aNode.getAggregatedNodes().isEmpty()) {

                result += "<p>";

                Set<Concept> aggregatedConcepts = new HashSet<>();

                aNode.getAggregatedNodes().forEach((aggregatedNode) -> {
                    aggregatedConcepts.addAll(aggregatedNode.getConcepts());
                });

                String aggregateStr = "<nodeName> is an <b>aggregate <nodeTypeName></b> that summarizes "
                        + "<font color='RED'><aggregatedNodeCount></font> small <nodeTypeName count=<aggregateNotCount>> "
                        + " that summarized a total of <font color = 'RED'><aggrgateConceptCount></font> <conceptTypeName "
                        + "count=<aggrgateConceptCount>>.";

                aggregateStr = aggregateStr.replaceAll("<aggregatedNodeCount>", Integer.toString(aNode.getAggregatedNodes().size()));
                aggregateStr = aggregateStr.replaceAll("<aggrgateConceptCount>", Integer.toString(aggregatedConcepts.size()));

                result += aggregateStr;
            }
        }
        result = formatter.format(result);
        return result;
    }
}

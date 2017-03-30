
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetabn;

import edu.njit.cs.saboc.blu.core.abn.targetbased.aggregate.AggregateTargetGroup;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetbased.configuration.TargetAbNConfiguration;

/**
 *
 * @author Chris O
 */
public class AggregateTargetGroupSummaryTextFactory extends TargetGroupSummaryTextFactory {

    public AggregateTargetGroupSummaryTextFactory(TargetAbNConfiguration config) {
        super(config);
    }

    @Override
    public String createNodeSummaryText(TargetGroup group) {
        
        TargetAbNConfiguration config = super.getConfiguration();
        
        AggregateTargetGroup aggregatePArea = (AggregateTargetGroup)group;
        
        String rootName = aggregatePArea.getName();

        String typeDesc;
        
        if(aggregatePArea.getAggregatedNodes().isEmpty()) {
            TargetGroup root = aggregatePArea.getAggregatedHierarchy().getRoot();
            
            int conceptCount = root.getConceptCount();
            
            typeDesc = String.format("<b>%s</b> is a regular target group that summarizes %d target %s.",
                    rootName, 
                    root.getConceptCount(), 
                    config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(conceptCount > 1 || conceptCount == 0)).toLowerCase();
            
        } else {
            int aggregatedGroupCount = aggregatePArea.getAggregatedNodes().size();
            int totalConceptCount = aggregatePArea.getConcepts().size();
            
            typeDesc = String.format("<b>%s</b> is an aggregate target group that summarizes %d target %s and %d %s.",
                    rootName, 
                    totalConceptCount, 
                    config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(totalConceptCount > 1 || totalConceptCount == 0).toLowerCase(),
                    aggregatedGroupCount,
                    config.getTextConfiguration().getNodeTypeName(aggregatedGroupCount > 1 || aggregatedGroupCount == 0).toLowerCase());
        }

        return String.format("%s",
                typeDesc
        );
    }
    
}

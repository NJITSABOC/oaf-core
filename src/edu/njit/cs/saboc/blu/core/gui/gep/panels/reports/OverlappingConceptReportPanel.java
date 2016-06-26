package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.OverlappingConceptDetails;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractEntityList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.models.OverlappingConceptReportTableModel;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class OverlappingConceptReportPanel extends AbNReportPanel {
    
    private final AbstractEntityList<OverlappingConceptDetails> overlappingConceptReportList; 
    
    public OverlappingConceptReportPanel(PartitionedAbNConfiguration config) {
        super(config);
        
        this.setLayout(new BorderLayout());
        
        this.overlappingConceptReportList = new AbstractEntityList<OverlappingConceptDetails> (
                new OverlappingConceptReportTableModel(config)) {
                    
            public String getBorderText(Optional<ArrayList<OverlappingConceptDetails>> reports) {
                if(reports.isPresent()) {
                    return String.format("Overlapping %s (%d total)", config.getTextConfiguration().getConceptTypeName(true), reports.get().size());
                } else {
                    return String.format("Overlapping %s", config.getTextConfiguration().getConceptTypeName(true));
                }
            }
        };
        
        this.add(overlappingConceptReportList, BorderLayout.CENTER);
    }
    
    @Override
    public void displayAbNReport(AbstractionNetwork abn) {
        PartitionedAbstractionNetwork pan = (PartitionedAbstractionNetwork)abn;
        
        Set<PartitionedNode> containers = pan.getBaseAbstractionNetwork().getNodes();
        
        PartitionedAbNConfiguration currentConfig = (PartitionedAbNConfiguration)config;
        
        ArrayList<OverlappingConceptDetails> entries = new ArrayList<>();
        
        containers.forEach((container) -> {
            entries.addAll(container.getOverlappingConceptDetails());
        });
        
        Collections.sort(entries, (a, b) -> a.getConcept().getName().compareToIgnoreCase(b.getConcept().getName()));
        
        overlappingConceptReportList.setContents(entries);
    }
}

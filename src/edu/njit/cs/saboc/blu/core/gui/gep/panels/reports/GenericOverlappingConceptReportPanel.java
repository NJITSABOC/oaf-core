package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports;

import SnomedShared.generic.GenericConceptGroup;
import SnomedShared.generic.GenericGroupContainer;
import edu.njit.cs.saboc.blu.core.abn.OverlappingConceptResult;
import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUPartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractEntityList;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;

/**
 *
 * @author Chris O
 */
public class GenericOverlappingConceptReportPanel<
        CONCEPT_T,
        ABN_T extends PartitionedAbstractionNetwork, 
        CONTAINER_T extends GenericGroupContainer, 
        GROUP_T extends GenericConceptGroup> extends AbNReportPanel<CONCEPT_T, GROUP_T, ABN_T> {
    
    private final AbstractEntityList<OverlappingConceptResult<CONCEPT_T, GROUP_T>> overlappingConceptReportList; 
    
    public GenericOverlappingConceptReportPanel(BLUPartitionedAbNConfiguration config) {
        super(config);
        
        this.setLayout(new BorderLayout());
        
        this.overlappingConceptReportList = new AbstractEntityList<OverlappingConceptResult<CONCEPT_T, GROUP_T>> (
                new GenericOverlappingConceptReportTableModel<>(config)) {
                    
            public String getBorderText(Optional<ArrayList<OverlappingConceptResult<CONCEPT_T, GROUP_T>>> reports) {
                if(reports.isPresent()) {
                    return String.format("Overlapping %s (%d total)", config.getConceptTypeName(true), reports.get().size());
                } else {
                    return String.format("Overlapping %s", config.getConceptTypeName(true));
                }
            }
        };
        
        this.add(overlappingConceptReportList, BorderLayout.CENTER);
    }
    
    @Override
    public void displayAbNReport(ABN_T abn) {
        ArrayList<CONTAINER_T> containers = abn.getContainers();
        
        BLUPartitionedAbNConfiguration currentConfig = (BLUPartitionedAbNConfiguration)config;
        
        ArrayList<OverlappingConceptResult<CONCEPT_T, GROUP_T>> entries = new ArrayList<>();
        
        containers.forEach((CONTAINER_T container) -> {
            entries.addAll(currentConfig.getContainerOverlappingResults(container));
        });
        
        Collections.sort(entries, new Comparator<OverlappingConceptResult<CONCEPT_T, GROUP_T>>() {
            public int compare(OverlappingConceptResult<CONCEPT_T, GROUP_T> a, OverlappingConceptResult<CONCEPT_T, GROUP_T> b) {
                String aOverlapName = config.getConceptName(a.getConcept());
                String bOverlapName = config.getConceptName(b.getConcept());
                
                return aOverlapName.compareToIgnoreCase(bOverlapName);
            }
        });
        
        overlappingConceptReportList.setContents(entries);
    }
}

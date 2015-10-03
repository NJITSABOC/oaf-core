package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports;

import SnomedShared.generic.GenericConceptGroup;
import SnomedShared.generic.GenericGroupContainer;
import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUPartitionedConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractEntityList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.BLUAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry.ContainerReport;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;

/**
 *
 * @author Chris O
 */
public class AbstractAbNContainerReportPanel<
        ABN_T extends PartitionedAbstractionNetwork, 
        CONTAINER_T extends GenericGroupContainer, 
        GROUP_T extends GenericConceptGroup, 
        CONCEPT_T> extends AbNReportPanel<CONCEPT_T, GROUP_T, ABN_T> {
    
    private final AbstractEntityList<ContainerReport<CONTAINER_T, GROUP_T, CONCEPT_T>> containerReportPanel; 
    
    protected AbstractAbNContainerReportPanel(BLUPartitionedConfiguration config, 
            BLUAbstractTableModel<ContainerReport<CONTAINER_T, GROUP_T, CONCEPT_T>> model) {
        
        super(config);
        
        this.setLayout(new BorderLayout());
        
        this.containerReportPanel = new AbstractEntityList<ContainerReport<CONTAINER_T, GROUP_T, CONCEPT_T>> (model) {
                    
            public String getBorderText(Optional<ArrayList<ContainerReport<CONTAINER_T, GROUP_T, CONCEPT_T>>> reports) {
                if(reports.isPresent()) {
                    return String.format("%s (%d total)", config.getTextConfiguration().getContainerTypeName(true), reports.get().size());
                } else {
                    return config.getTextConfiguration().getContainerTypeName(true);
                }
            }
        };
        
        this.add(containerReportPanel, BorderLayout.CENTER);
    }
    
    public AbstractAbNContainerReportPanel(BLUPartitionedConfiguration config) {
        this(config, new GenericContainerReportTableModel<>(config));
    }
    
    @Override
    public void displayAbNReport(ABN_T abn) {
        ArrayList<CONTAINER_T> containers = abn.getContainers();
        
        BLUPartitionedConfiguration currentConfig = (BLUPartitionedConfiguration)config;
        
        ArrayList<ContainerReport<CONTAINER_T, GROUP_T, CONCEPT_T>> entries = new ArrayList<>();
        
        containers.forEach((CONTAINER_T container) -> {
            HashSet<GROUP_T> groups = currentConfig.getDataConfiguration().getContainerGroupSet(container);
            
            HashSet<CONCEPT_T> concepts = new HashSet<>();
            
            groups.forEach((GROUP_T group) -> {
                concepts.addAll(currentConfig.getDataConfiguration().getGroupConceptSet(group));
            });
            
            entries.add(new ContainerReport<>(container, 
                    groups, 
                    concepts,
                    currentConfig.getDataConfiguration().getContainerOverlappingConcepts(container)));
        });
        
        Collections.sort(entries, new Comparator<ContainerReport<CONTAINER_T, GROUP_T, CONCEPT_T>>() {
            public int compare(ContainerReport<CONTAINER_T, GROUP_T, CONCEPT_T> a, ContainerReport<CONTAINER_T, GROUP_T, CONCEPT_T> b) {
                int aLevel = currentConfig.getDataConfiguration().getContainerLevel(a.getContainer());
                int bLevel = currentConfig.getDataConfiguration().getContainerLevel(b.getContainer());
                
                if(aLevel == bLevel) {
                    int aCount = a.getConcepts().size();
                    int bCount = b.getConcepts().size();
                    
                    return bCount - aCount;
                } else {
                    return aLevel - bLevel;
                }
            }
        });
        
        containerReportPanel.setContents(entries);
    }
}

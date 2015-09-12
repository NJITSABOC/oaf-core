package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericPArea;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateableConceptGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNNodeInformationPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractEntityList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractNodeDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry.AggregatedGroupEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionAdapter;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import javax.swing.JSplitPane;

/**
 *
 * @author Chris O
 */
public class GenericAreaAggregatedPAreaPanel<CONCEPT_T, 
        AREA_T extends GenericArea,
        PAREA_T extends GenericPArea, 
        AGGREGATEPAREA_T extends GenericPArea & AggregateableConceptGroup<CONCEPT_T, PAREA_T>> extends AbNNodeInformationPanel<AREA_T> {
    
    private final PAreaTaxonomyConfiguration config;
    
    private final JSplitPane splitPane;

    private final AbstractEntityList<AggregatedGroupEntry<CONCEPT_T, PAREA_T, AGGREGATEPAREA_T>> aggregatedGroupList;
    
    private final AbstractEntityList<CONCEPT_T> conceptList;
    
    public GenericAreaAggregatedPAreaPanel(
            AbstractEntityList<AggregatedGroupEntry<CONCEPT_T, PAREA_T, AGGREGATEPAREA_T>> aggregatedGroupList, 
            AbstractEntityList<CONCEPT_T> conceptList, 
            PAreaTaxonomyConfiguration config) {
        
        this.setLayout(new BorderLayout());
        
        this.aggregatedGroupList = aggregatedGroupList;
        this.conceptList = conceptList;
        this.config = config;
        
        this.aggregatedGroupList.addEntitySelectionListener(new EntitySelectionAdapter<AggregatedGroupEntry<CONCEPT_T, PAREA_T, AGGREGATEPAREA_T>>() {
            public void entityClicked(AggregatedGroupEntry<CONCEPT_T, PAREA_T, AGGREGATEPAREA_T> group) {
                conceptList.setContents(config.getSortedConceptList(group.getAggregatedGroup()));
            }
            
            public void noEntitySelected() {
                conceptList.clearContents();
            }
        });

        this.splitPane = AbstractNodeDetailsPanel.createStyledSplitPane(JSplitPane.VERTICAL_SPLIT);
        
        splitPane.setTopComponent(this.aggregatedGroupList);
        splitPane.setBottomComponent(this.conceptList);

        this.add(splitPane, BorderLayout.CENTER);
    }
    
    @Override
    public void setContents(AREA_T area) {
        
        splitPane.setDividerLocation(300);
                        
        ArrayList<AGGREGATEPAREA_T> aggregatePAreas = area.getAllPAreas();
        
        HashMap<PAREA_T, HashSet<AGGREGATEPAREA_T>> aggregatedInto = new HashMap<>();
        
        aggregatePAreas.forEach((AGGREGATEPAREA_T parea) -> {
             AggregateableConceptGroup<CONCEPT_T, PAREA_T> aggregatePArea = (AggregateableConceptGroup<CONCEPT_T, PAREA_T>)parea;
             
             if(!aggregatePArea.getAggregatedGroups().isEmpty()) {
                 HashSet<PAREA_T> aggregatedPAreas = aggregatePArea.getAggregatedGroups();
                 
                 aggregatedPAreas.forEach((PAREA_T aggregatedPArea) -> {
                     if(!aggregatedInto.containsKey(aggregatedPArea)) {
                         aggregatedInto.put(aggregatedPArea, new HashSet<>());
                     }
                     
                     aggregatedInto.get(aggregatedPArea).add(parea);
                 });
             }
        });
        
        ArrayList<AggregatedGroupEntry<CONCEPT_T, PAREA_T, AGGREGATEPAREA_T>> entries = new ArrayList<>();
        
        aggregatedInto.forEach((PAREA_T aggregatedPArea, HashSet<AGGREGATEPAREA_T> aggregatedIntoPAreas) -> {
            entries.add(new AggregatedGroupEntry<>(aggregatedPArea, aggregatedIntoPAreas));
        });
        
        Collections.sort(entries, new Comparator<AggregatedGroupEntry<CONCEPT_T, PAREA_T, AGGREGATEPAREA_T>>() {
            public int compare(AggregatedGroupEntry<CONCEPT_T, PAREA_T, AGGREGATEPAREA_T> a, AggregatedGroupEntry<CONCEPT_T, PAREA_T, AGGREGATEPAREA_T> b) {
                return a.getAggregatedGroup().getRoot().getName().compareToIgnoreCase(b.getAggregatedGroup().getRoot().getName());
            }
        });
        
        aggregatedGroupList.setContents(entries);
    }

    @Override
    public void clearContents() {
        this.aggregatedGroupList.clearContents();
        this.conceptList.clearContents();
    }
}

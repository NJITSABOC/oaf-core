package edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.rightclickmanager.EntityRightClickMenuItem;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer.SimpleConceptRenderer;
import edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer.SimpleConceptRenderer.HierarchyDisplayInfo;
import edu.njit.cs.saboc.nat.generic.gui.panels.ConceptListPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.dataretrievers.CommonBrowserDataRetrievers;

/**
 * Displays the parents of the focus concept
 * 
 * @author Chris O
 * @param <T>
 */
public class ParentsPanel<T extends Concept> extends ConceptListPanel<T> {
    
    public ParentsPanel(
            NATBrowserPanel<T> mainPanel, 
            ConceptBrowserDataSource<T> dataSource,
            boolean showFilter) {
        
        super(mainPanel, 
                dataSource, 
                CommonBrowserDataRetrievers.getParentsRetriever(dataSource), 
                new SimpleConceptRenderer<>(dataSource, HierarchyDisplayInfo.None),
                true,
                showFilter,
                true);
        
        
        super.addRightClickMenuItem(new EntityRightClickMenuItem<T>("TEST!") {

            @Override
            public boolean isEnabledFor(T item) {
                return true;
            }

            @Override
            public void doActionFor(T item) {
                
            }

            @Override
            public boolean enabledWhenNoSelection() {
                return false;
            }

            @Override
            public void doEmptyAction() {
                
            }
        });
        
        super.addRightClickMenuItem(new EntityRightClickMenuItem<T>("TEST 2!") {

            @Override
            public boolean isEnabledFor(T item) {
                return false;
            }

            @Override
            public void doActionFor(T item) {
                
            }

            @Override
            public boolean enabledWhenNoSelection() {
                return true;
            }

            @Override
            public void doEmptyAction() {
                
            }
        });
        
    }
}

package edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.rightclickmanager.EntityRightClickMenuItem;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer.SimpleConceptRenderer;
import edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer.SimpleConceptRenderer.HierarchyDisplayInfo;
import edu.njit.cs.saboc.nat.generic.gui.panels.ConceptListPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.dataretrievers.CommonBrowserDataRetrievers;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.dialog.ErrorReportDialog;

/**
 * Panel for displaying the children of the focus concept
 * 
 * @author Chris O
 * @param <T>
 */
public class ChildrenPanel<T extends Concept> extends ConceptListPanel<T> {
    
    public ChildrenPanel(NATBrowserPanel<T> mainPanel, 
            ConceptBrowserDataSource<T> dataSource,
            boolean showFilter) {
        
        super(mainPanel, 
                dataSource, 
                CommonBrowserDataRetrievers.getChildrenRetriever(dataSource), 
                new SimpleConceptRenderer<>(dataSource, HierarchyDisplayInfo.Descendants),
                true,
                showFilter,
                true);
        
        
        
        this.addRightClickMenuItem(new EntityRightClickMenuItem<T>("Erroneous child (remove)") {

            @Override
            public boolean isEnabledFor(T item) {
                return true;
            }

            @Override
            public void doActionFor(T item) {
                ErrorReportDialog.displayErroneousChildDialog(mainPanel, dataSource, item);
            }

            @Override
            public boolean enabledWhenNoSelection() {
                return false;
            }

            @Override
            public void doEmptyAction() {
                
            }
        });
        
        this.addRightClickMenuItem(new EntityRightClickMenuItem<T>("Other error with child (describe)") {

            @Override
            public boolean isEnabledFor(T item) {
                return true;
            }

            @Override
            public void doActionFor(T item) {
                ErrorReportDialog.displayOtherChildErrorDialog(mainPanel, dataSource, item);
            }

            @Override
            public boolean enabledWhenNoSelection() {
                return false;
            }

            @Override
            public void doEmptyAction() {
                
            }
        });
        
        this.addRightClickMenuItem(new EntityRightClickMenuItem<T>("Missing child (add)") {

            @Override
            public boolean isEnabledFor(T item) {
                return true;
            }

            @Override
            public void doActionFor(T item) {
                doEmptyAction();
            }

            @Override
            public boolean enabledWhenNoSelection() {
                return true;
            }

            @Override
            public void doEmptyAction() {
                ErrorReportDialog.displayMissingChildDialog(mainPanel, dataSource);
            }
        });
        
        this.addRightClickMenuItem(new EntityRightClickMenuItem<T>("Other error") {

            @Override
            public boolean isEnabledFor(T item) {
                return true;
            }

            @Override
            public void doActionFor(T item) {
                doEmptyAction();
            }

            @Override
            public boolean enabledWhenNoSelection() {
                return true;
            }

            @Override
            public void doEmptyAction() {
                ErrorReportDialog.displayOtherErrorDialog(mainPanel, dataSource);
            }
        });
    }
}

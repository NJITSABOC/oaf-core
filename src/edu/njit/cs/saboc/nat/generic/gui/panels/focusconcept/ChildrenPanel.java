
package edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.rightclickmanager.EntityRightClickMenuItem;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer.ConceptDescendantsRenderer;
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
                new ConceptDescendantsRenderer<>(mainPanel, dataSource),
                true,
                showFilter,
                true);
        
        
        
        this.addRightClickMenuItem(new EntityRightClickMenuItem<T>("Remove erroneous child") {

            @Override
            public boolean isEnabledFor(T item) {
                return mainPanel.getAuditDatabase().getLoadedAuditSet().isPresent();
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
        
        this.addRightClickMenuItem(new EntityRightClickMenuItem<T>("Report other error with child") {

            @Override
            public boolean isEnabledFor(T item) {
                return mainPanel.getAuditDatabase().getLoadedAuditSet().isPresent();
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
        
        this.addRightClickMenuItem(new EntityRightClickMenuItem<T>("Add missing child") {

            @Override
            public boolean isEnabledFor(T item) {
                return enabledWhenNoSelection();
            }

            @Override
            public void doActionFor(T item) {
                doEmptyAction();
            }

            @Override
            public boolean enabledWhenNoSelection() {
                return mainPanel.getAuditDatabase().getLoadedAuditSet().isPresent();
            }

            @Override
            public void doEmptyAction() {
                ErrorReportDialog.displayMissingChildDialog(mainPanel, dataSource);
            }
        });
    }
}

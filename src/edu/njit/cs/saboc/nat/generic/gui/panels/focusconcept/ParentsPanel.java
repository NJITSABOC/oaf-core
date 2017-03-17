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
        
        
        this.addRightClickMenuItem(new EntityRightClickMenuItem<T>("Remove erroneous parent") {

            @Override
            public boolean isEnabledFor(T item) {
                return mainPanel.getAuditDatabase().getLoadedAuditSet().isPresent();
            }

            @Override
            public void doActionFor(T item) {
                ErrorReportDialog.displayErroneousParentDialog(mainPanel, dataSource, item);
            }

            @Override
            public boolean enabledWhenNoSelection() {
                return false;
            }

            @Override
            public void doEmptyAction() {
                
            }
        });
        
        this.addRightClickMenuItem(new EntityRightClickMenuItem<T>("Remove redundant parent") {

            @Override
            public boolean isEnabledFor(T item) {
                return mainPanel.getAuditDatabase().getLoadedAuditSet().isPresent();
            }

            @Override
            public void doActionFor(T item) {
                ErrorReportDialog.displayRedundantParentErrorDialog(mainPanel, dataSource, item);
            }

            @Override
            public boolean enabledWhenNoSelection() {
                return false;
            }

            @Override
            public void doEmptyAction() {
                
            }
        });
        
        this.addRightClickMenuItem(new EntityRightClickMenuItem<T>("Replace erroneous parent") {

            @Override
            public boolean isEnabledFor(T item) {
                return mainPanel.getAuditDatabase().getLoadedAuditSet().isPresent();
            }

            @Override
            public void doActionFor(T item) {
                ErrorReportDialog.displayReplaceParentDialog(mainPanel, dataSource, item);
            }

            @Override
            public boolean enabledWhenNoSelection() {
                return false;
            }

            @Override
            public void doEmptyAction() {
                
            }
        });
        
        this.addRightClickMenuItem(new EntityRightClickMenuItem<T>("Report other error with parent") {

            @Override
            public boolean isEnabledFor(T item) {
                return mainPanel.getAuditDatabase().getLoadedAuditSet().isPresent();
            }

            @Override
            public void doActionFor(T item) {
                ErrorReportDialog.displayOtherParentErrorDialog(mainPanel, dataSource, item);
            }

            @Override
            public boolean enabledWhenNoSelection() {
                return false;
            }

            @Override
            public void doEmptyAction() {
                
            }
        });
        
        this.addRightClickMenuItem(new EntityRightClickMenuItem<T>("Add missing parent") {

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
                ErrorReportDialog.displayMissingParentDialog(mainPanel, dataSource);
            }
        });
        
        this.addRightClickMenuItem(new EntityRightClickMenuItem<T>("Report other error") {

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
                return  mainPanel.getAuditDatabase().getLoadedAuditSet().isPresent();
            }

            @Override
            public void doEmptyAction() {
                ErrorReportDialog.displayOtherErrorDialog(mainPanel, dataSource);
            }
        });
    }
}
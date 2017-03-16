package edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.errorreport.error.semanticrel.MissingSemanticRelationshipError;
import edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer.SimpleConceptRenderer;
import edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer.SimpleConceptRenderer.HierarchyDisplayInfo;
import edu.njit.cs.saboc.nat.generic.gui.panels.ConceptListPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.dataretrievers.CommonBrowserDataRetrievers;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.SelectRelationshipErrorReportPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer.MissingSemanticRelationshipInitializer;
import javax.swing.JDialog;

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
        
        super.addResultSelectedListener(new ResultSelectedListener<T>() {

            @Override
            public void resultSelected(T result) {
                JDialog testDialog = new JDialog();
                
                SelectRelationshipErrorReportPanel<T, InheritableProperty, MissingSemanticRelationshipError<T, InheritableProperty>> missingParentPanel = 
                        new SelectRelationshipErrorReportPanel<>(mainPanel, dataSource);
                
                missingParentPanel.setInitializer(new MissingSemanticRelationshipInitializer<>(dataSource.getOntology()));
                
                testDialog.add(missingParentPanel);
                testDialog.setModal(true);
                testDialog.setSize(1000, 800);
                testDialog.setVisible(true);
            }

            @Override
            public void noResultSelected() {
                
            }
        });
        
    }
}
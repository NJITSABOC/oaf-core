package edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer.SimpleConceptRenderer;
import edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer.SimpleConceptRenderer.HierarchyDisplayInfo;
import edu.njit.cs.saboc.nat.generic.gui.panels.ConceptListPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.dataretrievers.CommonBrowserDataRetrievers;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.ErrorSubmissionPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.IncorrectParentPanel;
import javax.swing.JDialog;

/**
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
                showFilter,
                true);
        
        
        super.addResultSelectedListener(new ResultSelectedListener<T>() {

            @Override
            public void resultSelected(T result) {
                JDialog testDialog = new JDialog();
                
                IncorrectParentPanel<T> missingParentPanel = new IncorrectParentPanel<>(mainPanel, dataSource, result);
                
                testDialog.add(new ErrorSubmissionPanel<>(mainPanel, dataSource, missingParentPanel));
                testDialog.setModal(true);
                testDialog.setSize(600, 800);
                testDialog.setVisible(true);
            }

            @Override
            public void noResultSelected() {
                
            }
        });
        
    }
}
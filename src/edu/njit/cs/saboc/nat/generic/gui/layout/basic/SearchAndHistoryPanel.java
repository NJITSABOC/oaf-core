
package edu.njit.cs.saboc.nat.generic.gui.layout.basic;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.BaseNATPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.auditset.AuditSetPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.history.HistoryPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.search.SearchPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JTabbedPane;

/**
 * A tabbed panel that displays the search panel, history panel, and audit set list 
 * panel. Intended use is to display lists of concepts that can be navigated to.
 * 
 * @author Chris O
 * @param <T>
 */
public class SearchAndHistoryPanel<T extends Concept> extends BaseNATPanel<T> {

    private final JTabbedPane tabbedPane;

    private final SearchPanel<T> searchPanel;

    private final HistoryPanel<T> historyPanel;
    
    private final AuditSetPanel<T> auditPanel;

    public SearchAndHistoryPanel(NATBrowserPanel<T> mainPanel) {

        super(mainPanel);

        this.setLayout(new BorderLayout());

        searchPanel = new SearchPanel<>(mainPanel);
        searchPanel.setPreferredSize(new Dimension(450, -1));
        searchPanel.addSearchResultSelectedListener( (result) -> {
            mainPanel.getFocusConceptManager().navigateTo(result.getConcept());
        });

        historyPanel = new HistoryPanel<>(mainPanel);
        
        auditPanel = new AuditSetPanel<>(mainPanel);
        
        this.tabbedPane = new JTabbedPane();
        
        tabbedPane.addTab("Search", searchPanel);
        tabbedPane.addTab("History", historyPanel);
        tabbedPane.addTab("Audit Set", auditPanel);
        
        this.add(tabbedPane, BorderLayout.CENTER);
    }

    @Override
    public void setEnabled(boolean value) {
        super.setEnabled(value);
        
        this.tabbedPane.setEnabled(value);
        
        this.searchPanel.setEnabled(value);
        this.historyPanel.setEnabled(value);
        this.auditPanel.setEnabled(value);
    }

    @Override
    public void reset() {
        this.searchPanel.reset();
        this.historyPanel.reset();
        this.auditPanel.reset();
    }
}

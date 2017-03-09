
package edu.njit.cs.saboc.nat.generic.gui.layout.basic;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.panels.NATLayoutPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.auditset.AuditSetPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.history.HistoryPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.search.SearchPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JTabbedPane;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class SearchAndHistoryPanel<T extends Concept> extends NATLayoutPanel<T> {

    private final JTabbedPane tabbedPane;

    private final SearchPanel<T> searchPanel;

    private final HistoryPanel<T> historyPanel;
    
    private final AuditSetPanel<T> auditPanel;

    public SearchAndHistoryPanel(NATBrowserPanel<T> mainPanel,
            ConceptBrowserDataSource<T> dataSource) {

        super(mainPanel);

        this.setLayout(new BorderLayout());

        searchPanel = new SearchPanel<>(mainPanel, dataSource);
        searchPanel.setPreferredSize(new Dimension(450, -1));
        searchPanel.addSearchResultSelectedListener( (result) -> {
            mainPanel.getFocusConceptManager().navigateTo(result.getConcept());
        });

        historyPanel = new HistoryPanel<>(mainPanel, dataSource);
        
        auditPanel = new AuditSetPanel<>(mainPanel, dataSource);
        
        this.tabbedPane = new JTabbedPane();
        
        tabbedPane.addTab("Search", searchPanel);
        tabbedPane.addTab("History", historyPanel);
        tabbedPane.addTab("Audit Set", auditPanel);
        
        this.add(tabbedPane, BorderLayout.CENTER);
    }

    @Override
    protected void setFontSize(int fontSize) {
        
    }
}

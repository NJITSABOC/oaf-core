package edu.njit.cs.saboc.nat.generic.gui.layout;

import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.GenericNATBrowser;
import edu.njit.cs.saboc.nat.generic.gui.panels.FocusConceptPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.HierarchyMetricsPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.LogoPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.ParentChildPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.RelationshipPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.SearchPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.SynonymPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public class ClassicLayout extends NATLayout {

    private LogoPanel logoPanel;
    private ParentChildPanel parentPanel;
    private ParentChildPanel childPanel;
    private SearchPanel searchPanel;
    private SynonymPanel synonymPanel;
    private HierarchyMetricsPanel hierarchyMetricsPanel;

    private FocusConceptPanel focusConceptPanel;
    
    private RelationshipPanel relationshipPanel;

    private final ConceptBrowserDataSource dataSource;

    public ClassicLayout(ConceptBrowserDataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public JPanel doLayout(GenericNATBrowser mainPanel) {
        
        JPanel layout = new JPanel();
        layout.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.weightx = c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;

        c.gridx = 0;
        c.gridy = 0;
        logoPanel = new LogoPanel(mainPanel, dataSource);
        layout.add(logoPanel, c);

        c.gridx = 1;
        c.gridy = 0;
        parentPanel = new ParentChildPanel(mainPanel, ParentChildPanel.PanelType.PARENT, dataSource);
        layout.add(parentPanel, c);

        c.gridx = 2;
        c.gridy = 0;
        searchPanel = new SearchPanel(mainPanel, dataSource);
        layout.add(searchPanel, c);

        c.gridx = 0;
        c.gridy = 1;
        synonymPanel = new SynonymPanel(mainPanel, dataSource);
        layout.add(synonymPanel, c);

        c.gridx = 1;
        c.gridy = 1;
        focusConceptPanel = new FocusConceptPanel(mainPanel, dataSource);
        layout.add(focusConceptPanel, c);

        c.gridx = 2;
        c.gridy = 1;
        relationshipPanel = new RelationshipPanel(mainPanel, dataSource);
        layout.add(relationshipPanel, c);

        c.gridx = 1;
        c.gridy = 2;
        childPanel = new ParentChildPanel(mainPanel, ParentChildPanel.PanelType.CHILD, dataSource);
        layout.add(childPanel, c);

        c.gridx = 2;
        c.gridy = 2;

        hierarchyMetricsPanel = new HierarchyMetricsPanel(mainPanel, dataSource);

        layout.add(hierarchyMetricsPanel, c);
        
        return layout;
    }
}

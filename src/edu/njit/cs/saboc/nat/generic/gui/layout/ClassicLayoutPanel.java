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
public class ClassicLayoutPanel extends JPanel {

    private LogoPanel logoPanel;
    private ParentChildPanel parentPanel;
    private ParentChildPanel childPanel;
    private SearchPanel searchPanel;
    private SynonymPanel synonymPanel;
    private HierarchyMetricsPanel hierarchyMetricsPanel;

    private FocusConceptPanel focusConceptPanel;
    private RelationshipPanel relationshipPanel;

    public ClassicLayoutPanel(GenericNATBrowser mainPanel, ConceptBrowserDataSource dataSource) {
        this.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.weightx = c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;

        c.gridx = 0;
        c.gridy = 0;
        logoPanel = new LogoPanel(mainPanel, dataSource);
        this.add(logoPanel, c);

        c.gridx = 1;
        c.gridy = 0;
        parentPanel = new ParentChildPanel(mainPanel, ParentChildPanel.PanelType.PARENT, dataSource);
        this.add(parentPanel, c);

        c.gridx = 2;
        c.gridy = 0;
        searchPanel = new SearchPanel(mainPanel, dataSource);
        this.add(searchPanel, c);

        c.gridx = 0;
        c.gridy = 1;
        synonymPanel = new SynonymPanel(mainPanel, dataSource);
        this.add(synonymPanel, c);

        c.gridx = 1;
        c.gridy = 1;
        focusConceptPanel = new FocusConceptPanel(mainPanel, dataSource);
        this.add(focusConceptPanel, c);

        c.gridx = 2;
        c.gridy = 1;
        relationshipPanel = new RelationshipPanel(mainPanel, dataSource);
        this.add(relationshipPanel, c);

        c.gridx = 1;
        c.gridy = 2;
        childPanel = new ParentChildPanel(mainPanel, ParentChildPanel.PanelType.CHILD, dataSource);
        this.add(childPanel, c);

        c.gridx = 2;
        c.gridy = 2;

        hierarchyMetricsPanel = new HierarchyMetricsPanel(mainPanel, dataSource);

        this.add(hierarchyMetricsPanel, c);
    }
}

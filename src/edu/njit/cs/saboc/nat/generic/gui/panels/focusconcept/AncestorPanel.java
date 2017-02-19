package edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.panels.BaseNATPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class AncestorPanel<T extends Concept> extends BaseNATPanel<T>  {
    
    private final ParentsPanel<T> parentsPanel;
    private final GrandparentsPanel<T> grandparentsPanel;
    private final FocusConceptAncestorsPanel<T> ancestorsPanel;
    
    private final CardLayout contentPanelLayout;
    private final JPanel contentPanel;

    private final JRadioButton btnShowParentsOnly;
    private final JRadioButton btnIncludeGrandparents;
    private final JRadioButton btnShowAllAncestors;
    
    public AncestorPanel(NATBrowserPanel<T> browserPanel, 
            ConceptBrowserDataSource<T> dataSource) {
        
        super(browserPanel, dataSource);
        
        this.setLayout(new BorderLayout());
        
        this.parentsPanel = new ParentsPanel<>(browserPanel, dataSource, true);
        this.grandparentsPanel = new GrandparentsPanel<>(browserPanel, dataSource);
        this.ancestorsPanel = new FocusConceptAncestorsPanel<>(browserPanel, dataSource, true);
        
        this.contentPanelLayout = new CardLayout();
        this.contentPanel = new JPanel(contentPanelLayout);

        this.contentPanel.add(parentsPanel, "Parents");
        this.contentPanel.add(grandparentsPanel, "Grandparents");
        this.contentPanel.add(ancestorsPanel, "Ancestors");
        
        ButtonGroup optionGroup = new ButtonGroup();
        
        this.btnShowParentsOnly = new JRadioButton("Parents Only");
        this.btnShowParentsOnly.addActionListener( (ae) -> {
            contentPanelLayout.show(contentPanel, "Parents");
        });
  
        
        this.btnIncludeGrandparents = new JRadioButton("Parents and Grandparents");
        this.btnIncludeGrandparents.addActionListener( (ae) -> {
            contentPanelLayout.show(contentPanel, "Grandparents");
        });
        
        this.btnShowAllAncestors = new JRadioButton("All Ancestors");
        this.btnShowAllAncestors.addActionListener( (ae) -> {
            contentPanelLayout.show(contentPanel, "Ancestors");
        });
        
        optionGroup.add(btnShowParentsOnly);
        optionGroup.add(btnIncludeGrandparents);
        optionGroup.add(btnShowAllAncestors);
        
        this.btnShowParentsOnly.setSelected(true);
        
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        northPanel.add(btnShowParentsOnly);
        northPanel.add(btnIncludeGrandparents);
        northPanel.add(btnShowAllAncestors);
        
        this.add(northPanel, BorderLayout.NORTH);
        this.add(contentPanel, BorderLayout.CENTER);
    }

    @Override
    protected void setFontSize(int fontSize) {
        
    }
}

package edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.panels.BaseNATPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class DescendantsPanel<T extends Concept> extends BaseNATPanel<T>  {
    
    private final ChildrenPanel<T> parentsPanel;
    private final GrandchildrenPanel<T> grandparentsPanel;
    
    private final CardLayout contentPanelLayout;
    private final JPanel contentPanel;

    private final JRadioButton btnShowChildrenOnly;
    private final JRadioButton btnIncludeGrandchildren;
    
    public DescendantsPanel(NATBrowserPanel<T> browserPanel, ConceptBrowserDataSource<T> dataSource) {
        super(browserPanel, dataSource);
        
        this.setLayout(new BorderLayout());
        
        this.parentsPanel = new ChildrenPanel<>(browserPanel, dataSource, true);
        this.grandparentsPanel = new GrandchildrenPanel<>(browserPanel, dataSource);
        
        this.contentPanelLayout = new CardLayout();
        this.contentPanel = new JPanel(contentPanelLayout);
                
        this.contentPanel.add(parentsPanel, "Children");
        this.contentPanel.add(grandparentsPanel, "Grandchildren");
        
        ButtonGroup optionGroup = new ButtonGroup();
        
        this.btnShowChildrenOnly = new JRadioButton("Children Only");
        this.btnShowChildrenOnly.addActionListener( (ae) -> {
            contentPanelLayout.show(contentPanel, "Children");
        });
        
        this.btnIncludeGrandchildren = new JRadioButton("Children and Grandchildren");
        this.btnIncludeGrandchildren.addActionListener( (ae) -> {
            contentPanelLayout.show(contentPanel, "Grandchildren");
        });
        
        optionGroup.add(btnShowChildrenOnly);
        optionGroup.add(btnIncludeGrandchildren);
        
        this.btnShowChildrenOnly.setSelected(true);
        
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        northPanel.add(btnShowChildrenOnly);
        northPanel.add(btnIncludeGrandchildren);
        
        this.add(northPanel, BorderLayout.NORTH);
        this.add(contentPanel, BorderLayout.CENTER);
    }

    @Override
    protected void setFontSize(int fontSize) {
        
    }
}

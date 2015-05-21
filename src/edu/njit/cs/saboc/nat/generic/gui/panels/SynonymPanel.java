package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.blu.core.gui.iconmanager.IconManager;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.FilterableList;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.FocusConcept;
import edu.njit.cs.saboc.nat.generic.GenericNATBrowser;
import edu.njit.cs.saboc.nat.generic.gui.filterablelist.FilterableSynonymEntry;
import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;


/**
 * The middle left panel of the NAT.  Displays synonyms of the Focus Concept.
 */
public class SynonymPanel extends BaseNavPanel implements Toggleable {
    private FilterableList list = new FilterableList();

    public SynonymPanel(final GenericNATBrowser mainPanel, ConceptBrowserDataSource dataSource) {
        super(mainPanel, dataSource);

        focusConcept.addDisplayPanel(FocusConcept.Fields.SYNONYMS, this);
        
        
        setBackground(mainPanel.getNeighborhoodBGColor());
        setLayout(new BorderLayout());
        add(list, BorderLayout.CENTER);

        JButton filterButton = new JButton();
        filterButton.setBackground(mainPanel.getNeighborhoodBGColor());
        filterButton.setPreferredSize(new Dimension(24, 24));
        filterButton.setIcon(IconManager.getIconManager().getIcon("filter.png"));
        filterButton.setToolTipText("Filter these entries");
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                list.toggleFilterPanel();
            }
        });

        FlowLayout buttonLayout = new FlowLayout(FlowLayout.TRAILING);
        buttonLayout.setHgap(0);
        JPanel northPanel = new JPanel(buttonLayout);
        northPanel.setBackground(mainPanel.getNeighborhoodBGColor());
        northPanel.add(filterButton);
        northPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        add(northPanel, BorderLayout.NORTH);
        
        setBorder(BaseNavPanel.createConceptBorder("Synonyms"));
    }

    @Override
    public void dataPending() {
        setBorder(BaseNavPanel.createConceptBorder("Synonyms"));
        list.showPleaseWait();
    }

    public void dataEmpty() {
        setBorder(BaseNavPanel.createConceptBorder("Synonyms"));
        list.showDataEmpty();
    }

    @Override
    public void dataReady() {
        ArrayList<String> synonyms = (ArrayList<String>)focusConcept.getConceptList(FocusConcept.Fields.SYNONYMS);
        
        ArrayList<Filterable> synonymEntries = new ArrayList<Filterable>();

        for(String s : synonyms) {
            synonymEntries.add(new FilterableSynonymEntry(s));
        }

        list.setContents(synonymEntries);
        
        setBorder(BaseNavPanel.createConceptBorder("Synonyms  (" +
                synonyms.size() + ")"));
    }
    
    public void toggle() {
        list.toggleFilterPanel();
    }
}

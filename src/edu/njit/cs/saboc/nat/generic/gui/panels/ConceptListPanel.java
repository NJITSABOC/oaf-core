package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.blu.core.gui.iconmanager.IconManager;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.data.BrowserConcept;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.FocusConcept;
import edu.njit.cs.saboc.nat.generic.GenericNATBrowser;
import edu.njit.cs.saboc.nat.generic.gui.filterablelist.BrowserNavigableFilterableList;
import edu.njit.cs.saboc.nat.generic.gui.filterablelist.FilterableConceptEntry;
import edu.njit.cs.saboc.nat.generic.gui.listeners.DataLoadedListener;
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
 *
 * @author Chris O
 */
public class ConceptListPanel extends BaseNavPanel implements Toggleable {
    private BrowserNavigableFilterableList list;
    
    private FocusConcept.Fields field;
    
    private DataLoadedListener<ArrayList<BrowserConcept>> dataLoadedListener;
    
    public ConceptListPanel(GenericNATBrowser mainPanel, FocusConcept.Fields field, 
            ConceptBrowserDataSource dataSource, DataLoadedListener<ArrayList<BrowserConcept>> listener) {
        
        super(mainPanel, dataSource);
        
        list = new BrowserNavigableFilterableList(mainPanel.getFocusConcept(), mainPanel.getOptions());
        
        this.field = field;
        
        this.dataLoadedListener = listener;
        
        mainPanel.getFocusConcept().addDisplayPanel(field, this);
        
        this.setLayout(new BorderLayout());
        
        this.add(list, BorderLayout.CENTER);
        
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
    }
    
    public void dataReady() {
        ArrayList<BrowserConcept> concepts = (ArrayList<BrowserConcept>) focusConcept.getConceptList(field);

        ArrayList<Filterable> conceptEntries = new ArrayList<Filterable>();

        for (BrowserConcept c : concepts) {
            conceptEntries.add(new FilterableConceptEntry(c));
        }

        list.setContents(conceptEntries);
        
        if(dataLoadedListener != null) {
            dataLoadedListener.dataLoaded(concepts);
        }
    }

    public void dataPending() {
        list.showPleaseWait();
    }

    public void dataEmpty() {
        list.showDataEmpty();
    }
    
    public void toggle() {
        list.toggleFilterPanel();
    }
}

package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.blu.core.gui.iconmanager.IconManager;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.FilterableList;
import edu.njit.cs.saboc.nat.generic.GenericNATBrowser;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.fields.NATDataField;
import edu.njit.cs.saboc.nat.generic.gui.filterablelist.FilterableSynonymEntry;
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
public class StringListPanel extends BaseNavPanel implements Toggleable {

    private FilterableList list = new FilterableList();
    
    private NATDataField<ArrayList<String>> field;
    
    private DataLoadedListener<ArrayList<String>> dataLoadedListener;

    public StringListPanel(final GenericNATBrowser mainPanel, NATDataField<ArrayList<String>> field, 
            ConceptBrowserDataSource dataSource, 
            DataLoadedListener<ArrayList<String>> dataLoadedListener, 
            boolean showFilter) {
        
        super(mainPanel, dataSource);
        
        this.field = field;
        this.dataLoadedListener = dataLoadedListener;

        focusConcept.addDisplayPanel(field, this);

        setBackground(mainPanel.getNeighborhoodBGColor());
        setLayout(new BorderLayout());
        add(list, BorderLayout.CENTER);

        if (showFilter) {

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
    }

    @Override
    public void dataPending() {
        list.showPleaseWait();
    }

    public void dataEmpty() {
        list.showDataEmpty();
    }

    @Override
    public void dataReady() {
        ArrayList<String> strs = field.getData(focusConcept.getConcept());
        
        ArrayList<Filterable> strEntries = new ArrayList<Filterable>();

        for (String s : strs) {
            strEntries.add(new FilterableSynonymEntry(s));
        }

        list.setContents(strEntries);
        
        dataLoadedListener.dataLoaded(strs);
    }

    public void toggle() {
        list.toggleFilterPanel();
    }
}

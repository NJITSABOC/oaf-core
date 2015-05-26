package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.GenericNATBrowser;
import edu.njit.cs.saboc.nat.generic.gui.filterablelist.FilterableSynonymEntry;
import edu.njit.cs.saboc.nat.generic.gui.listeners.DataLoadedListener;
import java.awt.BorderLayout;
import java.util.ArrayList;
import static java.util.Collections.list;
import javax.swing.JPanel;


/**
 * The middle left panel of the NAT.  Displays synonyms of the Focus Concept.
 */
public class SynonymPanel extends JPanel {
    private StringListPanel listPanel;

    public SynonymPanel(final GenericNATBrowser mainPanel, ConceptBrowserDataSource dataSource) {
        
        listPanel = new StringListPanel(mainPanel, mainPanel.getFocusConcept().COMMON_DATA_FIELDS.SYNONYMS, 
                dataSource, 
                new DataLoadedListener<ArrayList<String>>() { 
                    public void dataLoaded(ArrayList<String> data) {
                        setBorder(BaseNavPanel.createConceptBorder(String.format("Synonyms (%d)", data.size())));
                    }
                },
                true);

        add(listPanel, BorderLayout.CENTER);
        
        setBorder(BaseNavPanel.createConceptBorder("Synonyms"));
    }
}

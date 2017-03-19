package edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.rightclickmenu;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.rightclickmanager.EntityRightClickMenuGenerator;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.dialog.ErrorReportDialog;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class ChildrenRightClickMenu<T extends Concept> extends EntityRightClickMenuGenerator<T> {
    
    private final NATBrowserPanel<T> mainPanel;
    private final ConceptBrowserDataSource<T> dataSource;
    
    public ChildrenRightClickMenu(
            NATBrowserPanel<T> mainPanel, 
            ConceptBrowserDataSource<T> dataSource) {
        
        this.mainPanel = mainPanel;
        this.dataSource = dataSource;
    }

    @Override
    public ArrayList<JComponent> buildRightClickMenuFor(T item) {
        ArrayList<JComponent> components = new ArrayList<>();

        JLabel nameLabel = new JLabel(item.getName());
        nameLabel.setFont(nameLabel.getFont().deriveFont(14.0f));

        components.add(nameLabel);
        components.add(new JSeparator());

        return components;
    }

    @Override
    public ArrayList<JComponent> buildEmptyListRightClickMenu() {
        ArrayList<JComponent> components = new ArrayList<>();
        
        JMenuItem reportMissingParent = new JMenuItem("Add missing child");
        reportMissingParent.addActionListener((ae) -> {
            ErrorReportDialog.displayMissingChildDialog(mainPanel, dataSource);
        });
        
        components.add(reportMissingParent);

        return components;
    }
    
}
package edu.njit.cs.saboc.nat.generic;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.layout.NATLayout;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

/**
 * Generic Internal Frame for displaying a NAT in an internal windowed frame 
 * 
 * @author Chris
 * @param <T>
 */
public class NATBrowserFrame<T extends Concept> extends JInternalFrame {

    private final GenericNATBrowserPanel<T> browser;
    
    public NATBrowserFrame(
            JFrame parentFrame, 
            ConceptBrowserDataSource<T> dataSource, 
            NATLayout layout) {
        
        super("Neighborhood Auditing Tool (NAT)", 
                true, 
                true, 
                true);
        
        this.browser = new GenericNATBrowserPanel<>(parentFrame, dataSource, layout);
        
        this.setSize(1400, 600);
        
        this.add(browser);
        
        this.setVisible(true);
    }

    public void nagivateTo(T c) {
        browser.navigateTo(c);
    }
}

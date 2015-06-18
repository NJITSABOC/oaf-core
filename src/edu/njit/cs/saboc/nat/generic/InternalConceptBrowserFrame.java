package edu.njit.cs.saboc.nat.generic;

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
public class InternalConceptBrowserFrame<T> extends JInternalFrame {

    /**
     * The NAT browser
     */
    private final GenericNATBrowser<T> browser;
    
    public InternalConceptBrowserFrame(JFrame parentFrame, ConceptBrowserDataSource<T> dataSource, NATLayout layout) {
        super("Generic NAT",
                true, //resizable
                true, //closable
                true, //maximizable
                true);//iconifiable
        
        browser = new GenericNATBrowser<T>(parentFrame, dataSource, layout);
        

        this.setSize(1200, 550);
 
        this.add(browser);
        
        this.setVisible(true);
    }

    /**
     * 
     * @param c Concept to navigate to
     */
    public void nagivateTo(T c) {
        browser.navigateTo(c);
    }
}

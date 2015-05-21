package edu.njit.cs.saboc.nat.generic;

import edu.njit.cs.saboc.nat.generic.data.BrowserConcept;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

/**
 *
 * @author Chris
 */
public class InternalConceptBrowserFrame extends JInternalFrame {

    private GenericNATBrowser browser;
    
    public InternalConceptBrowserFrame(JFrame parentFrame, ConceptBrowserDataSource dataSource) {
        super("Generic NAT",
                true, //resizable
                true, //closable
                true, //maximizable
                true);//iconifiable
        
        browser = new GenericNATBrowser(parentFrame, dataSource);

        setSize(1200, 550);

        this.add(browser);
        this.setVisible(true);
    }

    public void nagivateTo(BrowserConcept c) {
        browser.navigateTo(c);
    }
}

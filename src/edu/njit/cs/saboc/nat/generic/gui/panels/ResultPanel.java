package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import javax.swing.SwingUtilities;

/**
 *
 * @author Chris O
 * @param <T>
 * @param <V>
 */
public abstract class ResultPanel<T extends Concept, V> extends BaseNATPanel<T> {
    
    public interface DataRetriever<T extends Concept, V> {
        public V getData(T concept);
        
        public String getDataType();
    }
       
    private final DataRetriever<T, V> dataRetriever;
    
    public ResultPanel(
            NATBrowserPanel<T> mainPanel,
            ConceptBrowserDataSource<T> dataSource,
            DataRetriever<T, V> dataRetriever) {
        
        super(mainPanel, dataSource);
        
        this.dataRetriever = dataRetriever;
        
        mainPanel.getFocusConceptManager().addFocusConceptListener( (concept) -> {
            
            
            dataPending();
            
            Thread loadThread = new Thread( () -> {
                V result = dataRetriever.getData(concept);
                
                SwingUtilities.invokeLater( () -> {
                    displayResults(result);
                });
            });
            
            loadThread.start();
        });
    }
    
    protected DataRetriever<T, V> getDataRetriever() {
        return dataRetriever;
    }
    
    public abstract void dataPending();
    public abstract void displayResults(V data);
}

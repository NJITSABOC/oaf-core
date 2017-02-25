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
       
    private DataRetriever<T, V> dataRetriever;
    
    private boolean isActive;
    private boolean loaded;
    
    public ResultPanel(
            NATBrowserPanel<T> mainPanel,
            ConceptBrowserDataSource<T> dataSource,
            DataRetriever<T, V> dataRetriever) {
        
        super(mainPanel, dataSource);
        
        this.dataRetriever = dataRetriever;
        
        mainPanel.getFocusConceptManager().addFocusConceptListener( (concept) -> {
            this.loaded = false;
            
            if(isActive) {                
                doLoad(concept);
            }
        });
        
        this.isActive = true;
        this.loaded = false;
    }
    
    public void setActive(boolean value) {
        this.isActive = value;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    private void doLoad(T concept) {
        if(!isActive) {
            return;
        }
        
        if(loaded) {
            return;
        }
        
        this.loaded = true;
        
        dataPending();

        Thread loadThread = new Thread(() -> {
            V result = dataRetriever.getData(concept);

            SwingUtilities.invokeLater(() -> {
                displayResults(result);
            });
        });

        loadThread.start();
    }
    
    protected void reload() {
        doLoad(getMainPanel().getFocusConceptManager().getActiveFocusConcept());
    }
    
    protected void forceReload() {
        this.loaded = false;
        
        reload();
    }
    
    public void setDataRetriever(DataRetriever<T, V> dataRetriever) {
        this.dataRetriever = dataRetriever;
        
        if(isActive) {
            forceReload();
        }
    }
    
    public DataRetriever<T, V> getCurrentDataRetriever() {
        return dataRetriever;
    }
    
    public abstract void dataPending();
    public abstract void displayResults(V data);
}

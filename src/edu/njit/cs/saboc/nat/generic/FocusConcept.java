package edu.njit.cs.saboc.nat.generic;


import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.fields.CommonDataFields;
import edu.njit.cs.saboc.nat.generic.fields.NATDataField;
import edu.njit.cs.saboc.nat.generic.gui.panels.BaseNavPanel;
import javax.swing.SwingUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class encapsulates the Focus Concept and handles getting
 * the concepts related to it.
 * 
 * @author Paul Accisano
 */
public class FocusConcept<T> {
    
    private ConceptBrowserDataSource<T> dataSource;
    private GenericNATBrowser<T> browser;
    
    private T activeFocusConcept = null;

    // Concept data
    private final Map<NATDataField, Object> dataLists = new HashMap<>();

    // Whether or not a given field has already been filled
    private final Map<NATDataField, Boolean> alreadyFilled = new HashMap<>();
    
    // The panels that actually display concepts
    private final Map<NATDataField, BaseNavPanel<T>> displayPanels = new HashMap<>();

    // The panels that need to be notified of a concept change
    private final ArrayList<BaseNavPanel<T>> listeners = new ArrayList<>();

    private History<T> history = new History<T>();

    private final Options options;

    private ArrayList<UpdateThread> updateThreads = new ArrayList<>();
    
    public final CommonDataFields COMMON_DATA_FIELDS;

    public FocusConcept(GenericNATBrowser browser, Options options, ConceptBrowserDataSource<T> dataSource) {
        this.browser = browser;
        this.options = options;
        this.dataSource = dataSource;
        
        this.COMMON_DATA_FIELDS = new CommonDataFields(dataSource);
    }

    public History getHistory() {
        return history;
    }
    
    public void setNATEnabled(boolean value) {
        if(value) {
            this.reloadCurrentConcept();
            options.setNavigationLocked(false);
        } else {
            options.setNavigationLocked(true);
            this.setAllDataPending();
        }
    }

    // Sets panel corresponding to the given field.  Called by NATtab upon
    // panel construction.
    public void addFocusConceptListener(BaseNavPanel fcl) {
        listeners.add(fcl);
    }

    public void addDisplayPanel(NATDataField displayField, BaseNavPanel panel) {
        displayPanels.put(displayField, panel);
    }

    public void reloadCurrentConcept() {
        if(activeFocusConcept != null) {
            navigate(getConcept());
        }
    }

    public void navigateRoot() {
        navigate(dataSource.getRoot());
    }

    // Sets the Focus Concept.
    public void navigate(T c) {
        activeFocusConcept = c;

        history.addHistoryConcept(c);
        
        displayPanels.keySet().forEach((NATDataField field) -> { alreadyFilled.put(field, false); });

        // Clear out the old stuff
        dataLists.clear();

        cancel();

        // Update all fields
        updateAll();
    }
    
    public GenericNATBrowser<T> getAssociatedBrowser() {
        return browser;
    }

    public T getConcept() {
        return activeFocusConcept;
    }

    // Convenience methods
    public String getConceptId() {
        return dataSource.getConceptId(activeFocusConcept);
    }

    public String getConceptName() {
        return dataSource.getConceptName(activeFocusConcept);
    }

    // Returns the concepts in a field
    public Object getConceptList(NATDataField field) {
        return dataLists.get(field);
    }

    public void setAllDataEmpty() {
        displayPanels.keySet().forEach((NATDataField field) -> {displayPanels.get(field).dataEmpty(); });
    }
    
    public void setAllDataPending() {
        displayPanels.keySet().forEach((NATDataField field) -> { displayPanels.get(field).dataPending(); });
    }

    public void updateAll() {
        displayPanels.keySet().forEach( (NATDataField field) -> { update(field); });
        
        displayPanels.values().forEach((BaseNavPanel panel) -> {panel.focusConceptChanged();});
    }

    // Updates the given field of the Focus Concept
    public void update(NATDataField field) {
        
        // Don't update a panel that does not exist.
        if(displayPanels.get(field) == null) {
            return;
        }

        // If we already have the requested data, update the corresponding
        // panel immediately.
        if(alreadyFilled.get(field)) {
            displayPanels.get(field).dataReady();
            return;
        }

        // Tell the corresponding panel that data is on the way.
        displayPanels.get(field).dataPending();

        UpdateThread thd = new UpdateThread(field);
        
        thd.start();
        
        updateThreads.add(thd);
    }

    // Cancels any query that is executing and clears the query queue.
    protected void cancel() {
        for(UpdateThread t : updateThreads) {
            t.cancel();
        }

        updateThreads.clear();
    }

    private class UpdateThread extends Thread {
        public NATDataField field;

        public Object result = null;
        
        private boolean cancelled = false;
        private boolean executing = true;

        public UpdateThread(NATDataField field) {
            this.field = field;
        }

        public void cancel() {
            cancelled = true;
        }

        public boolean isCancelled() {
            return cancelled;
        }

        public boolean isExecuting() {
            return executing;
        }

        // The thread entry function
        @Override
        public void run() {

            result = field.getData(activeFocusConcept);

            // Send the notification to the main thread that we're done.
            try {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        executing = false;
                        finished(UpdateThread.this);
                    }
                });
            }
            catch(Exception e) {
                
            }
        }
    }

    // Executed by the main thread after an OracleThread finishes
    private void finished(UpdateThread thread) {
        // If the thread was cancelled, then this information is outdated.
        if (!thread.isCancelled()) {
            dataLists.put(thread.field, thread.result);

            alreadyFilled.put(thread.field, true);

            displayPanels.get(thread.field).dataReady();
        }
    }
}

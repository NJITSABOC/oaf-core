
package edu.njit.cs.saboc.blu.core.gui.dialogs;

/**
 *
 * @author Chris
 */
public class AbNLoadingStatusMonitor {
    public static final String STATE_PENDING = "Pending";
    public static final String STATE_LOADING_XML = "Loading Taxonomy Information";
    public static final String STATE_INIT_DATA_STRUCTURES = "Initializing Data Structures";
    public static final String STATE_ANALYZING_HIERARCHY = "Analyzing Hierarchy";
    public static final String STATE_BUILD_PAREAS = "Creating Partial-areas";
    public static final String STATE_BUILD_AREAS = "Creating Areas";
    public static final String STATE_BUILD_GRAPH = "Building Visualization";
    public static final String STATE_COMPLETE = "Complete";
            
    private int percentComplete;
    
    private String state;
    
    public AbNLoadingStatusMonitor() {
        percentComplete = 0;
        state = AbNLoadingStatusMonitor.STATE_PENDING;
    }
    
    public void updateState(String state, int percent) {
        this.percentComplete = percent;
        this.state = state;
    }
    
    public void updatePercent(int percent) {
        this.percentComplete = percent;
    }
    
    public int getPercentComplete() {
        return percentComplete;
    }
    
    public String getStateName() {
        return state;
    }
}

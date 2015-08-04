package edu.njit.cs.saboc.nat.generic;

import edu.njit.cs.saboc.nat.generic.gui.listeners.NATOptionsListener;
import java.util.ArrayList;

/**
 * A singleton class that keeps track of user-specified options and initial settings.
 */
public class NATOptions {
    
    /**
     * List of attached listeners
     */
    private final ArrayList<NATOptionsListener> listeners = new ArrayList<>();
    
    /**
     * If concept IDs are displayed in result lists
     */
    private boolean idsVisible = true;
    
    /**
     * If URIs are displayed in after entries in result lists
     */
    private boolean urisVisible = false;
    
    /**
     * Current font size of the NAT
     */
    private int fontSize = 14;
    
    
    private boolean lockNavigation = false;

    public NATOptions() {
        
    }
    
    /**
     * 
     * @param listener The listener to be added
     */
    public void addOptionsListener(NATOptionsListener listener) {
        listeners.add(listener);
    }

    /**
     * 
     * @return true if ids are to be displayed in result lists, false otherwise
     */
    public boolean areIdsVisible() {
        return idsVisible;
    }

    /**
     * 
     * @param value true if ids should be displayed in result lists, false otherwise
     */
    public void setIdsVisible(boolean value) {
        this.idsVisible = value;
        
        listeners.forEach((NATOptionsListener listener) -> {
            listener.showIDChanged(value);
        });
    }
    
    /**
     * 
     * @return true if URIs are to be displayed in result lists, false otherwise
     */
    public boolean areURIsVisible() {
        return urisVisible;
    }
    
    /**
     * 
     * @param value true if ids should be displayed in result lists, false otherwise
     */
    public void setURIsVisible(boolean value) {
        this.urisVisible = value;
        
        listeners.forEach((NATOptionsListener listener) -> {
            listener.showURIChanged(value);
        });
    }
    
    /**
     * 
     * @return The current font size
     */
    public int getFontSize() {
        return fontSize;
    }
    
    /**
     * 
     * @param fontSize The font size to be used in the NAT (includes result lists and other UI elements)
     */
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
        
        listeners.forEach((NATOptionsListener listener) -> {
            listener.fontSizeChanged(fontSize);
        });
    }
    
    public void forceFontSizeRefresh() {
        setFontSize(fontSize);
    }
    
    public boolean navigationLocked() {
        return lockNavigation;
    }
    
    public void setNavigationLocked(boolean value) {
        this.lockNavigation = value;
    }
}
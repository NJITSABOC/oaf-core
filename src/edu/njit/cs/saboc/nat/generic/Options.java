package edu.njit.cs.saboc.nat.generic;

import edu.njit.cs.saboc.nat.generic.gui.listeners.NATOptionsListener;
import java.util.ArrayList;

/**
 * A singleton class that keeps track of user-specified options.
 */
public class Options {
    
    private final ArrayList<NATOptionsListener> listeners = new ArrayList<>();
    
    private boolean idsVisible = true;
    
    private int fontSize = 14;

    public Options() {
        
    }
    
    public void addOptionsListener(NATOptionsListener listener) {
        listeners.add(listener);
    }

    public boolean areIdsVisible() {
        return idsVisible;
    }

    public void setIdsVisible(boolean value) {
        this.idsVisible = value;
        
        listeners.forEach((NATOptionsListener listener) -> {
            listener.showIDChanged(value);
        });
    }
    
    public int getFontSize() {
        return fontSize;
    }
    
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
        
        listeners.forEach((NATOptionsListener listener) -> {
            listener.fontSizeChanged(fontSize);
        });
    }
}
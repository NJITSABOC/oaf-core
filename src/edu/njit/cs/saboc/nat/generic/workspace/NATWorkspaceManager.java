package edu.njit.cs.saboc.nat.generic.workspace;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.toolstate.OAFRecentlyOpenedFileManager;
import edu.njit.cs.saboc.blu.core.utils.toolstate.OAFRecentlyOpenedFileManager.RecentlyOpenedFileException;
import edu.njit.cs.saboc.blu.core.utils.toolstate.RecentlyOpenedFile;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 */
public class NATWorkspaceManager<T extends Concept> {

    private final OAFRecentlyOpenedFileManager recentWorkspaceFileManager;

    private final NATBrowserPanel<T> mainPanel;
    
    public NATWorkspaceManager(
            NATBrowserPanel<T> mainPanel,
            OAFRecentlyOpenedFileManager recentWorkspaceFileManager) {
        
        this.mainPanel = mainPanel;
        this.recentWorkspaceFileManager = recentWorkspaceFileManager;
    }

    public ArrayList<RecentlyOpenedFile> getRecentWorkspaces() {
        return recentWorkspaceFileManager.getRecentlyOpenedFiles();
    }
    
    public NATWorkspace<T> getWorkspaceFor(File file) {
        return new NATWorkspace(mainPanel, file);
    }
    
    public void addOrUpdateWorkspace(NATWorkspace workspace) {
        
        File file = workspace.getFile();
        
        try {
            recentWorkspaceFileManager.addOrUpdateRecentlyOpenedFile(file);
        } catch(RecentlyOpenedFileException rofe) {
            
        }
    }
    
    public NATWorkspace createNewWorkspace(File file, String name) {
        NATWorkspace workspace = NATWorkspace.createNewWorkspaceFromCurrent(mainPanel, file, name);
        
        this.addOrUpdateWorkspace(workspace);
        
        return workspace;
    }
}

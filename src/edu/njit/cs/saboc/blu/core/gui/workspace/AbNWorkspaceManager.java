package edu.njit.cs.saboc.blu.core.gui.workspace;

import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivationParser;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.MultiAbNGraphFrame;
import edu.njit.cs.saboc.blu.core.utils.toolstate.OAFRecentlyOpenedFileManager;
import edu.njit.cs.saboc.blu.core.utils.toolstate.RecentlyOpenedFile;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public class AbNWorkspaceManager {

    private final OAFRecentlyOpenedFileManager recentWorkspaceFileManager;
    
    private final MultiAbNGraphFrame graphFrame;
    
    private final AbNDerivationParser abnParser;

    public AbNWorkspaceManager(
            MultiAbNGraphFrame graphFrame,
            OAFRecentlyOpenedFileManager recentWorkspaceFileManager,
            AbNDerivationParser abnParser) {

        this.graphFrame = graphFrame;
        this.recentWorkspaceFileManager = recentWorkspaceFileManager;
        this.abnParser = abnParser;
    }
    
    public OAFRecentlyOpenedFileManager getRecentWorkspaceFileManager() {
        return recentWorkspaceFileManager;
    }
    
    public AbNWorkspace getWorkspaceFor(File file) {
        return new AbNWorkspace(graphFrame, file, abnParser);
    }

    public void addOrUpdateWorkspace(AbNWorkspace workspace) {

        File file = workspace.getFile();

        try {
            recentWorkspaceFileManager.addOrUpdateRecentlyOpenedFile(file);
        } catch (OAFRecentlyOpenedFileManager.RecentlyOpenedFileException rofe) {

        }
    }

    public AbNWorkspace createNewWorkspace(File file, String name) {
        
        AbNWorkspace workspace = AbNWorkspace.createNewWorkspaceFromCurrent(graphFrame, name, file, abnParser);

        this.addOrUpdateWorkspace(workspace);

        return workspace;
        
    }
}

package edu.njit.cs.saboc.blu.core.utils.toolstate;

import edu.njit.cs.saboc.blu.core.utils.toolstate.OAFRecentlyOpenedFileManager.RecentlyOpenedFileException;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Chris O
 */
public class OAFStateFileManager {
    
    private final OAFRecentlyOpenedFileManager recentlyOpenedOntologiesManager;
    
    private final OAFOntologySpecificFileManager recentAbNWorkspaceManager;
    
    private final OAFOntologySpecificFileManager recentNATWorkspaceManager;
    
    private final OAFOntologySpecificFileManager recentAuditSetsManager;
    
    
    
    private final Map<File, OAFRecentlyOpenedFileManager> recentAuditSetFileManagers;
    private final Map<File, OAFRecentlyOpenedFileManager> recentAbNWorkspaceFileManagers;
    private final Map<File, OAFRecentlyOpenedFileManager> recentNATWorkspaceFileManagers;
    
    public OAFStateFileManager(String toolName) throws RecentlyOpenedFileException {

        this.recentlyOpenedOntologiesManager = new OAFRecentlyOpenedFileManager(toolName, "recently_opened_ontologies", 10);

        this.recentAbNWorkspaceManager = new OAFOntologySpecificFileManager(
                String.format("%s\\recentAbNWorkspace", toolName), "recentAbNWorkspace");
        
        this.recentNATWorkspaceManager = new OAFOntologySpecificFileManager(
                String.format("%s\\recentNATWorkspace", toolName), "recentNATWorkspace");

        this.recentAuditSetsManager = new OAFOntologySpecificFileManager(
                String.format("%s\\recentAuditSets", toolName), "recentAuditSets");
        
        
        this.recentAuditSetFileManagers = new HashMap<>();
        this.recentAbNWorkspaceFileManagers = new HashMap<>();
        this.recentNATWorkspaceFileManagers = new HashMap<>();
    }
    
    public OAFRecentlyOpenedFileManager getRecentlyOpenedOntologiesManager() {
        return recentlyOpenedOntologiesManager;
    }

    public OAFRecentlyOpenedFileManager getRecentlyOpenedAuditSets(File ontologyFile) throws RecentlyOpenedFileException {

        if (!recentAuditSetFileManagers.containsKey(ontologyFile)) {
            recentAuditSetFileManagers.put(ontologyFile,
                    new OAFRecentlyOpenedFileManager(
                            this.recentAuditSetsManager.addOrGetOntologyFile(ontologyFile), 10));
        }

        return recentAuditSetFileManagers.get(ontologyFile);
    }
    

    public OAFRecentlyOpenedFileManager getRecentAbNWorkspaces(File ontologyFile) throws RecentlyOpenedFileException {

        if (!recentAbNWorkspaceFileManagers.containsKey(ontologyFile)) {
            recentAbNWorkspaceFileManagers.put(ontologyFile,
                    new OAFRecentlyOpenedFileManager(
                            this.recentAbNWorkspaceManager.addOrGetOntologyFile(ontologyFile), Integer.MAX_VALUE));
        }

        return recentAbNWorkspaceFileManagers.get(ontologyFile);
    }
    

    public OAFRecentlyOpenedFileManager getRecentNATWorkspaces(File ontologyFile) throws RecentlyOpenedFileException {

        if (!recentNATWorkspaceFileManagers.containsKey(ontologyFile)) {
            recentNATWorkspaceFileManagers.put(ontologyFile,
                    new OAFRecentlyOpenedFileManager(
                            this.recentNATWorkspaceManager.addOrGetOntologyFile(ontologyFile), Integer.MAX_VALUE));
        }

        return recentNATWorkspaceFileManagers.get(ontologyFile);
    }
}

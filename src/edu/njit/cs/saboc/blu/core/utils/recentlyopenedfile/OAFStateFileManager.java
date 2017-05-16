package edu.njit.cs.saboc.blu.core.utils.recentlyopenedfile;

import edu.njit.cs.saboc.blu.core.utils.recentlyopenedfile.OAFRecentlyOpenedFileManager.RecentlyOpenedFileException;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Chris O
 */
public class OAFStateFileManager {
    
    private final OAFRecentlyOpenedFileManager recentlyOpenedOntologiesManager;
    
    private final OAFOntologySpecificFileManager recentAbNsManager;
    
    private final OAFOntologySpecificFileManager recentAuditSetsManager;
    
    private final Map<File, OAFRecentlyOpenedFileManager> recentAuditSetManagers;
    
    public OAFStateFileManager(String toolName) throws RecentlyOpenedFileException {

        this.recentlyOpenedOntologiesManager = new OAFRecentlyOpenedFileManager(toolName, "recently_opened_ontologies", 10);

        this.recentAbNsManager = new OAFOntologySpecificFileManager(
                String.format("%s\\recentAbNs", toolName), "recentAbNs");

        this.recentAuditSetsManager = new OAFOntologySpecificFileManager(
                String.format("%s\\recentAuditSets", toolName), "recentAuditSets");
        
        this.recentAuditSetManagers = new HashMap<>();
    }
    
    public OAFRecentlyOpenedFileManager getRecentlyOpenedOntologiesManager() {
        return recentlyOpenedOntologiesManager;
    }

    public OAFRecentlyOpenedFileManager getRecentlyOpenedAuditSets(File ontologyFile) throws RecentlyOpenedFileException {

        if(!recentAuditSetManagers.containsKey(ontologyFile)) {
            recentAuditSetManagers.put(ontologyFile, 
                    new OAFRecentlyOpenedFileManager(
                            this.recentAuditSetsManager.addOrGetOntologyFile(ontologyFile), 10));
        }
        
        return recentAuditSetManagers.get(ontologyFile);
    }
}

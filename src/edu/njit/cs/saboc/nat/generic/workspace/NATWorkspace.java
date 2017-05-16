package edu.njit.cs.saboc.nat.generic.workspace;

import edu.njit.cs.saboc.blu.core.gui.workspace.OAFWorkspace;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.toolstate.FileUtilities;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditSet;
import edu.njit.cs.saboc.nat.generic.history.FocusConceptHistory;
import edu.njit.cs.saboc.nat.generic.history.FocusConceptHistory.HistoryListener;
import edu.njit.cs.saboc.nat.generic.history.FocusConceptHistoryEntry;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class NATWorkspace<T extends Concept> extends OAFWorkspace {
    
    private final NATBrowserPanel<T> browserPanel;
    private final FocusConceptHistory<T> workspaceHistory;
    
    private Optional<File> auditSet;
    
    public static NATWorkspace createNewWorkspaceFromCurrent(
            NATBrowserPanel browserPanel, 
            File file,
            String name) {
        
        return new NATWorkspace(browserPanel, file, name);
    }
    
    /**
     * Constructor for creating a new workspace from the current view
     */ 
    private NATWorkspace(NATBrowserPanel<T> browserPanel, File file, String name) {
        
        super(file);
        
        this.browserPanel = browserPanel;
        
        super.setName(name);
        
        this.workspaceHistory = new FocusConceptHistory<>();
        this.workspaceHistory.setHistory(browserPanel.getFocusConceptManager().getHistory().getHistory());
        
        if(browserPanel.getAuditDatabase().getLoadedAuditSet().isPresent()) {
            this.setAuditSet(browserPanel.getAuditDatabase().getLoadedAuditSet().get());
        } else {
            this.auditSet = Optional.empty();
        }
        
        saveWorkspace();
    }
        
    /**
     * Constructor for initializing a workspace from a file
     * 
     * @param browserPanel
     * @param file
     */ 
    public NATWorkspace(
            NATBrowserPanel browserPanel,
            File file) {
        
        super(file);
        
        this.browserPanel = browserPanel;
        
        this.auditSet = Optional.empty();
        this.workspaceHistory = new FocusConceptHistory<>();
        
        this.workspaceHistory.addHistoryListener(new HistoryListener() {
            @Override
            public void historyEntryAdded() {
                saveWorkspace();
            }

            @Override
            public void historyBack() { }

            @Override
            public void historyForward() { }

        });
        
        loadWorkspace();
    }
    
    public FocusConceptHistory<T> getHistory() {
        return workspaceHistory;
    }
    
    public NATBrowserPanel<T> getBrowserPanel() {
        return browserPanel;
    }
    
    public Optional<File> getAuditSet() {
        return auditSet;
    }
    
    public final void setAuditSet(AuditSet<T> auditSet) {
        this.auditSet = Optional.of(auditSet.getFile());
        
        saveWorkspace();
    }
    
    @Override
    public final void loadWorkspace() {
        
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(super.getFile())) {
            
            JSONObject obj = (JSONObject) parser.parse(reader);
            
            String name = obj.get("name").toString();
            super.setName(name);
            
            if(obj.containsKey("auditset")) {
                String auditSetFilePath = obj.get("auditset").toString();
                
                File auditSetFile = new File(auditSetFilePath);
                
                if(FileUtilities.ensureFileExistsAndWritable(auditSetFile)) {
                    this.auditSet = Optional.of(auditSetFile);
                }
            }
            
            JSONArray historyArr = (JSONArray) obj.get("history");
            
            Set<String> conceptIds = new HashSet<>();
            
            historyArr.forEach((object) -> {
                JSONObject entryObj = (JSONObject) object;

                conceptIds.add(entryObj.get("conceptid").toString());
            });
            
            Set<T> concepts = this.getBrowserPanel().getDataSource().getConceptsFromIds(conceptIds);
            
            Map<String, T> conceptIdMap = new HashMap<>();
            
            concepts.forEach( (concept) -> {
                conceptIdMap.put(concept.getIDAsString(), concept);
            });
            
            ArrayList<FocusConceptHistoryEntry<T>> historyEntries = new ArrayList<>();
            
            historyArr.forEach( (object) -> {
                JSONObject entryObj = (JSONObject)object;
                
                String time = entryObj.get("time").toString();
                String conceptIdStr = entryObj.get("conceptid").toString();
                
                Date date = new Date(Long.parseLong(time));
                
                if(conceptIdMap.containsKey(conceptIdStr)) {
                    FocusConceptHistoryEntry<T> entry = new FocusConceptHistoryEntry<>(conceptIdMap.get(conceptIdStr), date);
                    
                    historyEntries.add(entry);
                }
                
                historyEntries.sort( (a, b) -> a.getTimeViewed().compareTo(b.getTimeViewed()));
            });
            
            this.workspaceHistory.setHistory(historyEntries);
            
        } catch (IOException ioe) {

        } catch (ParseException pe) {

        }
    }
    
    @Override
    public final void saveWorkspace() {
        JSONObject obj = new JSONObject();

        obj.put("name", super.getName());

        if (auditSet.isPresent()) {
            obj.put("auditset", auditSet.get().getAbsolutePath());
        }

        obj.put("history", workspaceHistory.toJSON());
        
        if (FileUtilities.ensureFileExistsAndWritable(super.getFile())) {
            try (FileWriter fileWriter = new FileWriter(super.getFile())) {
                fileWriter.write(obj.toJSONString());

                fileWriter.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}

package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.auditset;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.exportabn.ExportAbNUtilities;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditSet;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditSetLoader;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditSetLoaderException;
import edu.njit.cs.saboc.nat.generic.gui.panels.BaseNATPanel;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.*;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class AuditSetPanel<T extends Concept> extends BaseNATPanel<T> {
    
    private final AuditConceptList<T> auditConceptList;
    
    private final JLabel nameLabel;
    private final JLabel lastSavedDateLabel;
    
    private final JButton btnCreateFromConceptList;
    private final JButton btnExportAuditSet;
    
    private final JButton btnOpenAuditSet;

    private final JButton btnLoadRecentlyOpened;
    private final JComboBox recentlyOpenedVersionBox;
    private ArrayList<File> recentlyOpenedList = new ArrayList<>();

    private final String prefsKey_RecentlyOpenedAuditSet = "Recently Opened Audit Set";
    private final String defaultJSON = "{}";
    
    public AuditSetPanel(NATBrowserPanel<T> browserPanel, ConceptBrowserDataSource<T> dataSource) {
        super(browserPanel, dataSource);
        
        this.setLayout(new BorderLayout());
        
        JPanel managementPanel = new JPanel();
        
        this.btnCreateFromConceptList = new JButton("Create");
        this.btnCreateFromConceptList.addActionListener( (ae) -> {
            
            JPopupMenu testMenu = new JPopupMenu();
            
            JMenuItem emptyOption = new JMenuItem("New empty audit set");
            emptyOption.setFont(emptyOption.getFont().deriveFont(14.0f));
            emptyOption.addActionListener( (actionEvent) -> {
                createEmptyAuditSet();
            });
            
            testMenu.add(emptyOption);
            
            JMenuItem fileOption = new JMenuItem("From Concept ID file");
            fileOption.setFont(fileOption.getFont().deriveFont(14.0f));
            fileOption.addActionListener( (actionEvent) -> {
                
            });
            
            testMenu.add(fileOption);
            
            testMenu.show(managementPanel, btnCreateFromConceptList.getX() + 10, btnCreateFromConceptList.getY() + 10);
        });
        
        this.btnOpenAuditSet = new JButton("Open");
        this.btnOpenAuditSet.addActionListener( (ae) -> {
            openAuditSet();
        });
        
        this.btnLoadRecentlyOpened = new JButton("Load Recently Opened");
        this.recentlyOpenedVersionBox = new JComboBox();
        this.recentlyOpenedVersionBox.setBackground(Color.WHITE);
        loadHistory();
        this.btnLoadRecentlyOpened.addActionListener( (ae) -> {
            try {
                File file = recentlyOpenedList.get(recentlyOpenedVersionBox.getSelectedIndex());
                AuditSet<T> auditSet = AuditSetLoader.<T>createAuditSetFromJSON(file, getDataSource());
                setCurrentAuditSet(auditSet);
            } catch (AuditSetLoaderException asle) {
                asle.printStackTrace();
                JOptionPane.showMessageDialog(
                        null,
                        asle.getMessage(),
                        "Audit Set Loading Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton clearHistoryBtn = new JButton("Clear History");
        clearHistoryBtn.addActionListener( (ae) -> {
            int answer = JOptionPane.showOptionDialog(
                    null,
                    "The history of recently opened items will be lost."
                            + "\nAre you sure to clear history?",
                    "Recently Opened Files: Clear History?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    new Object[]{"Yes","No"},
                    "No");
            switch(answer) {
                case 0:
                    eraseHistory();
                    loadHistory();
                case 1:
                default:
            }
        });
        
        this.btnExportAuditSet = new JButton("Save As");
        this.btnExportAuditSet.addActionListener( (ae) -> {
            exportAuditSet();
        });
        
        managementPanel.add(btnCreateFromConceptList);
        managementPanel.add(btnOpenAuditSet);
        managementPanel.add(btnExportAuditSet);
        
        
        this.nameLabel = new JLabel();
        this.lastSavedDateLabel = new JLabel();
        
        
        JPanel namePanel = new JPanel();
        namePanel.add(new JLabel("Audit Set Name: "));
        namePanel.add(nameLabel);
        
        JPanel lastSavedPanel = new JPanel();
        lastSavedPanel.add(new JLabel("Last saved: "));
        lastSavedPanel.add(lastSavedDateLabel);
        
        JPanel detailsPanel = new JPanel(new GridLayout(2, 1));
        
        detailsPanel.add(recentlyOpenedVersionBox);
        detailsPanel.add(btnLoadRecentlyOpened);
        detailsPanel.add(clearHistoryBtn);
        detailsPanel.add(namePanel);
        detailsPanel.add(lastSavedPanel);
        
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(managementPanel, BorderLayout.NORTH);
        northPanel.add(detailsPanel, BorderLayout.CENTER);
                
        this.add(northPanel, BorderLayout.NORTH);
                
        this.auditConceptList = new AuditConceptList<>(browserPanel, dataSource);
        
        this.add(auditConceptList, BorderLayout.CENTER);
    }
    
    public void displayDetails(AuditSet<T> auditSet) {
        this.nameLabel.setText(auditSet.getName());
        
        SimpleDateFormat dateFormatter = new SimpleDateFormat();
        
        if(auditSet.getFile().isPresent()) {
            this.lastSavedDateLabel.setText(dateFormatter.format(auditSet.getLastSavedDate()));
        } else {
            this.lastSavedDateLabel.setText("[not yet saved to file]");
        }
    }
    

    private void setCurrentAuditSet(AuditSet<T> auditSet) {
        getMainPanel().getAuditDatabase().setAuditSet(auditSet);

        this.auditConceptList.reloadAuditSet();

        displayDetails(auditSet);
    }
    
    private void createEmptyAuditSet() {
        Optional<String> auditSetName = promptForAuditSetName();
        
        if(auditSetName.isPresent()) {
            AuditSet<T> auditSet = new AuditSet<>(getDataSource(), auditSetName.get(), new HashSet<>());
            
            setCurrentAuditSet(auditSet);
        }
    }
    
    
    private void createAuditSetFromFile() {
        Optional<File> idFile = ExportAbNUtilities.displayFileSelectOpenDialog();
        
        if(idFile.isPresent()) {
            try {
                AuditSet<T> auditSet = AuditSetLoader.<T>createAuditSetFromConceptIds(idFile.get(), getDataSource());
                setCurrentAuditSet(auditSet);
            } catch (AuditSetLoaderException asle) {
 
            }
        }
    }
    
    private void openAuditSet() {
        Optional<File> idFile = ExportAbNUtilities.displayFileSelectOpenDialog();

        if (idFile.isPresent()) {
            
            try {
                AuditSet<T> auditSet = AuditSetLoader.<T>createAuditSetFromJSON(idFile.get(), getDataSource());
                setCurrentAuditSet(auditSet);
                saveHistory(idFile.get());
                loadHistory();
            } catch (AuditSetLoaderException asle) {
                asle.printStackTrace();
            }
        }
    }
    
    private void exportAuditSet() {
        Optional<File> auditSetFile = ExportAbNUtilities.displayFileSelectSaveDialog();

        if(auditSetFile.isPresent()) {
            if(getMainPanel().getAuditDatabase().getLoadedAuditSet().isPresent()) {
                AuditSet<T> auditSet = getMainPanel().getAuditDatabase().getLoadedAuditSet().get();
                
                boolean success = auditSet.saveToFile(auditSetFile.get());
                
                if(success) {
                    displayDetails(auditSet);
                    saveHistory(auditSetFile.get());
                    loadHistory();
                } else {
                    JOptionPane.showMessageDialog(getMainPanel().getParentFrame(), 
                            "<html>An error occured when saving the Audit Set."
                                    + "<p>Click \"Save As\" and create a new file to save your work.", 
                            "Error Saving Audit Set", 
                            JOptionPane.ERROR_MESSAGE);
                }
                
                
            }
        }
    }
    
    
    private Optional<String> promptForAuditSetName() {
        
        String auditSetName = "";
        
        while (auditSetName != null && auditSetName.isEmpty()) {

            auditSetName = JOptionPane.showInputDialog(
                    getMainPanel().getParentFrame(),
                    "Enter the name of the audit set (required)",
                    "Enter Audit Set Name",
                    JOptionPane.QUESTION_MESSAGE);
        }
        
        return Optional.ofNullable(auditSetName);
    }
    
    
    private List<Map.Entry<String,Long>> getRecentlyOpened(Class<?> c, String prefsKey) throws ParseException {
        Preferences prefsRoot = Preferences.userNodeForPackage(c);
        String recentlyOpenedReleasesListJSON = prefsRoot.get(prefsKey,defaultJSON);
        System.out.println(recentlyOpenedReleasesListJSON);
        JSONParser parser = new JSONParser();
        JSONObject recentlyOpenedReleasesJSONobj = (JSONObject) parser.parse(recentlyOpenedReleasesListJSON);
        HashMap<String,Long> unsorted = new HashMap<>();
        recentlyOpenedReleasesJSONobj.keySet().forEach( key -> {
            unsorted.put((String) key,(Long) recentlyOpenedReleasesJSONobj.get(key));
        });
        List<Map.Entry<String,Long>> sortedEntries = new ArrayList<>(unsorted.entrySet());
        Collections.sort(sortedEntries,
                (Map.Entry<String,Long> e1, Map.Entry<String,Long> e2) ->
                        e2.getValue().compareTo(e1.getValue())
        );
        return sortedEntries;
    }

    private ArrayList<File> getHistory(Class<?> c, String prefsKey) throws ParseException {
        List<Map.Entry<String,Long>> sortedEntries = getRecentlyOpened(c,prefsKey);
        ArrayList<File> returnList = new ArrayList<>(sortedEntries.size());

        for (Map.Entry<String,Long> entry : sortedEntries){
            String path = entry.getKey();
            File temp = new File(path);
            try {
                if(temp.exists())
                    returnList.add(temp);
            } catch (SecurityException se) {
                se.printStackTrace();
                JOptionPane.showMessageDialog(
                        null,
                        "One of the recently opened location is not readable - READ ACCESS DENIED."
                                + "\nLocation path: " + path
                                + "\nThis location is removed from the recently opened list.",
                        "File/Directory Read Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        return returnList;
    }

    private void eraseHistory(){
        eraseHistory(this.getClass(),prefsKey_RecentlyOpenedAuditSet);
    }

    private void eraseHistory(Class<?> c, String prefsKey){
        Preferences prefsRoot = Preferences.userNodeForPackage(c);
        prefsRoot.remove(prefsKey);
    }

    private void loadHistory(){
        try {
            recentlyOpenedVersionBox.removeAllItems();
        }catch(NullPointerException e){
            System.out.println("NullPointerException!!");
        }
        recentlyOpenedList.clear();
        try{
            recentlyOpenedList = getHistory(this.getClass(), prefsKey_RecentlyOpenedAuditSet);
            recentlyOpenedList.forEach( file -> {
                recentlyOpenedVersionBox.addItem(file.getName());
            });
        }
        catch(ParseException pe) {
            pe.printStackTrace();
            JOptionPane.showMessageDialog(
                    null,
                    "Reading the list of recently opened files failed - PARSE ERROR",
                    "Recently Opened Files: Read Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
        catch(ClassCastException|NumberFormatException e){
            e.printStackTrace();
            int answer = JOptionPane.showOptionDialog(
                    null,
                    "Reading the list of recently opened files failed - DATA ERROR"
                            + "\nHistory data seems corrupted.\nReset history?",
                    "Recently Opened Files: Read Failed",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.ERROR_MESSAGE,
                    null,
                    new Object[]{"Yes","No"},
                    "No");
            switch(answer) {
                case 0:
                    eraseHistory();
                case 1:
                default:
            }
        }
        catch(Exception e){
            e.printStackTrace();
            int answer = JOptionPane.showOptionDialog(
                    null,
                    "Reading the list of recently opened files failed - UNKNOWN ERROR"
                            + "\nReset history?",
                    "Recently Opened Files: Read Failed",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.ERROR_MESSAGE,
                    null,
                    new Object[]{"Yes","No"},
                    "No");
            switch(answer) {
                case 0:
                    eraseHistory();
                case 1:
                default:
            }
        }
        if(recentlyOpenedList.isEmpty()){
            recentlyOpenedVersionBox.addItem("NO History");
            btnLoadRecentlyOpened.setEnabled(false);
        }
        else{
            btnLoadRecentlyOpened.setEnabled(true);
        }
    }

    private void saveHistory(File selectedFile){
        Preferences prefsRoot = Preferences.userNodeForPackage(this.getClass());
        String recentlyOpenedReleasesListJSON = prefsRoot.get(prefsKey_RecentlyOpenedAuditSet,defaultJSON);
        System.out.println(recentlyOpenedReleasesListJSON);
        JSONParser parser = new JSONParser();
        try{
            JSONObject recentlyOpenedReleasesJSONObj = (JSONObject) parser.parse(recentlyOpenedReleasesListJSON);
            recentlyOpenedReleasesJSONObj.put(selectedFile.getAbsolutePath(),new Date().getTime());
            prefsRoot.put(prefsKey_RecentlyOpenedAuditSet, JSONValue.toJSONString(recentlyOpenedReleasesJSONObj));
        }catch(ParseException pe){
            pe.printStackTrace();
            JOptionPane.showMessageDialog(
                    null,
                    "Saving the list of recently opened files failed - PARSE ERROR",
                    "Recently Opened Files: Save Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}

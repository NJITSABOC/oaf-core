package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.history;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionListener;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.MultiAbNGraphFrame;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Chris O
 */
public class AbNDerivationHistoryPanel extends JPanel implements ActionListener{
    
    private final AbNDerivationHistoryList derivationList;
    
    private final ArrayList<AbNDerivationHistoryEntry> entries = new ArrayList<>();
        
    public AbNDerivationHistoryPanel() {
        
        this.setLayout(new BorderLayout());

                
        this.derivationList = new AbNDerivationHistoryList();
        this.derivationList.addEntitySelectionListener(new EntitySelectionListener<AbNDerivationHistoryEntry>() {

            @Override
            public void entityClicked(AbNDerivationHistoryEntry entity) {

            }

            @Override
            public void entityDoubleClicked(AbNDerivationHistoryEntry entity) {
                entity.displayEntry();
            }

            @Override
            public void noEntitySelected() {
                
            }
        });
        
        this.add(derivationList, BorderLayout.CENTER);
        

//        JButton button1 = new JButton("SAVE");
//        button1.setActionCommand("SAVE");
//        button1.addActionListener((ActionListener) this);
//
//        JButton button2 = new JButton("LOAD");
//        button2.setActionCommand("LOAD");
//        button2.addActionListener((ActionListener) this);
//
//        JPanel subPanel = new JPanel();
//        subPanel.add(button1);
//        subPanel.add(button2);
//
//        this.add(subPanel, BorderLayout.AFTER_LAST_LINE);



            
    }
    
    public void addEntry(AbNDerivationHistoryEntry entry) {
        entries.add(entry);
        System.out.println("New Derivation:");

        System.out.println(entry.getDerivation().serializeToJSON().toJSONString());
        
        derivationList.setContents(entries);
    }
    
    public ArrayList<AbNDerivationHistoryEntry> getEntries(){
        return entries;
    } 
    
    
    public void actionPerformed(ActionEvent e) {
        
        if(e.getActionCommand().equalsIgnoreCase("SAVE")){

        for (AbNDerivationHistoryEntry entry : entries) {
            JSONArray arr = entry.getDerivation().serializeToJSON();
            try (FileWriter file = new FileWriter("testing.json")) {
                file.write(arr.toJSONString());
                System.out.println("Serialized JSON Object to File...");
                System.out.println("JSON Object: " + arr);
                file.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

        }
        }
        else if (e.getActionCommand().equalsIgnoreCase("LOAD")) {
            
             JSONParser parser = new JSONParser();

        try {

            JSONArray jsonArr = (JSONArray) parser.parse(new FileReader("testing.json"));
            System.out.println(jsonArr);
            JSONObject resultObject = findJSONObjectByName(jsonArr, "ClassName");
            String className = resultObject.get("ClassName").toString();
            System.out.println(className);
            entries.get(entries.size()-1).getDerivation();

    

        } catch (Exception ioe) {
            ioe.printStackTrace();
        }

        System.out.println("Done Deserialization.");
            
        }

    }
    
    
        public JSONObject findJSONObjectByName(JSONArray jsonArr, String name) {

        JSONObject result = new JSONObject();

        if (jsonArr.isEmpty()) {
            return result;
        }
        JSONParser parser = new JSONParser();
        for (Object o : jsonArr) {
            
            
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject = (JSONObject) parser.parse(o.toString());
            } catch (ParseException ex) {
                Logger.getLogger(AbNDerivationHistoryPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (jsonObject.containsKey(name)) {
                result = jsonObject;
                break;
            }

        }

        return result;
    }

    
}

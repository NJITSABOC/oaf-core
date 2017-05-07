package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.history;

import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivationParser;
import edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.PopupToggleButton;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.MultiAbNGraphFrame;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.history.AbNDerivationHistoryParser.AbNDerivationHistoryParseException;
import java.awt.BorderLayout;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
public class AbNHistoryButton extends PopupToggleButton {

    private final AbNDerivationHistoryPanel derivationHistoryPanel;
    
    private final AbNDerivationHistory derivationHistory;
    
    public AbNHistoryButton(
            MultiAbNGraphFrame graphFrame, 
            AbNDerivationHistory derivationHistory,
            AbNDerivationParser abnParser) {
        
        super(graphFrame.getParentFrame(), "History");

        JPanel historyPanel = new JPanel(new BorderLayout());
        
        this.derivationHistory = derivationHistory;

        this.derivationHistoryPanel = new AbNDerivationHistoryPanel();
        this.derivationHistoryPanel.showHistory(derivationHistory);
        
        this.addActionListener( (ae) -> {
            this.derivationHistoryPanel.showHistory(derivationHistory);
        });

        JButton saveBtn = new JButton("Save Selected Entry");
        saveBtn.addActionListener((se) -> {

            AbNDerivationHistoryEntry entry = derivationHistoryPanel.getSelectedEntry();

            JSONArray arr = new JSONArray();
            JSONObject abnJSON = entry.toJSON();

            arr.add(abnJSON);

            try (FileWriter file = new FileWriter("testing.json")) {
                file.write(arr.toJSONString());

                file.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        });

        JButton saveAllBtn = new JButton("Save History");
        saveAllBtn.addActionListener((ae) -> {
            ArrayList<AbNDerivationHistoryEntry> entries = derivationHistory.getHistory();

            JSONArray arr = new JSONArray();

            for (AbNDerivationHistoryEntry entry : entries) {
                arr.add(entry.toJSON());
            }

            File outputFile = new File("testing.json");

            try (FileWriter file = new FileWriter(outputFile)) {
                file.write(arr.toJSONString());

                file.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        });

        JButton loadBtn = new JButton("Load History");
        loadBtn.addActionListener((ae) -> {
            JSONParser parser = new JSONParser();

            File file = new File("testing.json");

            try (FileReader reader = new FileReader(file)) {
                JSONArray json = (JSONArray) parser.parse(reader);

                AbNDerivationHistoryParser historyParser = new AbNDerivationHistoryParser();

                try {
                    ArrayList<AbNDerivationHistoryEntry> entries = historyParser.getDerivationHistory(graphFrame, abnParser, json);

                    this.derivationHistory.setHistory(entries);
                } catch (AbNDerivationHistoryParseException parseException) {

                    parseException.printStackTrace();
                } catch (AbNDerivationParser.AbNParseException abnpe) {

                    abnpe.printStackTrace();
                }

                derivationHistoryPanel.showHistory(derivationHistory);

            } catch (IOException ioe) {
                ioe.printStackTrace();
            } catch (ParseException pe) {
                pe.printStackTrace();
            }
        });

        JPanel subPanel = new JPanel();
        subPanel.add(saveBtn);
        subPanel.add(saveAllBtn);
        subPanel.add(loadBtn);

        historyPanel.add(derivationHistoryPanel, BorderLayout.CENTER);
        historyPanel.add(subPanel, BorderLayout.SOUTH);

        this.setPopupContent(historyPanel);
    }
}

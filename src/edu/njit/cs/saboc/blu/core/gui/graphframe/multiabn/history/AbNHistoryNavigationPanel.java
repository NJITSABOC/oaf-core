package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.history;

import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivationParser;
import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivationParser.AbNParseException;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.MultiAbNGraphFrame;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.history.AbNDerivationHistoryParser.AbNDerivationHistoryParseException;
import edu.njit.cs.saboc.blu.core.gui.iconmanager.ImageManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Chris O
 */
public class AbNHistoryNavigationPanel extends JPanel {

    private final JButton backBtn;
    private final JButton forwardBtn;

    private final JButton viewHistoryBtn;
    
    private final MultiAbNGraphFrame graphFrame;
    
    private final AbNDerivationHistory derivationHistory;
    
    private final AbNHistoryNavigationManager historyNavigationManager;
    
    private final AbNDerivationParser abnParser;

    public AbNHistoryNavigationPanel(
            MultiAbNGraphFrame graphFrame,
            AbNDerivationHistory derivationHistory,
            AbNDerivationParser abnParser) {
        
        this.graphFrame = graphFrame;
        this.derivationHistory = derivationHistory;
        this.abnParser = abnParser;
        
        historyNavigationManager = new AbNHistoryNavigationManager(derivationHistory);
        
        backBtn = new JButton();
        backBtn.setIcon(ImageManager.getImageManager().getIcon("left-arrow.png"));
        backBtn.addActionListener((ae) -> {
            historyNavigationManager.goBack();
            
            updateNavigationButtons();
        });
        
        backBtn.setPreferredSize(new Dimension(60, 24));

        forwardBtn = new JButton();
        forwardBtn.setIcon(ImageManager.getImageManager().getIcon("right-arrow.png"));
        forwardBtn.addActionListener((ae) -> {
            
            historyNavigationManager.goForward();
            
            updateNavigationButtons();
        });
        
        forwardBtn.setPreferredSize(new Dimension(60, 24));
        
        derivationHistory.addHistoryChangedListener( () -> {
            updateNavigationButtons();
        });

        viewHistoryBtn = new JButton("View History");
        viewHistoryBtn.addActionListener( (ae) -> {
            showHistoryDialog();
        });
        
        this.add(backBtn);
        this.add(Box.createHorizontalStrut(4));
        this.add(viewHistoryBtn);
        this.add(Box.createHorizontalStrut(4));
        this.add(forwardBtn);
    }

    private void updateNavigationButtons() {
        backBtn.setEnabled(historyNavigationManager.canGoBack());
        forwardBtn.setEnabled(historyNavigationManager.canGoForward());
    }
    
    public void showHistoryDialog() {
        JDialog historyDialog = new JDialog();
        historyDialog.setSize(600, 800);
        historyDialog.add(makeHistoryPanel());

        historyDialog.setVisible(true);
    }
    
    private JPanel makeHistoryPanel() {
        JPanel historyPanel = new JPanel(new BorderLayout());
        
        AbNDerivationHistoryPanel derivationHistoryPanel = new AbNDerivationHistoryPanel();
        derivationHistoryPanel.showHistory(derivationHistory);

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

            try(FileReader reader = new FileReader(file)) {
                JSONArray json = (JSONArray) parser.parse(reader);

                AbNDerivationHistoryParser historyParser = new AbNDerivationHistoryParser();

                try {
                    ArrayList<AbNDerivationHistoryEntry> entries = historyParser.getDerivationHistory(graphFrame, abnParser, json);

                    this.derivationHistory.setHistory(entries);
                } catch (AbNDerivationHistoryParseException parseException) {
                    
                    parseException.printStackTrace();
                } catch (AbNParseException abnpe) {
                    
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
     
        return historyPanel;
    }
}

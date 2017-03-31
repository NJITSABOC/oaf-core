package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.DisjointPArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.PAreaTaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.tan.DisjointCluster;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNExplorationPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNExplorationPanelGUIInitializer;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AbNPainter;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.history.AbNDerivationHistoryEntry;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.history.AbNDerivationHistoryPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.prefs.Preferences;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.filechooser.FileFilter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import testing.AbNDerivationCoreParser;
import testing.AbNDerivationParserUtils;

/**
 *
 * @author Chris O
 */
public class MultiAbNGraphFrame<T extends AbNDerivationCoreParser>extends JInternalFrame {
    
    private final JFrame parentFrame;

    private final JPanel taskPanel;
    
    private final AbNExplorationPanel abnExplorationPanel;

    private Optional<AbstractionNetworkGraph> optCurrentGraph = Optional.empty();
    private Optional<TaskBarPanel> optCurrentTaskBarPanel = Optional.empty();
    
    private final AbNGraphFrameInitializers initializers;
    
    private final MultiAbNDisplayManager displayManager;
    
    private final AbNDerivationHistoryPanel derivationHistoryPanel;
    
    private final AbNDerivationCoreParser abnParser;
        
    public MultiAbNGraphFrame(JFrame parentFrame, AbNGraphFrameInitializers initializers, T abnParser) {
        
        super("Ontology Abstraction Framework (OAF) Display",
                true, //resizable
                true, //closable
                true, //maximizable
                true);//iconifiable

        this.parentFrame = parentFrame;
        this.initializers = initializers;
        this.abnParser = abnParser;
        
        this.displayManager = new MultiAbNDisplayManager(this, null);
        
        this.taskPanel = new JPanel();
        this.taskPanel.setLayout(new BorderLayout());
        
        this.abnExplorationPanel = new AbNExplorationPanel();
        this.abnExplorationPanel.showLoading();
        
        this.setLayout(new BorderLayout());
        
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(taskPanel, BorderLayout.CENTER);
        
        this.derivationHistoryPanel = new AbNDerivationHistoryPanel();
        
        Preferences prefs = Preferences.userNodeForPackage(getClass());
        
        JButton showDerivationHistoryBtn = new JButton("Abstraction Network History");
        showDerivationHistoryBtn.addActionListener((ae) -> {
            JDialog historyDialog = new JDialog();
            historyDialog.setSize(600, 800);

            historyDialog.add(derivationHistoryPanel);
            
            JButton saveBtn = new JButton("SAVE");
            saveBtn.addActionListener((as) -> {
                AbNDerivationHistoryEntry entry = derivationHistoryPanel.getSelectedEntry();
                
                    JSONArray arr = entry.getDerivation().serializeToJSON();
                    prefs.remove("Selected View");
                    prefs.put("Selected View", arr.toJSONString());
                    
//                    try (FileWriter file = new FileWriter("testing.json", true)) {
//                        file.write(arr.toJSONString());
//                        System.out.println("Serialized JSON Object to File...");
//                        System.out.println("JSON Object: " + arr);
//                        file.close();
//                    } catch (IOException ioe) {
//                        ioe.printStackTrace();
//                    }

            });
       
            JButton saveAllBtn = new JButton("SAVE ALL");
            saveBtn.addActionListener((as) -> {
                ArrayList<AbNDerivationHistoryEntry> entries = derivationHistoryPanel.getEntries();

                for (AbNDerivationHistoryEntry entry : entries) {
                    JSONArray arr = entry.getDerivation().serializeToJSON();
                    try (FileWriter file = new FileWriter("testing.json",true)) {
                        file.write(arr.toJSONString());
                        System.out.println("Serialized JSON Object to File...");
                        System.out.println("JSON Object: " + arr);
                        file.close();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }

                }

            });

            JButton loadBtn = new JButton("LOAD Last Viewed Taxonomy");
            loadBtn.addActionListener((ActionEvent al) -> {
                JSONParser parser = new JSONParser();
                try {
//                    JSONArray jsonArr = (JSONArray) parser.parse(new FileReader("testing.json"));                     
                    String strJson = prefs.get("Selected View","0");
                    
                    JSONArray jsonArr = new JSONArray();
                    if(strJson != null)  {
                        System.out.println("Get the array from prefs");
                        jsonArr = (JSONArray) parser.parse((strJson));
                    }
                   
                    JSONObject resultObject = AbNDerivationParserUtils.findJSONObjectByName(jsonArr, "ClassName");
                    String className = resultObject.get("ClassName").toString();
                    System.out.println(className);
                    
                    AbstractionNetwork abn = abnParser.coreParser(jsonArr).getAbstractionNetwork();
                    if (abn instanceof PAreaTaxonomy) {
                        displayPAreaTaxonomy((PAreaTaxonomy) abn, false);
                    } else if (abn instanceof DisjointAbstractionNetwork) {
                        DisjointAbstractionNetwork dabn = (DisjointAbstractionNetwork) abn;
                        if (dabn.getParentAbstractionNetwork() instanceof PAreaTaxonomy) {
                            displayDisjointPAreaTaxonomy(dabn, false);
                        } else if (dabn.getParentAbstractionNetwork() instanceof ClusterTribalAbstractionNetwork) {
                            displayDisjointTAN(dabn, false);                            
                        }
                        
                    } else if (abn instanceof ClusterTribalAbstractionNetwork) {
                        displayTAN((ClusterTribalAbstractionNetwork) abn, false);
                    } else if (abn instanceof TargetAbstractionNetwork) {
                        displayTargetAbstractionNewtork((TargetAbstractionNetwork) abn, false);                       
                    }
           
                } catch (Exception ioe) {
                    ioe.printStackTrace();
                }
                
                System.out.println("Done Deserialization.");
                
            });
            
            JPanel subPanel = new JPanel();
            subPanel.add(saveBtn);
            subPanel.add(saveAllBtn);
            subPanel.add(loadBtn);
            historyDialog.add(subPanel, BorderLayout.AFTER_LAST_LINE);

            historyDialog.setVisible(true);
        });
        
        JPanel historyButtonPanel = new JPanel();
        historyButtonPanel.add(showDerivationHistoryBtn);
        
        northPanel.add(historyButtonPanel, BorderLayout.WEST);
        
        this.add(northPanel, BorderLayout.NORTH);
        this.add(abnExplorationPanel, BorderLayout.CENTER);
        
        this.addInternalFrameListener(new InternalFrameAdapter() {
            
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                if(optCurrentTaskBarPanel.isPresent()) {
                    optCurrentTaskBarPanel.get().disposeAllPopupButtons();
                }

                abnExplorationPanel.getDisplayPanel().kill();
            }

            @Override
            public void internalFrameDeactivated(InternalFrameEvent e) {
                if(optCurrentTaskBarPanel.isPresent()) {
                    optCurrentTaskBarPanel.get().closeAllPopupButtons();
                }
            }

            @Override
            public void internalFrameIconified(InternalFrameEvent e) {
                if(optCurrentTaskBarPanel.isPresent()) {
                    optCurrentTaskBarPanel.get().closeAllPopupButtons();
                }
            }
        });

        this.addComponentListener(new ComponentAdapter() {
            
            @Override
            public void componentResized(ComponentEvent e) {
                JInternalFrame frame = (JInternalFrame) e.getSource();
                
                if (optCurrentTaskBarPanel.isPresent()) {
                    optCurrentTaskBarPanel.get().updatePopupLocations(frame.getSize());
                }
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                JInternalFrame frame = (JInternalFrame) e.getSource();

                if (optCurrentTaskBarPanel.isPresent()) {
                    optCurrentTaskBarPanel.get().updatePopupLocations(frame.getSize());
                }
            }
        });

        this.setSize(1200, 512);
        this.setVisible(true);
    }
    
    public JFrame getParentFrame() {
        return parentFrame;
    }
    
    public AbNExplorationPanel getAbNExplorationPanel() {
        return abnExplorationPanel;
    }
        
    public void displayPAreaTaxonomy(PAreaTaxonomy taxonomy) {
        displayPAreaTaxonomy(taxonomy, true);
    }
    
    public void displayPAreaTaxonomy(PAreaTaxonomy taxonomy, boolean createHistoryEntry) {
        initialize(taxonomy, initializers.getPAreaTaxonomyInitializer());
        
        if (createHistoryEntry) {
            AbNDerivationHistoryEntry<PAreaTaxonomy> entry = new AbNDerivationHistoryEntry<>(
                    taxonomy.getDerivation(),
                    (abn) -> {
                        this.displayPAreaTaxonomy(abn, false);
                    },
                    "Partial-area Taxonomy"
            );
            
            this.derivationHistoryPanel.addEntry(entry);
        }
    }
    
    public void displayAreaTaxonomy(PAreaTaxonomy taxonomy) {
        displayAreaTaxonomy(taxonomy, true);
    }
    
    public void displayAreaTaxonomy(PAreaTaxonomy taxonomy, boolean createHistoryEntry) {
        initialize(taxonomy, initializers.getAreaTaxonomyInitializer());

        if (createHistoryEntry) {
            AbNDerivationHistoryEntry<PAreaTaxonomy> entry = new AbNDerivationHistoryEntry<>(
                    taxonomy.getDerivation(),
                    (abn) -> {
                        this.displayAreaTaxonomy(abn, false);
                    },
                    "Area Taxonomy"
            );

            this.derivationHistoryPanel.addEntry(entry);
        }
    }

    public void displayDisjointPAreaTaxonomy(
        DisjointAbstractionNetwork<DisjointPArea, PAreaTaxonomy<PArea>, PArea> disjointTaxonomy) {
        
        displayDisjointPAreaTaxonomy(disjointTaxonomy, true);
    }
    
    public void displayDisjointPAreaTaxonomy(
        DisjointAbstractionNetwork<DisjointPArea, PAreaTaxonomy<PArea>, PArea> disjointTaxonomy, boolean createHistoryEntry) {

        initialize(disjointTaxonomy, initializers.getDisjointPAreaTaxonomyInitializer());

        if (createHistoryEntry) {
            AbNDerivationHistoryEntry<DisjointAbstractionNetwork<DisjointPArea, PAreaTaxonomy<PArea>, PArea>> entry = new AbNDerivationHistoryEntry<>(
                    disjointTaxonomy.getDerivation(),
                    (DisjointAbstractionNetwork<DisjointPArea, PAreaTaxonomy<PArea>, PArea> abn) -> {
                        this.displayDisjointPAreaTaxonomy(abn, false);
                    },
                    "Disjoint Partial-area Taxonomy"
            );

            this.derivationHistoryPanel.addEntry(entry);
        }
    }
    
    public void displayTAN(ClusterTribalAbstractionNetwork tan) {
        displayTAN(tan, true);
    }
    
    public void displayTAN(ClusterTribalAbstractionNetwork tan, boolean createHistoryEntry) {
        initialize(tan, initializers.getTANInitializer());
        
        if (createHistoryEntry) {
            AbNDerivationHistoryEntry<ClusterTribalAbstractionNetwork> entry = new AbNDerivationHistoryEntry<>(
                    tan.getDerivation(),
                    (abn) -> {
                        this.displayTAN(abn, false);
                    },
                    "Cluster Tribal Abstraction Network"
            );

            this.derivationHistoryPanel.addEntry(entry);
        }
    }
    
    public void displayBandTAN(ClusterTribalAbstractionNetwork tan) {
        displayBandTAN(tan, true);
    }
    
    public void displayBandTAN(ClusterTribalAbstractionNetwork tan, boolean createHistoryEntry) {
        initialize(tan, initializers.getBandTANInitializer());
        
        if (createHistoryEntry) {
            AbNDerivationHistoryEntry<ClusterTribalAbstractionNetwork> entry = new AbNDerivationHistoryEntry<>(
                    tan.getDerivation(),
                    (abn) -> {
                        this.displayBandTAN(abn, false);
                    },
                    "Band Tribal Abstraction Network"
            );

            this.derivationHistoryPanel.addEntry(entry);
        }
    }
    
    public void displayDisjointTAN(DisjointAbstractionNetwork<DisjointCluster, ClusterTribalAbstractionNetwork<Cluster>, Cluster> disjointTAN) {
        displayDisjointTAN(disjointTAN, true);
    }
    
    public void displayDisjointTAN(
            DisjointAbstractionNetwork<DisjointCluster, ClusterTribalAbstractionNetwork<Cluster>, Cluster> disjointTAN, 
            boolean createHistoryEntry) {
        
        initialize(disjointTAN, initializers.getDisjointTANInitializer());
        
        if (createHistoryEntry) {
            AbNDerivationHistoryEntry<DisjointAbstractionNetwork<DisjointCluster, ClusterTribalAbstractionNetwork<Cluster>, Cluster>> entry = new AbNDerivationHistoryEntry<>(
                    disjointTAN.getDerivation(),
                    
                    (DisjointAbstractionNetwork<DisjointCluster, ClusterTribalAbstractionNetwork<Cluster>, Cluster> abn) -> {
                        this.displayDisjointTAN(abn, false);
                    },
                    
                    "Disjoint Cluster Tribal Abstraction Network"
            );

            this.derivationHistoryPanel.addEntry(entry);
        }
    }
    
    public void displayTargetAbstractionNewtork(TargetAbstractionNetwork targetAbN) {
        displayTargetAbstractionNewtork(targetAbN, true);
    }
    
    public void displayTargetAbstractionNewtork(TargetAbstractionNetwork targetAbN, boolean createHistoryEntry) {
        initialize(targetAbN, initializers.getTargetAbNInitializer());
        
        if (createHistoryEntry) {
            AbNDerivationHistoryEntry<TargetAbstractionNetwork> entry = new AbNDerivationHistoryEntry<>(
                    targetAbN.getDerivation(),
                    (abn) -> {
                        this.displayTargetAbstractionNewtork(abn, false);
                    },
                    "Target Abstraction Network"
            );

            this.derivationHistoryPanel.addEntry(entry);
        }
    }
    
    private void initialize(
            AbstractionNetwork abn,
            GraphFrameInitializer initializer) {

        this.abnExplorationPanel.showLoading();

        Thread loadThread = new Thread(() -> {
            AbNPainter painter = initializer.getAbNPainter(abn);
            AbNConfiguration config = initializer.getConfiguration(abn, displayManager);
            
            AbstractionNetworkGraph graph = initializer.getGraph(parentFrame, config, initializer.getLabelCreator(abn));

            AbNExplorationPanelGUIInitializer explorationInitializer = initializer.getExplorationGUIInitializer(config);
            TaskBarPanel tbp = initializer.getTaskBar(this, config);

            this.optCurrentGraph = Optional.of(graph);
            this.optCurrentTaskBarPanel = Optional.of(tbp);
            
            displayAbstractionNetwork(graph, tbp, painter, config, explorationInitializer);
        });
        
        loadThread.start();
    }
    
    private void displayAbstractionNetwork(
            AbstractionNetworkGraph graph,
            TaskBarPanel tbp,
            AbNPainter painter,
            AbNConfiguration gepConfiguration,
            AbNExplorationPanelGUIInitializer initializer) {

        SwingUtilities.invokeLater(() -> {
            this.taskPanel.removeAll();
            this.taskPanel.add(tbp, BorderLayout.CENTER);
            
            this.taskPanel.revalidate();
            this.taskPanel.repaint();

            this.abnExplorationPanel.initialize(graph, gepConfiguration, painter, initializer);
        });
    }
    
    public void saveCurrentView() {
        final JFileChooser chooser = new JFileChooser();

        chooser.setFileFilter(new FileFilter() {
            
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }

                return f.getName().endsWith(".png");
            }

            @Override
            public String getDescription() {
                return "Portal Network Graphics (.png) Images";
            }
        });

        int returnVal = chooser.showSaveDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            BufferedImage graphImage = abnExplorationPanel.getDisplayPanel().getCurrentViewImage();

            try {
                String file = chooser.getSelectedFile().getAbsolutePath();

                if (!file.toLowerCase().endsWith(".png")) {
                    file += ".png";
                }

                ImageIO.write(graphImage, "png", new File(file));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

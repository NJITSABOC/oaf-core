package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.DisjointPArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
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
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.history.AbNHistoryNavigationPanel;
import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import javax.imageio.ImageIO;
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
import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivationParser;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.history.AbNDerivationHistory;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.history.AbNDerivationHistoryParser;

/**
 *
 * @author Chris O
 */
public class MultiAbNGraphFrame extends JInternalFrame {

    private final JFrame parentFrame;

    private final JPanel taskPanel;

    private final AbNExplorationPanel abnExplorationPanel;

    private Optional<AbstractionNetworkGraph> optCurrentGraph = Optional.empty();
    
    private Optional<TaskBarPanel> optCurrentTaskBarPanel = Optional.empty();

    private final AbNGraphFrameInitializers initializers;

    private final MultiAbNDisplayManager displayManager;


    private final AbNDerivationHistory abnDerivationHistory;

    private final AbNHistoryNavigationPanel historyNavigationPanel;


    private final AbNDerivationParser abnParser;

    public MultiAbNGraphFrame(
            JFrame parentFrame, 
            AbNGraphFrameInitializers initializers, 
            AbNDerivationParser abnParser) {

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


        this.abnDerivationHistory = new AbNDerivationHistory();

        this.historyNavigationPanel = new AbNHistoryNavigationPanel();

        northPanel.add(historyNavigationPanel, BorderLayout.WEST);

        this.add(northPanel, BorderLayout.NORTH);
        this.add(abnExplorationPanel, BorderLayout.CENTER);

        this.addInternalFrameListener(new InternalFrameAdapter() {

            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                if (optCurrentTaskBarPanel.isPresent()) {
                    optCurrentTaskBarPanel.get().disposeAllPopupButtons();
                }

                abnExplorationPanel.getDisplayPanel().kill();
            }

            @Override
            public void internalFrameDeactivated(InternalFrameEvent e) {
                if (optCurrentTaskBarPanel.isPresent()) {
                    optCurrentTaskBarPanel.get().closeAllPopupButtons();
                }
            }

            @Override
            public void internalFrameIconified(InternalFrameEvent e) {
                if (optCurrentTaskBarPanel.isPresent()) {
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
    
    public void displayAbstractionNetwork(AbstractionNetwork<?> abn) {
        displayAbstractionNetwork(abn, true);
    }
    
    public void displayAbstractionNetwork(AbstractionNetwork<?> abn, boolean createHistoryEntry) {
        
        if(abn instanceof PAreaTaxonomy) {
            displayPAreaTaxonomy((PAreaTaxonomy)abn, createHistoryEntry);
        } else if(abn instanceof ClusterTribalAbstractionNetwork) {
            displayTAN( (ClusterTribalAbstractionNetwork)abn, createHistoryEntry);
        } else if(abn instanceof TargetAbstractionNetwork) {
            displayTargetAbstractionNetwork( (TargetAbstractionNetwork)abn, createHistoryEntry);
        } else if(abn instanceof DisjointAbstractionNetwork) {
            DisjointAbstractionNetwork<?, ?, ?> disjointAbN = (DisjointAbstractionNetwork)abn;
            
            if(disjointAbN.getParentAbstractionNetwork() instanceof PAreaTaxonomy) {
                
                displayDisjointPAreaTaxonomy(
                        (DisjointAbstractionNetwork<DisjointPArea, PAreaTaxonomy<PArea>, PArea>)disjointAbN, 
                        createHistoryEntry);
                
            } else if(disjointAbN.getParentAbstractionNetwork() instanceof ClusterTribalAbstractionNetwork) {
                
                displayDisjointTAN(
                        (DisjointAbstractionNetwork<DisjointCluster, ClusterTribalAbstractionNetwork<Cluster>, Cluster>)disjointAbN, 
                        createHistoryEntry);
                
            }
        }
    }
    
    public void addDerivationHistoryEntry(AbstractionNetwork<?> abn) {
        this.abnDerivationHistory.addEntry(new AbNDerivationHistoryEntry<>(abn.getDerivation(), this));
    }

    public void displayPAreaTaxonomy(PAreaTaxonomy taxonomy) {
        displayPAreaTaxonomy(taxonomy, true);
    }

    public void displayPAreaTaxonomy(PAreaTaxonomy taxonomy, boolean createHistoryEntry) {
        initialize(taxonomy, initializers.getPAreaTaxonomyInitializer());

        if (createHistoryEntry) {
            addDerivationHistoryEntry(taxonomy);
        }
    }

    public void displayAreaTaxonomy(PAreaTaxonomy taxonomy) {
        displayAreaTaxonomy(taxonomy, true);
    }

    public void displayAreaTaxonomy(PAreaTaxonomy taxonomy, boolean createHistoryEntry) {
        initialize(taxonomy, initializers.getAreaTaxonomyInitializer());

        if (createHistoryEntry) {
            addDerivationHistoryEntry(taxonomy);
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
            addDerivationHistoryEntry(disjointTaxonomy);
        }
    }

    public void displayTAN(ClusterTribalAbstractionNetwork tan) {
        displayTAN(tan, true);
    }

    public void displayTAN(ClusterTribalAbstractionNetwork tan, boolean createHistoryEntry) {
        initialize(tan, initializers.getTANInitializer());

        if (createHistoryEntry) {
            addDerivationHistoryEntry(tan);
        }
    }

    public void displayBandTAN(ClusterTribalAbstractionNetwork tan) {
        displayBandTAN(tan, true);
    }

    public void displayBandTAN(ClusterTribalAbstractionNetwork tan, boolean createHistoryEntry) {
        initialize(tan, initializers.getBandTANInitializer());

        if (createHistoryEntry) {
            addDerivationHistoryEntry(tan);
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
            addDerivationHistoryEntry(disjointTAN);
        }
    }

    public void displayTargetAbstractionNetwork(TargetAbstractionNetwork targetAbN) {
        displayTargetAbstractionNetwork(targetAbN, true);
    }

    public void displayTargetAbstractionNetwork(TargetAbstractionNetwork targetAbN, boolean createHistoryEntry) {
        initialize(targetAbN, initializers.getTargetAbNInitializer());

        if (createHistoryEntry) {
            addDerivationHistoryEntry(targetAbN);
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

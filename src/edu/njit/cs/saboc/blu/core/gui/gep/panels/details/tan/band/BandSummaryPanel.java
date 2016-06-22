package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.band;

import SnomedShared.Concept;
import edu.njit.cs.saboc.blu.core.abn.tan.Band;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeSummaryPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.BLUGenericTANConfiguration;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class BandSummaryPanel<BAND_T extends Band, CLUSTER_T extends Cluster> extends NodeSummaryPanel<BAND_T> {

    private final BandTribesDetailsPanel bandTribesPanel;
    
    private final BLUGenericTANConfiguration config;
    
    public BandSummaryPanel(BLUGenericTANConfiguration config) {

        this.bandTribesPanel = new BandTribesDetailsPanel(config);
        
        this.add(bandTribesPanel);
        
        this.config = config;
    }
    
    @Override
    protected String createDescriptionStr(BAND_T band) {
        String bandName = config.getTextConfiguration().getContainerName(band);
        
        ArrayList<CLUSTER_T> clusters = band.getAllClusters();
        
        HashSet<Concept> allConcepts = new HashSet<>();
        
        clusters.forEach( (CLUSTER_T cluster) -> {
            allConcepts.addAll(cluster.getConcepts());
        });
        
        String result = String.format("<html><b>%s</b> is a tribal band that summarizes %d %s in %d clusters.", 
                bandName, 
                allConcepts.size(), 
                config.getTextConfiguration().getConceptTypeName(allConcepts.size() != 1).toLowerCase(),
                band.getAllClusters().size());
        
        result += "<p><b>Help / Description:</b><br>";
        result += config.getTextConfiguration().getContainerHelpDescription(band);
        
        return result;
    }
    
    @Override
    public void clearContents() {
        super.clearContents();
        
        bandTribesPanel.clearContents();
    }

    @Override
    public void setContents(BAND_T band) {
        super.setContents(band);
        
        ArrayList<Concept> tribalPatriarchs = new ArrayList<>(band.getPatriarchs());
        
        bandTribesPanel.setContents(tribalPatriarchs);
    }
}

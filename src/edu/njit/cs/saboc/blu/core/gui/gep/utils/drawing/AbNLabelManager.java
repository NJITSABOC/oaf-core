package edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JLabel;

/**
 * A manager that creates all of the labels for an AbN's groups
 * @author Chris
 */
public class AbNLabelManager {
    private final int MAX_GROUP_LABELSHEET_SIZE = 2048;
    
    /**
     * Stores the label sheet and position for a given group
     */
    private class GroupLabelPositionEntry {
        public int x;
        public int y;

        public BufferedImage labelSheet = null;
        
        public GroupLabelPositionEntry(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        public void setLabelSheet(BufferedImage labelSheet) {
            this.labelSheet = labelSheet;
        }
    }

    private final HashMap<JLabel, BufferedImage> labelImages = new HashMap<JLabel, BufferedImage>();
    
    private final HashMap<Integer, GroupLabelPositionEntry> groupLabelMap = new HashMap<Integer, GroupLabelPositionEntry>();
        
    private final ArrayList<BufferedImage> labelSheets = new ArrayList<BufferedImage>();
    
    
    private final SinglyRootedNodeLabelCreator labelCreator;
    
    public AbNLabelManager(AbstractionNetwork abn, SinglyRootedNodeLabelCreator labelCreator) {
        this.labelCreator = labelCreator;
        
        HashMap<Integer, ? extends GenericConceptGroup> groups = abn.getGroups();

        ArrayList<GenericConceptGroup> groupList = new ArrayList<GenericConceptGroup>(groups.values());
                
        createGroupLabelSheets(groupList);
    }
    
    private final void createGroupLabelSheets(ArrayList<GenericConceptGroup> groups) {
        int pendingCount = groups.size();
        
        int totalProcessed = 0;
        
        while(pendingCount > 0) {
            
            int size = MAX_GROUP_LABELSHEET_SIZE;

            int rows = size / SinglyRootedNodeEntry.ENTRY_HEIGHT;
            int cols = size / SinglyRootedNodeEntry.ENTRY_WIDTH;

            
            while (doSubdivide(size, pendingCount)) {
                size /= 2;

                rows = size / SinglyRootedNodeEntry.ENTRY_HEIGHT;
                cols = size / SinglyRootedNodeEntry.ENTRY_WIDTH;
            }
            
            BufferedImage labelSheet = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g = (Graphics2D) labelSheet.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g.setColor(new Color(0, 0, 0, 0)); // Transparent background
            g.fillRect(0, 0, labelSheet.getWidth(), labelSheet.getHeight());

            g.setColor(Color.BLACK);

            g.setFont(new Font("SansSerif", Font.PLAIN, 14));
            
            ArrayList<GenericConceptGroup> processedGroups = new ArrayList<GenericConceptGroup>();
            
            for (int y = 0; y < rows; y++) {
                for (int x = 0; x < cols; x++) {
                    GenericConceptGroup group = groups.get(totalProcessed);

                    groupLabelMap.put(group.getId(), new GroupLabelPositionEntry(x, y));

                    drawGroupLabelAtPosition(x * SinglyRootedNodeEntry.ENTRY_WIDTH, y * SinglyRootedNodeEntry.ENTRY_HEIGHT, group, g);

                    totalProcessed++;
                    processedGroups.add(group);

                    if (totalProcessed >= groups.size()) {
                        break;
                    }
                }

                if (totalProcessed >= groups.size()) {
                    break;
                }
            }

            pendingCount -= (rows * cols);

            BufferedImage mipmap = createMipMap(labelSheet);
            
            labelSheets.add(mipmap);
            
            for(GenericConceptGroup group : processedGroups) {
                groupLabelMap.get(group.getId()).setLabelSheet(mipmap);
            }
        }
    }
   
    private boolean doSubdivide(int size, final int remaining) {
        size /= 2;

        int rows = size / SinglyRootedNodeEntry.ENTRY_HEIGHT;
        int cols = size / SinglyRootedNodeEntry.ENTRY_WIDTH;

        return remaining < (4 * rows * cols);
    }

    private BufferedImage createMipMap(BufferedImage image) {
        BufferedImage mipmap = new BufferedImage(image.getWidth() + (image.getWidth() / 2), image.getHeight(), image.getType());
        
        Graphics2D mipmapG = mipmap.createGraphics();
        
        mipmapG.setColor(new Color(0,0,0,0));
        mipmapG.fillRect(0, 0, mipmap.getWidth(), mipmap.getHeight());
        
        mipmapG.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        mipmapG.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        mipmapG.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        mipmapG.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw full sized image
        mipmapG.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        
        int scale = 2;
        
        int yPos = 0;
                
        for(int c = 1; c <= 4; c++) {
            int size = image.getWidth() / scale;
            
            mipmapG.drawImage(image, image.getWidth(), yPos, image.getWidth() + size, yPos + size, 0, 0, image.getWidth(), image.getHeight(), null);

            
            yPos += size;
            
            scale *= 2;
        }
        
        return mipmap;
    }

    public void drawGroupLabel(GenericConceptGroup group, double scale, Graphics2D g, int x, int y) {
        GroupLabelPositionEntry entry = groupLabelMap.get(group.getId());
        
        if(entry == null) {
            return;
        }
                
        int labelWidth = SinglyRootedNodeEntry.ENTRY_WIDTH;
        int labelHeight = SinglyRootedNodeEntry.ENTRY_HEIGHT;
        
        int srcWidth = labelWidth;
        int srcHeight = labelHeight;
        
        int mipmapXOffset = 0;
        int mipmapYOffset = 0;
        
        if (scale < 0.55 && scale > 0.25) {
            srcWidth /= 2;
            srcHeight /= 2;
            
            mipmapXOffset = entry.labelSheet.getHeight();
        } else if(scale < 0.125) {
            srcWidth /= 4;
            srcHeight /= 4;
            
            mipmapXOffset = entry.labelSheet.getHeight();
            mipmapYOffset = entry.labelSheet.getHeight() / 2;
        }
        
        int srcX = mipmapXOffset + (int) (entry.x * srcWidth);
        int srcY = mipmapYOffset + (int) (entry.y * srcHeight);

        g.drawImage(entry.labelSheet, x, y, x + (int) (labelWidth * scale), y + (int) (labelHeight * scale), srcX, srcY, srcX + srcWidth, srcY + srcHeight, null);
    }
    
    private void drawGroupLabelAtPosition(int xPos, int yPos, GenericConceptGroup group, Graphics2D g) {

        String rootName = labelCreator.getRootNameStr(group);
        String conceptCountLabel = labelCreator.getCountStr(group);

        boolean finished = false;

        int fontHeight = g.getFontMetrics().getHeight();
        
        int y = 0;

        int strIndex = 0;

        while (!finished) {

            String line = rootName.substring(strIndex, Math.min(strIndex + 18, rootName.length()));

            if (!line.isEmpty() && line.contains(" ") && !line.substring(0, line.lastIndexOf(" ")).isEmpty()) {
                line = line.substring(0, line.lastIndexOf(" "));

                strIndex += line.length();
            } else {
                finished = true;
                strIndex = rootName.length();
            }

            y += fontHeight;

            if ((y + fontHeight) > SinglyRootedNodeEntry.ENTRY_HEIGHT) {
                finished = true;
            }

            if (strIndex >= rootName.length()) {
                finished = true;
            }

            if (finished) {

                boolean fit = false;

                String appendStr = conceptCountLabel;

                if (strIndex < rootName.length() - 1) { // Didn't use whole string...
                    if (g.getFontMetrics().stringWidth(line + "...") <= SinglyRootedNodeEntry.ENTRY_WIDTH) { // Within bounds with dots...
                        if (g.getFontMetrics().stringWidth(line + "... " + conceptCountLabel) <= SinglyRootedNodeEntry.ENTRY_WIDTH) {
                            line += ("... " + conceptCountLabel);
                            fit = true;
                        } else {
                            appendStr = ("..." + appendStr);
                        }
                    }

                } else { // Used the whole string...
                    if (g.getFontMetrics().stringWidth(line + " " + conceptCountLabel) <= SinglyRootedNodeEntry.ENTRY_WIDTH) { // Can fit concept count
                        line += (" " + conceptCountLabel);
                        fit = true;
                    } else {
                        appendStr = (" " + appendStr);
                    }
                }

                if (!fit) {
                    int boundDifference = g.getFontMetrics().stringWidth(line + appendStr) - SinglyRootedNodeEntry.ENTRY_WIDTH;

                    int cutPoint = 1;

                    int chopSize = g.getFontMetrics().stringWidth(line.substring(line.length() - cutPoint, line.length()));

                    while (cutPoint < line.length() && chopSize < boundDifference) {
                        cutPoint++;

                        chopSize = g.getFontMetrics().stringWidth(line.substring(line.length() - cutPoint, line.length()));
                    }

                    line = line.substring(0, line.length() - cutPoint) + appendStr;
                }
            }

            int x = (SinglyRootedNodeEntry.ENTRY_WIDTH - g.getFontMetrics().stringWidth(line)) / 2;
                        
            g.drawString(line, xPos + x, yPos + y - 4);
        }
    }
    
    public void drawLabel(Graphics2D graphics, JLabel label, double scale, int x, int y) {
                
        if(!labelImages.containsKey(label)) {
            createLabelImage(label);
        }
        
        BufferedImage labelImage = labelImages.get(label);
        
        graphics.drawImage(labelImage, x, y, (int)(labelImage.getWidth() * scale), (int)(labelImage.getHeight() * scale), null);
    }
    
    public void removeLabel(JLabel label) {
        labelImages.remove(label);
    }
    
    private void createLabelImage(JLabel label) {
        BufferedImage image = new BufferedImage(label.getWidth(), label.getHeight(), BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D g2d = (Graphics2D)image.getGraphics();
        
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        label.print(g2d);
        
        labelImages.put(label, image);
    }
}

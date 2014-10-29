package edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing;

import edu.njit.cs.saboc.blu.core.graph.nodes.GenericGroupEntry;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;


/**
 *
 * @author Chris
 */
public class TextDrawingUtilities {

    public static void drawPartitionTitle(Graphics2D g, ArrayList<String> lines, int y, Rectangle centeringBounds) {
        for (String line : lines) {
            int x = (centeringBounds.width - g.getFontMetrics().stringWidth(line)) / 2;
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
        }
    }

    public static void drawTextWithinBounds(Graphics2D g, String text, Rectangle bounds, Rectangle centeringBounds) {

        LinkedList<String> queue = new LinkedList<String>();
        Collections.addAll(queue, text.split("\\s+"));

        ArrayList<String> lines = new ArrayList<String>();

        Font font = g.getFont();

        int fontSize = font.getSize();

        boolean finished = false;

        while (!finished) {
            while (!queue.isEmpty()) {
                String line = queue.removeFirst();

                while (!queue.isEmpty()
                        && g.getFontMetrics().stringWidth(line + " "
                        + queue.getFirst()) < bounds.width) {
                    line += (" " + queue.removeFirst());
                }

                lines.add(line);
            }

            if ((lines.size() * g.getFontMetrics().getHeight()) > bounds.height) {
                fontSize -= 1;
                g.setFont(new Font(font.getFontName(), font.getStyle(), fontSize));
                lines.clear();
                Collections.addAll(queue, text.split("\\s+"));
            } else {
                finished = true;
            }
        }

        int y = bounds.y;

        for (String line : lines) {
            int x = (centeringBounds.width - g.getFontMetrics().stringWidth(line)) / 2;
            
            if(g.getFontMetrics().stringWidth(line) > bounds.width) {
                System.err.println("Line is wider then bounds.");
            }

            g.drawString(line, x, y += g.getFontMetrics().getHeight());
        }

        g.setFont(new Font("Ariel", Font.PLAIN, 20));
    }
    
    public static void drawGroupLabel(Graphics2D g, GenericGroupEntry group, Rectangle bounds, Rectangle centeringBounds) {

        String rootName = group.getGroup().getRoot().getName().replaceAll("_", " ");
        String conceptCountLabel = String.format("(%d)", group.getGroup().getConceptCount());
        
        if (!group.getShowSemanticTag()) {
            if (rootName.lastIndexOf("(") != -1) {
                rootName = rootName.substring(0, rootName.lastIndexOf("("));
            }
        }
        
        boolean finished = false;

        int y = bounds.y;
        
        int fontHeight = g.getFontMetrics().getHeight();
                
        int strIndex = 0;
               
        while (!finished) {
            
            String line = rootName.substring(strIndex, Math.min(strIndex + 18, rootName.length()));
            
            if(!line.isEmpty() && line.contains(" ") && !line.substring(0, line.lastIndexOf(" ")).isEmpty()) {
                line = line.substring(0, line.lastIndexOf(" "));
                
                strIndex += line.length();
            } else {
                finished = true;
                strIndex = rootName.length();
            }
            
            
            
            y += fontHeight;
            
            if((y + fontHeight) > bounds.height) {
                finished = true;
             }
            
            if(strIndex >= rootName.length()) {
                finished = true;
            }
            
            if (finished) {
                
                boolean fit = false;
                
                String appendStr = conceptCountLabel;
                
                if (strIndex < rootName.length() - 1) { // Didn't use whole string...
                    if (g.getFontMetrics().stringWidth(line + "...") <= bounds.width) { // Within bounds with dots...
                        if(g.getFontMetrics().stringWidth(line + "... " + conceptCountLabel) <= bounds.width) {
                            line += ("... " + conceptCountLabel);
                            fit = true;
                        } else {
                            appendStr = ("..." + appendStr);
                        }
                    }
                    
                } else { // Used the whole string...
                    if(g.getFontMetrics().stringWidth(line + " " + conceptCountLabel) <= bounds.width) { // Can fit concept count
                        line += (" " + conceptCountLabel);
                        fit = true;
                    } else {
                        appendStr = (" " + appendStr);
                    }
                }
                
                if (!fit) {
                    int boundDifference = g.getFontMetrics().stringWidth(line + appendStr) - bounds.width;

                    int cutPoint = 1;

                    int chopSize = g.getFontMetrics().stringWidth(line.substring(line.length() - cutPoint, line.length()));

                    while (cutPoint < line.length() && chopSize < boundDifference) {
                        cutPoint++;

                        chopSize = g.getFontMetrics().stringWidth(line.substring(line.length() - cutPoint, line.length()));
                    }

                    line = line.substring(0, line.length() - cutPoint) + appendStr;
                }
            }

            int x = (centeringBounds.width - g.getFontMetrics().stringWidth(line)) / 2;
            g.drawString(line, x, y);
        }

        


    }
    
    public static void drawTextWithNewlines(Graphics2D g, String text, Rectangle bounds, int x, int y) {
        g.setFont(new Font("Ariel", Font.PLAIN, 20));

        LinkedList<String> queue = new LinkedList<String>();
        Collections.addAll(queue, text.split("\\s+"));

        ArrayList<String> lines = new ArrayList<String>();

        boolean finished = false;

        int fontSize = 20;

        while (!finished) {
            while (!queue.isEmpty()) {
                String line = queue.removeFirst();

                while (!queue.isEmpty()
                        && g.getFontMetrics().stringWidth(line + " "
                        + queue.getFirst()) < (2 * bounds.width) - 10) {
                    line += (" " + queue.removeFirst());
                }

                lines.add(line);
            }

            if((lines.size() * g.getFontMetrics().getHeight()) > 2 * bounds.height - 4) {
                fontSize -= 1;
                g.setFont(new Font("Ariel", Font.PLAIN, fontSize));
                lines.clear();
                Collections.addAll(queue, text.split("\\s+"));
            } else {
                finished = true;
            }
        }

        for(String line  : lines) {
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
        }

        g.setFont(new Font("Ariel", Font.PLAIN, 20));
    }
}

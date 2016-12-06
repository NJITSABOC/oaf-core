/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.njit.cs.saboc.blu.core.gui.frame;

import javax.swing.JInternalFrame;

/**
 *
 * @author hl395
 */
public interface AbnSelectionFrameFactory {
    JInternalFrame returnSelectionFrame(BLUFrame jFrame);
}

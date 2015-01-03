/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth.terminalManager;

import java.awt.event.ComponentEvent;

/**
 *
 * @author D
 */

public interface WindowResizeDelegate {
    /**
     * onClose.
     * 
     * if you return false window wont close, if you return true it will.
     */
    public void windowResizing(ComponentEvent e);
}

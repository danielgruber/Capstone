/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth;

import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import labyrinth.terminalManager.TerminalManager;
import labyrinth.terminalManager.TerminalWriter;
import labyrinth.terminalManager.WindowDelegate;
import labyrinth.terminalManager.view.ViewManager;

/**
 *
 * @author D
 */
public class Labyrinth {

    static TerminalManager manager;
    static ViewManager viewManager;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        manager = new TerminalManager();
        manager.start();
        
        viewManager = new ViewManager(manager);
        viewManager.setView(new GameMenu(false));
    }
    
}

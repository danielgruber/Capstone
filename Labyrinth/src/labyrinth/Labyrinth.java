/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth;

import labyrinth.terminalManager.TerminalManager;
import labyrinth.terminalManager.TerminalWriter;

/**
 *
 * @author D
 */
public class Labyrinth {

    static TerminalManager manager;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        manager = new TerminalManager();
        manager.start();
        
        TerminalWriter w = new TerminalWriter(manager);
        w.writeText("Hallo Welt", 1, 1);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth.terminalManager;

import com.googlecode.lanterna.terminal.Terminal.Color;

/**
 *
 * @author D
 */
public class TerminalWriter {
    
    TerminalManager manager;
    
    public TerminalWriter(TerminalManager m) {
        this.manager = m;
    }
    
    /**
     * writes a string to terminal. Retruns false if terminal's size is too small.
     * 
     * @param String    text
     * @param int       x
     * @param int       y
     * @param Color     foreground
     * @param Color     background
     * @return 
     */
    public boolean writeText(String text, int x, int y, Color foreground, Color background) {
        boolean completeSuccess = true;
        for(int i = 0; i < text.length(); i++) {
            if(!manager.setCharacter(new CharacterUpdate(text.charAt(i), x + i, y, foreground, background))) {
                completeSuccess = false;
                break;
            }
        }
        
        return completeSuccess;
    }
    
    /**
     *  writes a string to terminal. Retruns false if terminal's size is too small.
     * 
     * @param String    text
     * @param int       x
     * @param int       y
     * @return 
     */
    public boolean writeText(String text, int x, int y) {
        boolean completeSuccess = true;
        for(int i = 0; i < text.length(); i++) {
            if(!manager.setCharacter(new CharacterUpdate(text.charAt(i), x + i, y))) {
                completeSuccess = false;
                break;
            }
        }
        
        return completeSuccess;
    }
}

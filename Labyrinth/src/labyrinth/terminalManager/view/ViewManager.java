/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth.terminalManager.view;

import labyrinth.terminalManager.CharacterDelegate;
import com.googlecode.lanterna.terminal.TerminalSize;
import java.awt.event.ComponentEvent;
import labyrinth.terminalManager.*;

/**
 * manages a terminal via terminalManager and a current View, which is displayed in the terminal.
 * implements ViewUpdateDelegate to have updates from the views.
 * 
 * @author D
 */
public class ViewManager implements WindowResizeDelegate, CharacterDelegate {
    
    /**
     * terminalManager that is managed.
     */
    TerminalManager manager;
    
    /**
     * current view.
     */
    View view;
    
    public ViewManager(labyrinth.terminalManager.TerminalManager m) {
        super();
        
        manager = m;
        manager.windowResizeDelegate = this;
    }
    
    public void windowResizing(ComponentEvent e) {
        if(view != null) {
            TerminalSize size = manager.getSize();
            view.windowSizeUpdated(size.getRows(), size.getColumns());
        }
    }
    
    public void setView(View v) {
        
        if(v == null) {
            throw new IllegalArgumentException("ViewManager.setView(View v): The View can't be null.");
        }
        
        if(view.equals(v)) {
            return;
        }
        
        if(view != null) {
            view.characterDelegate = null;
            view.setVisible(false);
            view = null;
            manager.clear();
        }
        
        
        view = v;
        view.characterDelegate = this;
        view.setVisible(true);
    }

    @Override
    public boolean setCharacter(int x, int y, char c) {
        return manager.setCharacter(x, y, c);
    }
    
    @Override
    public boolean setCharacter(CharacterUpdate c) {
        return manager.setCharacter(c);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth.terminalManager.view;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal.Color;
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
public class ViewManager implements WindowResizeDelegate, CharacterDelegate, KeyboardDelegate {
    
    /**
     * terminalManager that is managed.
     */
    TerminalManager manager;
    
    /**
     * current view.
     */
    View view;
    
    public ViewManager(TerminalManager m) {
        super();
        
        manager = m;
        manager.windowResizeDelegate = this;
        manager.keyboardDelegate = this;
    }
    
    public void windowResizing(ComponentEvent e) {
        if(view != null) {
            TerminalSize size = manager.getSize();
            
            synchronized (view) {
                view.windowSizeUpdated(size.getRows(), size.getColumns());
                manager.clear();
                ViewCharacter[][] info = view.getCompleteView(size.getRows(), size.getColumns());
                for(int y = 0; y < info.length; y++) {
                    for(int k = 0; k < info[y].length; k++) {
                        ViewCharacter vc = info[y][k];
                        if(vc != null) {
                            setCharacter(getCharacterUpdate(k, y, vc.getCharacter(), vc.foregroundColor, vc.backgroundColor));
                        } else {
                            setCharacter(getCharacterUpdate(k, y, ' ', null, null));
                        }
                    }
                }
            }
        }
    }
    
    public void setView(View v) {
        
        if(v == null) {
            throw new IllegalArgumentException("ViewManager.setView(View v): The View can't be null.");
        }
        
        if(view != null && view.equals(v)) {
            return;
        }
        
        
        removeView();
        
        view = v;
        
        view.windowSizeUpdated(getTerminalSize().getRows(), getTerminalSize().getColumns());
        manager.setMinimumSize(view.getMinimumSize());
        
        ViewCharacter[][] info = view.getCompleteView(getTerminalSize().getRows(), getTerminalSize().getColumns());
        
        //debugViewInfo(info);
        
        for(int y = 0; y < info.length; y++) {
            for(int k = 0; k < info[y].length; k++) {
                ViewCharacter vc = info[y][k];
                if(vc != null) {
                    setCharacter(getCharacterUpdate(k, y, vc.getCharacter(), vc.foregroundColor, vc.backgroundColor));
                }
            }
        }
        view.characterDelegate = this;
        view.setVisible(true); 
    }
    
    void debugViewInfo(ViewCharacter[][] info) {
        for(int x = 0; x < info.length; x++) {
            for(int y = 0; y < info[0].length; y++) {
                ViewCharacter vc = info[x][y];
                if(vc != null) {
                    System.out.print(vc.getCharacter() + ",\t");
                } else {
                     System.out.print(",\t");
                }
            }
            
            System.out.print("\n");
        }
    }
    
    public CharacterUpdate getCharacterUpdate(int x, int y, char c, Color f, Color b) {
        return new CharacterUpdate(c, x + 1, y + 1, f, b);
    }
    
    public TerminalSize getTerminalSize() {
        return manager.getSize();
    }
    
    public void removeView() {
        if(view != null) {
            view.characterDelegate = null;
            view.setVisible(false);
            view = null;
            manager.clear();
        }
    }
    
    @Override
    public boolean setCharacter(int x, int y, char c) {
        return manager.setCharacter(x + 1, y + 1, c);
    }
    
    @Override
    public boolean setCharacter(CharacterUpdate c) {
        return manager.setCharacter(c);
    }

    @Override
    public void keyPressed(Key key) {
       if(view != null && view instanceof KeyboardDelegate) {
           ((KeyboardDelegate)view).keyPressed(key);
       }
    }
}

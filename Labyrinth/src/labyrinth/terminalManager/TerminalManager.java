/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth.terminalManager;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.*;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

interface WindowDelegate {
    /**
     * onClose.
     * 
     * if you return false window wont close, if you return true it will.
     */
    public boolean windowClosing();
    public void windowResizing();
}

interface KeyboardDelegate {
    public void keyPressed(Key key);
}

/**
 *
 * @author D
 */
public class TerminalManager extends Thread {
    
    /**
     * public protocols
     */
    public WindowDelegate windowDelegate;
    public KeyboardDelegate keyboardDelegate;
    
    /**
     * the terminal.
     */
    private SwingTerminal terminal;
    private JFrame frame;
    
    /**
     * inforation whether frame should run
     */
    private volatile boolean shouldRun = true;
    
    /**
     * buffer for character-updates.
     */
    private LinkedList<CharacterUpdate> frameUpdates;
    
    public TerminalManager() {
        terminal = TerminalFacade.createSwingTerminal();
        frameUpdates = new LinkedList();
    }
    
     
    public JFrame getFrame() {
        return frame;
    }
    
    public SwingTerminal getTerminal() {
        return terminal;
    }
    
    public TerminalSize getSize() {
        return terminal.getTerminalSize();
    }
    
    /**
     * starts the thread and creates the window.
     */
    @Override
    public void start() {
        
        terminal.enterPrivateMode();
        
        frame = terminal.getJFrame();
        
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowEventAdapter());
        
        if(shouldRun) {
            super.start();
        } else {
            shouldRun = true;
        }
    }
    
    /**
     * stops the thread.
     */
    public void stopTask() {
        shouldRun = false;
        
        terminal.exitPrivateMode();
    }
    
    /**
     * sets a character at the specific point.
     * returns false if character can't be set when the terminal is too small.
     */
    public boolean setCharacter(int x, int y, char character) {
        TerminalSize size = this.getSize();
        if(size.getRows() < x || size.getColumns() < y) {
            return false;
        }
        
        frameUpdates.addLast(new CharacterUpdate(character, x, y));
        
        return true;
    }
    
    public boolean setCharacter(CharacterUpdate c) {
        TerminalSize size = this.getSize();
        if(size.getRows() < c.positionX || size.getColumns() < c.positionY) {
            return false;
        }
        
        frameUpdates.addLast(c);
        
        return true;
    }
    
    public void clear() {
        synchronized (terminal) {
            frameUpdates.clear();
            terminal.clearScreen();
        }
    }
    
    /**
     * run-method for thread.
     */
    public void run() {
        while(shouldRun) {
            this.checkKeyInput();
            this.updateWindow();
            
            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                Logger.getLogger(TerminalManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    protected void checkKeyInput() {
        Key k = terminal.readInput();
        if(k != null) {
            if(keyboardDelegate != null) {
                keyboardDelegate.keyPressed(k) ;
            }
        }
    }
    
    protected void updateWindow() {
        CharacterUpdate c;
        while(!frameUpdates.isEmpty()) {
            c = frameUpdates.pop();
            
            synchronized (terminal) {
                terminal.moveCursor(c.positionX, c.positionY);

                if(c.foregroundColor != null) {
                    terminal.applyBackgroundColor(c.foregroundColor);
                }

                if(c.backgroundColor != null) {
                    terminal.applyBackgroundColor(c.backgroundColor);
                }

                terminal.putCharacter(c.character);
            }
        }
    }
    
    class WindowEventAdapter extends WindowAdapter {
        public void windowClosing(WindowEvent ev) {
            boolean response = true;
            if(windowDelegate != null) {
                response = windowDelegate.windowClosing();
            }

            // close and exit
            if(response) {
                 frame.dispose();
                 System.exit(0);
            }
         }
    }
}

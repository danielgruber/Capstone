/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth.terminalManager;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.*;
import com.googlecode.lanterna.terminal.Terminal.Color;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author D
 */
public class TerminalManager extends Thread implements CharacterDelegate {
    
    /**
     * public protocols
     */
    public WindowDelegate windowDelegate;
    public KeyboardDelegate keyboardDelegate;
    public WindowResizeDelegate windowResizeDelegate;
    
    /**
     * the terminal.
     */
    private Terminal terminal;
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
        terminal.setCursorVisible(false);
        frameUpdates = new LinkedList();
    }
    
     
    public JFrame getFrame() {
        return frame;
    }
    
    public Terminal getTerminal() {
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
        
        if(terminal instanceof SwingTerminal) {
            frame = ((SwingTerminal)terminal).getJFrame();

            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.addWindowListener(new WindowEventAdapter());
            frame.addComponentListener(new WindowResizedAdapter());
        }
            
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
        synchronized (frameUpdates) {
            TerminalSize size = this.getSize();
            if(size.getColumns() < x || size.getRows() < y) {
                return false;
            }

            frameUpdates.addLast(new CharacterUpdate(character, x, y));
        }
        
        return true;
    }
    
    public boolean setCharacter(CharacterUpdate c) {
        synchronized (frameUpdates) {
            TerminalSize size = this.getSize();
            if(size.getColumns() < c.positionX || size.getRows() < c.positionY) {
                //System.out.println("Size out of bounds: X:"+c.positionX+"/"+size.getRows()+"; Y: "+c.positionY+"/"+size.getColumns()+"");
                return false;
            }

            frameUpdates.addLast(c);

            return true;
        }
    }
    
    public boolean setMinimumSize(Dimension d) {
        if(frame != null) {
            frame.setMinimumSize(d);
            return true;
        } else {
            return false;
        }
    }
    
    public void clear() {
        synchronized (frameUpdates) {
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
        synchronized (frameUpdates) {
            while(!frameUpdates.isEmpty()) {
                c = frameUpdates.pop();

                synchronized (terminal) {
                    terminal.moveCursor(c.positionX, c.positionY);

                    //System.out.println(c);
                    
                    if(c.foregroundColor != null) {
                        terminal.applyForegroundColor(c.foregroundColor);
                    } else {
                        terminal.applyForegroundColor(Color.WHITE);
                    }

                    if(c.backgroundColor != null) {
                        terminal.applyBackgroundColor(c.backgroundColor);
                    } else {
                        terminal.applyBackgroundColor(Color.BLACK);
                    }

                    terminal.putCharacter(c.character);
                }
            }
        }
    }
    
    class WindowEventAdapter extends WindowAdapter {
        public void windowClosing(WindowEvent ev) {
            boolean response = true;
            if(windowDelegate != null) {
                response = windowDelegate.windowClosing(ev);
            }

            // close and exit
            if(response) {
                 frame.dispose();
                 System.exit(0);
            }
         }
    }
    
    class WindowResizedAdapter extends ComponentAdapter {
        @Override
        public void componentResized(ComponentEvent e) {
            // do stuff        
            if(windowDelegate != null) {
                windowDelegate.windowResizing(e);
            }
            
            if(windowResizeDelegate != null) {
                windowResizeDelegate.windowResizing(e);
            }
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth.terminalManager;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.terminal.*;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.charset.Charset;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

interface WindowDelegate {
    /**
     * onClose.
     * 
     * if you return false window wont close, if you return true it will.
     */
    public boolean windowClosing();
}

/**
 *
 * @author D
 */
public class TerminalManager {
    /**
     * the terminal.
     */
    SwingTerminal terminal;
    JFrame frame;
    
    WindowDelegate windowDelegate;
    
    public TerminalManager() {

        terminal = TerminalFacade.createSwingTerminal();
        frame = terminal.getJFrame();
        
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowEventAdapter());
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

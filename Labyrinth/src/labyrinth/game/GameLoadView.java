/**
 *
 * @author Daniel Gruber
 * @copyright 2014 Daniel Gruber
 * @license GNU Lesser General Public License, version 3; see "LICENSE.txt"
 */
package labyrinth.game;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.input.Key.Kind;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.Terminal.Color;
import labyrinth.terminalManager.KeyboardDelegate;
import labyrinth.terminalManager.view.View;
import labyrinth.terminalManager.view.ViewCharacter;

/**
 *
 * @author D
 */
public class GameLoadView extends View implements KeyboardDelegate {

    
    
    int topPos;
    boolean d = true;
    String currentName = "level.properties";
    public GameLoadDelegate delegate;
    
    @Override
    public ViewCharacter[][] getCompleteView(int rows, int columns) {
        ViewCharacter[][] view = createViewMatrix(rows, columns);
        
        topPos = (rowsVisible - 10) / 2;
        
        fillLineWithAlignCenter(view[topPos], "Type Filename to load. Default is level.properties.");
        fillLineWithAlignCenter(view[topPos + 1], "Folder is the folder in which you launched the program.");
        
        fillLineWithAlignCenter(view[topPos + 3], currentName);
        for(int i = 0; i < view[topPos + 3].length; i++) {
            if(view[topPos + 3][i] != null) {
                view[topPos + 3][i].foregroundColor = Color.BLACK;
                view[topPos + 3][i].backgroundColor = Color.WHITE;
            }
        }
        
        return view;
    }
    
    @Override
    public void keyPressed(Key key) {
        if(key.getKind() == Kind.Enter) {
            load();
        } else if(key.getKind() == Kind.Escape) {
            if(delegate != null) {
                delegate.cancelLoading();
            }
        } else if(  key.getKind() != Kind.Backspace && 
                    key.getKind() != Kind.ArrowDown && 
                    key.getKind() != Kind.ArrowLeft && 
                    key.getKind() != Kind.ArrowUp && 
                    key.getKind() != Kind.ArrowRight) {
            
            if(d == true) {
                d = false;
                currentName = "";
            }
            
            if(currentName.length() < 40) {
                currentName += key.getCharacter();
            }
            
            printName();
        } else if(key.getKind() == Kind.Backspace) {
            if(currentName.length() > 0) {
                currentName = currentName.substring(0, currentName.length() - 1);
            }
            printName();
        }
    }
    
    public void printName() {
        ViewCharacter[] line = fillLineWithAlignCenter(new ViewCharacter[this.columnsVisible], currentName);
        for(ViewCharacter c : line) {
            c.backgroundColor = Color.BLACK;
            c.foregroundColor = Color.WHITE;
        }
        this.setCharacterLine(line, topPos + 3);
    }
    
    public void load() {
        if(delegate != null) {
            
            String n = currentName.equals("") ?  "level.properties" : currentName;
            delegate.loadFile(n);
        }
    }
    
    @Override
    protected void windowSizeUpdated(int rows, int columns) {
        super.windowSizeUpdated(rows, columns);
        topPos = (rowsVisible - 10) / 2;
    }
}

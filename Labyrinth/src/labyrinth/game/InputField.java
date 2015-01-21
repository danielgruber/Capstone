/**
 *
 * @author Daniel Gruber
 * @copyright 2014 Daniel Gruber
 * @license GNU Lesser General Public License, version 3; see "LICENSE.txt"
 */
package labyrinth.game;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;
import labyrinth.terminalManager.KeyboardDelegate;
import labyrinth.terminalManager.view.View;
import labyrinth.terminalManager.view.ViewCharacter;

/**
 *
 * @author D
 */
abstract public class InputField extends View implements KeyboardDelegate {
    /**
     * top position where to start putting text to have vertical align middle.
     */
    int topPos;
    
    /**
     * indicates if default value was used.
     */
    boolean d = true;
    
    String currentName = "";
    
    /**
     * 
     * @param rows
     * @param columns
     * @return ViewCharacters.
     */
    @Override
    public ViewCharacter[][] getCompleteView(int rows, int columns) {
        ViewCharacter[][] view = createViewMatrix(rows, columns);
        
        topPos = (rowsVisible - 10) / 2;
        
        fillLineWithAlignCenter(view[topPos], this.getMessage());
        fillLineWithAlignCenter(view[topPos + 1], this.getSecondMessage());
        
        fillLineWithAlignCenter(view[topPos + 3], this.defaultName());
        for(int i = 0; i < view[topPos + 3].length; i++) {
            if(view[topPos + 3][i] != null) {
                view[topPos + 3][i].foregroundColor = Terminal.Color.BLACK;
                view[topPos + 3][i].backgroundColor = Terminal.Color.WHITE;
            }
        }
        
        return view;
    }
    
    abstract public String getMessage();
    abstract public String getSecondMessage();
    abstract public String defaultName();
    
    public void keyPressed(Key key) {
        if(key.getKind() == Key.Kind.Enter) {
            String n = currentName.trim().equals("") ?  this.defaultName() : currentName;
            submit(n);
        } else if(key.getKind() == Key.Kind.Escape) {
            cancel();
        } else if(  key.getKind() != Key.Kind.Backspace && 
                    key.getKind() != Key.Kind.ArrowDown && 
                    key.getKind() != Key.Kind.ArrowLeft && 
                    key.getKind() != Key.Kind.ArrowUp && 
                    key.getKind() != Key.Kind.ArrowRight) {
            
            if(d == true) {
                d = false;
                currentName = "";
            }
            
            if(currentName.length() < 40) {
                currentName += key.getCharacter();
            }
            
            printName();
        } else if(key.getKind() == Key.Kind.Backspace) {
            if(currentName.length() > 0) {
                currentName = currentName.substring(0, currentName.length() - 1);
            }
            printName();
        }
    }
    
    public void printName() {
        ViewCharacter[] line = fillLineWithAlignCenter(new ViewCharacter[this.columnsVisible], currentName);
        for(ViewCharacter c : line) {
            c.backgroundColor = Terminal.Color.BLACK;
            c.foregroundColor = Terminal.Color.WHITE;
        }
        this.setCharacterLine(line, topPos + 3);
    }
    
    abstract public void submit(String info);
    abstract public void cancel();
    
    @Override
    protected void windowSizeUpdated(int rows, int columns) {
        super.windowSizeUpdated(rows, columns);
        topPos = (rowsVisible - 10) / 2;
    }
}

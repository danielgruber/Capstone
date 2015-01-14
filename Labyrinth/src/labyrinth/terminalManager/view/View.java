/**
 *
 * @author Daniel Gruber
 * @copyright 2014 Daniel Gruber
 * @license GNU Lesser General Public License, version 3; see "LICENSE.txt"
*/
package labyrinth.terminalManager.view;

import com.googlecode.lanterna.terminal.Terminal;
import java.awt.Dimension;
import labyrinth.terminalManager.CharacterDelegate;
import labyrinth.terminalManager.CharacterUpdate;


/**
 * a view returns all characters that should be visible in the current window.
 * it also updates them with the ViewUpdateDelegate protocol.
 * (The implementation of protocols is based on the objective C implemention)
 * 
 * @author D
 */
abstract public class View extends Thread {
    /**
     * current size of the visible window in columns.
     */
    protected int columnsVisible;
    
    /**
     * current size of the visible window in rows.
     */
    protected int rowsVisible;
    
    /**
     * delegate to character updates.
     */
    public CharacterDelegate characterDelegate;
    
    private boolean isVisible = false;
    
    abstract public ViewCharacter[][] getCompleteView(int rows, int columns);
    
    /**
     * gets minimum size of window.
     */
    public Dimension getMinimumSize() {
        return new Dimension(640,480);
    }
    
    /**
     * called by ViewManager when the window size updates.
     */
    void windowSizeUpdated(int rows, int columns) {
        this.rowsVisible = rows;
        this.columnsVisible = columns;
        
        System.out.println("Size updated: " + rows + "x" + columns);
    }
    
    void setVisible(boolean visiblity) {
        isVisible = visiblity;
    }
    
    public boolean getVisible() {
        return isVisible;
    }
    
    public boolean isVisible() {
        return getVisible();
    }
    
    public ViewCharacter c(char c) {
        return new ViewCharacter(c);
    }
    
    /**
     * fills an array with ViewCharacters
     * 
     * @param arr
     * @param pos
     * @param s
     * @return 
     */
    public ViewCharacter[] fillCharArray(ViewCharacter[] arr, int pos, String s) {
        for(int i = 0; i < s.length(); i++) {
            arr[i + pos] = new ViewCharacter(s.charAt(i));
        }
        return arr;
    }
    
    public ViewCharacter[] fillLineWithAlignCenter(ViewCharacter[] arr, String s) {
        if(arr.length < s.length()) {
            return fillCharArray(arr, 0, s);
        } else {
            int l = Math.round((arr.length - s.length()) / 2);
            for(int i = 0; i < arr.length; i++) {
                if(i >= l && i < l + s.length()) {
                    arr[i] = new ViewCharacter(s.charAt(i - l));
                } else {
                    arr[i] = new ViewCharacter(' ');
                    
                }
            }
            return arr;
            
        }
    }
    
    public boolean setCharacterLine(ViewCharacter[] arr, int y, int x) {
        boolean good = true;
        for(int i = 0; i < arr.length; i++) {
            CharacterUpdate c = getCharacterUpdate(x + i, y, arr[i].getCharacter(), arr[i].foregroundColor, arr[i].backgroundColor);
            if(!this.setCharacter(c)) {
                good = false;
            }
        }
        return good;
    }
    
    public boolean setCharacterLine(ViewCharacter[] arr, int y) {
        return this.setCharacterLine(arr, y, 0);
    }
    
    public ViewCharacter[][] createViewMatrix(int x, int y) {
        ViewCharacter[][] view = new ViewCharacter[x][y];
        
        return view;
    }
    
    public CharacterUpdate getCharacterUpdate(int x, int y, char c, Terminal.Color f, Terminal.Color b) {
        return new CharacterUpdate(c, x + 1, y + 1, f, b);
    }
    
    public boolean setCharacter(int x, int y, char c) {
        return characterDelegate.setCharacter(x, y, c);
    }
    
    public boolean setCharacter(CharacterUpdate c) {
        return characterDelegate.setCharacter(c);
    }
    
}

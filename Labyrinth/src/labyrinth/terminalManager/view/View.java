/**
 *
 * @author Daniel Gruber
 * @copyright 2014 Daniel Gruber
 * @license GNU Lesser General Public License, version 3; see "LICENSE.txt"
*/
package labyrinth.terminalManager.view;

import java.awt.Dimension;
import labyrinth.terminalManager.CharacterDelegate;


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
    }
    
    abstract public char[][] getCompleteView(int rows, int columns);
    
    void setVisible(boolean visiblity) {
        isVisible = visiblity;
    }
    
    public boolean getVisible() {
        return isVisible;
    }
    
    public boolean isVisible() {
        return getVisible();
    }
    
}

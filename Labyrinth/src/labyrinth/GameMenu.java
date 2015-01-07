/**
 *
 * @author Daniel Gruber
 * @copyright 2014 Daniel Gruber
 * @license GNU Lesser General Public License, version 3; see "LICENSE.txt"
 */
package labyrinth;

import com.googlecode.lanterna.input.Key;
import labyrinth.terminalManager.KeyboardDelegate;
import labyrinth.terminalManager.view.View;
import labyrinth.terminalManager.view.ViewCharacter;

/**
 *
 * @author D
 */
public class GameMenu extends View implements KeyboardDelegate {
    
    public final static int GAME_RESUME = 1;
    public final static int GAME_EXIT = 2;
    public final static int GAME_EXIT_WITHOUT_SAVE = 3;
    public final static int GAME_LOAD = 4;
    public final static int GAME_SAVE = 5;
    public final static int GAME_NEW = 6;
    public final static int SHOW_LEGEND = 7;
    
    /**
     * defines if this isthe view for game loaded or not loaded.
     */
    public boolean gameLoaded = false;
    
    public GameMenu(boolean gameLoaded) {
        this.gameLoaded = gameLoaded;
    }
    
    /**
     * defines if a game is running.
     * @param rows
     * @param columns
     * @return 
     */
    @Override
    public ViewCharacter[][] getCompleteView(int rows, int columns) {
        ViewCharacter[][] view = createViewMatrix(rows, columns);
        
        int topPos = (rowsVisible - 10) / 2;
        view[topPos] = fillLineWithAlignCenter(view[topPos], "Was wollen Sie tun?");
        view[topPos + 1] = fillLineWithAlignCenter(view[topPos + 1], "");
        
        return view;
    }

    @Override
    public void keyPressed(Key key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

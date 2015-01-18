/**
 *
 * @author Daniel Gruber
 * @copyright 2014 Daniel Gruber
 * @license GNU Lesser General Public License, version 3; see "LICENSE.txt"
 */
package labyrinth;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.input.Key.Kind;
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
    public final static int GAME_LOAD = 4;
    public final static int GAME_SAVE = 5;
    public final static int GAME_NEW = 6;
    public final static int SHOW_LEGEND = 7;
    
    public final static int[] menuInGame = new int[] {GAME_RESUME, GAME_LOAD, GAME_SAVE, GAME_NEW, SHOW_LEGEND, GAME_EXIT};
    public final static String[] menuInGameLabels = new String[] {"Resume Game", "Load Game", "Save Game", "Start New Game", "Show Help", "Exit"};
    public final static int[] menuNotInGame = new int[] {GAME_NEW, GAME_LOAD, SHOW_LEGEND, GAME_EXIT};
    public final static String[] menuNotInGameLabels = new String[] {"Start New Game", "Load Game", "Show Help", "Exit"};
    
    /**
     * defines if this isthe view for game loaded or not loaded.
     */
    public boolean gameLoaded = false;
    protected int position = 0;
    protected int topPos;
    protected int arrowPos = 4;
    
    public GameMenuDelegate delegate;
    
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
        
        topPos = (rowsVisible - 10) / 2;
        fillLineWithAlignCenter(view[topPos], "What do you want to do?");
        fillLineWithAlignCenter(view[topPos + 1], "");
        
        int[] menuKeys = getMenuKeys();
        String[] menuLabels = getMenuLabels();
        
        for(int i = 0; i < menuKeys.length; i++) {
             view[topPos + 2 + i] = fillLineWithAlignCenter(view[topPos + 2 + i], menuLabels[i]);
             
             if(position == i) {
                 view[topPos + 2 + i][arrowPos] = c('-');
                 view[topPos + 2 + i][arrowPos + 1] = c('>');
             }
        }
        
        return view;
    }
    
    public int[] getMenuKeys() {
        int[] menuKeys = menuNotInGame;
        if(gameLoaded) {
            menuKeys = menuInGame;
        }
        return menuKeys;
    }

    public String[] getMenuLabels() {
        String[] menu = menuNotInGameLabels;
        if(gameLoaded) {
            menu = menuInGameLabels;
        }
        return menu;
    }
    
    public void moveArrow(int oldPos, int newPos) {
        setCharacter(arrowPos, oldPos, ' ');
        setCharacter(arrowPos + 1, oldPos, ' ');
        
        setCharacter(arrowPos, newPos, '-');
        setCharacter(arrowPos + 1, newPos, '>');
    }
    
    public void arrowDown() {
        int[] menu = getMenuKeys();
        if(position < menu.length - 1) {
            position++;
            moveArrow(topPos + 1 + position, topPos + 2 + position);
        } else {
            moveArrow(topPos + 2 + position, topPos + 2);
            position = 0;

        }
    }
    
    public void arrowUp() {
        if(position > 0) {
            position--;
            moveArrow(topPos + 3 + position, topPos + 2 + position);
        } else {
            position = getMenuKeys().length - 1;
            moveArrow(topPos + 2, topPos + 2 + position);
        }
    }
    
    @Override
    public void keyPressed(Key key) {
        if(key.getKind() == Kind.ArrowDown) {
            arrowDown();
        } else if(key.getKind() == Kind.ArrowUp) {
            arrowUp();
        } else if(key.getKind() == Kind.Enter) {
            runAction();
        } else if(key.getKind() == Kind.Escape) {
            System.exit(0);
        }
    }
    
    public void runAction() {
        int[] menu = getMenuKeys();
        
        if(menu[position] == GAME_EXIT) {
            System.exit(0);
        } else {
            if(this.delegate != null) {
                this.delegate.actionFired(menu[position]);
            }
        }
    }
    
    @Override
    protected void windowSizeUpdated(int rows, int columns) {
        super.windowSizeUpdated(rows, columns);
        topPos = (rowsVisible - 10) / 2;
    }
}

interface GameMenuDelegate {
    public void actionFired(int action);
}
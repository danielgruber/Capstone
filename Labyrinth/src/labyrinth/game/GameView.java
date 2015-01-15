/**
 *
 * @author Daniel Gruber
 * @copyright 2014 Daniel Gruber
 * @license GNU Lesser General Public License, version 3; see "LICENSE.txt"
 */
package labyrinth.game;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.input.Key.Kind;
import labyrinth.game.GameObjects.GameObject;
import labyrinth.terminalManager.KeyboardDelegate;
import labyrinth.terminalManager.view.View;
import labyrinth.terminalManager.view.ViewCharacter;

/**
 *
 * @author D
 */
public class GameView extends View implements KeyboardDelegate {

    public GameViewDelegate delegate;
    
    /**
     * reference to model.
    */
    GameModel model;
    
    /**
     * information about the current position of the view.
     */
    int StatusBarHeight = 2;
    int scrollTop = 0;
    int scrollLeft = 0;
    
    
    
    public GameView(GameModel model) {
        super();
        
        this.model = model;
    }
    
    @Override
    public ViewCharacter[][] getCompleteView(int rows, int columns) {
        ViewCharacter[][] view = this.createViewMatrix(rows, columns);
        
        view[0] = getStatusBar();
        
        ViewCharacter[][] viewRows = getCompleteGameView(new SizeInfo(columns, rows - 2), calculateTopLeft());
        System.arraycopy(viewRows, 0, view, 2, rows - 2);
    
        
        return view;
    }
    
    public ViewCharacter[][] getCompleteGameView(SizeInfo size, PositionInfo topLeft) {
        ViewCharacter[][] view = this.createViewMatrix(size.height, size.width);
        
        GameObject o;

        for(int x = 0; x + topLeft.x < model.width() && x < size.width; x++) {
            for(int y = 0; y + topLeft.y < model.height() && y < size.height; y++) {
                o = model.getObjectAt(new PositionInfo(x + topLeft.x,y + topLeft.y));
                
                if(o != null) {
                    view[y][x] = o.getCharacter();
                }
            }
        }
        
        return view;
    }
    
    public PositionInfo calculateTopLeft() {
        int left = scrollLeft;
        int top = scrollTop;
        
        if(model.width() < left + this.columnsVisible && model.width() > this.columnsVisible) {
            left = model.width() - this.columnsVisible;
        }
        
        if(model.height() < top + this.rowsVisible && model.height() > this.columnsVisible) {
            top = model.height() - this.columnsVisible;
        }
        
        return new PositionInfo(left, top);
    }
    
    public ViewCharacter[] getStatusBar() {
        String key = (this.model.inventory.hasItemOfType(Key.class)) ? "Key" : "No Key";
        return this.fillCharArray(new ViewCharacter[columnsVisible], 0, "Lifes: " + model.lifeManager.getLifes() + "  Have Key?: " + key);
    }
    
    @Override
    public void keyPressed(Key key) {
        if(key.getKind() == Kind.Escape) {
            if(delegate != null) {
                delegate.wantsToMenu();
            }
        }
    }
}

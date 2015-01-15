/**
 *
 * @author Daniel Gruber
 * @copyright 2014 Daniel Gruber
 * @license GNU Lesser General Public License, version 3; see "LICENSE.txt"
 */
package labyrinth.game;

import com.googlecode.lanterna.input.Key;
import labyrinth.terminalManager.view.View;
import labyrinth.terminalManager.view.ViewCharacter;

/**
 *
 * @author D
 */
public class GameView extends View {

    GameModel model;
    
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
        
        return view;
    }
    
    public ViewCharacter[] getStatusBar() {
        String key = (this.model.inventory.hasItemOfType(Key.class)) ? "Key" : "No Key";
        return this.fillCharArray(new ViewCharacter[columnsVisible], 0, "Lifes: " + model.lifeManager.getLifes() + "  Have Key?: " + key);
    }
    
}

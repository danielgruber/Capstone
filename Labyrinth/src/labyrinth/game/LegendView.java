package labyrinth.game;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.input.Key.Kind;
import labyrinth.game.GameObjects.Ausgang;
import labyrinth.game.GameObjects.DynamicMonster;
import labyrinth.game.GameObjects.Eingang;
import labyrinth.game.GameObjects.GameObject;
import labyrinth.game.GameObjects.KeyObject;
import labyrinth.game.GameObjects.Player;
import labyrinth.game.GameObjects.StaticMonster;
import labyrinth.game.GameObjects.Wall;
import labyrinth.terminalManager.KeyboardDelegate;
import labyrinth.terminalManager.view.View;
import labyrinth.terminalManager.view.ViewCharacter;


/**
 *
 * @author Daniel Gruber
 * @copyright 2014 Daniel Gruber
 * @license GNU Lesser General Public License, version 3; see "LICENSE.txt"
 */
/**
 *
 * @author D
 */
public class LegendView extends View implements KeyboardDelegate {

    int topPos;
    
    public LegendDelegate delegate;
    
    @Override
    public ViewCharacter[][] getCompleteView(int rows, int columns) {
        
        topPos = (rowsVisible - 10) / 2;

        ViewCharacter[][] view = this.createViewMatrix(rows, columns);
        drawGameObject(view, new Eingang(), topPos);
        drawGameObject(view, new Ausgang(), topPos + 1);
        drawGameObject(view, new Player(), topPos + 2);
        drawGameObject(view, new KeyObject(), topPos + 3);
        drawGameObject(view, new StaticMonster(), topPos + 4);
        drawGameObject(view, new Wall(), topPos + 5);
        
        this.fillLineWithAlignCenter(view[topPos + 7], "Press Escape to leave.");
        
        return view;
    }
    
    public void drawGameObject(ViewCharacter[][] view, GameObject o, int position) {
        view[position] = this.fillLineWithAlignCenter(view[position], o.getCharacter().getCharacter() + " " + o.getName());
        for(ViewCharacter vc : view[position]) {
            vc.foregroundColor = o.getCharacter().foregroundColor;
            vc.backgroundColor = o.getCharacter().backgroundColor;
        }
    }
    
     @Override
    protected void windowSizeUpdated(int rows, int columns) {
        super.windowSizeUpdated(rows, columns);
        topPos = (rowsVisible - 10) / 2;
    }

    @Override
    public void keyPressed(Key key) {
        if(key.getKind() == Kind.Escape) {
            if(this.delegate != null) {
                this.delegate.wantsToMenu();
            }
        }
    }
    
}

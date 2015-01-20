/**
 *
 * @author Daniel Gruber
 * @copyright 2014 Daniel Gruber
 * @license GNU Lesser General Public License, version 3; see "LICENSE.txt"
 */
package labyrinth.game.GameObjects;

import com.googlecode.lanterna.terminal.Terminal.Color;
import labyrinth.terminalManager.view.ViewCharacter;

/**
 *
 * @author D
 */
public class StaticMonster extends GameObject {

    
    @Override
    public ViewCharacter getCharacter() {
        ViewCharacter c = new ViewCharacter('M');
        c.foregroundColor = Color.RED;
        return c;
    }

    @Override
    public int coliding(GameObject colidingObject) {
       if(colidingObject instanceof Player) {
           return GameObject.LOSE_LIFE;
       }
       
       return GameObject.CANNOT_GO;
    }
    
    
    @Override
    public String getPropertyRepresentation() {
        return "3";
    }
    
}

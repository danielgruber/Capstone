/**
 *
 * @author Daniel Gruber
 * @copyright 2014 Daniel Gruber
 * @license GNU Lesser General Public License, version 3; see "LICENSE.txt"
 */
package labyrinth.game.GameObjects;

import com.googlecode.lanterna.terminal.Terminal;
import labyrinth.terminalManager.view.ViewCharacter;

/**
 *
 * @author D
 */
public class Ausgang extends GameObject {

    @Override
    public ViewCharacter getCharacter() {
        ViewCharacter c = new ViewCharacter(' ');
        c.backgroundColor = Terminal.Color.GREEN;
        return c;
    }

    @Override
    public int coliding(GameObject colidingObject) {
        if(colidingObject instanceof Player) {
            return GameObject.WIN_WHEN_KEY;
        }
        return GameObject.CANNOT_GO;
    }
    
    
    @Override
    public String getPropertyRepresentation() {
        return "2";
    }

    @Override
    public String getName() {
        return "Exit";
    }
}

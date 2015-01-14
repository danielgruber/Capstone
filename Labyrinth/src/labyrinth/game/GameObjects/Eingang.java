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
public class Eingang extends GameObject {
    
    @Override
    public ViewCharacter getCharacter() {
        ViewCharacter c = new ViewCharacter(' ');
        c.backgroundColor = Terminal.Color.YELLOW;
        return c;
    }

    @Override
    public int coliding(GameObject colidingObject) {
        return GameObject.CANNOT_GO;
    }
    
    
    @Override
    public String getPropertyRepresentation() {
        return "1";
    }
}

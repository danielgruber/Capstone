/**
 *
 * @author Daniel Gruber
 * @copyright 2014 Daniel Gruber
 * @license GNU Lesser General Public License, version 3; see "LICENSE.txt"
 */
package labyrinth.game.GameObjects;

import labyrinth.game.InventoryGameObject;
import labyrinth.terminalManager.view.ViewCharacter;

/**
 *
 * @author D
 */
public class KeyObject extends GameObject implements InventoryGameObject {

    
    @Override
    public ViewCharacter getCharacter() {
        return new ViewCharacter('K');
    }

    @Override
    public int coliding(GameObject colidingObject) {
        System.out.println("colide");
        if(colidingObject instanceof Player) {
            return GameObject.COLLECT;
        }
       
       return GameObject.CANNOT_GO;
    }
    
    
    @Override
    public String getPropertyRepresentation() {
        return "5";
    }
}

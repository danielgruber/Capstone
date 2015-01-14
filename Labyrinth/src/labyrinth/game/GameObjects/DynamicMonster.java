/**
 *
 * @author Daniel Gruber
 * @copyright 2014 Daniel Gruber
 * @license GNU Lesser General Public License, version 3; see "LICENSE.txt"
 */
package labyrinth.game.GameObjects;

import com.googlecode.lanterna.input.Key;
import labyrinth.terminalManager.view.ViewCharacter;

/**
 *
 * @author D
 */
public class DynamicMonster extends DynamicGameObject {
   
    
    @Override
    public ViewCharacter getCharacter() {
        return new ViewCharacter('M');
    }

    @Override
    public int coliding(GameObject colidingObject) {
       if(colidingObject instanceof Player) {
           return GameObject.LOSE_LIFE;
       }
       
       return GameObject.CANNOT_GO;
    }

    @Override
    public DynamicObjectUpdate moveObject(int positionX, int positionY, Key k) {
        DynamicObjectUpdate c = new DynamicObjectUpdate(positionX, positionY);
        if(Math.random() > 0.5) {
            if(Math.random() > 0.5) {
                c.moveY(positionY + 1);
            } else {
                c.moveY(positionY - 1);
            }
        } else {
            if(Math.random() > 0.5) {
                c.moveX(positionX + 1);
            } else {
                c.moveX(positionX - 1);
            }
        }
        
        return c;
    }
    
    
    @Override
    public String getPropertyRepresentation() {
        return "4";
    }
}

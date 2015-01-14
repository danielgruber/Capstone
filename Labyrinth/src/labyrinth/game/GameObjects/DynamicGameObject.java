/**
 *
 * @author Daniel Gruber
 * @copyright 2014 Daniel Gruber
 * @license GNU Lesser General Public License, version 3; see "LICENSE.txt"
 */
package labyrinth.game.GameObjects;

import labyrinth.game.GameObjects.DynamicObjectUpdate;
import labyrinth.game.GameObjects.GameObject;
import com.googlecode.lanterna.input.Key;

/**
 *
 * @author D
 */
abstract public class DynamicGameObject extends GameObject {
    /**
     * is called when the game object should be moved.
     * 
     * you get the current position of the game object and should return an DynamicObjectUpdate.
     * 
     * @param positionX
     * @param positionY
     * @param key
     * @return DynamicObjectUpdate
     */
    abstract public DynamicObjectUpdate moveObject(int positionX, int positionY, Key k);
}

/**
 *
 * @author Daniel Gruber
 * @copyright 2014 Daniel Gruber
 * @license GNU Lesser General Public License, version 3; see "LICENSE.txt"
 */
package labyrinth.game.GameObjects;

/**
 *
 * @author D
 */
public class DynamicObjectUpdate {
    protected int positionX;
    protected int positionY;
    protected boolean hasMoved = false;
    
     /**
     * creates an DynamicObjectUpdate with hasMoved set to false.
     * 
     * @param positionX
     * @param positonY
     */
    public DynamicObjectUpdate(int positionX, int positonY) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.hasMoved = false;
    }
    
    /**
     * creates an DynamicObjectUpdate.
     * 
     * @param positionX
     * @param positonY
     * @param hasMoved 
     */
    public DynamicObjectUpdate(int positionX, int positonY, boolean hasMoved) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.hasMoved = hasMoved;
    }
    
    public void move(int x, int y) {
        positionX = x;
        positionY = y;
        hasMoved = true;
    }
    
    public void moveX(int x) {
        positionX = x;
        hasMoved = true;
    }
    
    public void moveY(int y) {
        positionY = y;
        hasMoved = true;
    }
}

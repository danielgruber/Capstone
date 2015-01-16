/**
 *
 * @author Daniel Gruber
 * @copyright 2014 Daniel Gruber
 * @license GNU Lesser General Public License, version 3; see "LICENSE.txt"
 */
package labyrinth.game.GameObjects;

import labyrinth.game.PositionInfo;

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
    public DynamicObjectUpdate(int positionX, int positionY) {
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
    public DynamicObjectUpdate(int positionX, int positionY, boolean hasMoved) {
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
    
    public boolean hasMoved() {
        return hasMoved;
    }
    
    public PositionInfo getPosition() {
        return new PositionInfo(positionX, positionY);
    }
    
    public String toString() {
        return "X: " + positionX + "; Y: " + positionY;
    }
}

/**
 *
 * @author Daniel Gruber
 * @copyright 2014 Daniel Gruber
 * @license GNU Lesser General Public License, version 3; see "LICENSE.txt"
 */
package labyrinth.game;

import java.util.LinkedList;

/**
 *
 * @author D
 */
public class InventoryManager {
    /**
     * list of items in inventory.
     */
    LinkedList<InventoryGameObject> items;
    
    /**
     * generates a new empty inventory.
     */
    public InventoryManager() {
        items = new LinkedList();
    }
    
    public void add(InventoryGameObject item) {
        items.add(item);
    }
    
    public void remove(InventoryGameObject item) {
        items.remove(item);
    }
    
    public boolean hasItemOfType(Class type) {
        for(InventoryGameObject i : items) {
            if(type.isInstance(i)) {
                return true;
            }
        }
        
        return false;
    }
    
    public InventoryGameObject getFirstItemOfType(Class type) {
        for(InventoryGameObject i : items) {
            if(type.isInstance(i)) {
                return i;
            }
        }
        
        return null;
    }
}

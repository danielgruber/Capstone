/**
 *
 * @author Daniel Gruber
 * @copyright 2014 Daniel Gruber
 * @license GNU Lesser General Public License, version 3; see "LICENSE.txt"
 */
package labyrinth.game.GameObjects;

import labyrinth.terminalManager.view.ViewCharacter;

/**
 *
 * @author D
 */
abstract public class GameObject {
    
    public final static int LOSE_LIFE = -2;
    public final static int COLLECT = -3;
    public final static int WIN_WHEN_KEY = -4;
    public final static int CANNOT_GO = -5;
    
    public static GameObject generate_object(String info) {
        if(info == null) {
            return null;
        }
        
        if(info.equals("")) {
            return null;
        }
        
        if(info.equals("1")) {
            return new Eingang();
        }
        
        if(info.equals("2")) {
            return new Ausgang();
        }
        
        if(info.equals("3")) {
            return new StaticMonster();
        }
        
        if(info.equals("4")) {
            return new DynamicMonster();
        }
        
        if(info.equals("5")) {
            return new Key();
        }
        
        if(info.equals("6")) {
            return new Player();
        }
        
        return new Wall();
    } 
    
    /**
     * returns the character that is represented by the game object.
     */
    abstract public ViewCharacter getCharacter(); 
    
     /**
     * defines action when coliding. The action is called on the object
     * which is notj in debt of the colision.
     * 
     * possible Actions:
     * 
     * GameObject.LOSE_LIFE
     * GameObject.COLLECT
     * GameObject.CANNOT_GO
     * GameObject.WIN_WHEN_KEY
     */
    abstract public int coliding(GameObject colidingObject);
    
    /**
     * returns representation for property-list.
     */
    abstract public String getPropertyRepresentation();
}

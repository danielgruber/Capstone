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
    
    public static GameObject generate_object(String info, int x, int y) {
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
            DynamicMonster m = new DynamicMonster();
            m.x = x;
            m.y = y;
            return m;
        }
        
        if(info.equals("5")) {
            return new KeyObject();
        }
        
        if(info.equals("6")) {
            Player p = new Player();
            p.x = x;
            p.y = y;
            return p;
        }
        
        return new Wall();
    } 
    
    public static GameObject generate_object(String info) {
        return generate_object(info, -1, -1);
    }
    
    /**
     * returns the character that is represented by the game object.
     */
    abstract public ViewCharacter getCharacter(); 
    
     /**
     * defines action when coliding. The action is called on the object
     * which is not in debt of the colision.
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

/**
 *
 * @author Daniel Gruber
 * @copyright 2014 Daniel Gruber
 * @license GNU Lesser General Public License, version 3; see "LICENSE.txt"
 */
package labyrinth.game;

/**
 *
 * @author D
 */
public class LifeManager {
    
    int lifes;
    
    LifeManagerDelegate delegate;
    
    public LifeManager(int lifes) {
        this.lifes = lifes;
    }
    
    public LifeManager() {
        this.lifes = 5;
    }
    
    public int loseLife() {
        if(lifes > 0) {
            lifes--;
        }
        
        checkForDelegate();
        
        return lifes;
    }
    
    public int increaseLife() {
        lifes++;
        
        checkForDelegate();
        
        return lifes;
    }
    
    public int getLifes() {
        return lifes;
    }
    
    public void setLifes(int lifes) {
        this.lifes = lifes;
    }
    
    public void checkForDelegate() {
        if(this.delegate != null) {
            this.delegate.lifeUpdate(lifes);
            if(lifes == 0) {
                this.delegate.allLifesLost();
            }
        }
    }
    
}

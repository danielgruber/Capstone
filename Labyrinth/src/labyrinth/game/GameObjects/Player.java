/**
 *
 * @author Daniel Gruber
 * @copyright 2014 Daniel Gruber
 * @license GNU Lesser General Public License, version 3; see "LICENSE.txt"
 */
package labyrinth.game.GameObjects;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.input.Key.Kind;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.Terminal.Color;
import labyrinth.terminalManager.view.ViewCharacter;

/**
 *
 * @author D
 */
public class Player extends DynamicGameObject {
    
    @Override
    public DynamicObjectUpdate moveObject(int positionX, int positionY, Key k) {
        DynamicObjectUpdate d = new DynamicObjectUpdate(positionX, positionY);
        if(k.getKind() == Kind.ArrowDown) {
            d.moveY(positionY + 1);
        }
        if(k.getKind() == Kind.ArrowUp) {
            d.moveY(positionY - 1);
        }
        if(k.getKind() == Kind.ArrowLeft) {
            d.moveX(positionX - 1);
        }
        if(k.getKind() == Kind.ArrowRight) {
            d.moveX(positionX + 1);
        }
        return d;
    }

    @Override
    public ViewCharacter getCharacter() {
        ViewCharacter c = new ViewCharacter('P');
        c.foregroundColor = Color.GREEN;
        return c;
    }

    @Override
    public int coliding(GameObject colidingObject) {
        if(colidingObject instanceof DynamicMonster || colidingObject instanceof StaticMonster) {
            return GameObject.LOSE_LIFE;
        }
        
        return GameObject.CANNOT_GO;
    }
    
    
    @Override
    public String getPropertyRepresentation() {
        return "6";
    }
    
}

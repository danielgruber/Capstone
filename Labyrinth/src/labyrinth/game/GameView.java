/**
 *
 * @author Daniel Gruber
 * @copyright 2014 Daniel Gruber
 * @license GNU Lesser General Public License, version 3; see "LICENSE.txt"
 */
package labyrinth.game;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.input.Key.Kind;
import java.util.LinkedList;
import labyrinth.game.GameObjects.DynamicGameObject;
import labyrinth.game.GameObjects.DynamicMonster;
import labyrinth.game.GameObjects.DynamicObjectUpdate;
import labyrinth.game.GameObjects.GameObject;
import labyrinth.game.GameObjects.Player;
import labyrinth.terminalManager.KeyboardDelegate;
import labyrinth.terminalManager.view.View;
import labyrinth.terminalManager.view.ViewCharacter;

/**
 *
 * @author D
 */
public class GameView extends View implements KeyboardDelegate {

    public GameViewDelegate delegate;
    
    /**
     * reference to model.
    */
    GameModel model;
    
    /**
     * information about the current position of the view.
     */
    int StatusBarHeight = 1;
    int scrollTop = 0;
    int scrollLeft = 0;
    boolean hasStarted = false;
    
    /**
     * safe area.
     */
    final int safeArea = 4;
    private int loop = 0;
    
    /**
     * linked list for keys.
     */
    LinkedList<Key> keyQueue;
    
    /**
     * constructor.
     * 
     * @param model 
     */
    public GameView(GameModel model) {
        super();
        
        this.keyQueue = new LinkedList();
        this.model = model;
    }
    
    @Override
    public ViewCharacter[][] getCompleteView(int rows, int columns) {
        ViewCharacter[][] view = this.createViewMatrix(rows, columns);
        
        view[0] = getStatusBar();
        
        updateTopLeftByPlayer();
        
        ViewCharacter[][] viewRows = getCompleteGameView(new SizeInfo(columns, rows - StatusBarHeight), calculateTopLeft());
        System.arraycopy(viewRows, 0, view, StatusBarHeight, rows - StatusBarHeight);
    
        
        return view;
    }
    
    public ViewCharacter[][] getCompleteGameView(SizeInfo size, PositionInfo topLeft) {
        ViewCharacter[][] view = this.createViewMatrix(size.height, size.width);
        
        GameObject o;

        for(int x = 0; x + topLeft.x < model.width() && x < size.width; x++) {
            for(int y = 0; y + topLeft.y < model.height() && y < size.height; y++) {
                o = model.getObjectAt(new PositionInfo(x + topLeft.x,y + topLeft.y));
                
                if(o != null) {
                    view[y][x] = o.getCharacter();
                }
            }
        }
        
        return view;
    }
    
    public PositionInfo calculateTopLeft() {
        int left = scrollLeft;
        int top = scrollTop;
        
        if(model.width() < left + this.columnsVisible && model.width() > this.columnsVisible) {
            left = model.width() - this.columnsVisible;
        }
        
        if(model.height() < top + this.rowsVisible && model.height() > this.columnsVisible) {
            top = model.height() - this.columnsVisible;
        }
        
        return new PositionInfo(left, top);
    }
    
    public ViewCharacter[] getStatusBar() {
        String key = (this.model.inventory.hasItemOfType(Key.class)) ? "Key" : "No Key";
        return this.fillCharArray(new ViewCharacter[columnsVisible], 0, "Lifes: " + model.lifeManager.getLifes() + "  Have Key?: " + key);
    }
    
    public void updateTopLeftByPlayer() {
        if(!model.isPlayerWithinSafeArea(new PositionInfo(scrollLeft, scrollTop), new SizeInfo(this.columnsVisible, this.rowsVisible), safeArea)) {
            PositionInfo p = model.getTopLeftByPlayer(new SizeInfo(this.columnsVisible, this.rowsVisible));
            scrollLeft = p.x;
            scrollTop = p.y;
        }
    }
    
    @Override
    public void keyPressed(Key key) {
        if(key.getKind() == Kind.Escape) {
            if(delegate != null) {
                delegate.wantsToMenu();
            }
        }
        
        synchronized(keyQueue) {
            keyQueue.add(key);
        }
    }
    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        
        if(!hasStarted) {
            this.start();
            hasStarted = true;
        }
    }
    
    protected void win() {
        
    }
    
    public void updateStatusBar() {
        this.setCharacterLine(this.getStatusBar(), 0);
    }
    
    protected boolean checkForColision(GameObject object, GameObject coliding) {
        int action = object.coliding(coliding);
        
        if(action == GameObject.CANNOT_GO) {
            return false;
        } else if(action == GameObject.LOSE_LIFE) {
            System.out.println("Lose life");
            model.lifeManager.loseLife();
            updateStatusBar();
            return false;
        } else if(action == GameObject.COLLECT && coliding instanceof InventoryGameObject) {
            System.out.println("Collect");
            model.inventory.add((InventoryGameObject) coliding);
            updateStatusBar();
            return true;
        } else if(action == GameObject.WIN_WHEN_KEY) {
            if(model.inventory.hasItemOfType(Key.class)) {
                win();
                return false;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
    
    protected void moveObject(DynamicGameObject object, DynamicObjectUpdate c) {
        model.matrix[object.x][object.y] = null;
        
        PositionInfo newPos = c.getPosition();
        
        this.setCharacter(object.x, object.y + StatusBarHeight, ' ');
        this.setCharacter(newPos.x, newPos.y + StatusBarHeight, object.getCharacter());
        
        model.matrix[newPos.x][newPos.y] = object;
        object.x = newPos.x;
        object.y = newPos.y;
        
        
    }
    
    public void run() {
        while(isActive) {
            if(isVisible) {
                
                loop++;
                
                Key latest = null;
                
                synchronized(keyQueue) {
                    
                    if(!keyQueue.isEmpty()) {
                        latest = keyQueue.pop();
                    }
                    
                    for(DynamicGameObject object : model.dynamicObjects) {
                        
                        if(object instanceof Player || loop % 45 == 0) {
                            DynamicObjectUpdate update = object.moveObject(object.x, object.y, latest);

                            
                            
                            if(update.hasMoved()) {
                                PositionInfo p = update.getPosition();

                                if(model.matrix[p.x][p.y] == null || checkForColision(object, model.matrix[p.x][p.y])) {
                                    moveObject(object, update);
                                }

                            }
                        }
                        
                        
                    }
                    
                    updateTopLeftByPlayer();
                }
                
                // sleep for about a frame.
                try {
                    sleep(16);
                } catch(Exception e) {}
            } else {
                
                // sleep a while until view is visible.
                try {
                    sleep(500);
                } catch(Exception e) {}
            }
        }
    }
    
}

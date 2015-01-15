/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth;

import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import labyrinth.game.GameFileNotFoundOrMalformedException;
import labyrinth.game.GameLoadDelegate;
import labyrinth.game.GameLoadView;
import labyrinth.game.GameModel;
import labyrinth.game.GameView;
import labyrinth.terminalManager.TerminalManager;
import labyrinth.terminalManager.TerminalWriter;
import labyrinth.terminalManager.WindowDelegate;
import labyrinth.terminalManager.view.ViewManager;

/**
 *
 * @author D
 */
public class Labyrinth implements GameMenuDelegate, GameLoadDelegate {

    TerminalManager manager;
    ViewManager viewManager;
    
    GameMenu menu;
    GameLoadView loadView;
    GameView gameView;
    GameModel gameModel;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new Labyrinth();
    }
    
    public Labyrinth() {
        manager = new TerminalManager();
        manager.start();
        
        createMenu();
        
        viewManager = new ViewManager(manager);
        viewManager.setView(menu);
    }
    
    public void createMenu() {
        menu = new GameMenu(false);
        menu.delegate = this;
    }
    
    public void createLoadView() {
        loadView = new GameLoadView();
        loadView.delegate = this;
    }

    @Override
    public void actionFired(int action) {
        if(action == GameMenu.GAME_LOAD) {
            createLoadView();
            viewManager.setView(loadView);
        }
    }
    
    public GameModel loadGameModel(String file) throws GameFileNotFoundOrMalformedException, Exception {
        gameModel = new GameModel(file);
        return gameModel;
    }
    
    public GameView loadGameViewWithModel(GameModel m) {
        gameView = new GameView(m);
        return gameView;
    }
    
    @Override
    public void cancelLoading() {
        viewManager.setView(menu);
    }
    
    @Override
    public void loadFile(String filename) {
        try {
            GameModel m = loadGameModel(filename);
            m.debug();
            GameView v = loadGameViewWithModel(m);
            viewManager.setView(v);
        } catch (Exception ex) {
            Logger.getLogger(Labyrinth.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

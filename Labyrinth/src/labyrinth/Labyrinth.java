/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import labyrinth.game.GameFileNotFoundOrMalformedException;
import labyrinth.game.GameLoadDelegate;
import labyrinth.game.GameLoadView;
import labyrinth.game.GameModel;
import labyrinth.game.GameView;
import labyrinth.game.GameViewDelegate;
import labyrinth.message.Message;
import labyrinth.message.MessageDelegate;
import labyrinth.terminalManager.TerminalManager;
import labyrinth.terminalManager.view.ViewManager;

/**
 *
 * @author D
 */
public class Labyrinth implements GameMenuDelegate, GameLoadDelegate, GameViewDelegate, MessageDelegate {

    /**
     * managers for terminal and views.
     */
    TerminalManager manager;
    ViewManager viewManager;
    
    /**
     * all views and models.
     */
    GameMenu menu;
    GameLoadView loadView;
    GameView gameView;
    GameModel gameModel;
    
    /**
     * game information
     */
    boolean inGame = false;
    
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
        menu = new GameMenu(inGame);
        menu.delegate = this;
    }
    
    public void createLoadView() {
        loadView = new GameLoadView();
        loadView.delegate = this;
    }

    public GameModel loadGameModel(String file) throws GameFileNotFoundOrMalformedException, Exception {
        gameModel = new GameModel(file);
        return gameModel;
    }
    
    public GameView loadGameViewWithModel(GameModel m) {
        gameView = new GameView(m);
        return gameView;
    }
    
    public void showMenu() {
        menu.gameLoaded = inGame;
        viewManager.setView(menu);
    }
    
    /**
     * delegate of GameMenu.
     * 
     * @param action 
     */
    @Override
    public void actionFired(int action) {
        if(action == GameMenu.GAME_LOAD) {
            createLoadView();
            viewManager.setView(loadView);
        } else if(action == GameMenu.GAME_RESUME && inGame) {
            viewManager.setView(gameView);
        } else if(action == GameMenu.SHOW_LEGEND) {
            
        } else if(action == GameMenu.GAME_NEW) {
            generateLevelAndLoad();
        }
    }

    // adds numbers at the end of the file.
    public String getFileName(String start, String ext) {
        if(!fileExists(start + "." + ext)) {
            return start + "." + ext;
        }
        
        int i = 0; 
        while(fileExists(start + "-" + i + "." + ext)) {
            i++;
        }
        return start + "-" + i + "." + ext;
    }
    
    public boolean fileExists(String f) {
        File file = new File(f);
        return file.exists();
    }
    
    public void generateLevelAndLoad() {
        String file = getFileName("generated", "properties");
        Generator.generate(false, file);
        loadFile(file, true);
    }
    
    /**
     * delegate of GameLoadView.
     */
    @Override
    public void cancelLoading() {
        viewManager.setView(menu);
    }
    
    public void loadFile(String filename, boolean removeWhenWon) {
        try {
            GameModel m = loadGameModel(filename);
            m.removeWhenWon = removeWhenWon;
            GameView v = loadGameViewWithModel(m);
            v.delegate = this;
            viewManager.setView(v);
            inGame = true;
        } catch (Exception ex) {
            Logger.getLogger(Labyrinth.class.getName()).log(Level.SEVERE, null, ex);
            Message exception = new Message("exception", "Could not load file. It does not exist or is corrupted.");
            exception.delegate = this;
            viewManager.setView(exception);
        }
    }
    
    @Override
    public void loadFile(String filename) {
        loadFile(filename, false);
    }
    
    /**
     * delegate of GameView.
     */
    
    @Override
    public void wantsToMenu() {
        showMenu();
    }

    @Override
    public void won(String f) {
        Message win = new Message("win", "congratulations!");
        inGame = false;
        win.delegate = this;
        this.viewManager.setView(win);
    }

    @Override
    public void lost(String f) {
        Message lose = new Message("lose", ":-(");
        inGame = false;
        lose.delegate = this;
        this.viewManager.setView(lose);
    }
}

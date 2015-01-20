/**
 *
 * @author Daniel Gruber
 * @copyright 2014 Daniel Gruber
 * @license GNU Lesser General Public License, version 3; see "LICENSE.txt"
 */
package labyrinth.game;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.input.Key.Kind;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.Terminal.Color;
import labyrinth.terminalManager.KeyboardDelegate;
import labyrinth.terminalManager.view.View;
import labyrinth.terminalManager.view.ViewCharacter;

/**
 *
 * @author D
 */
public class GameLoadView extends InputField {

    String message = "Type Filename to load. Default is level.properties.";
    String secondMessage = "Folder is the folder in which you launched the program.";

    /**
     * Delegate.
     */
    public GameLoadDelegate delegate;

    
    public void submit(String name) {
        if(delegate != null) {
            delegate.loadFile(name);
        }
    }

    @Override
    public void cancel() {
       if(delegate != null) {
           this.delegate.cancelLoading();
       }
    }
    
}

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
public class GameSaveView extends InputField {

    public GameSaveViewDelegate delegate;
    
    @Override
    public void submit(String n) {
        if(this.delegate != null) {
            this.delegate.save(n);
        }
    }

    @Override
    public void cancel() {
        if(this.delegate != null) {
            this.delegate.continueGame();
        }
    }

    @Override
    public String getMessage() {
        return  "Type Filename in which the state should be stored. Default is savegame.properties.";
    }

    @Override
    public String getSecondMessage() {
        return "Folder is the folder in which you launched the program.";
    }

    @Override
    public String defaultName() {
        return "savegame.properties";
    }
    
}

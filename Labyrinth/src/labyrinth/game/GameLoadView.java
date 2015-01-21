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
public class GameLoadView extends InputField {

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

    @Override
    public String getMessage() {
        return "Type Filename to load. Default is level.properties.";
    }

    @Override
    public String getSecondMessage() {
        return "Folder is the folder in which you launched the program.";
    }
    
    @Override
    public String defaultName() {
        return "level.properties";
    }
    
}

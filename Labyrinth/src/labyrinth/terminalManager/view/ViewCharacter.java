/**
 *
 * @author Daniel Gruber
 * @copyright 2014 Daniel Gruber
 * @license GNU Lesser General Public License, version 3; see "LICENSE.txt"
 */
package labyrinth.terminalManager.view;

import com.googlecode.lanterna.terminal.Terminal;

/**
 *
 * @author D
 */
public class ViewCharacter {
    
    char character;
    Terminal.Color foregroundColor;
    Terminal.Color backgroundColor;
    
    public ViewCharacter(char c) {
        character = c;
    }
    
    public char getCharacter() {
        return character;
    }
}

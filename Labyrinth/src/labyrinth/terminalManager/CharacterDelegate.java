/**
 *
 * @author Daniel Gruber
 * @copyright 2014 Daniel Gruber
 * @license GNU Lesser General Public License, version 3; see "LICENSE.txt"
*/
package labyrinth.terminalManager;

import labyrinth.terminalManager.CharacterUpdate;

/**
 *
 * @author D
 */

public interface CharacterDelegate {
    /**
     * called when a character is updated.
     * 
     * @param x
     * @param y
     * @param c 
     */
    public boolean setCharacter(int x, int y, char c);
    public boolean setCharacter(CharacterUpdate c);
}
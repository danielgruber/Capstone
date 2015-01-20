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
public interface GameSaveViewDelegate {
    public void save(String filename);
    public void continueGame();
}

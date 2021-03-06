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
public interface GameViewDelegate {
    public void wantsToMenu();
    public void won(String file);
    public void lost(String file);
}

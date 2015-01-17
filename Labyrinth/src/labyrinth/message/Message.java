/**
 *
 * @author Daniel Gruber
 * @copyright 2014 Daniel Gruber
 * @license GNU Lesser General Public License, version 3; see "LICENSE.txt"
 */
package labyrinth.message;

/**
 *
 * @author D
 */
public class Message {
    
    String messageType;
    
    /**
     * message.
     * 
     * @param message: Stirng: 3 methods: Exception, Win, Lose
     */
    public Message(String message) {
        this.messageType = message;
    }
}

/**
 *
 * @author Daniel Gruber
 * @copyright 2014 Daniel Gruber
 * @license GNU Lesser General Public License, version 3; see "LICENSE.txt"
 */
package labyrinth.message;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.input.Key.Kind;
import labyrinth.terminalManager.KeyboardDelegate;
import labyrinth.terminalManager.view.View;
import labyrinth.terminalManager.view.ViewCharacter;

/**
 *
 * @author D
 */
public class Message extends View implements KeyboardDelegate {
    
    String messageType;
    String messageDetails;
    
    int topPos;
    
    public MessageDelegate delegate;
    
    /**
     * message.
     * 
     * @param messageType: String: 3 methods: Exception, Win, Lose
     * @param details: String: detail information
     */
    public Message(String messageType, String details) {
        super();
        
        this.messageType = messageType;
        this.messageDetails = details;
    }

    /**
     * message.
     * 
     * @param messageType: String: 3 methods: Exception, Win, Lose
     * @param details: String: detail information
     */
    public Message(String messageType) {
        super();
        
        this.messageType = messageType;
        this.messageDetails = "";
    }
    
    @Override
    public ViewCharacter[][] getCompleteView(int rows, int columns) {
       ViewCharacter[][] view = createViewMatrix(rows, columns);
        
        topPos = (rowsVisible - 13) / 2;
        
        if(this.messageType.equals("win")) {
            fillLineWithAlignCenter(view[topPos +  0], "____     ___                           ____              ___               ");
            fillLineWithAlignCenter(view[topPos +  1], "`MM(     )M'                           `Mb(      db      )d' 68b           ");
            fillLineWithAlignCenter(view[topPos +  2], " `MM.    d'                             YM.     ,PM.     ,P  Y89           ");
            fillLineWithAlignCenter(view[topPos +  3], "  `MM.  d'     _____   ___   ___        `Mb     d'Mb     d'  ___ ___  __   ");
            fillLineWithAlignCenter(view[topPos +  4], "   `MM d'     6MMMMMb  `MM    MM         YM.   ,P YM.   ,P   `MM `MM 6MMb  ");
            fillLineWithAlignCenter(view[topPos +  5], "    `MM'     6M'   `Mb  MM    MM         `Mb   d' `Mb   d'    MM  MMM9 `Mb ");
            fillLineWithAlignCenter(view[topPos +  6], "     MM      MM     MM  MM    MM          YM. ,P   YM. ,P     MM  MM'   MM ");
            fillLineWithAlignCenter(view[topPos +  7], "     MM      MM     MM  MM    MM          `Mb d'   `Mb d'     MM  MM    MM ");
            fillLineWithAlignCenter(view[topPos +  8], "     MM      MM     MM  MM    MM           YM,P     YM,P      MM  MM    MM ");
            fillLineWithAlignCenter(view[topPos +  9], "     MM      YM.   ,M9  YM.   MM           `MM'     `MM'      MM  MM    MM ");
            fillLineWithAlignCenter(view[topPos + 10], "    _MM_      YMMMMM9    YMMM9MM_           YP       YP      _MM__MM_  _MM_");
            
            fillLineWithAlignCenter(view[topPos + 13], messageDetails);
        } else if(this.messageType.equals("lose")) {
            fillLineWithAlignCenter(view[topPos +  0], "____     ___                           ____                               ");
            fillLineWithAlignCenter(view[topPos +  1], "`MM(     )M'                           `MM'                               ");
            fillLineWithAlignCenter(view[topPos +  2], " `MM.    d'                             MM                           /    ");
            fillLineWithAlignCenter(view[topPos +  3], "  `MM.  d'     _____   ___   ___        MM        _____     ____    /M    ");
            fillLineWithAlignCenter(view[topPos +  4], "   `MM d'     6MMMMMb  `MM    MM        MM       6MMMMMb   6MMMMb\\ /MMMMM ");
            fillLineWithAlignCenter(view[topPos +  5], "    `MM'     6M'   `Mb  MM    MM        MM      6M'   `Mb MM'    `  MM    ");
            fillLineWithAlignCenter(view[topPos +  6], "     MM      MM     MM  MM    MM        MM      MM     MM YM.       MM    ");
            fillLineWithAlignCenter(view[topPos +  7], "     MM      MM     MM  MM    MM        MM      MM     MM  YMMMMb   MM    ");
            fillLineWithAlignCenter(view[topPos +  8], "     MM      MM     MM  MM    MM        MM      MM     MM      `Mb  MM    ");
            fillLineWithAlignCenter(view[topPos +  9], "     MM      YM.   ,M9  YM.   MM        MM    / YM.   ,M9 L    ,MM  YM.  ,");
            fillLineWithAlignCenter(view[topPos + 10], "    _MM_      YMMMMM9    YMMM9MM_      _MMMMMMM  YMMMMM9  MYMMMM9    YMMM9");
            
            fillLineWithAlignCenter(view[topPos + 13], messageDetails);
        } else if(this.messageType.equals("exception")) {
            
            fillLineWithAlignCenter(view[topPos +  0], "__________                                  ");
            fillLineWithAlignCenter(view[topPos +  1], "`MMMMMMMMM                                  ");
            fillLineWithAlignCenter(view[topPos +  2], " MM      \\                                  ");
            fillLineWithAlignCenter(view[topPos +  3], " MM        ___  __ ___  __   _____   ___  __");
            fillLineWithAlignCenter(view[topPos +  4], " MM    ,   `MM 6MM `MM 6MM  6MMMMMb  `MM 6MM");
            fillLineWithAlignCenter(view[topPos +  5], " MMMMMMM    MM69 \"  MM69 \" 6M'   `Mb  MM69 \"");
            fillLineWithAlignCenter(view[topPos +  6], " MM    `    MM'     MM'    MM     MM  MM'   ");
            fillLineWithAlignCenter(view[topPos +  7], " MM         MM      MM     MM     MM  MM    ");
            fillLineWithAlignCenter(view[topPos +  8], " MM         MM      MM     MM     MM  MM    ");
            fillLineWithAlignCenter(view[topPos +  9], " MM      /  MM      MM     YM.   ,M9  MM    ");
            fillLineWithAlignCenter(view[topPos + 10], "_MMMMMMMMM _MM_    _MM_     YMMMMM9  _MM_   ");
            
            fillLineWithAlignCenter(view[topPos + 13], messageDetails);
        }
        
        fillLineWithAlignCenter(view[topPos + 15], "Press enter or esacpe to continue.");
        
        return view;
    }
    
    @Override
    protected void windowSizeUpdated(int rows, int columns) {
        super.windowSizeUpdated(rows, columns);
        topPos = (rowsVisible - 10) / 2;
    }

    @Override
    public void keyPressed(Key key) {
        if(key.getKind() == Kind.Enter || key.getKind() == Kind.Escape) {
            
            if(this.delegate != null) {
                this.delegate.wantsToMenu();
            }
        }
    }
    
}

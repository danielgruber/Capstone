/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth.terminalManager;

import com.googlecode.lanterna.terminal.Terminal.Color;

/**
 *
 * @author D
 */
public class CharacterUpdate {
    char character;
    int positionX;
    int positionY;
    Color foregroundColor;
    Color backgroundColor;
    
    public CharacterUpdate(char character, int positionX, int positionY) {
        this.character = character;
        this.positionX = positionX;
        this.positionY = positionY;
        this.foregroundColor = Color.WHITE;
        this.backgroundColor = Color.BLACK;
    }
    
    public CharacterUpdate(char character, int positionX, int positionY, Color foreground, Color background) {
        this.character = character;
        this.positionX = positionX;
        this.positionY = positionY;
        

        this.foregroundColor = foreground;
        
        this.backgroundColor = background;
    }
    
    @Override
    public String toString() {
        String s = "Character: " + this.character +  
                "; X: " + this.positionX + 
                "; Y: " + this.positionY;
        
        if(foregroundColor != null) {
            s += "; Foreground: " + foregroundColor.toString();
        }
        
        if(backgroundColor != null) {
            s += "; Background: " + backgroundColor.toString();
        }
        
        return s;
    }
}

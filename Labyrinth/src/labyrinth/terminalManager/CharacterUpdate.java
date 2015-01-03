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
    
    CharacterUpdate(char character, int positionX, int positionY) {
        this.character = character;
        this.positionX = positionX;
        this.positionY = positionY;
        this.foregroundColor = Color.WHITE;
        this.backgroundColor = Color.BLACK;
    }
    
    CharacterUpdate(char character, int positionX, int positionY, Color foreground, Color background) {
        this.character = character;
        this.positionX = positionX;
        this.positionY = positionY;
        this.foregroundColor = foreground;
        this.backgroundColor = background;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth.terminalManager;

import com.googlecode.lanterna.input.Key;

/**
 *
 * @author D
 */

public interface KeyboardDelegate {
    public void keyPressed(Key key);
}
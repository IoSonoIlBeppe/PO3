/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame;

/**
 *
 * @author atorsell
 */
public interface Effect extends GameEntity {
    // pays for effect and places it in the stack
    void play();
    
    // resolves the effect
    void resolve();
}

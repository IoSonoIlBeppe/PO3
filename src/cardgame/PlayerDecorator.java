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
public interface PlayerDecorator extends Player {
    PlayerDecorator decorate(Player p);  
    Player removeDecorator(PlayerDecorator d);
}

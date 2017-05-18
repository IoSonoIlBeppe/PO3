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
public interface Card extends GameEntity {
    // returns the effect to be placed on the stack
    Effect getEffect(DecoratedPlayer owner);
    String type(); //sorcery, instant, or creature
    String ruleText();
    boolean isInstant();
}

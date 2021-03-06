/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame;

import java.util.List;

/**
 *
 * @author atorsell
 */
public interface Creature extends Damageable {
    
    void tap();
    void untap();
    boolean isTapped();
    
    boolean canAttack();
    void attack();
    
    boolean canDefend();
    void defend();
    
    int getDamage();
    void resetDamage();
    void destroy();
    
    int getPower();
    int getToughness();
    
    // returns all the effects associated to this creature
    List<Effect> effects();
    
    // returns only the effects that can be played currently
    // depending on state, e.g., tapped/untapped
    List<Effect> avaliableEffects();
    
    void setTopDecorator(DecoratedCreature c);
    DecoratedCreature getTopDecorator();
    
}

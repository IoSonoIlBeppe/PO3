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
public interface CreatureDecorator extends Creature {
    CreatureDecorator decorate(Creature c);  
    Creature removeDecorator(CreatureDecorator d);
}

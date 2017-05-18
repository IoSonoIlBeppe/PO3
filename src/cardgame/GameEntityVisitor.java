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
public interface GameEntityVisitor {
    void visit(DecoratedPlayer p);
    void visit(Card c);
    void visit(Effect e);
    void visit(DecoratedCreature c);
    void visit(Enchantment e);
}

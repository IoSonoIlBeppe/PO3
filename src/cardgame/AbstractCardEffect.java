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
public abstract class AbstractCardEffect extends AbstractEffect {
    protected DecoratedPlayer owner;
    protected Card card;
    
    protected AbstractCardEffect(DecoratedPlayer p, Card c) { owner=p; card=c; }
    
    public void play() { 
        owner.getHand().remove(card);
        super.play();
    }
    
    public String name() { return card.name(); }
    public String toString() { return card.toString() + " (Effect)"; }
}

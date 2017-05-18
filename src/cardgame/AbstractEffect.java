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
public abstract class AbstractEffect extends AbstractGameEntity implements Effect {
    
    
    
    @Override
    public void play() {  CardGame.instance.getStack().add(this); }
    @Override
    public void resolve() { super.remove(); }
    
    @Override
    public void accept(GameEntityVisitor v) { v.visit(this); }
    
    public String toString() { return name() + " (Effect)"; }
 
}

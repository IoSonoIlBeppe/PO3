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
public abstract class AbstractEnchantment extends AbstractGameEntity implements Enchantment {
    protected DecoratedPlayer owner;        
    protected AbstractEnchantment(DecoratedPlayer owner) { this.owner=owner; }
    @Override
    public DecoratedPlayer getOwner() { return owner; }
    @Override
    public void setOwner(DecoratedPlayer p) { owner=p; }
        
    @Override
        public void insert() {
            CardGame.instance.getTriggers().trigger(Triggers.ENTER_ENCHANTMENT_FILTER,this);
        }
    
    @Override
        public void remove() {
            owner.getEnchantments().remove(this);
            super.remove();
            CardGame.instance.getTriggers().trigger(Triggers.EXIT_ENCHANTMENT_FILTER,this);
        }
        
    @Override
        public String toString() {
            return name() + " (Enchantment)";
        }
     

    @Override
        public void accept(GameEntityVisitor v) { v.visit(this); }
    
}

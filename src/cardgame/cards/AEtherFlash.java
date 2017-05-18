/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame.cards;

import cardgame.AbstractCard;
import cardgame.AbstractEnchantment;
import cardgame.AbstractEnchantmentCardEffect;
import cardgame.Card;
import cardgame.CardConstructor;
import cardgame.CardGame;
import cardgame.DecoratedCreature;
import cardgame.DecoratedPlayer;
import cardgame.Effect;
import cardgame.Enchantment;
import cardgame.Permanent;
import cardgame.StaticInitializer;
import cardgame.TriggerAction;
import cardgame.Triggers;
import java.util.ArrayList;

/**
 *
 * @author atorsell
 */
public class AEtherFlash extends AbstractCard {
    static private final String cardName = "AEther Flash";
    
    static private StaticInitializer initializer = 
            new StaticInitializer(cardName, new CardConstructor() { 
                public Card create() { return new AEtherFlash(); }
                } );

    public String name() { return cardName; }
    public String type() { return "Enchantment"; }
    public String ruleText() { 
        return "Whenever a creature comes into play, " + name() + " deals 2 damage to it"; 
    }
    public String toString() { return name() + "[" + ruleText() +"]";}
    public boolean isInstant() { return false; }
    
    private static class AEtherFlashEffect extends AbstractEnchantmentCardEffect {
        AEtherFlashEffect(DecoratedPlayer p, Card c) { super(p,c); }
        @Override
        public Enchantment createEnchantment() {
            return new AEtherFlashEnchantment(owner);
        }
    }
    public Effect getEffect(DecoratedPlayer p) { return new AEtherFlashEffect(p,this); }
    
    private static class AEtherFlashEnchantment extends AbstractEnchantment {
        AEtherFlashTrigger action = new AEtherFlashTrigger();
        AEtherFlashEnchantment(DecoratedPlayer p) { super(p); }
        public String name() { return cardName; }
        public void insert() {
            super.insert();
            CardGame.instance.getTriggers().register(Triggers.ENTER_CREATURE_FILTER, action);
        }
        public void remove() {
            CardGame.instance.getTriggers().deregister(action);
            super.remove();
        }
        
        private static class AEtherFlashTrigger implements TriggerAction {
            @Override
            public void execute(Object arg) {
                if (arg==null || !(arg instanceof DecoratedCreature)) return;
                
                DecoratedCreature c=(DecoratedCreature)arg;
                c.inflictDamage(2);
            }
            
        }
    }
}

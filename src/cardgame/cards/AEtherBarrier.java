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
public class AEtherBarrier extends AbstractCard {
    static private final String cardName = "AEther Barrier";
    
    static private StaticInitializer initializer = 
            new StaticInitializer(cardName, new CardConstructor() { 
                public Card create() { return new AEtherBarrier(); }
                } );

    public String name() { return cardName; }
    public String type() { return "Enchantment"; }
    public String ruleText() { 
        return "Whenever a player plays a creature spell, that player sacrifices a permanent"; 
    }
    public String toString() { return name() + "[" + ruleText() +"]";}
    public boolean isInstant() { return false; }
    
    private static class AEtherBarrierEffect extends AbstractEnchantmentCardEffect {
        AEtherBarrierEffect(DecoratedPlayer p, Card c) { super(p,c); }
        @Override
        public Enchantment createEnchantment() {
            return new AEtherBarrierEnchantment(owner);
        }
    }
    public Effect getEffect(DecoratedPlayer p) { return new AEtherBarrierEffect(p,this); }
    
    private static class AEtherBarrierEnchantment extends AbstractEnchantment {
        AEtherBarrierTrigger action = new AEtherBarrierTrigger();
        AEtherBarrierEnchantment(DecoratedPlayer p) { super(p); }
        public String name() { return cardName; }
        public void insert() {
            super.insert();
            CardGame.instance.getTriggers().register(Triggers.ENTER_CREATURE_FILTER, action);
        }
        public void remove() {
            CardGame.instance.getTriggers().deregister(action);
            super.remove();
        }
        
        private class AEtherBarrierTrigger implements TriggerAction {
            @Override
            public void execute(Object arg) {
                if (arg==null || !(arg instanceof DecoratedCreature)) return;
                
                DecoratedCreature c=(DecoratedCreature)arg;
                chooseRemovePermanent(c.getOwner());
            }
            
            private void chooseRemovePermanent(DecoratedPlayer p) {
                System.out.println( p.name() +"choose permanent to sacrifice");
                ArrayList<Permanent> targets = new ArrayList<>();
                int i=1;
                for (DecoratedCreature c:p.getCreatures()) {
                    if (c.isTarget()) {
                        System.out.println(i+ ") " + c.name());
                        targets.add(c);
                        ++i;
                    }
                }
                for (Enchantment e:p.getEnchantments()) {
                    if (e.isTarget()) {
                        System.out.println(i+ ") " + e.name());
                        targets.add(e);
                        ++i;
                    }
                }
                boolean chosen=false;
                int idx=0;
                while(!chosen) {
                    idx = CardGame.instance.getScanner().nextInt()-1;
                    if (idx<0 && idx>=targets.size()) {
                         System.out.println("Selection not valid. Please choose again");
                    }
                    else chosen=true;
                }
                targets.get(idx).remove();
            }
        }
    }
}

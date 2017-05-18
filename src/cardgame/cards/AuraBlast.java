/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame.cards;

import cardgame.AbstractCard;
import cardgame.AbstractCardEffect;
import cardgame.Card;
import cardgame.CardConstructor;
import cardgame.CardGame;
import cardgame.DecoratedCreature;
import cardgame.DecoratedPlayer;
import cardgame.Effect;
import cardgame.Enchantment;
import cardgame.StaticInitializer;
import cardgame.TargetingEffect;
import java.util.ArrayList;

/**
 *
 * @author andrea
 */
public class AuraBlast extends AbstractCard {
    static private final String cardName = "Aura Blast";
    static private StaticInitializer initializer = 
            new StaticInitializer(cardName, new CardConstructor() { 
                public Card create() { return new AuraBlast(); }
                } );
    
    public String name() { return cardName; }
    public String type() { return "Instant"; }
    public String ruleText() { return "Destroy target enchantment"; }
    public String toString() { return name() + " [" + ruleText() +"]";}
    public boolean isInstant() { return true; }
    
    private static class AuraBlastEffect extends AbstractCardEffect implements TargetingEffect {
        Enchantment target;
        
        public AuraBlastEffect(DecoratedPlayer p, Card c) { super(p,c); }
        public void play() {
            pickTarget(owner);
            super.play();
        }
        
        public String toString() {
            if (target==null) return super.toString() + " (no target)";
            else return name() + " [Destroy " + target.name() + "]";
        }
        
        public void pickTarget(DecoratedPlayer p) {
            System.out.println( p.name() + ": choose target for " + name() );
            
            ArrayList<Enchantment> targets = new ArrayList<>();
            int i=1;
            
            DecoratedPlayer curPlayer = CardGame.instance.getPlayer(0);
            for (Enchantment e:curPlayer.getEnchantments()) {
                if (e.isTarget()) {
                    System.out.println(i+ ") " + curPlayer.name() + ": " + e.name());
                    targets.add(e);
                    ++i;
                }
            }
            
            curPlayer = CardGame.instance.getPlayer(1);
            for (Enchantment e:curPlayer.getEnchantments()) {
                if (e.isTarget()) {
                    System.out.println(i+ ") " + curPlayer.name() + ": " + e.name());
                    targets.add(e);
                    ++i;
                }
            }
            
            int idx= CardGame.instance.getScanner().nextInt()-1;
            if (idx<0 || idx>=targets.size()) target=null;
            else target=targets.get(idx);
        }
        
        public void resolve() {
            if (target==null) {
                System.out.println(cardName + " has no target");
            } else if (target.isRemoved() ) {
                System.out.println(cardName + " target not in play anymore");
            } else {
                target.remove();
            }
        }
    }

    public Effect getEffect(DecoratedPlayer owner) { 
        return new AuraBlastEffect(owner, this); 
    }
}

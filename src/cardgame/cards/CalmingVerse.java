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
import cardgame.Enchantment;
import cardgame.DecoratedPlayer;
import cardgame.Effect;
import cardgame.StaticInitializer;
import java.util.ArrayList;

/**
 *
 * @author andrea
 */
public class CalmingVerse extends AbstractCard {
    static private final String cardName = "Calming Verse";
    
    static private StaticInitializer initializer = 
            new StaticInitializer(cardName, new CardConstructor() { 
                public Card create() { return new CalmingVerse(); }
                } );
    
    private static class DayOfJudgmentEffect extends AbstractCardEffect {
        public DayOfJudgmentEffect(DecoratedPlayer p, Card c) { super(p,c); }
        public void resolve() {
            
            ArrayList<Enchantment> enchantments = new ArrayList<>();
            enchantments.addAll(CardGame.instance.getPlayer(0).getEnchantments());
            enchantments.addAll(CardGame.instance.getPlayer(1).getEnchantments()); 
            for (Enchantment e:enchantments) {
                if (!e.isRemoved()) e.remove();
            }
        }
    }

    public Effect getEffect(DecoratedPlayer owner) { 
        return new DayOfJudgmentEffect(owner, this); 
    }
    
    public String name() { return cardName; }
    public String type() { return "Sorcery"; }
    public String ruleText() { return "Destroy all enchantments"; }
    public String toString() { return name() + "[" + ruleText() +"]";}
    public boolean isInstant() { return false; }
    
}

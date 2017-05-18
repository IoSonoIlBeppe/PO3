/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame.cards;

import cardgame.AbstractCard;
import cardgame.AbstractCreature;
import cardgame.AbstractCreatureCardEffect;
import cardgame.Card;
import cardgame.CardConstructor;
import cardgame.CardGame;
import cardgame.Creature;
import cardgame.DecoratedPlayer;
import cardgame.Effect;
import cardgame.Enchantment;
import cardgame.StaticInitializer;
import cardgame.TriggerAction;
import cardgame.Triggers;

/**
 *
 * @author andrea
 */
public class ArgothianEnchantress extends AbstractCard {

    static private final String cardName = "Argothian Enchantress";

    static private StaticInitializer initializer
            = new StaticInitializer(cardName, new CardConstructor() {
                public Card create() {
                    return new ArgothianEnchantress();
                }
            });

    public String name() {
        return cardName;
    }

    public String type() {
        return "Creature";
    }

    public String ruleText() {
        return "Put in play a creature " + cardName + "(0/1) with Shroud";
    }

    public String toString() {
        return name() + "[" + ruleText() + "]";
    }

    public boolean isInstant() {
        return false;
    }

    private static class ArgothianEnchantressEffect extends AbstractCreatureCardEffect {

        public ArgothianEnchantressEffect(DecoratedPlayer p, Card c) {
            super(p, c);
        }

        protected Creature createCreature() {
            return new ArgothianEnchantressCreature(owner);
        }
    }

    public Effect getEffect(DecoratedPlayer p) {
        return new ArgothianEnchantressEffect(p, this);
    }

    private static class ArgothianEnchantressCreature extends AbstractCreature {

        private final ArgothianEnchantressAction action;

        ArgothianEnchantressCreature(DecoratedPlayer owner) {
            super(owner);
            action = new ArgothianEnchantressAction(owner);
        }

        public String name() {
            return cardName;
        }

        public int getPower() {
            return 0;
        }

        public int getToughness() {
            return 1;
        }

        public boolean isTarget() {
            return false;
        }

        public void insert() {
            super.insert();
            CardGame.instance.getTriggers().register(Triggers.ENTER_ENCHANTMENT_FILTER, action);
        }

        public void remove() {
            CardGame.instance.getTriggers().deregister(action);
            super.remove();
        }

    }

    private static class ArgothianEnchantressAction implements TriggerAction {

        private final DecoratedPlayer owner;

        ArgothianEnchantressAction(DecoratedPlayer p) {
            owner = p;
        }

        public void execute(Object arg) {
            if (arg == null || !(arg instanceof Enchantment)) {
                return;
            }
            Enchantment e = (Enchantment) arg;
            if (e.getOwner() == owner) {
                owner.draw();
            }
        }
    }

}

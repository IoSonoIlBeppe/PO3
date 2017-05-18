/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame.cards;

import cardgame.AbstractCard;
import cardgame.AbstractCardEffect;
import cardgame.AbstractCreatureDecorator;
import cardgame.Card;
import cardgame.CardConstructor;
import cardgame.CardGame;
import cardgame.DecoratedCreature;
import cardgame.DecoratedPlayer;
import cardgame.Effect;
import cardgame.StaticInitializer;
import cardgame.TargetingEffect;
import cardgame.TriggerAction;
import cardgame.Triggers;
import java.util.ArrayList;

/**
 *
 * @author Beppe
 */
public class ArtfulManeuver extends AbstractCard {

    static private final String cardName = "Artful Maneuver";

    static private StaticInitializer initializer
            = new StaticInitializer(cardName, new CardConstructor() {
                public Card create() {
                    return new ArtfulManeuver();
                }
            });

    private static class ArtfulManeuverEffect extends AbstractCardEffect implements TargetingEffect {

        private DecoratedCreature target;

        public ArtfulManeuverEffect(DecoratedPlayer p, Card c) {
            super(p, c);
        }

        public void play() {
            
            super.play();
        }

        public void resolve() {

            //la carta deve essere esiliata 
            
            
            
            TriggerAction rebound = new TriggerAction() {
                @Override
                public void execute(Object args) {
                    pickTarget(owner);
                    if (target == null) {
                        return;
                    }

                    if (target.isRemoved()) {
                        System.out.println("Attaching " + cardName + " to removed creature");
                        return;
                    }

                    final ArtfulManeuverDecorator decorator = new ArtfulManeuverDecorator();
                    TriggerAction action = new TriggerAction() {
                        public void execute(Object arg) {
                            if (!target.isRemoved()) {
                                System.out.println("Triggered removal of " + cardName + " from " + target);
                                target.removeDecorator(decorator);
                            } else {
                                System.out.println("Triggered dangling removal of " + cardName + " from removed target. Odd should have been invalidated!");
                            }
                        }
                    };
                    System.out.println("Attaching " + cardName + " to " + target.name() + " and registering end of turn trigger");
                    CardGame.instance.getTriggers().register(Triggers.END_FILTER, action);

                    decorator.setRemoveAction(action);
                    target.addDecorator(decorator);
                }
            };
            System.out.println(cardName + " exiled. " + target.name() + " will be buffed during the next turn");
            CardGame.instance.getTriggers().register(Triggers.UNTAP_FILTER, rebound, 1);
//            if (target == null) {
//                return;
//            }
//
//            if (target.isRemoved()) {
//                System.out.println("Attaching " + cardName + " to removed creature");
//                return;
//            }
//
//            final ArtfulManeuverDecorator decorator = new ArtfulManeuverDecorator();
//            TriggerAction action = new TriggerAction() {
//                public void execute(Object arg) {
//                    if (!target.isRemoved()) {
//                        System.out.println("Triggered removal of " + cardName + " from " + target);
//                        target.removeDecorator(decorator);
//                    } else {
//                        System.out.println("Triggered dangling removal of " + cardName + " from removed target. Odd should have been invalidated!");
//                    }
//                }
//            };
//            System.out.println("Ataching " + cardName + " to " + target.name() + " and registering end of turn trigger");
//            CardGame.instance.getTriggers().register(Triggers.END_FILTER, action);
//
//            decorator.setRemoveAction(action);
//            target.addDecorator(decorator);
        }

        public void pickTarget(DecoratedPlayer p) {
            System.out.println(p.name() + ": choose target for " + cardName);

            ArrayList<DecoratedCreature> creatures = new ArrayList<>();
            int i = 1;

            DecoratedPlayer player1 = CardGame.instance.getPlayer(0);
            DecoratedPlayer player2 = CardGame.instance.getPlayer(1);

            for (DecoratedCreature c : player1.getCreatures()) {
                if (c.isTarget()) {
                    System.out.println(i + ") " + player1.name() + ": " + c);
                    ++i;
                    creatures.add(c);
                }
            }
            for (DecoratedCreature c : player2.getCreatures()) {
                if (c.isTarget()) {
                    System.out.println(i + ") " + player2.name() + ": " + c);
                    ++i;
                    creatures.add(c);
                }
            }

            int idx = CardGame.instance.getScanner().nextInt() - 1;
            if (idx < 0 || idx >= creatures.size()) {
                target = null;
            } else {
                target = creatures.get(idx);
            }
        }

        public String toString() {
            if (target == null) {
                return super.toString() + " (no target)";
            } else if (target.isRemoved()) {
                return super.toString() + " (removed creature)";
            } else {
                return cardName + " [" + target.name() + " gets +2/+2 until end of turn]";
            }
        }

    }

    public Effect getEffect(DecoratedPlayer owner) {
        return new ArtfulManeuverEffect(owner, this);
    }

    private static class ArtfulManeuverDecorator extends AbstractCreatureDecorator {

        TriggerAction action;

        public void setRemoveAction(TriggerAction a) {
            action = a;
        }

        public void remove() {
            System.out.println("Removing " + cardName + " and deregistering end of turn trigger");
            if (action != null) {
                CardGame.instance.getTriggers().deregister(action);
            }
            super.remove();
        }

        public int getPower() {
            return decorated.getPower() + 2;
        }

        public int getToughness() {
            return decorated.getToughness() + 2;
        }
    }

    public String name() {
        return cardName;
    }

    public String type() {
        return "Instant";
    }

    public String ruleText() {
        return "Target creature gets +2/+2 until end of turn"
                + "\nRebound: if you cast this spell from your hand, exile it as it resolves. At the beginning "
                + "of the next upkeep, you may cast this card from exile without paying its mana cost";
    }

    public String toString() {
        return cardName + "[" + ruleText() + "]";
    }

    public boolean isInstant() {
        return true;
    }

    public void exile() {

    }

}

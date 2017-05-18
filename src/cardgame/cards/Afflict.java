package cardgame.cards;

import cardgame.*;
import java.util.ArrayList;



public class Afflict extends AbstractCard {
    
    static private final String cardName = "Afflict";
    
    static private StaticInitializer initializer = 
            new StaticInitializer(cardName, new CardConstructor() { 
                public Card create() { return new Afflict(); }
                } );
    
    private static class AfflictEffect extends AbstractCardEffect implements TargetingEffect {
        private DecoratedCreature target;
        public AfflictEffect(DecoratedPlayer p, Card c) { super(p,c); }
        public void play() {
            pickTarget(owner);
            super.play();
        }
        public void resolve() {
            if (target==null) return;
            
            if (target.isRemoved()) {
                System.out.println("Attaching " + cardName + " to removed creature");
                return;
            }
            
            final AfflictDecorator decorator=new AfflictDecorator();
            TriggerAction action = new TriggerAction() {
                    public void execute(Object arg) {
                        if (!target.isRemoved()){
                            System.out.println("Triggered removal of " + cardName + " from " + target);
                            target.removeDecorator(decorator);
                        } else {
                            System.out.println("Triggered dangling removal of " + cardName + " from removed target. Odd should have been invalidated!" );
                        }
                    }    
                };
            System.out.println("Ataching " + cardName + " to " + target.name() + " and registering end of turn trigger");
            CardGame.instance.getTriggers().register(Triggers.END_FILTER, action);
                        
            decorator.setRemoveAction(action);
            target.addDecorator(decorator);
        }
        
        public void pickTarget(DecoratedPlayer p) {
            System.out.println( p.name() + ": choose target for " + name() );
            
            ArrayList<DecoratedCreature> creatures=new ArrayList<>();
            int i=1;
            
            DecoratedPlayer player1 = CardGame.instance.getPlayer(0);
            DecoratedPlayer player2 = CardGame.instance.getPlayer(1);
            
            for (DecoratedCreature c: player1.getCreatures()) {
                if (c.isTarget()) {
                    System.out.println( i + ") " + player1.name() + ": " + c);
                    ++i;
                    creatures.add(c);
                }
            }
            for (DecoratedCreature c: player2.getCreatures()) {
                if (c.isTarget()) {
                    System.out.println( i + ") " + player2.name() + ": " + c);
                    ++i;
                    creatures.add(c);
                }
            }
            
            int idx= CardGame.instance.getScanner().nextInt()-1;
            if (idx<0 || idx>=creatures.size()) target=null;
            else target=creatures.get(idx);
        }
        
        public String toString() {
            if (target==null ) return super.toString() + " (no target)";
            else if (target.isRemoved()) return super.toString() + " (removed creature)";
            else return name() + " [" + target.name() + " gets -1/-1 until end of turn]";
        }
                
    }

    public Effect getEffect(DecoratedPlayer owner) { 
        return new AfflictEffect(owner, this); 
    }
    
    private static class AfflictDecorator extends AbstractCreatureDecorator {
        TriggerAction action;
        public void setRemoveAction(TriggerAction a) { action=a; }
        public void remove() {
            System.out.println("Removing " + cardName + " and deregistering end of turn trigger");
            if (action!=null) 
                CardGame.instance.getTriggers().deregister(action); 
            super.remove();
        }
        
        public int getPower() { return decorated.getPower()-1; }
        public int getToughness() { return decorated.getToughness()-1; }
    }
    
    public String name() { return cardName; }
    public String type() { return "Instant"; }
    public String ruleText() { return "Target creature gets -1/-1 until end of turn"; }
    public String toString() { return name() + "[" + ruleText() +"]";}
    public boolean isInstant() { return true; }
    
}

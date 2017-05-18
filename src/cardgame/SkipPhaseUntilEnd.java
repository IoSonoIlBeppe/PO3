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
public class SkipPhaseUntilEnd implements Phase {
    private final Phases phaseId;
    private int skipNum;
    DecoratedPlayer owner;
    EndPhaseAction action = new EndPhaseAction();
    
    public SkipPhaseUntilEnd(DecoratedPlayer p, Phases id) {
        owner=p;
        phaseId=id;
        skipNum=1;
        register();
    }
    public SkipPhaseUntilEnd(DecoratedPlayer p, Phases id, int skip) {
        owner=p;
        phaseId=id;
        skipNum=skip;
        register();
    }
    
    
    @Override
    public void execute() {
        DecoratedPlayer currentPlayer = CardGame.instance.getCurrentPlayer();
        System.out.println(currentPlayer.name() + ": skip " + phaseId.name() +" phase");
    }
    
    public void countDown() {
        --skipNum;
        if (skipNum==0) deregister();
    }
    
    private void register() {
        owner.setPhase(phaseId, this);
        CardGame.instance.getTriggers().register(Triggers.END_FILTER, action);
    }
    
    private void deregister() {
        CardGame.instance.getTriggers().deregister(action);
        owner.removePhase(phaseId, this);
    }
    
    private class EndPhaseAction implements TriggerAction {
        @Override
        public void execute(Object arg) {
            countDown();
        }
    }
}

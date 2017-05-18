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
public class SkipPhaseCount implements Phase {
    private final Phases phaseId;
    private int skipNum;
    
    public SkipPhaseCount(Phases id) {
        phaseId=id;
        skipNum=1;
    }
    public SkipPhaseCount(Phases id, int skip) {
        phaseId=id;
        skipNum=skip;
    }
    
    
    @Override
    public void execute() {
        DecoratedPlayer currentPlayer = CardGame.instance.getCurrentPlayer();
        System.out.println(currentPlayer.name() + ": skip " + phaseId.name() +" phase");
        --skipNum;
        if (skipNum==0) currentPlayer.removePhase(phaseId,this);
    }
}

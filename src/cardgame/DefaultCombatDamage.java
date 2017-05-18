/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame;

import java.util.ArrayList;

/**
 *
 * @author atorsell
 */
public class DefaultCombatDamage implements Phase {
    @Override
    public void execute() {
        System.out.println("-> defender declaration subphase");
        ArrayList<CombatEntry> combatField = CardGame.instance.getCombatField();
        final DecoratedPlayer adversary = CardGame.instance.getCurrentAdversary();
        
        //distribute damage
        System.out.println("Damage distribution subphase");
        for (CombatEntry e:combatField) {
            if (e.attacker.isRemoved()) continue;
            
            boolean is_blocked=false;
            int defender_damage=0;
            int attacker_residual_power=e.attacker.getPower();
            
            for (DecoratedCreature defender:e.defenders) {
                if (defender.isRemoved()) continue;
                is_blocked=true;
                
                //compute damage attacker inflict to this defender
                int attacker_damage = Math.min(attacker_residual_power, 
                        defender.getToughness()-defender.getDamage() );
                //accumulate defender damage
                defender_damage += Math.max(0,defender.getPower());
                
                System.out.println(e.attacker + " deals " + attacker_damage + " damage to " + defender);
                System.out.println(defender + " deals " + attacker_damage + " damage to " + e.attacker);
                
                //inflict damage to defender
                if (attacker_damage>0) {
                    defender.inflictDamage(attacker_damage);
                    attacker_residual_power-=attacker_damage;
                }
            }
            
            
            if (!is_blocked) {
                //inflict damage to adversary player
                System.out.println(e.attacker + " deals " + e.attacker.getPower() + " damage to " + adversary.name());
                adversary.inflictDamage(Math.max(0,e.attacker.getPower()));
            } else {
                //inflict cumulative damage to attacker
                defender_damage = Math.min(defender_damage, 
                        e.attacker.getToughness()-e.attacker.getDamage() );
                e.attacker.inflictDamage(defender_damage);
            }
            
        }
    }
}

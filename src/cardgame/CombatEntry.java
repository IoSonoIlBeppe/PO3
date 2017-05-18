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
public class CombatEntry {
    public DecoratedCreature attacker;
    public ArrayList<DecoratedCreature> defenders=new ArrayList<>();
}

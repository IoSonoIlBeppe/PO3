/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author atorsell
 */
public interface Player {
   
    boolean isTarget();
    
    void setDeck(Iterator<Card> deck);
    Library getDeck();
    
    List<Card> getHand();
    int getMaxHandSize();
    void setMaxHandSize(int size);
    void draw();
    
    
    int getLife();
    void inflictDamage(int dmg);
    void inflictDamage(Creature c, int dmg);
    void heal(int pts);
    void lose(String s);
    
    
    List<DecoratedCreature> getCreatures();
    void destroy(Creature c);
    List<Enchantment> getEnchantments();
    void destroy(Enchantment e);
    List<Card> getExiledCreatures();
    Card retrieveExiledCard(Card c);
}

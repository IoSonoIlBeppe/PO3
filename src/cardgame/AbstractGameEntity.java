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
public abstract class AbstractGameEntity implements GameEntity {
    protected boolean isRemoved=false;
    public boolean isRemoved() { return isRemoved; }
    public boolean isTarget() { return true; }
    public void remove() { isRemoved=true; }
    public void insert() { isRemoved=false; }
}

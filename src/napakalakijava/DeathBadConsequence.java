/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package napakalakijava;

/**
 *
 * @author jumacasni
 */
public class DeathBadConsequence extends NumericBadConsequence{
    protected boolean death;
    
    public DeathBadConsequence(String text){
        super(text, Player.MAXLEVEL, BadConsequence.MAXTREASURES, BadConsequence.MAXTREASURES);    
        death = true;
    }
    
    public boolean getDeath(){
        return death;
    }
}

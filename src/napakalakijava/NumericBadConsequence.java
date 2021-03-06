/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package napakalakijava;

import java.util.ArrayList;

/**
 *
 * @author jumacasni
 */
public class NumericBadConsequence extends BadConsequence {
    protected int nVisibleTreasures;
    protected int nHiddenTreasures;
    
    public NumericBadConsequence(String text, int levels, int nVisible, int nHidden){
        super(text,levels);
        nVisibleTreasures = nVisible;
        nHiddenTreasures = nHidden;
    }
    
    @Override
    public boolean isEmpty(){
        return (nVisibleTreasures == 0 && nHiddenTreasures == 0);
    }
    
    public int getNVisibleTreasures(){
        return nVisibleTreasures;
    }
    
    public int getNHiddenTreasures(){
        return nHiddenTreasures;
    }
    
    @Override
    public void substractVisibleTreasure(Treasure t){
        if(nVisibleTreasures != 0)
            nVisibleTreasures--; 
    }
    
    @Override
    public void substractHiddenTreasure(Treasure t){
        if(nHiddenTreasures != 0)
            nHiddenTreasures--;
    }
    
    @Override    
    public BadConsequence adjustToFitTreasureLists(ArrayList<Treasure> v, ArrayList<Treasure> h){
        BadConsequence nuevaBadConsequence;
        int nuevoNVisible, nuevoNHidden;

        if(nVisibleTreasures <= v.size())
            nuevoNVisible = nVisibleTreasures;
        else
            nuevoNVisible = v.size();

        if(nHiddenTreasures <= h.size())
            nuevoNHidden = nHiddenTreasures;
        else
            nuevoNHidden = h.size();

        nuevaBadConsequence = new NumericBadConsequence(super.getText(),super.getLevels(),nuevoNVisible, nuevoNHidden);

        
        return nuevaBadConsequence;
    }
    
    @Override
    public String toString(){
    
        return super.getText() + " \nNiveles que pierdes = " + Integer.toString(super.getLevels()) +
               "\nNumero visible treasures = " + Integer.toString(nVisibleTreasures) +
               "\nNumero hidden treasures = " + Integer.toString(nHiddenTreasures);
    }
}

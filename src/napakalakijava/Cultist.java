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
public class Cultist {
    private String name;
    private int gainedLevels;
    
    public Cultist(String n, int g){
        name = n;
        gainedLevels = g;
    }
    
    public int getGainedLevels(){
        return gainedLevels;
    }
    
    public String toString(){
        String s = "";
        s += "Name: " + name + " - Gained Levels: " + Integer.toString(gainedLevels) + "\n";
        
        return s;
    }
}

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
public class Monster {
    private String name;
    private int combatLevel;
    private Prize prize;
    private BadConsequence badConsequence;
    private int levelChangeAgainstCultistPlayer;
    
    public Monster(String n, int cl, BadConsequence bc, Prize p){
        name = n;
        combatLevel = cl;
        prize = p;
        badConsequence = bc;
        levelChangeAgainstCultistPlayer = 0;
    }
    
    public Monster(String n, int cl, BadConsequence bc, Prize p, int lC){
        name = n;
        combatLevel = cl;
        prize = p;
        badConsequence = bc;
        levelChangeAgainstCultistPlayer = lC;
    }
    
    public String getName(){
        return name;
    }
    
    public int getCombatLevel(){
        return combatLevel;
    }
    
    public Prize getPrize(){
        return prize;
    }
    
    public BadConsequence getBadConsequence(){
        return badConsequence;
    }
    
    public int getCombatLevelAgainstCultistPlayer(){
        return combatLevel + levelChangeAgainstCultistPlayer;
    }
    public String toString(){
        String s = "";
        
        s += "Nombre = ";
        s += name;
        s += "\n";
        s += "Combat Level = ";
        s += Integer.toString(combatLevel);
        s += "\n";
        s += "Prize = ";
        s += prize.toString();
        s += "\n";
        s += "BadConsequence = ";
        s += badConsequence.toString();
        
        return s;
    }
    
    public int getLevelsGained(){
        return prize.getLevel();
    }
    
    public int getTreasuresGained(){
        return prize.getTreasures();
    }
}


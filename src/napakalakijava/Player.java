/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package napakalakijava;

import java.util.ArrayList;
import GUI.Dice;

/**
 *
 * @author jumacasni
 */
public class Player {
   static final int MAXLEVEL = 10;
   private String name;
   private int level;
   private boolean dead = true;
   private boolean canISteal = true;
   protected Player enemy;
   private ArrayList<Treasure> visibleTreasures;
   private ArrayList<Treasure> hiddenTreasures;
   private BadConsequence pendingBadConsequence;
   
   public Player(String name){
       this.name = name;
       level = 1;
       enemy = null;
       pendingBadConsequence = null;
       visibleTreasures = new ArrayList();
       hiddenTreasures = new ArrayList();
   }
   
   public Player(Player p){
       name = p.name;
       level = p.level;
       enemy = p.enemy;
       visibleTreasures = p.visibleTreasures;
       hiddenTreasures = p.hiddenTreasures;
       pendingBadConsequence = p.pendingBadConsequence;
   }
   
   public String getName(){
       return name;
   }
   
   public BadConsequence getPendingBadConsequence(){
       return pendingBadConsequence;
   }
   
   private void bringToLife(){
       dead = false;
   }
   
   public int getCombatLevel(){
     int nivel = level;
     
     for(Treasure t : visibleTreasures) 
         nivel += t.getBonus();
     
     return nivel;
   }
   
   private void incrementLevels(int l){
       if(level + l >= 10)
           level = 10;
       
       else
           level += l;
   }
   
   private void decrementLevels(int l){
       if(level - l <= 1)
           level = 1;
       
       else
           level -= l;
   }
   
   private void setPendingBadConsequence(BadConsequence b){
       pendingBadConsequence = b;
   }
   
   private void applyPrize(Monster m){
       int nLevels = m.getLevelsGained();
       incrementLevels(nLevels);
       int nTreasures = m.getTreasuresGained();
       
       if(nTreasures > 0){
           CardDealer dealer = CardDealer.getInstance();
           Treasure t;
           
           for(int i = 0; i < nTreasures; i++){
               t = dealer.nextTreasure();
               hiddenTreasures.add(t);
           }
       }
   }
   
   private void applyBadConsequence(Monster m){
       BadConsequence badConsequence = m.getBadConsequence();
       int nLevels = badConsequence.getLevels();
       decrementLevels(nLevels);
       BadConsequence pendingBad = badConsequence.adjustToFitTreasureLists(visibleTreasures, hiddenTreasures);
       setPendingBadConsequence(pendingBad);
   }
   
   private boolean canMakeTreasureVisible(Treasure t){
       boolean sePuede = true;
        
        if(t.getType() == TreasureKind.ONEHAND){
            if(howManyVisibleTreasures(t.getType()) > 1 ||
               howManyVisibleTreasures(TreasureKind.BOTHHANDS) > 0)
                sePuede = false;
        }
        else if(t.getType() == TreasureKind.BOTHHANDS){
            if(howManyVisibleTreasures(t.getType()) > 0 ||
               howManyVisibleTreasures(TreasureKind.ONEHAND) > 0)
                sePuede = false;
        }
        else{
            if(howManyVisibleTreasures(t.getType()) > 0)
                sePuede = false;
        }        
        
        return sePuede;
   }
   
   private int howManyVisibleTreasures(TreasureKind tk){
       int n = 0;
       
       for(Treasure t : visibleTreasures){
           if(t.getType() == tk)
               n += 1;
       }
       
       return n;
   }
   
   private void dieIfNoTreasures(){
       if(visibleTreasures.isEmpty() && hiddenTreasures.isEmpty())
           dead = true;
   }
   
   public boolean isDead(){
       return dead;
   }
   
   public ArrayList<Treasure> getHiddenTreasures(){
       return hiddenTreasures;
   }
   
   public ArrayList<Treasure> getVisibleTreasures(){
       return visibleTreasures;
   }
   
   public Player getEnemy(){
        return enemy;
    }
   
   public CombatResult combat(Monster m){
       int myLevel = getCombatLevel();
       int monsterLevel = getOponentLevel(m);
       CombatResult resultado;
       
       if(!canISteal){
           Dice dice = Dice.getInstance();
           int number = dice.nextNumber();
           
           if(number < 3){
               int enemyLevel = enemy.getCombatLevel();
               monsterLevel += enemyLevel;
           }
       }
       
       if(myLevel > monsterLevel){
           applyPrize(m);
           if(level >= MAXLEVEL)
               resultado = CombatResult.WINGAME;
           
           else
               resultado = CombatResult.WIN;
       }
       
       else{
           applyBadConsequence(m);
           
           if(shouldConvert()){
               resultado = CombatResult.LOSEANDCONVERT;
           }
           
           else{
                resultado = CombatResult.LOSE;  
           }
       }
       
       return resultado;
   }
   
   public void makeTreasureVisible(Treasure t){
       boolean canI = canMakeTreasureVisible(t);
       
       if(canI){
           visibleTreasures.add(t);
           hiddenTreasures.remove(t);
       }
   }
   
   public void discardVisibleTreasure(Treasure t){
       visibleTreasures.remove(t);
       if(pendingBadConsequence != null && !pendingBadConsequence.isEmpty())
           pendingBadConsequence.substractVisibleTreasure(t);
       
       dieIfNoTreasures();
   }
   
   public void discardHiddenTreasure(Treasure t){
       hiddenTreasures.remove(t);
       if(pendingBadConsequence != null && !pendingBadConsequence.isEmpty())
           pendingBadConsequence.substractHiddenTreasure(t);
       
       dieIfNoTreasures();
   }
   
   public boolean validState(){
       
       if(pendingBadConsequence != null){
           return (pendingBadConsequence.isEmpty() && hiddenTreasures.size() <= 4);
       }
       
       else
           return hiddenTreasures.size() <= 4;
   }
   
   public void initTreasures(){
       CardDealer dealer = CardDealer.getInstance();
       Dice dice = Dice.getInstance();
       bringToLife();
       Treasure treasure = dealer.nextTreasure();
       hiddenTreasures.add(treasure);
       int number = dice.nextNumber("Es el turno de " + name, "Lanza el dado para inicializar tus tesoros");
       
       if(number > 1){
           treasure = dealer.nextTreasure();
           hiddenTreasures.add(treasure);
       }
       
       if(number == 6){
           treasure = dealer.nextTreasure();
           hiddenTreasures.add(treasure);
       }
               
   }
   
   public int getLevels(){
       return level;
   }
   
   public Treasure stealTreasure(){
       boolean canI = canISteal();
       Treasure treasure = null;
       
       if(canI){
           boolean canYou = enemy.canYouGiveMeATreasure();
           
           if(canYou){
               treasure = enemy.giveMeATreasure();
               hiddenTreasures.add(treasure);
               haveStolen();
           }
       }
       
       return treasure;
   }
   
   public void setEnemy(Player enemy){
       this.enemy = enemy;
   }
   
   protected Treasure giveMeATreasure(){
       int numeroAleatorio = (int) (Math.random()*(hiddenTreasures.size()));
       
       Treasure t = hiddenTreasures.get(numeroAleatorio);
       hiddenTreasures.remove(numeroAleatorio);
       
       return t;
   }
   
   public boolean canISteal(){
       return canISteal;
   }
   
   protected boolean canYouGiveMeATreasure(){
       return hiddenTreasures.size() > 0;
   }
   
   private void haveStolen(){
       canISteal = false;
   }
   
   public void discardAllTreasures(){
       ArrayList<Treasure> copiaVisible = new ArrayList(visibleTreasures);
       ArrayList<Treasure> copiaHidden = new ArrayList(hiddenTreasures);
        
       for(int i = 0; i < copiaVisible.size(); i++){
           Treasure treasure = copiaVisible.get(i);
           discardVisibleTreasure(treasure);
       }
       
       for(int i = 0; i < copiaHidden.size(); i++){
           Treasure treasure = copiaHidden.get(i);
           discardHiddenTreasure(treasure);
       }
   }
   
   protected int getOponentLevel(Monster m){
       return m.getCombatLevel();
   }
   
   protected boolean shouldConvert(){
       Dice dice = Dice.getInstance();
       int number = dice.nextNumber(name, "si pierdes y sacas un 6, te conviertes en sectario");
       
       if(number == 6)
           return true;
       
       return false;
   }
   
   public String toString(){
        String s = "";
        s += name + " ";
        s += "(Nivel " + Integer.toString(level) + ")\n";
        s += "Dead: " + String.valueOf(dead) + " - Puede robar: " + String.valueOf(canISteal) + "\n";
        s += "Enemigo: " + enemy.getName();
       
        if(pendingBadConsequence != null)
            s += "\nPending Bad Consequence: " + pendingBadConsequence.toString();
       
       
        return s;
   }
}

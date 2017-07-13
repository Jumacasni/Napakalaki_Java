/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package napakalakijava;
import java.util.*;
/**
 *
 * @author jumacasni
 */
public class Napakalaki {
    private static Napakalaki instance = null;
    private Player currentPlayer;
    private ArrayList<Player> players;
    private CardDealer dealer;
    private Monster currentMonster;
    
    private Napakalaki(){
        
    }
    
    private void initPlayers(ArrayList<String> names){
        players = new ArrayList();
        
        for(String n : names)
            players.add(new Player(n));
    }
    
    private Player nextPlayer(){
        Player p;
        int posicion;
        
        if(currentPlayer == null)     // Es la primera jugada
            posicion = (int) (Math.random()*(players.size()));

        
        else{
            int posicion_act = players.indexOf(currentPlayer);
            
            if(posicion_act == players.size()-1)
                posicion = 0;
            
            else
                posicion = posicion_act + 1;
        }
        
        p = players.get(posicion);
        currentPlayer = p;
        
        return p;
    }
    
    private boolean nextTurnAllowed(){
        if(currentPlayer == null)
            return true;
        
        else
            return currentPlayer.validState();
    }
    
    private void setEnemies(){
        int numeroAleatorio;
        Player enemy;
        
        for(Player p : players){
            numeroAleatorio = (int) (Math.random()*(players.size()));
            enemy = players.get(numeroAleatorio);
            while(enemy == p){
                numeroAleatorio = (int) (Math.random()*(players.size()));
                enemy = players.get(numeroAleatorio);
            }
            
            p.setEnemy(enemy);
        }
    }
    
    public static Napakalaki getInstance(){
        if (instance == null)
            instance = new Napakalaki();
        
        return instance;
    }
    
    public CombatResult developCombat(){
        CombatResult combatResult = currentPlayer.combat(currentMonster);
        dealer.giveMonsterBack(currentMonster);
        
        if(combatResult == CombatResult.LOSEANDCONVERT){
            Cultist c = dealer.nextCultist();
            CultistPlayer cp = new CultistPlayer(currentPlayer, c);
            
            for(Player p : players)
                if(p.getEnemy() == currentPlayer)
                    p.setEnemy(cp);
            
            // Reemplazar de la lista de Players:
            int pos = players.indexOf(currentPlayer);
            players.set(pos,cp);
            
            // Reemplazar CurrentPlayer::
            currentPlayer = cp;
        }
        
        return combatResult;
    }
    
    public void discardVisibleTreasures(ArrayList<Treasure> treasures){
        for(Treasure t : treasures){
            currentPlayer.discardVisibleTreasure(t);
            dealer.giveTreasureBack(t);
        }
    }
    
    public void discardHiddenTreasures(ArrayList<Treasure> treasures){
        for(Treasure t : treasures){
            currentPlayer.discardHiddenTreasure(t);
            dealer.giveTreasureBack(t);
        }
    }
    
    public void makeTreasuresVisible(ArrayList<Treasure> treasures){
        for(Treasure t : treasures){
            currentPlayer.makeTreasureVisible(t);
        }
    }
    
    public void initGame(ArrayList<String> players){
        initPlayers(players);
        setEnemies();
        dealer = CardDealer.getInstance();
        dealer.initCards();
        nextTurn();
    }
    
    public Player getCurrentPlayer(){
        return currentPlayer;
    }
    
    public Monster getCurrentMonster(){
        return currentMonster;
    }
    
    public boolean nextTurn(){
        boolean stateOK = nextTurnAllowed();
        
        if(stateOK){
            currentMonster = dealer.nextMonster();
            currentPlayer = nextPlayer();
            boolean dead = currentPlayer.isDead();
            
            if(dead)
                currentPlayer.initTreasures();
        }
        
        return stateOK;
    }
    
    public boolean endOfGame(CombatResult result){
        if(result == CombatResult.WINGAME)
            return true;
        
        return false;
    }
}

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
public class CultistPlayer extends Player {
    static int totalCultistPlayers = 0;
    private Cultist myCultistCard;
    
    public CultistPlayer(Player p, Cultist c){
        super(p);
        myCultistCard = c;
        totalCultistPlayers++;
    }
    
    public Cultist getCultist(){
        return myCultistCard;
    }
    
    @Override
    public int getCombatLevel(){
        int n = super.getCombatLevel();
        double nAux = n + 0.7*super.getCombatLevel();
        n = (int)nAux;
        n += myCultistCard.getGainedLevels() * totalCultistPlayers;
        
        return n;
    }
    
    @Override
    protected int getOponentLevel(Monster m){
        return m.getCombatLevelAgainstCultistPlayer();
    }
    
    @Override
    protected boolean shouldConvert(){
        return false;
    }
    
    @Override
    protected Treasure giveMeATreasure(){
        int numeroAleatorio;
        numeroAleatorio = (int) (Math.random()*(getVisibleTreasures().size()));
        Treasure t = getVisibleTreasures().get(numeroAleatorio);
        getVisibleTreasures().remove(t);
        
        return t;
    }
    
    @Override
    protected boolean canYouGiveMeATreasure(){
        return getVisibleTreasures().size() > 0;
    }
    
    public int getTotalCultistPlayers(){
        return totalCultistPlayers;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package napakalakijava;
import GUI.*;
import java.util.ArrayList;

/**
 *
 * @author jumacasni
 */
public class NapakalakiMain {
    public static void main(String [] args){
        
        Napakalaki game = Napakalaki.getInstance();
        NapakalakiView napakalakiView = new NapakalakiView();
        Dice.createInstance(napakalakiView);
        ArrayList<String> names = new ArrayList();
        PlayerNamesCapture namesCapture = new PlayerNamesCapture(napakalakiView, true);
        
        names = namesCapture.getNames();
        game.initGame(names);
        
        napakalakiView.setNapakalaki(game);
        napakalakiView.setVisible(true);
    }
}

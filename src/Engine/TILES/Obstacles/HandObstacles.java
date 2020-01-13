package Engine.TILES.Obstacles;

import java.util.ArrayList;
import java.util.Collection;

/*
Classe pour créer une main d'obstacle, peut importe le nombre d'obstacle dans la main
 */

public class HandObstacles {
    protected ArrayList<Obstacle> obstacles;

    //obstacles -> liste des obstacles dans la main

    //constructeur
    public HandObstacles(){
        obstacles = new ArrayList<Obstacle>();
    }

    // Retourne le nombre de cartes dans la main
    public int getAmount(){
        return obstacles.size();
    }

    // Ajoute un obstacle dans la main
    public void add(Obstacle obstacle){
        obstacles.add(obstacle);
    }

    // Vide la main
    public void clear(){
        obstacles.clear();
    }

    // Retire une carte de la main
    public void discardOne(Obstacle obstacle){
        obstacles.remove(obstacle);
    }

    // Retirer plusieurs cartes dans la main A TESTER
    public void discard(Collection<Obstacle> c){
        obstacles.removeAll(c);
    }

    // Afficher la main dans le terminal
    public String showHand(){
        String str = "";
        for (Obstacle o : obstacles){
            str += o.printMaterial() + "\n";
        }
        return str;
    }

    // Fonction pour ajouter sur plateau ?




}

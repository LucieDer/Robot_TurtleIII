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

    public int getStoneAmount() {
        int nbOfStoneWall = 0;
        for(Obstacle obstacle : this.obstacles){
            if (obstacle.getM_material() == "Pierre"){
                nbOfStoneWall++;
            }
        }
        return nbOfStoneWall;
    }

    public int getIceAmount() {
        int nbOfIceWall = 0;
        for(Obstacle obstacle : this.obstacles){
            if (obstacle.getM_material() == "Glace"){
                nbOfIceWall++;
            }
        }
        return nbOfIceWall;
    }



    public Obstacle getStoneWall() {
        int index;
        for(Obstacle obstacle : this.obstacles){
            if (obstacle.getM_material() == "Pierre"){
                index = obstacles.indexOf(obstacle);
                return this.obstacles.remove(index);
            }
        }
        return null;
    }

    public Obstacle getIceWall() {
        int index;
        for(Obstacle obstacle : this.obstacles){
            if (obstacle.getM_material() == "Glace"){
                index = obstacles.indexOf(obstacle);
                return this.obstacles.remove(index);
            }
        }
        return null;
    }

}

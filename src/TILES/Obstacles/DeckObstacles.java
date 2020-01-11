package TILES.Obstacles;

import TILES.Type;

import java.util.Random;

/*
Classe pour créer le deck d'obstacles
Cette classe hérite de la classe HandObstacles
 */

public class DeckObstacles extends HandObstacles {
    private final int amount = 36;
    private final int stone = 20;
    private final int ice = 12;
    private final int crate = 4;


    Random rand = new Random();

    // Fonction pour générer le deck
    public void populate(){
        for (Material m : Material.values()){


                // 20 tuiles PIERRE
                if (m.getMaterialValue() == "Pierre"){
                    for(int j=0 ; j < stone; j++){
                        Obstacle obstacle = new StoneWall(false);
                        this.add(obstacle);
                    }
                }

                // 12 tuiles GLACE
                if (m.getMaterialValue() == "Glace"){
                    for(int j=0 ; j < ice; j++){
                        Obstacle obstacle = new IceWall(false);
                        this.add(obstacle);
                    }
                }

                // 4 tuiles BOIS
                if (m.getMaterialValue() == "Bois"){
                    for(int j=0 ; j < crate; j++){
                        Obstacle obstacle = new Crate(false);
                        this.add(obstacle);
                    }
                }

        }
    }


    // Fonction pour mélanger le paquet
    public void shuffle(){
        for (int i = obstacles.size() -1 ; i>0; i--) {
            //échanger une carte aléatoire entre première et dernière carte
            int pick = rand.nextInt(i);
            Obstacle randCard = obstacles.get(pick);
            Obstacle lastCard = obstacles.get(i);
            obstacles.set(i, randCard);
            obstacles.set(pick, lastCard);
        }
    }

    // Fonction pour distribuer des cartes dans le deck à une autre main
    public void deal(HandObstacles h, int amount){
        for (int i=0; i< amount; i++){
            h.add(obstacles.remove(0));
        }
    }


    // Fonction pour distribuer un obstacle dont on connaît le type à une certaine main
    public void dealSpecific(HandObstacles h, String material, int amount){
        for (int i=0; i< amount; i++){
            for (Obstacle o : obstacles){
                if (o.getM_material() == material) {
                    h.add(o);
                    obstacles.remove(o);
                    break;
                }


            }
        }
    }

}

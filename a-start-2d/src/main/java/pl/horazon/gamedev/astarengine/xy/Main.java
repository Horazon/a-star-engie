package pl.horazon.gamedev.astarengine.xy;

import pl.horazon.gamedev.astarengine.path.PathFinder;
import pl.horazon.gamedev.astarengine.util.Utils;

public class Main {
	
    public static void main(String arg[]){

    	GameBoard2D borad = new GameBoard2D("map.txt");      
    	borad.loadMap();

        Poind2D start = new Poind2D(0, 0);
        Poind2D stop = new Poind2D(4,3);
        
        PathFinder<Poind2D, GameBoard2D> pathFinder = new PathFinder<>(start, stop, borad);
        
        pathFinder.run();
        Poind2D p = pathFinder.getRoad();
        
        Utils.printRoad(p);
        borad.printMapWithRoad(p);
    }
}

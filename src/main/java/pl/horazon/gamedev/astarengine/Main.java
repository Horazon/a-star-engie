package pl.horazon.gamedev.astarengine;

import pl.horazon.gamedev.astarengine.api.GameBoard;
import pl.horazon.gamedev.astarengine.path.PathFinder;
import pl.horazon.gamedev.astarengine.xy.GameBoard2D;
import pl.horazon.gamedev.astarengine.xy.Poind2D;

public class Main {
	
    public static void main(String arg[]){

    	GameBoard2D borad = new GameBoard2D("map.txt");      
    	borad.loadMap();

        Poind2D start = new Poind2D(0, 0);
        Poind2D stop = new Poind2D(4,3);
        
        PathFinder pathFinder = new PathFinder(start, stop, borad);
        
        long startTime = System.currentTimeMillis();
        pathFinder.run();
        long estimatedTime = System.currentTimeMillis() - startTime;

        System.out.println("Elipsed time: " + estimatedTime + " ms");
    }
}
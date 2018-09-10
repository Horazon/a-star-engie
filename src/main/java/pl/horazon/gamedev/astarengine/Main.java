package pl.horazon.gamedev.astarengine;

import pl.horazon.gamedev.astarengine.hex.GameBoardHex;
import pl.horazon.gamedev.astarengine.hex.PointHex;
import pl.horazon.gamedev.astarengine.path.PathFinder;
import pl.horazon.gamedev.astarengine.util.Utils;
import pl.horazon.gamedev.astarengine.xy.GameBoard2D;
import pl.horazon.gamedev.astarengine.xy.Poind2D;

public class Main {
	
	public static void main9(String arg[]){

    	GameBoardHex boardHex = new GameBoardHex("map-hex.txt");
    	boardHex.loadMap();
    	
    	PointHex start = new PointHex(0, 0);
    	PointHex stop = new PointHex(13, 3);
    	
    	PathFinder<PointHex, GameBoardHex> pathFinder = new PathFinder<>(start, stop, boardHex);
    	
        pathFinder.run();
        PointHex p = pathFinder.getRoad();
        
        Utils.printRoad(p);
        boardHex.printMapWithRoad(p);
    }
	
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

package pl.horazon.gamedev.astarengine.hex;

import pl.horazon.gamedev.astarengine.hex.GameBoardHex;
import pl.horazon.gamedev.astarengine.hex.PointHex;
import pl.horazon.gamedev.astarengine.path.PathFinder;
import pl.horazon.gamedev.astarengine.util.Utils;

public class Main {

	public static void main(String arg[]) {

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
}

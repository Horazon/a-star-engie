package pl.horazon.gamedev.astarengine.xy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pl.horazon.gamedev.astarengine.api.GameBoard;
import pl.horazon.gamedev.astarengine.load.FieldParser;

public class GameBoard2D implements GameBoard<Poind2D> {

	private static final Logger logger = LogManager.getLogger(GameBoard2D.class);

	private static final int EMPTY = 0;
	private static final int WALL = 1;
	private static final int OPEN = 4;
	private static final int CLOSED = 5;
	private static final int CURRENT = 6;
	private static final int BEGIN = 7;
	private static final int END = 8;
	private static final int ROAD = 99;

	private static Map<Integer, String> signMap = new HashMap<>();

	static {
		signMap.put(EMPTY, "_");
		signMap.put(WALL, "1");
		signMap.put(OPEN, ".");
		signMap.put(CLOSED, "#");
		signMap.put(CURRENT, "X");
		signMap.put(BEGIN, "B");
		signMap.put(END, "E");
		signMap.put(ROAD, "*");
	}

	private String fileName;

	private int[][] map;
	private int mapX;
	private int mapY;

	public GameBoard2D(String fileName) {
		this.fileName = fileName;
	}

	public void loadMap() {
		ClassLoader classLoader = getClass().getClassLoader();

		File dir = new File(classLoader.getResource(fileName).getFile());

		try {
			FieldParser p = new FieldParser(dir);
			p.parse();
			map = p.getMap();
			mapX = map[0].length;
			mapY = map.length;
		} catch (IOException e) {
			logger.error(e);
		}
	}

	public List<Poind2D> getNeighbors(Poind2D p) {

		List<Poind2D> points = new ArrayList<>();

		addNeighborIfPossible(p.x, p.y - 1, points);
		addNeighborIfPossible(p.x, p.y + 1, points);
		addNeighborIfPossible(p.x - 1, p.y, points);
		addNeighborIfPossible(p.x + 1, p.y, points);

		return points;
	}

	public void addNeighborIfPossible(int x, int y, List<Poind2D> points) {
		if (isMoveAlloved(x, y)) {
			points.add(new Poind2D(x, y));
		}
	}

	public boolean isMoveAlloved(int x, int y) {
		if (x < 0 || y < 0 || x >= mapX || y >= mapY)
			return false;

		return map[y][x] != WALL;
	}

	private int[][] copyMap() {
		return Arrays.stream(map).toArray(int[][]::new);
	}

	public void printMapWithRoad(Poind2D point) {
		int[][] printmap = copyMap();

		Poind2D next = point;

		while (next != null) {
			printmap[next.y][next.x] = ROAD;
			next = (Poind2D) next.getFrom();
		}

		printMap(printmap);
	}

	public void printMap(Collection<Poind2D> openNeighbors, Collection<Poind2D> closedNeighbors, Poind2D current,
			Poind2D start, Poind2D stop) {
		int[][] printmap = copyMap();

		for (Poind2D k : openNeighbors) {
			printmap[k.y][k.x] = OPEN;
		}

		for (Poind2D k : closedNeighbors) {
			printmap[k.y][k.x] = CLOSED;
		}

		printmap[current.y][current.x] = CURRENT;
		printmap[start.y][start.x] = BEGIN;
		printmap[stop.y][stop.x] = END;

		printMap(printmap);
	}

	private void printMap(int[][] printMap) {
		for (int y = 0; y < mapY; y++) {
			StringBuilder b = new StringBuilder();
			b.append('[');

			for (int x = 0; x < mapX; x++) {
				int iMax = mapX - 1;
				String c = getSign(printMap[y][x]);

				b.append(c);
				if (x == iMax)
					b.append(']').toString();
			}
			logger.info(b.toString());
		}
	}

	private String getSign(int code) {
		return signMap.get(code);
	}
}

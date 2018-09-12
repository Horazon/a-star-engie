package pl.horazon.gamedev.astarengine.hex;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pl.horazon.gamedev.astarengine.api.GameBoard;
import pl.horazon.gamedev.astarengine.load.FieldParser;
import pl.horazon.gamedev.astarengine.util.Utils;

public class GameBoardHex implements GameBoard<PointHex> {

	private static final Logger logger = LogManager.getLogger(GameBoardHex.class);

	private static final int EMPTY = 0;
	private static final int WALL = 1;
	private static final int OPEN = 4;
	private static final int CLOSED = 5;
	private static final int CURRENT = 6;
	private static final int BEGIN = 7;
	private static final int END = 8;
	private static final int ROAD = 99;
	private static final int OFFSET = 199;

	private String fileName;

	private int[][] map;
	private int mapX;
	private int mapY;

	private static Map<Integer, String> signMap = new HashMap<>();

	static {
		signMap.put(EMPTY, "[ ] ");
		signMap.put(WALL, "[1] ");
		signMap.put(OPEN, "[.] ");
		signMap.put(CLOSED, "[#] ");
		signMap.put(CURRENT, "[X] ");
		signMap.put(BEGIN, "[B] ");
		signMap.put(END, "[E] ");
		signMap.put(ROAD, "[*] ");
		signMap.put(OFFSET, "  ");
	}

	public GameBoardHex(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public List<PointHex> getNeighbors(PointHex p) {
		List<PointHex> points = new ArrayList<>();

		addNeighborIfPossible(p.x - 2, p.y, points); // lewo
		addNeighborIfPossible(p.x + 2, p.y, points); // prawo
		addNeighborIfPossible(p.x - 1, p.y - 1, points); // lewo gora
		addNeighborIfPossible(p.x + 1, p.y - 1, points); // prawo gora
		addNeighborIfPossible(p.x - 1, p.y + 1, points); // lewo dol
		addNeighborIfPossible(p.x + 1, p.y + 1, points); // prawo dol

		return points;
	}

	public void addNeighborIfPossible(int x, int y, List<PointHex> points) {
		if (isMoveAlloved(x, y)) {
			points.add(new PointHex(x, y));
		}
	}

	public boolean isMoveAlloved(int x, int y) {
		if (x < 0 || y < 0 || x >= mapX || y >= mapY)
			return false;

		return map[y][x] != WALL;
	}

	@Override
	public void printMapWithRoad(PointHex point) {
		int[][] printmap = Utils.cloneArray(map);

		PointHex next = point;

		while (next != null) {
			printmap[next.y][next.x] = ROAD;
			next = (PointHex) next.getFrom();
		}

		printMap(printmap);
	}

	public void printMap(Collection<PointHex> openNeighbors, Collection<PointHex> closedNeighbors, PointHex current,
			PointHex start, PointHex stop) {
		int[][] printmap = Utils.cloneArray(map);

		for (PointHex k : openNeighbors) {
			printmap[k.y][k.x] = OPEN;
		}

		for (PointHex k : closedNeighbors) {
			printmap[k.y][k.x] = CLOSED;
		}

		printmap[current.y][current.x] = CURRENT;
		printmap[start.y][start.x] = BEGIN;
		printmap[stop.y][stop.x] = END;

		printMap(printmap);
	}

	public void printMap(int[][] printMap) {

		boolean isFirstLine = true;
		boolean offset = false;

		for (int y = 0; y < mapY; y++) {
			StringBuilder l1 = new StringBuilder();
			StringBuilder l2 = new StringBuilder();
			StringBuilder l3 = new StringBuilder();

			if (offset) {
				l2.append(getSign(OFFSET));
				l3.append(getSign(OFFSET));
			}

			offset = !offset;
			
			for (int x = 0; x < mapX; x++) {

				if (map[y][x] != -1) {

					if (isFirstLine) {
						l1.append("/ \\ ");
					}

					l2.append(getSign(printMap[y][x]));
					l3.append("\\ / ");
				}

			}
			
			if (isFirstLine) {
				isFirstLine = false;
				logger.info(l1.toString());
			}
			logger.info(l2.toString());
			logger.info(l3.toString());
		}
		
		logger.info("==================");
	}

	private String getSign(int code) {
		return signMap.get(code);
	}

	@Override
	public void loadMap() {
		ClassLoader classLoader = getClass().getClassLoader();

		File dir = new File(classLoader.getResource(fileName).getFile());

		try {
			FieldParser p = new FieldParser(dir);
			p.parseHex();
			map = p.getMap();
			mapX = map[0].length;
			mapY = map.length;
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

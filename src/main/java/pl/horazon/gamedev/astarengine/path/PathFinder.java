package pl.horazon.gamedev.astarengine.path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pl.horazon.gamedev.astarengine.api.GameBoard;
import pl.horazon.gamedev.astarengine.api.Point;
import pl.horazon.gamedev.astarengine.common.PointXY;
import pl.horazon.gamedev.astarengine.util.Utils;

public class PathFinder<P extends Point, G extends GameBoard<P>> {

	public enum Result {
		PATH_FOUND, PATH_NOT_FOUND;
	}

	private static final Logger logger = LogManager.getLogger(PathFinder.class);

	private final P startPoint;
	private final P stopPoint;
	private final G gameBoard;

	private List<P> openNeighbors = new ArrayList<>();
	private List<P> closedNeighbors = new ArrayList<>();
	private List<P> currentNeighbors = new ArrayList<>();

	private Result result;

	private long estimatedTime;
	private int runCount;
	private P current;

	public PathFinder(P startPoint, P stopPoint, G gameBoard) {
		this.startPoint = startPoint;
		this.stopPoint = stopPoint;
		this.gameBoard = gameBoard;

		setup();
	}

	private void setup() {
		startPoint.calcH(stopPoint);
		startPoint.setG(0);
		startPoint.setF(0);
		startPoint.setFrom(null);

		openNeighbors.add(startPoint);
		
		current = startPoint;
	}

	public Result getResult() {
		return result;
	}

	public void run() {

		long startTime = System.currentTimeMillis();

		while (!stopPoint.equals(current) && isMoveAvaliable()) {
			current = findChipestPoint();

			openNeighbors.remove(current);
			openNeighbors(current);

			recalculateNeighbors(current, stopPoint);

			closedNeighbors.add(current);
			currentNeighbors.clear();

			runCount++;
		}

		if (current.equals(stopPoint)) {
			logger.info("Path: exists");
			result = Result.PATH_FOUND;
		} else {
			logger.info("Path: not exists");
			result = Result.PATH_NOT_FOUND;
		}

		estimatedTime = System.currentTimeMillis() - startTime;
		
		printStats();
	}

	private void printStats() {
		logger.info("Run count : " + runCount);
		logger.info("Open      : " + openNeighbors.size());
		logger.info("Closed    : " + closedNeighbors.size());
		logger.info("All       : " + (closedNeighbors.size() + openNeighbors.size()));
		logger.info("Elipsed time: {} ms", estimatedTime);
	}

	private void recalculateNeighbors(Point current, Point stop) {
		for (Point a : currentNeighbors) {
			a.calcH(stop);
			calcGF(a, current);
		}
	}
	
	public void calcGF(Point current, Point potential) {

		int newG = Utils.calculateG(current, potential);
		int newF = Utils.calculateF(newG, potential);
		
		if(Utils.isBetterPath(current, newF)) {
			current.setF(newF);
			current.setG(newG);
			current.setFrom(potential);
		}
	}

	private void openNeighbors(P p) {
		for (P point : gameBoard.getNeighbors(p)) {
			openNeighbor(point);
		}
	}

	private void openNeighbor(P p) {
		if (openNeighbors.contains(p) || closedNeighbors.contains(p) || !gameBoard.isMoveAlloved(p)) {
			return;
		}

		openNeighbors.add(p);
		currentNeighbors.add(p);
	}

	private P findChipestPoint() {
		Collections.sort(openNeighbors);
		return openNeighbors.get(0);
	}

	private boolean isMoveAvaliable() {
		return !openNeighbors.isEmpty();
	}

	public P getRoad() {
		return current;
	}
}

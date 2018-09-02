package pl.horazon.gamedev.astarengine.path;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pl.horazon.gamedev.astarengine.util.Utils;
import pl.horazon.gamedev.astarengine.api.GameBoard;
import pl.horazon.gamedev.astarengine.api.Point;

public class PathFinder<P extends Point> {

	private static final Logger logger = LogManager.getLogger(PathFinder.class);

	public enum Result {
		PATH_FOUND, PATH_NOT_FOUND;
	}

	private final P startPoint;
	private final P stopPoint;

	private Result result;
	private GameBoard<P> gameBoard;

	private List<P> openNeighbors = new ArrayList<>();
	private Set<P> closedNeighbors = new HashSet<>();
	private Set<P> currentNeighbors = new HashSet<>();

	public PathFinder(P startPoint, P stopPoint, GameBoard<P> gameBoard) {
		this.startPoint = startPoint;
		this.stopPoint = stopPoint;
		this.gameBoard = gameBoard;
	}

	public Result getResult() {
		return result;
	}

	public void run() {
		logger.info(String.format("Punkt startowy: %s", startPoint));
		logger.info(String.format("Punkt stop: %s", stopPoint));

		logger.info("START ANALIZY");

		P current = startPoint;

		current.calcH(stopPoint);
		current.calcGF(current);
		current.setFrom(null);

		openNeighbors.add(current);

		int runCount = 1;

		while (!current.equals(stopPoint) && isMoveAvaliable()) {
			gameBoard.printMap(openNeighbors, closedNeighbors, current, startPoint, stopPoint);

			current = findChipestPoint();

			openNeighbors.remove(current);
			openNeighbors(current);

			recalculateNeighbors(current, stopPoint);

			closedNeighbors.add(current);
			currentNeighbors.clear();

			runCount++;
		}

		logger.info("Iteracji: " + runCount);
		logger.info("Otwarte: " + openNeighbors.size());
		logger.info("Zamkniete: " + closedNeighbors.size());
		logger.info("Wszystkie: " + (closedNeighbors.size() + openNeighbors.size()));

		if (current.equals(stopPoint)) {
			logger.info("Droga: JEST");
			Utils.printRoad(current);
			gameBoard.printMapWithRoad(current);
			result = Result.PATH_FOUND;
		} else {
			logger.info("Droga: BRAK");
			result = Result.PATH_NOT_FOUND;
		}
	}

	private void recalculateNeighbors(Point current, Point stop) {
		for (Point a : currentNeighbors) {
			a.calcH(stop);
			a.calcGF(current);
		}
	}

	private void openNeighbors(P p) {
		Collection<P> points = gameBoard.getNeighbors(p);

		for (P point : points) {
			logger.debug(String.format("Sasiad %s", point.toString()));
		}

		for (P point : points) {
			openNeighbor(point);
		}
	}

	private void openNeighbor(P p) {

		logger.debug(String.format("Proba otwarcia %s", p.toString()));

		if (openNeighbors.contains(p)) {
			// nie otwieramy otwartego punktu
			logger.debug(String.format("= Punkt %s juz jest otwarty", p));
			return;
		}

		if (closedNeighbors.contains(p)) {
			// nie otwieramy zakmnietego punktu
			logger.debug(String.format("= Punkt %s jest zamkniety", p));
			return;
		}

		if (!gameBoard.isMoveAlloved(p)) {
			// nie otwieramy tez punktow - scian
			logger.debug(String.format("= Punkt %sjest sciana", p));
			return;
		}

		logger.debug(String.format("= Otwieramy punkt %s", p));
		openNeighbors.add(p);
		currentNeighbors.add(p);
	}

	private P findChipestPoint() {

		Collections.sort(openNeighbors);
		P min = (P) openNeighbors.get(0);

		logger.debug(String.format("Min %s %s", min.toString(), min.getF()));

		return min;
	}

	private boolean isMoveAvaliable() {
		return !openNeighbors.isEmpty();

	}
}

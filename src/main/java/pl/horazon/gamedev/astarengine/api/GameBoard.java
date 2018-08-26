package pl.horazon.gamedev.astarengine.api;

import java.util.List;
import java.util.Set;

public interface GameBoard<T extends Point> {
	public abstract boolean isMoveAlloved(T p);
	public abstract List<T> getNeighbors(T p);
	
	public abstract void printMapWithRoad(T point);
	public abstract void printMap(Set<T> openNeighbors, Set<T> closedNeighbors, T current, T start, T stop) ;
}
package pl.horazon.gamedev.astarengine.api;

import java.util.Collection;

public interface GameBoard<T extends Point> {
	public abstract Collection<T> getNeighbors(T p);
	
	public abstract void printMapWithRoad(T point);
	public abstract void printMap(Collection<T> openNeighbors, Collection<T> closedNeighbors, T current, T start, T stop) ;
	public void loadMap();
}
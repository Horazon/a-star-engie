package pl.horazon.gamedev.astarengine.api;

import java.util.Collection;
import java.util.List;

public interface GameBoard<T extends Point> {
	public abstract boolean isMoveAlloved(T p);
	public abstract Collection<T> getNeighbors(T p);
	
	public abstract void printMapWithRoad(T point);
	public abstract void printMap(Collection<T> openNeighbors, Collection<T> closedNeighbors, T current, T start, T stop) ;
	public void loadMap();
}
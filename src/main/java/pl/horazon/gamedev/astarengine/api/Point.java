package pl.horazon.gamedev.astarengine.api;

public interface Point extends Comparable<Point> {

    public abstract int getG();
	public abstract void setG(int g);
	
	public abstract int getF();
	public abstract void setF(int f);
	
	public abstract int getH();
	public abstract void setH(int h);

    public abstract Point getFrom();
    public abstract void setFrom(Point from);
    
	public abstract int getMoveCost();

    public abstract void calcH(Point p);
}

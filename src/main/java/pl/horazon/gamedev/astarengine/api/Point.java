package pl.horazon.gamedev.astarengine.api;

public interface Point extends Comparable<Point> {

    public abstract int getF();
    
    public abstract void calcH(Point p);
    public abstract void calcGF(Point p);
    
    public abstract boolean isMe(Point p);

    public abstract void setFrom(Point from);
    public abstract Point getFrom();
}

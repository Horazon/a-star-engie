package pl.horazon.gamedev.astarengine.common;

import static pl.horazon.gamedev.astarengine.util.Utils.calculateManhatanDisstance;

import pl.horazon.gamedev.astarengine.api.Point;

public class PointXY implements Point {

	protected Point from;
	private static final int MOVE_COST = 10;

	public final int x;
	public final int y;
	
	private int g;
	private int f;
	private int h;

	public PointXY(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void calcH(Point p) {
		PointXY p2 = (PointXY) p;
		this.h = calculateManhatanDisstance(this.x, p2.x, this.y, p2.y);
	}

	@Override
	public boolean isMe(Point p) {
		PointXY p2 = (PointXY) p;
		return p2.x == x && p2.y == y;
	}

	@Override
	public boolean equals(Object obj) {
		return isMe((PointXY) obj);
	}

	@Override
	public int hashCode() {
		int hashcode = 0;

		hashcode += x * 20;
		hashcode += y * 20;

		return hashcode;
	}

	@Override
	public int compareTo(Point that) {
		PointXY that2 = (PointXY) that;

		if (that2.f == this.f) {
			return 0;
		}
		if (that2.f > this.f) {
			return -1;
		}
		if (that2.f < this.f) {
			return 1;
		}

		return 0;
	}

	@Override
	public String toString() {
		return String.format("(%s:%s)", x, y);
	}

	@Override
	public void setFrom(Point from) {
		this.from = from;
	}

	@Override
	public Point getFrom() {
		return from;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getF() {
		return f;
	}

	public void setF(int f) {
		this.f = f;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public int getMoveCost() {
		return MOVE_COST;
	}
}

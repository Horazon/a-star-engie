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
	public int getF() {
		return f;
	}

	@Override
	public void calcGF(Point p) {

		PointXY p2 = (PointXY) p;
		int newG = 0;

		if (isMe(p)) {
			newG = 0;
		} else {
			newG = p2.g + MOVE_COST;
		}

		int newF = newG + h;

		if(!(newF <= f && f == 0)) {
			f = newF;
			g = newG;
			this.from = p;
		}
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
}

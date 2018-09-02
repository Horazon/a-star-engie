package pl.horazon.gamedev.astarengine.hex;

import static pl.horazon.gamedev.astarengine.util.Utils.calculateManhatanDisstance;

import pl.horazon.gamedev.astarengine.api.Point;
import pl.horazon.gamedev.astarengine.xy.Poind2D;

public class PointHex implements Point {
	
	protected Point from;
	private final static int mv_cost = 10;

	int x, y, g, h, f;

	public PointHex(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void calcH(Point p) {
		PointHex p2 = (PointHex) p;
		this.h = calculateManhatanDisstance(this.x, p2.x, this.y, p2.y);
		 System.out.println(String.format("H %s %s", this, h));
	}

	@Override
	public int getF() {
		return f;
	}

	@Override
	public void calcGF(Point p) {

		PointHex p2 = (PointHex) p;
		int new_g = 0;

		if (isMe(p)) {
			new_g = 0;
		} else {
			new_g = p2.g + mv_cost;
		}

		int new_f = new_g + h;

		if (new_f >= f && f != 0) {
			 System.out.println(String.format("Nowa wyliczona wartosc f wieksza od poprzedniej new:%s i old:%s", new_f, f));
		} else {
			// System.out.println(String.format("Nowa wyliczona wartosc f mniejsza od poprzedniej new:%s i old:%s", new_f, f));
			f = new_f;
			g = new_g;
			this.from = p;
		}
	}

	@Override
	public boolean isMe(Point p) {
		PointHex p2 = (PointHex) p;
		return p2.x == x && p2.y == y;
	}

	@Override
	public boolean equals(Object obj) {
		return isMe((PointHex) obj);
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
		// Order by film length
		// return Integer.compare(this.f, that.f);

		PointHex that2 = (PointHex) that;
		
		if(that2.f == this.f) {
			return 0;
		}
		if(that2.f > this.f) {
			return -1;
		}
		if(that2.f < this.f) {
			return 1;
		}
		
		return 0;
		//return Integer.compare(this.f, that2.f);
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

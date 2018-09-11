package pl.horazon.gamedev.astarengine.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import pl.horazon.gamedev.astarengine.api.Point;

public class Utils {

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public static void printRoad(Point chipest) {
        StringBuilder sb = new StringBuilder();

        Point next = chipest;

        while(next != null){
            sb.append(String.format("%s -> ", next));
            next = next.getFrom();
        }

        System.out.println("ROAD: " + sb.toString());
    }

    public static int calculateManhatanDisstance(int x0, int x1, int y0, int y1) {
        return Math.abs(x1-x0) + Math.abs(y1-y0);
    }
    
    public static int calculateG(Point me, Point current) {
		int newG;
		if (me.equals(current)) {
			newG = 0;
		} else {
			newG = current.getG() + current.getMoveCost();
		}
		return newG;
	}
    
    public static int calculateF(int g, Point point) {
    	return g + point.getH();
	}
    
	public static boolean isBetterPath(Point p, int f) {
		return !(f <= p.getF() && p.getF() == 0);
	}
	
	public static int[][] cloneArray(int[][] src) {
	    int length = src.length;
	    int[][] target = new int[length][src[0].length];
	    for (int i = 0; i < length; i++) {
	        System.arraycopy(src[i], 0, target[i], 0, src[i].length);
	    }
	    return target;
	}
}

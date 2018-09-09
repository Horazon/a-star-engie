package pl.horazon.gamedev.astarengine.hex;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import pl.horazon.gamedev.astarengine.api.GameBoard;
import pl.horazon.gamedev.astarengine.load.FieldParser;

public class GameBoardHex implements GameBoard<PointHex> {

	String fileName;
    int[][] map;

    public GameBoardHex(String fileName) {
        this.fileName = fileName;
    }
	
	@Override
	public boolean isMoveAlloved(PointHex p) {
		if(p.x<0 || p.y<0 || p.x>=map[0].length || p.y>=map.length) return false;
		
	    if(map[p.y][p.x] == 1){
	        return false;
	    } else {
	        return true;
	    }
	}

	@Override
	public List<PointHex> getNeighbors(PointHex p) {
		List<PointHex> points = new ArrayList<>();
		
	    points.add(new PointHex(p.x-2, p.y)); // lewo
	    points.add(new PointHex(p.x+2, p.y)); // prawo
	    points.add(new PointHex(p.x-1, p.y-1)); // lewo gora
	    points.add(new PointHex(p.x+1, p.y-1)); // prawo gora
	    points.add(new PointHex(p.x-1, p.y+1)); // lewo dol
	    points.add(new PointHex(p.x+1, p.y+1)); // prawo dol
	
	    return points;
	}

	@Override
	public void printMapWithRoad(PointHex point) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void printMap(Collection<PointHex> openNeighbors, Collection<PointHex> closedNeighbors, PointHex current, PointHex start,
			PointHex stop) {
	
	    for(int y = 0; y<map.length; y++){
	        StringBuilder b = new StringBuilder();
	        b.append('[');
	    	
	        for(int x = 0; x<map[y].length; x++) {
	            int iMax = map[y].length - 1;
	        	String c = "";
	
	            if(map[y][x]==-1) {
	            	c = " ";
	            } else if(map[y][x]==0) {
	            	c = "0";
	            } else if(map[y][x]==1) {
	            	c = "1";
	            } 
	            
	            b.append(c);
	            if (x == iMax)
	                b.append(']').toString();
	        }
	        System.out.println(b.toString());
	    }
	    
	    boolean f = true;
	    boolean offset = false;
	    
	    for(int y = 0; y<map.length; y++){
	        StringBuilder l1 = new StringBuilder();
	        StringBuilder l2 = new StringBuilder();
	        StringBuilder l3 = new StringBuilder();

        	
        	if(offset) {
        		l2.append("  ");
        	}
        	
	        if(offset) {
        		l3.append("  ");
        	}
        	
        	offset = !offset;
	        for(int x = 0; x<map[y].length; x++) {
	        	
	        	
	            if(map[y][x]==0) {
	            	if(f) {
	            		// tylko dla piewszej linii
	            		l1.append("/ \\ ");
	            	}
	            	
	            	PointHex p = new PointHex(x, y);
	            	if(start.equals(p)) {
		            	l2.append("[B] ");
	            	} else if(stop.equals(p)){

		            	l2.append("[E] ");
	            	} else if(openNeighbors.contains(p)){
		            	l2.append("[.] ");
	            	} else if(closedNeighbors.contains(p)){
		            	l2.append("[#] ");
	            	} else if(current.equals(p)){
		            	l2.append("[@] ");
	            	} else {
	            		l2.append("[ ] ");
	            	}
	            	
	            	
	            	l3.append("\\ / ");
	            } 
	            
	        }
	        if(f) {
        		f = false;
	        	System.out.println(l1.toString());
	        	}
	        System.out.println(l2.toString());
	        System.out.println(l3.toString());
	    }
	}
		
	
	
	@Override
	public void loadMap() {
	    ClassLoader classLoader = getClass().getClassLoader();
	
	    File dir = new File(classLoader.getResource(fileName).getFile());
	
	    try {
	        FieldParser p = new FieldParser(dir);
	        p.parseHex();
	        map = p.getMap();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	   
	}

}

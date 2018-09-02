package pl.horazon.gamedev.astarengine.xy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import pl.horazon.gamedev.astarengine.api.GameBoard;
import pl.horazon.gamedev.astarengine.load.FieldParser;

public class GameBoard2D implements GameBoard<Poind2D> {

    String fileName;
    int[][] map;

    public GameBoard2D(String fileName) {
        this.fileName = fileName;
    }

    public int[][] get(){
        return map;
    }
    
    public void loadMap() {
	    ClassLoader classLoader = getClass().getClassLoader();
	
	    File dir = new File(classLoader.getResource(fileName).getFile());
	
	    try {
	        FieldParser p = new FieldParser(dir);
	        p.parse();
	        map = p.getMap();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public List<Poind2D> getNeighbors(Poind2D p) {
	
	    List<Poind2D> points = new ArrayList<>();
	
	    points.add(new Poind2D(p.x, p.y-1));
	    points.add(new Poind2D(p.x, p.y+1));
	    points.add(new Poind2D(p.x-1, p.y));
	    points.add(new Poind2D(p.x+1, p.y));
	
	    return points;
	}

	public boolean isMoveAlloved(Poind2D p) {
	    if(p.x<0 || p.y<0 || p.x>=10 || p.y>=10) return false;
	
	
	    if(map[p.y][p.x] == 1){
	        return false;
	    } else {
	        return true;
	    }
	}

	public void printMapWithRoad(Poind2D point) {
	    int[][] printmap = new int[10][10];
	
	    for(int y = 0; y<10; y++){
	        for(int x = 0; x<10; x++) {
	            printmap[y][x] = get()[y][x];
	        }
	    }
	    
	    Poind2D next = point;
	
	    while(next != null){
	        printmap[next.y][next.x] = 99;
	        next = (Poind2D) next.getFrom();
	    }
	
	    for(int y = 0; y<10; y++){
	    	
	    	StringBuilder b = new StringBuilder();
	        b.append('[');
	    	
	        for(int x = 0; x<10; x++) {
	            int iMax = 10 - 1;
	        	String c = "";
	            if(printmap[y][x]==0) {
	            	c = "_";
	            } else if(printmap[y][x]==1) {
	            	c = "1";
	            } else if(printmap[y][x]==99) {
	                c = "*";
	            }
	            
	            b.append(c);
	            if (x == iMax)
	                b.append(']').toString();
	        }
	        System.out.println(b.toString());
	    }
	}

	public void printMap(Collection<Poind2D> openNeighbors, Collection<Poind2D> closedNeighbors, Poind2D current, Poind2D start, Poind2D stop) {
	    int[][] printmap = new int[10][10];
	
	    for(int y = 0; y<10; y++){
	        for(int x = 0; x<10; x++) {
	            printmap[y][x] = get()[y][x];
	        }
	    }
	
	    // 4 = .
	    for(Poind2D k : openNeighbors){
	        printmap[k.y][k.x] = 4;
	    }
	
	    // 5 = #
	    for(Poind2D k : closedNeighbors){
	        printmap[k.y][k.x] = 5;
	    }
	
	    // 6 = X
	    printmap[current.y][current.x] = 6;
	    
	    // 7 = B
	    printmap[start.y][start.x] = 7;
	    
	    // 8 = E
	    printmap[stop.y][stop.x] = 8;
	
	    for(int y = 0; y<10; y++){
	        StringBuilder b = new StringBuilder();
	        b.append('[');
	    	
	        for(int x = 0; x<10; x++) {
	            int iMax = 10 - 1;
	        	String c = "";
	
	            if(printmap[y][x]==0) {
	            	c = "_";
	            } else if(printmap[y][x]==1) {
	            	c = "1";
	            } else if(printmap[y][x]==4) {
	            	c = ".";
	            } else if(printmap[y][x]==5) {
	            	c = "#";
	            } else if(printmap[y][x]==6) {
	            	c = "X";
	            } else if(printmap[y][x]==7) {
	            	c = "B";
	            } else if(printmap[y][x]==8) {
	            	c = "E";
	            }
	            
	            b.append(c);
	            if (x == iMax)
	                b.append(']').toString();
	        }
	        System.out.println(b.toString());
	    }
	}
}

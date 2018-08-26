package pl.horazon.gamedev.astarengine.load;

import java.util.HashMap;
import java.util.Map;

public class Field {
    int id;

    Map<Integer, Integer> neighbors = new HashMap<Integer, Integer>();

    public Field(int id) {
        this.id = id;
    }

    public void addNeighbor(int f, int cost){
        neighbors.put(f, cost);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("field id: " + id);
        sb.append(", neighbors: ");
        for(Integer n : neighbors.keySet()){
            sb.append(n + "(" + neighbors.get(n) + "),");
        }

        return sb.toString();
    }
}

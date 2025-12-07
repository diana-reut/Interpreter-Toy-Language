package model.state;

import model.value.Value;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MapHeap implements Heap{
    private Map<Integer, Value> map = new HashMap<Integer,Value>();
    int free_index = 1;

    @Override
    public String toString() {
       StringBuilder sb = new StringBuilder("Heap:\n");
       for (Map.Entry<Integer, Value> entry : map.entrySet()) {
           sb.append(entry.getKey()).append("-> ").append(entry.getValue()).append('\n');
       }
       sb.append('\n');
       return sb.toString();
    }

    @Override
    public int add(Value value) {
        map.put(free_index, value);
        free_index++;
        return free_index-1;
    }

    @Override
    public Value get(int address) {
        if(!isDefined(address))
            throw new NotFoundException("Address " + address + " is not defined");
        return map.get(address);
    }

    @Override
    public void update(int address, Value value) {
        if(!isDefined(address))
            throw new NotFoundException("Address " + address + " is not defined");
        map.put(address, value);
    }

    @Override
    public boolean isDefined(int address) {
        return map.containsKey(address);
    }

    @Override
    public Map<Integer, Value> getMap() {
        return Collections.unmodifiableMap(map);
    }

    @Override
    public void setMap(Map<Integer, Value> newContent) {
        this.map = newContent;
    }
}

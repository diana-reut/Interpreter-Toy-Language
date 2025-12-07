package model.state;

import model.value.Value;

import java.util.Map;

public interface Heap {
    public int add(Value value);
    public Value get(int address);
    public void update(int address, Value value);
    public boolean isDefined(int address);
    Map<Integer, Value> getMap();
    void setMap(Map<Integer, Value> newContent);
}

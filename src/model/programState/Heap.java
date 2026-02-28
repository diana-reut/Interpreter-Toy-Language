package model.programState;

import model.exceptions.MyExceptionModel;
import model.value.Value;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Heap implements IHeapDict<Integer, Value> {
    int nextIndex = 1;
    private Map<Integer,Value> map = new HashMap<>();

    @Override
    public int put(Value value) throws MyExceptionModel {
        map.put(nextIndex++,value);
        return nextIndex - 1;
    }

    @Override
    public Value getValue(Integer key) {
        if(!(map.containsKey(key))){
            try {
                throw new MyExceptionModel("Address not defined");
            } catch (MyExceptionModel e) {
                throw new RuntimeException(e);
            }
        }
        return map.get(key);
    }

    @Override
    public boolean containsKey(Integer key) {
        return map.containsKey(key);
    }

    @Override
    public void update(Integer key, Value value) throws MyExceptionModel {
        if(!(map.containsKey(key))){
            try {
                throw new MyExceptionModel("Address not defined");
            } catch (MyExceptionModel e) {
                throw new RuntimeException(e);
            }
        }
        map.put(key,value);
    }

    @Override
    public Map<Integer, Value> getDictionary() {
        return Collections.unmodifiableMap(map);
    }

    @Override
    public void setDictionary(Map<Integer, Value> dictionary) {
        this.map = dictionary;
    }

    @Override
    public Map<Integer, Value> deepcopy() {
        Map<Integer, Value> copy = new HashMap<>();
        for (Map.Entry<Integer, Value> entry : map.entrySet()) {
            copy.put(entry.getKey(), entry.getValue().deepcopy());
        }
        return copy;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("  Heap:\n");
        map.forEach((k, v) ->
                {sb.append(k).append("->").append(v).append("\n");}
        );
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Heap heap)) return false;
        return Objects.equals(map, heap.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nextIndex, map);
    }
}

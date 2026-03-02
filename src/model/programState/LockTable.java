package model.programState;

import model.exceptions.MyExceptionModel;
import model.value.Value;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LockTable implements ILockTable<Integer, Integer> {
    Map<Integer, Integer> map = new HashMap<Integer, Integer>();
    private static int newFreeLocation = -1;

    @Override
    public synchronized int put(Integer value) throws MyExceptionModel {
        if(map.containsKey(value)){
            throw new MyExceptionModel("Value already exists!");
        }
        newFreeLocation++;
        map.put(newFreeLocation, value);
        return newFreeLocation;
    }

    @Override
    public synchronized Integer lookup(Integer key) {
        return map.get(key);
    }

    @Override
    public boolean containsKey(Integer key) {
        return map.containsKey(key);
    }

    @Override
    public synchronized void update(Integer key, Integer value) throws MyExceptionModel {
        if(!(map.containsKey(key))){
            throw new MyExceptionModel("Key does not exist!");
        }
        map.put(key, value);
    }

    @Override
    public Map<Integer, Integer> getDictionary() {
        return Collections.unmodifiableMap(map);
    }

    @Override
    public void setDictionary(Map<Integer, Integer> dictionary) {
        this.map = dictionary;
    }

    @Override
    public Map<Integer, Integer> deepcopy() {
        Map<Integer, Integer> copy = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            copy.put(entry.getKey(), entry.getValue());
        }
        return copy;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("  LockTable:\n");
        map.forEach((k, v) ->
                {sb.append(k).append("->").append(v).append("\n");}
        );
        return sb.toString();
    }
}

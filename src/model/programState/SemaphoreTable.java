package model.programState;

import javafx.util.Pair;
import model.exceptions.MyExceptionModel;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SemaphoreTable implements ISemaphoreTable<Integer, Integer, List<Integer>> {
    Map<Integer, Pair<Integer, List<Integer>>> map = new HashMap<>();
    int newFreeLocation = -1;

    @Override
    public synchronized int put(Integer value1, List<Integer> value2) throws MyExceptionModel {
        newFreeLocation++;
        map.put(newFreeLocation, new Pair<>(value1, value2));
        return newFreeLocation;
    }

    @Override
    public synchronized Pair<Integer, List<Integer>> getValue(Integer key) {
        return map.get(key);
    }

    @Override
    public synchronized boolean containsKey(Integer key) {
        return map.containsKey(key);
    }

    @Override
    public synchronized void update(Integer key, Integer value1, List<Integer> value2) throws MyExceptionModel {
        map.put(key, new Pair<>(value1, value2));
    }

    @Override
    public synchronized Map<Integer, Pair<Integer, List<Integer>>> getDictionary() {
        return Collections.unmodifiableMap(map);
    }

    @Override
    public synchronized void setDictionary(Map<Integer, Pair<Integer, List<Integer>>> dictionary) {
        this.map = dictionary;
    }
}

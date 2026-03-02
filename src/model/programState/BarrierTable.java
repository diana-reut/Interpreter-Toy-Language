package model.programState;

import javafx.util.Pair;
import model.exceptions.MyExceptionModel;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BarrierTable implements IBarrierTable<Integer, Integer, List<Integer>> {
    private static int newFreeLocation = -1;
    Map<Integer, Pair<Integer, List<Integer>>> barrierTable = new HashMap<>();

    @Override
    public synchronized int put(Integer value1, List<Integer> value2) throws MyExceptionModel {
        newFreeLocation++;
        barrierTable.put(newFreeLocation, new Pair<>(value1, value2));
        return newFreeLocation;
    }

    @Override
    public synchronized Pair<Integer, List<Integer>> getValue(Integer key) {
        return barrierTable.get(key);
    }

    @Override
    public synchronized boolean containsKey(Integer key) {
        return barrierTable.containsKey(key);
    }

    @Override
    public synchronized void update(Integer key, Integer value1, List<Integer> value2) throws MyExceptionModel {
        barrierTable.put(key,new Pair<>(value1, value2));
    }

    @Override
    public synchronized Map<Integer, Pair<Integer, List<Integer>>> getDictionary() {
        return Collections.unmodifiableMap(barrierTable);
    }

    @Override
    public synchronized void setDictionary(Map<Integer, Pair<Integer, List<Integer>>> dictionary) {
        this.barrierTable = dictionary;
    }
}

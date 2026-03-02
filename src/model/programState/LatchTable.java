package model.programState;

import model.exceptions.MyExceptionModel;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LatchTable implements ILatchTable<Integer, Integer> {
    Map<Integer, Integer> dictionary = new HashMap<>();
    private static int newFreeLocation = -1;

    @Override
    public synchronized int put(Integer value) throws MyExceptionModel {
        newFreeLocation++;
        dictionary.put(newFreeLocation, value);
        return newFreeLocation;
    }

    @Override
    public synchronized Integer getValue(Integer key) {
        return dictionary.get(key);
    }

    @Override
    public synchronized boolean containsKey(Integer key) {
        return dictionary.containsKey(key);
    }

    @Override
    public synchronized void update(Integer key, Integer value) throws MyExceptionModel {
        dictionary.put(key, value);
    }

    @Override
    public synchronized Map<Integer, Integer> getDictionary() {
        return Collections.unmodifiableMap(dictionary);
    }

    @Override
    public synchronized void setDictionary(Map<Integer, Integer> dictionary) {
        this.dictionary = dictionary;
    }
}

package model.programState;

import model.exceptions.MyExceptionModel;
import model.value.Value;

import java.io.BufferedReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FileTable implements IDictionary<String, BufferedReader>{
    Map<String, BufferedReader> map = new HashMap<String, BufferedReader>();

    @Override
    public void put(String key, BufferedReader value) {
        map.put(key, value);
    }

    @Override
    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    @Override
    public void update(String key, BufferedReader value) throws MyExceptionModel {
        if (!(map.containsKey(key))) {
            throw new MyExceptionModel("File does not exist");
        }
        map.put(key, value);
    }

    @Override
    public BufferedReader getValue(String key) {
        return  map.get(key);
    }

    @Override
    public void remove(String key) throws MyExceptionModel {
        if (!(map.containsKey(key))) {
            throw new MyExceptionModel("File does not exist");
        }
        map.remove(key);
    }

    @Override
    public Map<String, BufferedReader> getDictionary() {
        return Collections.unmodifiableMap(map);
    }

    @Override
    public IDictionary<String, BufferedReader> deepcopy() {
        var dict = new HashMap<>(map);
        var returnValue = new FileTable();
        returnValue.setDictionary(dict);
        return returnValue;
    }

    @Override
    public void setDictionary(Map<String, BufferedReader> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("  FileTable:\n");
        map.forEach((key, value) -> {sb.append(key + "\n");});
        return sb.toString();
    }
}

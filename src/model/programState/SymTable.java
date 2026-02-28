package model.programState;

import model.exceptions.MyExceptionModel;
import model.value.Value;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SymTable implements IDictionary<String, Value>{
    Map<String, Value> map = new HashMap<>();

    @Override
    public void put(String key, Value value) throws MyExceptionModel {
        if (map.containsKey(key)) {
            throw new MyExceptionModel("Variable already exists");
        }
        map.put(key, value);
    }

    @Override
    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    @Override
    public void update(String key, Value value) throws MyExceptionModel {
        if(!(map.containsKey(key))){
            throw new MyExceptionModel("Key not found");
        }
        map.put(key, value);
    }

    @Override
    public Map<String, Value> getDictionary() {
        return Collections.unmodifiableMap(map);
    }

    @Override
    public IDictionary<String, Value> deepcopy() {
        var dict = new HashMap<>(map);
        var returnValue = new SymTable();
        returnValue.setDictionary(dict);
        return returnValue;
    }

    @Override
    public void setDictionary(Map<String, Value> map) {
        this.map = map;
    }

    @Override
    public Value getValue(String key) {
        return map.get(key);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("  Symbol Table:\n");
        map.forEach((k, v) ->
                {sb.append(k).append("->").append(v).append("\n");}
        );
        return sb.toString();
    }
}

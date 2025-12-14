package model.state;

import model.type.Type;
import model.value.Value;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MapSymbolTable implements SymbolTable{
    private Map<String, Value> map = new HashMap<>();//exista hashcode() -  o functie care face hashing pe Object()

    @Override
    public boolean isDefined(String variableName) {
        return map.containsKey(variableName);
    }

    @Override
    public Type getType(String variableName) throws NotFoundException {
        if (!isDefined(variableName))
            throw new NotFoundException("Variable " + variableName + " is not defined");
        return map.get(variableName).getType();
    }

    @Override
    public void update(String variableName, Value value) throws NotFoundException {
        if (!isDefined(variableName))
            throw new NotFoundException("Variable " + variableName + " is not defined");
        map.put(variableName, value);
    }

    @Override
    public void declareVariable(String variableName, Type simpleType) throws AlreadyDefinedException {
        if (isDefined(variableName))
            throw new AlreadyDefinedException("Variable " + variableName + " is already defined");
        map.put(variableName, simpleType.getDefaultValue());
    }

    @Override
    public Value getValue(String variableName) throws NotFoundException {
        if (!isDefined(variableName))
            throw new NotFoundException("Variable " + variableName + " is not defined");
        return map.get(variableName);
    }

    @Override
    public Map<String, Value> getSymbolTable() {
        return Collections.unmodifiableMap(map);
    }

    public void setMap(Map<String, Value> map) {
        this.map = map;
    }

    @Override
    public Map<String, Value> deepcopy() {
        Map<String, Value> newDict = new HashMap<>();
        for (String key : map.keySet()) {
            newDict.put(key, map.get(key).deepCopy());
        }
        return newDict;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SymTable:\n");
        for (Map.Entry<String, Value> entry : map.entrySet()) {
            sb.append(entry.getKey() + " = " + entry.getValue() + "\n");
        }
        sb.append("\n");
        return sb.toString();
    }
}

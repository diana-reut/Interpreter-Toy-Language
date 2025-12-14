package model.typecheck;

import model.type.Type;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TypeEnvironment implements ITypeEnvironment{
    private Map<String, Type> map;

    public TypeEnvironment(){
        map = new HashMap<>();
    }
    public TypeEnvironment(Map<String, Type> map) {
        this.map = map;
    }

    @Override
    public void add(String variableName, Type type) throws TypeCheckException {
        if(isDefined(variableName)){
            throw new TypeCheckException("Variable already defined!");
        }
        map.put(variableName, type);
    }

    @Override
    public Type getType(String variableName) throws TypeCheckException {
        if(!(isDefined(variableName))){
            throw new TypeCheckException("Variable not defined!");
        }
        return map.get(variableName);
    }

    @Override
    public Map<String, Type> getMap() {
        return Collections.unmodifiableMap(map);
    }

    @Override
    public boolean isDefined(String variableName) {
        return map.containsKey(variableName);
    }

    @Override
    public ITypeEnvironment deepCopy() {
        return new TypeEnvironment(new HashMap<>(this.map));
    }
}

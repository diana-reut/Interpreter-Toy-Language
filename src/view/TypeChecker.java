package view;

import model.exceptions.MyExceptionModel;
import model.programState.IDictionary;
import model.type.Type;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TypeChecker implements IDictionary<String, Type> {
    private Map<String, Type> dict = new HashMap<>();

    @Override
    public Type getValue(String key) {
        return dict.get(key);
    }

    @Override
    public void put(String key, Type value) throws MyExceptionModel {
        dict.put(key, value);
    }

    @Override
    public boolean containsKey(String key) {
        return dict.containsKey(key);
    }

    @Override
    public void update(String key, Type value) throws MyExceptionModel {
        dict.put(key, value);
    }

    @Override
    public Map<String, Type> getDictionary() {
        return Collections.unmodifiableMap(dict);
    }

    @Override
    public IDictionary<String, Type> deepcopy() {
        var map = new HashMap<>(dict);
        var returnValue = new TypeChecker();
        returnValue.setDictionary(map);
        return returnValue;
    }

    @Override
    public void setDictionary(Map<String, Type> map) {
        dict = map;
    }
}

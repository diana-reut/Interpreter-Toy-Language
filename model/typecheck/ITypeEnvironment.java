package model.typecheck;

import model.type.Type;

import java.util.Map;

public interface ITypeEnvironment {
    void add(String variableName, Type type) throws TypeCheckException;
    Type getType(String variableName) throws TypeCheckException;
    Map<String, Type> getMap();
    boolean isDefined(String variableName);
    ITypeEnvironment deepCopy();
}

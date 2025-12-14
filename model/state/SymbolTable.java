package model.state;

import model.type.Type;
import model.value.Value;

import java.util.Map;

public interface SymbolTable {
    boolean isDefined(String variableName);
    Type getType(String variableName) throws NotFoundException;
    void update(String variableName, Value value) throws NotFoundException;
    void declareVariable(String variableName, Type simpleType) throws AlreadyDefinedException;
    Value getValue(String variableName) throws NotFoundException;
    Map<String, Value> getSymbolTable();
    Map<String, Value> deepcopy();
}

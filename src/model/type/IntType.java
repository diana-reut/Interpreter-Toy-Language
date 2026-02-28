package model.type;

import model.value.IntValue;
import model.value.Value;

public class IntType implements Type{
    @Override
    public String toString() {
        return "int";
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof IntType);
    }

    @Override
    public Value getDefaultValue() {
        return new IntValue(0);
    }

    @Override
    public Type deepcopy() {
        return new IntType();
    }
}

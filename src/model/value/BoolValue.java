package model.value;

import model.type.BoolType;
import model.type.Type;

public class BoolValue implements Value {
    public boolean value;
    public BoolValue(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public String toString()
    {
        return String.valueOf(value);
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public Value deepcopy() {
        return new BoolValue(this.value);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BoolValue boolValue)) return false;
        return value == boolValue.value;
    }
}

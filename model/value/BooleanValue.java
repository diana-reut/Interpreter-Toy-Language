package model.value;

import model.type.BooleanType;
import model.type.Type;

public record BooleanValue(boolean value) implements Value {

    @Override
    public Type getType() {
        return new BooleanType();
    }

    @Override
    public Value deepCopy() {
        return new BooleanValue(value);
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }
}

package model.value;

import model.type.IntType;
import model.type.Type;

import java.util.Objects;

public class IntValue implements Value {
    private int value;
    public IntValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof IntValue intValue)) return false;
        return value == intValue.value;
    }

    @Override
    public Value deepcopy() {
        return new IntValue(this.value);
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}

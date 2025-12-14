package model.value;

import model.type.IntegerType;
import model.type.Type;

import java.util.Objects;

public record IntegerValue(int value) implements Value {

    @Override
    public Type getType() {
        return new IntegerType();
    }

    @Override
    public Value deepCopy() {
        return new IntegerValue(value);
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        IntegerValue that = (IntegerValue) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}

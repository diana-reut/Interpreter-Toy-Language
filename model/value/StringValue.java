package model.value;

import model.type.StringType;
import model.type.Type;

public record StringValue(String value) implements Value {

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public Value deepCopy() {
        return new StringValue(value);
    }

    @Override
    public String toString() {
        return  "'" + value + "'";
    }
}

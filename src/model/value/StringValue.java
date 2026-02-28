package model.value;

import model.type.StringType;
import model.type.Type;

import java.util.Objects;

public class StringValue implements Value {
    private String value;
    public StringValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public Value deepcopy() {
        return new StringValue(this.value);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof StringValue that)) return false;
        return Objects.equals(value, that.value);
    }
}

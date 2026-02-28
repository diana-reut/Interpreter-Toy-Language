package model.type;

import model.value.StringValue;
import model.value.Value;

public class StringType implements Type {
    @Override
    public Value getDefaultValue() {
        return new StringValue("");
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof StringType);
    }

    @Override
    public Type deepcopy() {
        return new StringType();
    }

    @Override
    public String toString() {
        return "string";
    }
}

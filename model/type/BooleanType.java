package model.type;

import model.value.BooleanValue;
import model.value.Value;

public class BooleanType implements Type{

    @Override
    public Value getDefaultValue() {
        return new BooleanValue(false);
    }

    @Override
    public String toString() {
        return "boolean";
    }

    @Override
    public boolean equals(Object obj) {
        return (obj != null) && (getClass() == obj.getClass());
    }
}

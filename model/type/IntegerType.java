package model.type;

import model.value.IntegerValue;
import model.value.Value;

public class IntegerType implements Type{

    @Override
    public Value getDefaultValue() {
        return new IntegerValue(0);
    }

    @Override
    public String toString() {
        return "integer";
    }

    @Override
    public boolean equals(Object obj) {
        return (obj != null) && (getClass() == obj.getClass());
    }
}

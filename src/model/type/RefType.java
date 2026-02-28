package model.type;

import model.value.RefValue;
import model.value.Value;

public class RefType implements Type{
    Type inner;
    public RefType(Type innerType) {
        this.inner = innerType;
    }

    public Type getInner() {
        return inner;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RefType refType)) return false;
        return inner.equals(refType.getInner());
    }

    @Override
    public String toString() {
        return "Ref(" + inner.toString() + ")";
    }

    @Override
    public Value getDefaultValue() {
        return new RefValue(0, inner);
    }

    @Override
    public Type deepcopy() {
        return new RefType(inner.deepcopy());
    }
}

package model.value;

import model.type.RefType;
import model.type.Type;

import java.util.Objects;

public class RefValue implements Value{
    int address;
    Type locationType;
    public RefValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress() {
        return address;
    }

    public Type getLocationType() {
        return locationType;
    }

    @Override
    public String toString() {
        return "(" + address + ", " + locationType + ")";
    }

    @Override
    public Value deepcopy() {
        return new RefValue(address, locationType.deepcopy());
    }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RefValue refValue)) return false;
        return address == refValue.address && locationType.equals(refValue.locationType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, locationType);
    }
}

package model.value;

import model.type.RefType;
import model.type.Type;

public class RefValue implements Value {
    int address;
    Type locationType;

    public RefValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public RefValue(Type locationType) {
        this.locationType = locationType;
    }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    public int getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "(" + address + "," + locationType + ")";
    }
}

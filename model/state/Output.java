package model.state;

import model.value.Value;

import java.util.List;

public interface Output {
    void add(Value value);
    List<Value> getOutputList();
}

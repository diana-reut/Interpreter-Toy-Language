package model.state;

import model.value.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ListOutput implements Output{
    private final List<Value> outputList;

    public ListOutput() {
        outputList = new ArrayList<>();
    }

    @Override
    public void add(Value value) {
        outputList.add(value);
    }

    @Override
    public List<Value> getOutputList() {
        return Collections.unmodifiableList(outputList);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Out:\n");
        for (Value value : outputList) {
            sb.append(value.toString()).append("\n");
        }
        sb.append("\n");
        return sb.toString();
    }
}

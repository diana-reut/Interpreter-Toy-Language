package model.programState;

import model.value.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Output implements IList<Value> {
    List<Value> list = new ArrayList<>();

    @Override
    public void add(Value obj) {
        list.add(obj);
    }

    @Override
    public Value get(int index) {
        return list.get(index);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void set(int index, Value obj) {
        list.set(index, obj);
    }

    @Override
    public List<Value> getList() {
        return Collections.unmodifiableList(list);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("  Output:\n");
        list.forEach(v -> sb.append(v.toString()).append("\n"));
        return sb.toString();
    }
}

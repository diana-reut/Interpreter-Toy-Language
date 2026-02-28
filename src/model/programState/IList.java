package model.programState;

import java.util.List;

public interface IList<T> {
    void add(T obj);
    T get(int index);
    int size();
    boolean isEmpty();
    void set(int index, T obj);
    List<T> getList();
}

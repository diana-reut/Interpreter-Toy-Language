package model.programState;

import model.exceptions.MyExceptionModel;

import java.util.Map;

public interface IHeapDict<K, V> {
    int put(V value) throws MyExceptionModel;
    V getValue(K key);
    boolean containsKey(K key);
    void update(K key, V value) throws MyExceptionModel;
    Map<K, V> getDictionary();
    void setDictionary(Map<K, V> dictionary);
    Map<K, V> deepcopy();
}

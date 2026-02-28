package model.programState;

import model.exceptions.MyExceptionModel;

import java.util.Map;

public interface IDictionary<K, V> {
    void put(K key, V value) throws MyExceptionModel;
    default V getValue(K key) {
        return null;
    }
    default K getKey(V value){
        return null;
    }
    boolean containsKey(K key);
    void update(K key, V value) throws MyExceptionModel;
    default void remove(K key) throws MyExceptionModel{}
    Map<K, V> getDictionary();
    IDictionary<K, V> deepcopy();
    void setDictionary(Map<K, V> map);
}

package model.programState;

import javafx.util.Pair;
import model.exceptions.MyExceptionModel;

import java.util.Map;

public interface IBarrierTable<K, V1, V2> {
    int put(V1 value1, V2 value2) throws MyExceptionModel;
    Pair<V1,V2> getValue(K key);
    boolean containsKey(K key);
    void update(K key, V1 value1, V2 value2) throws MyExceptionModel;
    Map<K, Pair<V1, V2>> getDictionary();
    void setDictionary(Map<K, Pair<V1, V2>> dictionary);
}

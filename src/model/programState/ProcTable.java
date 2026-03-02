package model.programState;

import javafx.util.Pair;
import model.exceptions.MyExceptionModel;
import model.statement.IStatement;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcTable implements IProcTable<String, List<String>, IStatement>{
    Map<String, Pair<List<String>, IStatement>> table = new HashMap<>();

    @Override
    public void put(String key, List<String> value1, IStatement value2) throws MyExceptionModel {
        table.put(key, new Pair<>(value1,value2));
    }

    @Override
    public Pair<List<String>, IStatement> getValue(String key) {
        return table.get(key);
    }

    @Override
    public boolean containsKey(String key) {
        return table.containsKey(key);
    }

    @Override
    public void update(String key, List<String> value1, IStatement value2) throws MyExceptionModel {
        table.put(key, new Pair<>(value1,value2));
    }

    @Override
    public Map<String, Pair<List<String>, IStatement>> getDictionary() {
        return Collections.unmodifiableMap(table);
    }

    @Override
    public void setDictionary(Map<String, Pair<List<String>, IStatement>> dictionary) {
        this.table = dictionary;
    }
}

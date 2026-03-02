package model.statement;

import javafx.util.Pair;
import model.exceptions.MyExceptionModel;
import model.programState.IDictionary;
import model.programState.ProgramState;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

import java.util.ArrayList;
import java.util.List;

public record ReleaseSemStmt(String var) implements IStatement{
    @Override
    public ProgramState execute(ProgramState programState) throws MyExceptionModel {
        synchronized (programState.semaphoreTable()){
            boolean exists = programState.symTable().containsKey(var);
            if(!exists){
                throw new MyExceptionModel("variable not fount in sym table");
            }
            Value varValue = programState.symTable().getValue(var);
            if(!(varValue instanceof IntValue)){
                throw new MyExceptionModel("var not integer");
            }
            int foundIndex = ((IntValue) varValue).getValue();
            boolean existsSem =  programState.semaphoreTable().containsKey(foundIndex);
            if(!existsSem){
                throw new MyExceptionModel("variable not fount in semaphore table");
            }
            Pair<Integer, List<Integer>> valueOfIndex = programState.semaphoreTable().getValue(foundIndex);
            int N1 = valueOfIndex.getKey();
            List<Integer> N2 = valueOfIndex.getValue();
            if(N2.contains(programState.id())){
                List<Integer> newList = new ArrayList<>();
                for(Integer i : N2){
                    if(i != programState.id()){
                        newList.add(i);
                    }
                }
                programState.semaphoreTable().update(foundIndex,N1,newList);
                //N2.remove(programState.id());
            }
        }
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyExceptionModel {
        Type t = typeEnv.getValue(var);
        if(!(t instanceof IntType)){
            throw new MyExceptionModel("release:var not integer");
        }
        return typeEnv;
    }

    @Override
    public String toString() {
        return "release(" +  var + ")";
    }
}

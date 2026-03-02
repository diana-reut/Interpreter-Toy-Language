package model.statement;

import javafx.util.Pair;
import model.exceptions.MyExceptionModel;
import model.programState.IDictionary;
import model.programState.ProgramState;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

import java.util.List;

public record AwaitStmt(String var) implements IStatement{
    @Override
    public ProgramState execute(ProgramState programState) throws MyExceptionModel {
        synchronized (programState.barrierTable()){
            boolean exists = programState.symTable().containsKey(var);
            if(!exists){
                throw new MyExceptionModel("var does not exist");
            }
            Value value = programState.symTable().getValue(var);
            if(!(value instanceof IntValue)){
                throw new MyExceptionModel("the value from the symbol table is not an integer");
            }
            int foundIndex = ((IntValue) value).getValue();
            if(!(programState.barrierTable().containsKey(foundIndex))){
                throw new MyExceptionModel("the index does not exist in the barrier table");
            }
            Pair<Integer, List<Integer>> pair = programState.barrierTable().getValue(foundIndex);
            List<Integer> existingList = pair.getValue();
            int NL = existingList.size();
            int N1 = pair.getKey();
            if(N1 > NL){
                if(existingList.contains(programState.id())){
                    programState.exeStack().push(new AwaitStmt(var));
                }
                else{
                    existingList.add(programState.id());
                    programState.exeStack().push(new AwaitStmt(var));
                }
            }
        }
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyExceptionModel {
        Type type = typeEnv.getValue(var);
        if(!(type instanceof IntType)){
            throw new MyExceptionModel("await->the value is not an integer");
        }
        return typeEnv;
    }

    @Override
    public String toString() {
        return "await(" + var + ")";
    }
}

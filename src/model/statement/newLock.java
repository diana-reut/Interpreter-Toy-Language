package model.statement;

import model.exceptions.MyExceptionModel;
import model.programState.IDictionary;
import model.programState.ILockTable;
import model.programState.ProgramState;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;

public record newLock(String var) implements IStatement {
    @Override
    public ProgramState execute(ProgramState programState) throws MyExceptionModel {
        boolean exists = programState.symTable().containsKey(var);
        if(!exists){
            throw new  MyExceptionModel("The variable "+var+" does not exist");
        }
        var value = programState.symTable().getValue(var);
        if(!(value instanceof IntValue)){
            throw new MyExceptionModel("variable must be declared as an integer");
        }
        ILockTable lockTable = programState.lockTable();
        int newFreeLocation = lockTable.put(-1);
        programState.symTable().update(var, new IntValue(newFreeLocation));
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyExceptionModel {
        Type type = typeEnv.getValue(var);
        if(!(type instanceof IntType)){
            throw new MyExceptionModel("variable must be declared as an integer");
        }
        return typeEnv;
    }

    @Override
    public String toString() {
        return "newLock(" + var + ")";
    }
}

package model.statement;

import model.exceptions.MyExceptionModel;
import model.programState.IDictionary;
import model.programState.ProgramState;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

public record LatchAwaitStmt(String var) implements IStatement{
    @Override
    public ProgramState execute(ProgramState programState) throws MyExceptionModel {
        synchronized (programState.latchTable()) {
            boolean exists = programState.symTable().containsKey(var);
            if(!exists){
                throw new MyExceptionModel("await:value not in sym table");
            }
            Value value = programState.symTable().getValue(var);
            if(!(value instanceof IntValue)){
                throw new  MyExceptionModel("await:value not an integer");
            }
            boolean existsLatch = programState.latchTable().containsKey(((IntValue) value).getValue());
            if(!existsLatch){
                throw new MyExceptionModel("await:value not in latch table");
            }
            if(programState.latchTable().getValue(((IntValue) value).getValue()) != 0){
                programState.exeStack().push(new LatchAwaitStmt(var));
            }
        }
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyExceptionModel {
        Type varType = typeEnv.getValue(var);
        if(!(varType instanceof IntType)){
            throw new MyExceptionModel("countDown:value not an integer");
        }
        return typeEnv;
    }

    @Override
    public String toString() {
        return "await(" + var + ")";
    }
}

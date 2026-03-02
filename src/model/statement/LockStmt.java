package model.statement;

import model.exceptions.MyExceptionModel;
import model.programState.IDictionary;
import model.programState.ProgramState;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

public record LockStmt(String var) implements IStatement {
    @Override
    public synchronized ProgramState execute(ProgramState programState) throws MyExceptionModel {
        synchronized (programState.lockTable()) {
            if (!(programState.symTable().containsKey(var)))
                throw new MyExceptionModel("variable not in sym table");
            Value foundIndex = programState.symTable().getValue(var);
            if (!(foundIndex instanceof IntValue))
                throw new MyExceptionModel("index must be an integer");
            IntValue index = (IntValue) foundIndex;
            boolean existsLock = programState.lockTable().containsKey(index.getValue());
            if (!existsLock) throw new MyExceptionModel("lock table value does not exist");
            int value = programState.lockTable().lookup(index.getValue());
            if (value == -1) {
                programState.lockTable().update(index.getValue(), programState.id());
            } else programState.exeStack().push(new LockStmt(var));
        }
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
        return "lock(" + var + ")";
    }
}

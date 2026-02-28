package model.statement;

import model.exceptions.MyExceptionModel;
import model.programState.IDictionary;
import model.programState.ProgramState;
import model.type.Type;

public record NoOpStmt() implements IStatement{
    @Override
    public ProgramState execute(ProgramState programState) throws MyExceptionModel {
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyExceptionModel {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "no operation";
    }
}

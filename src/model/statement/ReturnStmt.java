package model.statement;

import model.exceptions.MyExceptionModel;
import model.programState.IDictionary;
import model.programState.ProgramState;
import model.type.Type;

public record ReturnStmt() implements IStatement {
    @Override
    public ProgramState execute(ProgramState programState) throws MyExceptionModel {
        programState.symTableStack().pop();
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyExceptionModel {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "return";
    }
}

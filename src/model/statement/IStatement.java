package model.statement;

import model.exceptions.MyExceptionModel;
import model.programState.IDictionary;
import model.programState.ProgramState;
import model.type.Type;

public interface IStatement {
    ProgramState execute(ProgramState programState) throws MyExceptionModel;
    IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyExceptionModel;
}

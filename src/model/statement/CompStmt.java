package model.statement;

import model.exceptions.MyExceptionModel;
import model.programState.IDictionary;
import model.programState.IStack;
import model.programState.ProgramState;
import model.type.Type;

public record CompStmt( IStatement first, IStatement second) implements IStatement {

    @Override
    public ProgramState execute(ProgramState programState) throws MyExceptionModel {
        IStack<IStatement> stack = programState.exeStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyExceptionModel {
        return second.typecheck(first.typecheck(typeEnv));
    }

    @Override
    public String toString() {
        return "(" + first + "; " + second + ")";
    }
}

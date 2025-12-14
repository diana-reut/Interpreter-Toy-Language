package model.statement;

import model.state.ProgramState;
import model.typecheck.ITypeEnvironment;
import model.typecheck.TypeCheckException;

public record CompoundStatement(Statement first, Statement second) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) {
        var stack = state.executionStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    @Override
    public Statement copyStatement() {
        return new  CompoundStatement(first.copyStatement(), second.copyStatement());
    }

    @Override
    public ITypeEnvironment typeCheck(ITypeEnvironment env) throws TypeCheckException {
        return second.typeCheck(first.typeCheck(env));
    }

    @Override
    public String toString() {
        return "(" + first.toString() + "; " + second.toString() + ")";
    }
}

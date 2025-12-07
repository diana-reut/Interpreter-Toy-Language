package model.statement;

import model.state.ProgramState;

public record CompoundStatement(Statement first, Statement second) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) {
        var stack = state.executionStack();
        stack.push(second);
        stack.push(first);
        return state;
    }

    @Override
    public Statement copyStatement() {
        return new  CompoundStatement(first.copyStatement(), second.copyStatement());
    }

    @Override
    public String toString() {
        return "(" + first.toString() + "; " + second.toString() + ")";
    }
}

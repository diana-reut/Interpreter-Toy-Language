package model.statement;

import model.expression.Expression;
import model.state.ProgramState;
import model.typecheck.ITypeEnvironment;
import model.typecheck.TypeCheckException;
import model.value.Value;

public record PrintStatement(Expression expression) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) {
        Value value = expression.evaluate(state.symbolTable(), state.heap());
        if (value == null) {
            throw new RuntimeException("Tried to print a null value!");
        }
        state.output().add(value);
        return null;
    }

    @Override
    public ITypeEnvironment typeCheck(ITypeEnvironment env) throws TypeCheckException {
        expression.typeCheck(env);
        return env;
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }
}

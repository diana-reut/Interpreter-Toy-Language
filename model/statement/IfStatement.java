package model.statement;

import model.expression.Expression;
import model.state.ProgramState;
import model.type.BooleanType;
import model.type.Type;
import model.typecheck.ITypeEnvironment;
import model.typecheck.TypeCheckException;
import model.value.BooleanValue;
import model.value.Value;

public record IfStatement(Expression condition, Statement thenBranch, Statement elseBranch) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) {
        Value result = condition.evaluate(state.symbolTable(), state.heap());
        if (result instanceof BooleanValue booleanValue) {
            if (booleanValue.value()) {
                state.executionStack().push(thenBranch);
            } else {
                state.executionStack().push(elseBranch);
            }
        } else {
            throw new RuntimeException("Condition expression does not evaluate to a boolean.");
        }
        return null;
    }

    @Override
    public ITypeEnvironment typeCheck(ITypeEnvironment env) throws TypeCheckException {
        Type typeExp = condition.typeCheck(env);
        if(!(typeExp instanceof BooleanType)){
            throw new TypeCheckException("Condition expression does not evaluate to a boolean.");
        }
        thenBranch.typeCheck(env.deepCopy());
        elseBranch.typeCheck(env.deepCopy());
        return env;
    }

    @Override
    public String toString() {
        return "(IF(" + condition.toString() + ")THEN(" + thenBranch.toString() +
                ")ELSE(" + elseBranch.toString() + "))";
    }

}

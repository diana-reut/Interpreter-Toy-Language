package model.statement;

import model.expression.Expression;
import model.state.Heap;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.type.BooleanType;
import model.type.Type;
import model.typecheck.ITypeEnvironment;
import model.typecheck.TypeCheckException;
import model.value.BooleanValue;
import model.value.Value;

public record WhileStatement(Expression expression, Statement statement) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) {
        SymbolTable symbolTable = state.symbolTable();
        Heap heap = state.heap();
        Value conditionValue = expression.evaluate(symbolTable, heap);
        if (!conditionValue.getType().equals(new BooleanType())) {
            throw new RuntimeException("While Statement Error: Condition expression " + expression.toString() + " is not a boolean.");
        }
        if (((BooleanValue) conditionValue).value()) {
            // Else, push the body statement and then the While statement back onto the stack.
            state.executionStack().push(this);
            state.executionStack().push(statement);
        }
        return null;
    }

    @Override
    public ITypeEnvironment typeCheck(ITypeEnvironment env) throws TypeCheckException {
        Type type = expression.typeCheck(env);
        if(!(type instanceof BooleanType)) {
            throw new TypeCheckException("While Statement Error: the condition <" + expression.toString() + ">aa is not a boolean");
        }
        statement.typeCheck(env.deepCopy());
        return env;
    }

    @Override
    public String toString() {
        return "while(" + expression + ") " + statement;
    }
}

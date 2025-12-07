package model.statement;

import model.expression.Expression;
import model.state.Heap;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.type.BooleanType;
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
        return state;
    }
}

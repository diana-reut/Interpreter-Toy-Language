package model.statement;

import model.expression.Expression;
import model.state.ProgramState;
import model.statement.exceptions.InvalidTypeException;
import model.value.StringValue;

import java.io.IOException;

public record CloseRFileStatement(Expression expression) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        var value = expression.evaluate(state.symbolTable(),  state.heap());
        if(!(value instanceof StringValue(String filename))) {
            throw new InvalidTypeException("Type must be string");
        }
        state.fileTable().closeFile(filename);
        return state;
    }

    @Override
    public String toString() {
        return "close(" + expression + ')';
    }
}

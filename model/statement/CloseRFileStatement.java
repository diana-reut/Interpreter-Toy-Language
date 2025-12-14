package model.statement;

import model.expression.Expression;
import model.state.ProgramState;
import model.statement.exceptions.InvalidTypeException;
import model.type.StringType;
import model.type.Type;
import model.typecheck.ITypeEnvironment;
import model.typecheck.TypeCheckException;
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
        return null;
    }

    @Override
    public ITypeEnvironment typeCheck(ITypeEnvironment env) throws TypeCheckException {
        Type type = expression.typeCheck(env);
        if(!(type instanceof StringType)) {
            throw new TypeCheckException("File name must be string");
        }
        return env;
    }

    @Override
    public String toString() {
        return "close(" + expression + ')';
    }
}

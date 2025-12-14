package model.statement;

import model.expression.Expression;
import model.state.ProgramState;
import model.statement.exceptions.FileAlreadyOpenException;
import model.statement.exceptions.InvalidTypeException;
import model.value.StringValue;

import java.io.*;

public record OpenRFileStatement(Expression expression) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        var value = expression.evaluate(state.symbolTable(), state.heap());
        if(!(value instanceof StringValue(String filename))) {
            throw new InvalidTypeException("Type must be string");
        }
        if(state.fileTable().isOpen(filename)){
            throw new FileAlreadyOpenException("file already opened");
        }

        BufferedReader bufferReader;
        try {
            bufferReader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        state.fileTable().addOpenFile(filename, bufferReader);
        return null;
    }

    @Override
    public String toString() {
        return "open(" + expression + ')';
    }
}

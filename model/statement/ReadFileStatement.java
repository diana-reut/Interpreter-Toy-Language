package model.statement;

import model.expression.Expression;
import model.state.ProgramState;
import model.statement.exceptions.FileNotOpenException;
import model.statement.exceptions.InvalidTypeException;
import model.type.IntegerType;
import model.type.Type;
import model.value.IntegerValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public record ReadFileStatement(Expression expression, String varName) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) {
        if(!(state.symbolTable().isDefined(varName))
                || !(state.symbolTable().getType(varName) instanceof IntegerType)) {
            throw new InvalidTypeException(varName);
        }
        var value = expression.evaluate(state.symbolTable(), state.heap());
        if(!(value instanceof StringValue(String filename))) {
            throw new InvalidTypeException("Type must be string");
        }
        if(!(state.fileTable().isOpen(filename))){
            throw new FileNotOpenException("file not opened");
        }

        BufferedReader br = state.fileTable().getOpenFile(filename);
        String line;
        try {
            line = br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(line == null){
            state.symbolTable().update(varName, new IntegerValue(0));
        }
        else{
            state.symbolTable().update(varName,new IntegerValue(Integer.parseInt(line)));
        }
        return null;
    }

    @Override
    public String toString() {
        return "readFile(" + expression + ", " + varName + ')';
    }
}

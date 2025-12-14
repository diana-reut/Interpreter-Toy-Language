package model.statement;

import model.expression.Expression;
import model.state.ProgramState;
import model.statement.exceptions.FileNotOpenException;
import model.statement.exceptions.InvalidTypeException;
import model.type.IntegerType;
import model.type.StringType;
import model.type.Type;
import model.typecheck.ITypeEnvironment;
import model.typecheck.TypeCheckException;
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
    public ITypeEnvironment typeCheck(ITypeEnvironment env) throws TypeCheckException {
        Type typeVar = env.getType(varName);
        if(!(typeVar instanceof IntegerType)){
            throw new TypeCheckException("The type for the variable in which we store the read result must be integer");
        }
        Type typeExp = expression.typeCheck(env);
        if(!(typeExp instanceof StringType)){
            throw new TypeCheckException("The name of the file must be a string");
        }
        return env;
    }

    @Override
    public String toString() {
        return "readFile(" + expression + ", " + varName + ')';
    }
}

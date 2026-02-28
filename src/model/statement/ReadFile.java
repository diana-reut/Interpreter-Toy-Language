package model.statement;

import model.exceptions.MyExceptionModel;
import model.expression.IExpression;
import model.programState.IDictionary;
import model.programState.ProgramState;
import model.type.IntType;
import model.type.StringType;
import model.type.Type;
import model.value.IntValue;
import model.value.StringValue;

import java.io.IOException;

public record ReadFile(IExpression expression, String variableName) implements IStatement{
    //where expression is the name of the file and variableName is where we assign the reading to

    @Override
    public ProgramState execute(ProgramState programState) throws MyExceptionModel {
        if(!(programState.symTable().containsKey(variableName))){
            throw new MyExceptionModel("Variable " + variableName + " not found");
        }
        if(!(programState.symTable().getValue(variableName) instanceof IntValue)){
            throw new MyExceptionModel("Variable " + variableName + " is not an integer");
        }
        var value = expression.eval(programState.symTable(), programState.heap());
        if (!(value.getType() instanceof StringType)){
            throw new  MyExceptionModel("Invalid type for read expression");
        }
        if(!(programState.fileTable().containsKey(((StringValue) value).getValue()))){
            throw new  MyExceptionModel("File not known");
        }
        var bufReader = programState.fileTable().getValue(((StringValue) value).getValue());
        try {
            var readValue = bufReader.readLine();
            if(readValue == null){
                programState.symTable().update(variableName, new IntValue(0));
            }
            else{
                programState.symTable().update(variableName, new IntValue(Integer.parseInt(readValue)));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyExceptionModel {
        Type intType = typeEnv.getValue(variableName);
        if(!(intType instanceof IntType)){
            throw new MyExceptionModel("Variable " + variableName + " is not an integer");
        }
        Type typ = expression.typecheck(typeEnv);
        if((typ instanceof StringType)){
            return typeEnv;
        }
        throw new MyExceptionModel("Type is not string");
    }

    @Override
    public String toString() {
        return "read(" + expression + ", " + variableName + ")";
    }
}

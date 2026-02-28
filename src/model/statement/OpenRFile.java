package model.statement;

import model.exceptions.MyExceptionModel;
import model.expression.IExpression;
import model.programState.IDictionary;
import model.programState.ProgramState;
import model.type.StringType;
import model.type.Type;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public record OpenRFile(IExpression expression) implements IStatement{
    @Override
    public ProgramState execute(ProgramState programState) throws MyExceptionModel {
        var value = expression.eval(programState.symTable(), programState.heap());
        if(!(value.getType() instanceof StringType))
            throw new MyExceptionModel("File name must be a string");
        if(programState.fileTable().containsKey(((StringValue) value).getValue()))
            throw new MyExceptionModel("File name already exists");
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(((StringValue) value).getValue()));
        } catch (FileNotFoundException e) {
            throw new MyExceptionModel("file not found");
        }
        programState.fileTable().put(((StringValue) value).getValue(), br);

        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyExceptionModel {
        Type typ = expression.typecheck(typeEnv);
        if((typ instanceof StringType)){
            return typeEnv;
        }
        throw new MyExceptionModel("Type is not string");
    }

    @Override
    public String toString() {
        return "open(" + expression + ")";
    }
}

package model.statement;

import model.exceptions.MyExceptionModel;
import model.expression.IExpression;
import model.programState.IDictionary;
import model.programState.ProgramState;
import model.type.StringType;
import model.type.Type;
import model.value.StringValue;

import java.io.IOException;

public record CloseRFile(IExpression expression) implements IStatement{
    @Override
    public ProgramState execute(ProgramState programState) throws MyExceptionModel {
        var Value = expression.eval(programState.symTable(), programState.heap());
        if(!(Value.getType() instanceof StringType)){
            throw new MyExceptionModel("File must be a string");
        }
        if(!(programState.fileTable().containsKey(((StringValue) Value).getValue()))){
            throw new MyExceptionModel("File does not exist");
        }

        var bufReader = programState.fileTable().getValue(((StringValue) Value).getValue());
        try {
            bufReader.close();
            programState.fileTable().remove(((StringValue) Value).getValue());
        } catch (IOException e) {
            throw new MyExceptionModel(e.getMessage());
        }
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
        return "close(" + expression + ")";
    }
}

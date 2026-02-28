package model.statement;

import model.exceptions.MyExceptionModel;
import model.expression.IExpression;
import model.programState.IDictionary;
import model.programState.ProgramState;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;

public record NewStatement(String variableName, IExpression expr) implements IStatement{

    @Override
    public ProgramState execute(ProgramState programState) throws MyExceptionModel {
        if(!(programState.symTable().containsKey(variableName))){
            throw new MyExceptionModel("Variable "+variableName+" not found");
        }
        if(!(programState.symTable().getValue(variableName) instanceof RefValue)){
            throw new MyExceptionModel("Variable "+variableName+" not of type Ref");
        }
        Value value = expr.eval(programState.symTable(), programState.heap());
        if(!(value.getType().equals(((RefValue) programState.symTable().getValue(variableName)).getLocationType()))){
            throw new MyExceptionModel("Inner type of the ref value is not the same one as for the expression");
        }
        int address = programState.heap().put(value);
        programState.symTable().update(variableName, new RefValue(address, value.getType()));
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyExceptionModel {
        Type typeVar = typeEnv.getValue(variableName);
        Type typeExp = expr.typecheck(typeEnv);
        if(typeVar.equals(new RefType(typeExp))){
            return typeEnv;
        }
        throw new MyExceptionModel("Type mismatch");
    }

    @Override
    public String toString() {
        return "new(" + variableName + ", " + expr + ")";
    }
}

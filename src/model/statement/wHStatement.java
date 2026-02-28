package model.statement;

import model.exceptions.MyExceptionModel;
import model.expression.IExpression;
import model.programState.IDictionary;
import model.programState.ProgramState;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;

public record wHStatement(String variableName, IExpression expr) implements IStatement{
    @Override
    public ProgramState execute(ProgramState programState) throws MyExceptionModel {
        var symTable = programState.symTable();
        var heap = programState.heap();
        if(!(symTable.containsKey(variableName))){
            throw new MyExceptionModel("Variable "+variableName+" not found");
        }
        if(!(symTable.getValue(variableName).getType() instanceof RefType)){
            throw new MyExceptionModel("Type of variable "+variableName+" is not a RefType");
        }
        var refValue = (RefValue) symTable.getValue(variableName);
        if(!(heap.containsKey(refValue.getAddress()))){
            throw new MyExceptionModel("Address of variable "+variableName+" not found in the heap");
        }
        var value = expr.eval(symTable, heap);
        if(!(value.getType().equals(((RefValue) symTable.getValue(variableName)).getLocationType()))){
            throw new MyExceptionModel("Type mismatch");
        }
        heap.update(refValue.getAddress(), value);
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyExceptionModel {
        Type varType = typeEnv.getValue(variableName);
        if(!(varType instanceof RefType)){
            throw new MyExceptionModel("Type of variable "+variableName+" is not a RefType");
        }
        Type exprType = expr.typecheck(typeEnv.deepcopy());
        if(varType.equals(new RefType(exprType))){
            return typeEnv;
        }
        throw new MyExceptionModel("Type mismatch");
    }

    @Override
    public String toString() {
        return "wh(" + variableName + "," + expr + ")";
    }
}

package model.statement;

import model.exceptions.MyExceptionModel;
import model.expression.IExpression;
import model.programState.IDictionary;
import model.programState.ProgramState;
import model.type.Type;
import model.value.Value;

public record AssignStmt(String id, IExpression expression) implements IStatement{
    @Override
    public ProgramState execute(ProgramState programState) throws MyExceptionModel {
        var symTable = programState.symTable();
        var heap = programState.heap();

        if (symTable.containsKey(id)) {
            Value expressionValue = expression.eval(symTable, heap);
            Type expectedType = symTable.getValue(id).getType();

            if(!expectedType.equals(expressionValue.getType())){
                throw new MyExceptionModel("expression type mismatch");
            }
            else{
                symTable.update(id, expressionValue);
            }
        }
        else {
            throw new MyExceptionModel(id + " is not declared");
        }
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyExceptionModel {
        Type typevar = typeEnv.getValue(id);
        Type typeexp = expression.typecheck(typeEnv);
        if(typevar.equals(typeexp)){
            return typeEnv;
        }
        throw new MyExceptionModel("type mismatch");
    }

    @Override
    public String toString() {
        return id + " = " + expression;
    }
}

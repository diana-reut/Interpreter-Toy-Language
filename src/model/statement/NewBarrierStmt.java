package model.statement;

import model.exceptions.MyExceptionModel;
import model.expression.IExpression;
import model.programState.IDictionary;
import model.programState.ProgramState;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

import java.util.ArrayList;

public record NewBarrierStmt(String var, IExpression exp) implements IStatement {
    @Override
    public ProgramState execute(ProgramState programState) throws MyExceptionModel {
        synchronized (programState.barrierTable()){
            Value value = exp.eval(programState.symTable(), programState.heap());
            if(!(value instanceof IntValue)){
                throw new MyExceptionModel("expression must be an integer");
            }
            int val = ((IntValue)value).getValue();
            boolean exists = programState.symTable().containsKey(var);
            if(!exists){
                throw new MyExceptionModel("variable "+var+" does not exist in the symbol table");
            }
            int location = programState.barrierTable().put(val, new ArrayList<>());
            programState.symTable().update(var, new IntValue(location));
        }
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyExceptionModel {
        Type type = typeEnv.getValue(var);
        if(!(type instanceof IntType)){
            throw new MyExceptionModel("newBarrier->the value is not an integer");
        }
        Type type2 = exp.typecheck(typeEnv);
        if(!(type2 instanceof IntType)){
            throw new MyExceptionModel("newBarrier->the expression is not an integer");
        }
        return typeEnv;
    }

    @Override
    public String toString() {
        return "newBarrier(" +var+", "+exp+")";
    }
}

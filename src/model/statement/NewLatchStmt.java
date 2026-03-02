package model.statement;

import model.exceptions.MyExceptionModel;
import model.expression.IExpression;
import model.programState.IDictionary;
import model.programState.ProgramState;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

public record NewLatchStmt(String var, IExpression expression) implements IStatement{
    @Override
    public ProgramState execute(ProgramState programState) throws MyExceptionModel {
        synchronized (programState.latchTable()) {
            Value num1 = expression.eval(programState.symTable(), programState.heap());
            if(!(num1 instanceof IntValue)){
                throw new MyExceptionModel("newLatch:Expression is not an integer");
            }
            boolean exists = programState.symTable().containsKey(var);
            if(!exists){
                throw new MyExceptionModel("newLatch:Variable does not exist in symbol table");
            }
            Value val = programState.symTable().getValue(var);
            if(!(val instanceof IntValue)){
                throw new MyExceptionModel("newLatch:Value is not an integer");
            }
            int location = programState.latchTable().put(((IntValue) num1).getValue());
            programState.symTable().update(var, new IntValue(location));
        }
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyExceptionModel {
        Type varType = typeEnv.getValue(var);
        if(!(varType instanceof IntType)){
            throw new MyExceptionModel("countDown:value not an integer");
        }
        return typeEnv;
    }

    @Override
    public String toString() {
        return "newLatch(" + var + ", " +  expression + ")";
    }
}

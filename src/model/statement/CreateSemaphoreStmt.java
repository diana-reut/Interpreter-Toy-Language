package model.statement;

import javafx.util.Pair;
import model.exceptions.MyExceptionModel;
import model.expression.IExpression;
import model.programState.IDictionary;
import model.programState.ProgramState;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

import java.util.ArrayList;

public record CreateSemaphoreStmt(String var, IExpression exp1) implements IStatement{
    @Override
    public ProgramState execute(ProgramState programState) throws MyExceptionModel {
        synchronized (programState.semaphoreTable()) {
            Value number1Value = exp1.eval(programState.symTable(), programState.heap());
            if(!(number1Value instanceof IntValue)){
                throw new MyExceptionModel("expr not integer");
            }
            int number1 = ((IntValue) number1Value).getValue();
            int location = programState.semaphoreTable().put(number1, new ArrayList<>());
            boolean exists = programState.symTable().containsKey(var);
            if(!exists){
                throw new MyExceptionModel("variable not fount in sym table");
            }
            Value varValue = programState.symTable().getValue(var);
            if(!(varValue instanceof IntValue)){
                throw new MyExceptionModel("var not integer");
            }
            programState.symTable().update(var, new IntValue(location));
        }
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyExceptionModel {
        Type t = typeEnv.getValue(var);
        if(!(t instanceof IntType)){
            throw new MyExceptionModel("create sem:var not integer");
        }
        Type t2 = exp1.typecheck(typeEnv);
        if(!(t2 instanceof IntType)){
            throw new MyExceptionModel("create sem:expr not integer");
        }
        return typeEnv;
    }

    @Override
    public String toString() {
        return "createSemaphore(" + var + ", " + exp1 + ")";
    }
}

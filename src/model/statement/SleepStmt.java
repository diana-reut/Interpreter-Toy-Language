package model.statement;

import model.exceptions.MyExceptionModel;
import model.expression.IExpression;
import model.expression.ValueExpr;
import model.programState.IDictionary;
import model.programState.ProgramState;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

public record SleepStmt(IExpression expression) implements IStatement {
    @Override
    public ProgramState execute(ProgramState programState) throws MyExceptionModel {
        Value value = expression.eval(programState.symTable(), programState.heap());
        if(!(value instanceof IntValue value1)) {
            throw new MyExceptionModel("Wrong expression type");
        }
        int intVal =  value1.getValue();
        if(intVal > 0)
            programState.exeStack().push(new SleepStmt(new ValueExpr(new IntValue(intVal - 1))));
        return null;
    }

    @Override
    public String toString() {
        return "sleep(" + expression + ")";
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyExceptionModel {
        Type type = expression.typecheck(typeEnv);
        if(!(type instanceof IntType)){
            throw new MyExceptionModel("expression is not an integer");
        }
        return typeEnv;
    }
}

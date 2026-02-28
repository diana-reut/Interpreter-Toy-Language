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

public record WaitStmt(IExpression expression) implements IStatement {
    @Override
    public ProgramState execute(ProgramState programState) throws MyExceptionModel {
        Value value = expression.eval(programState.symTable(), programState.heap());
        if(!(value instanceof IntValue val)) {
            throw new MyExceptionModel("Wrong type of expression");
        }
        int intValue = val.getValue();
        if(intValue > 0)
        {
            programState.exeStack().push(
                    new CompStmt(
                            new PrintStmt(new ValueExpr(new IntValue(intValue))),
                            new WaitStmt(new ValueExpr(new IntValue(intValue - 1)))
                    )
            );
        }
        return null;
    }

    @Override
    public String toString() {
        return "wait(" + expression + ")";
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyExceptionModel {
        Type type = expression.typecheck(typeEnv);
        if(!(type instanceof IntType)){
            throw new MyExceptionModel("expression type is not an integer");
        }
        return typeEnv;
    }
}

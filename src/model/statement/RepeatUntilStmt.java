package model.statement;

import model.exceptions.MyExceptionModel;
import model.expression.IExpression;
import model.expression.RelationalExpr;
import model.expression.ValueExpr;
import model.programState.IDictionary;
import model.programState.ProgramState;
import model.type.BoolType;
import model.type.Type;
import model.value.BoolValue;

public record RepeatUntilStmt(IStatement statement, IExpression expression) implements IStatement {

    @Override
    public String toString() {
        return "repeat(" + statement + ") until " + expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws MyExceptionModel {
        var newProgram = new CompStmt(
                statement,
                new WhileStmt(
                        new RelationalExpr(expression, new ValueExpr(new BoolValue(true)), "!="),
                        statement
                )
        );
        programState.exeStack().push(newProgram);
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyExceptionModel {
        statement.typecheck(typeEnv);
        Type type = expression.typecheck(typeEnv);
        if(!(type instanceof BoolType)){
            throw new MyExceptionModel("condition is not a boolean");
        }
        return typeEnv;
    }
}

package model.statement;

import model.exceptions.MyExceptionModel;
import model.expression.IExpression;
import model.programState.IDictionary;
import model.programState.ProgramState;
import model.type.BoolType;
import model.type.Type;

public record ForStmt(IStatement statement1, IExpression exp, IStatement statement2, IStatement statement) implements IStatement{
    @Override
    public ProgramState execute(ProgramState programState) throws MyExceptionModel {
        //for(v=exp1;v<exp2;v=exp3) stmt -> v=exp1;(while(v<exp2) stmt;v=exp3)
        var newStatement = new CompStmt(
                statement1,
                new WhileStmt(
                        exp,
                        new CompStmt(
                                statement,
                                statement2
                        )
                )
        );
        programState.exeStack().push(newStatement);
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyExceptionModel {
        statement1.typecheck(typeEnv.deepcopy());
        Type type1 = exp.typecheck(typeEnv.deepcopy());
        if(!(type1 instanceof BoolType)){
            throw new MyExceptionModel("Condition is not a boolean");
        }
        statement2.typecheck(typeEnv.deepcopy());
        statement.typecheck(typeEnv.deepcopy());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "for(" + statement1 + "; " + exp + "; " + statement2 + ")" + statement;
    }
}

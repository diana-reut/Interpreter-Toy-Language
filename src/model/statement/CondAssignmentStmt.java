package model.statement;

import model.exceptions.MyExceptionModel;
import model.expression.IExpression;
import model.programState.IDictionary;
import model.programState.ProgramState;
import model.type.BoolType;
import model.type.Type;

public record CondAssignmentStmt(String id, IExpression condition, IExpression thenExpr, IExpression elseExpr) implements IStatement {
    @Override
    public ProgramState execute(ProgramState programState) throws MyExceptionModel {
        var newProgram = new IfStmt(
                condition,
                new AssignStmt(id, thenExpr),
                new AssignStmt(id, elseExpr)
        );
        programState.exeStack().push(newProgram);
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyExceptionModel {
        Type typeCond = condition.typecheck(typeEnv);
        if(typeCond.equals(new BoolType())){
            Type typeThen = thenExpr.typecheck(typeEnv);
            Type typeElse = elseExpr.typecheck(typeEnv);
            Type variableType = typeEnv.getValue(id);
            if(!((variableType.equals(typeThen)) &&  (variableType.equals(typeElse)))){
                throw new MyExceptionModel("type mismatch");
            }
            return typeEnv;
        }
        else throw  new MyExceptionModel("Condition type is not bool");
    }

    @Override
    public String toString() {
        return id + "=" + condition + "?" +  thenExpr + ":" + elseExpr;
    }
}

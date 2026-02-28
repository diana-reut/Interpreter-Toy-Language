package model.statement;

import model.exceptions.MyExceptionModel;
import model.expression.IExpression;
import model.expression.RelationalExpr;
import model.programState.IDictionary;
import model.programState.ProgramState;
import model.type.Type;

public record SwitchStmt(IExpression exp, IExpression exp1, IStatement stmt1, IExpression exp2, IStatement stmt2, IStatement stmt3) implements IStatement {
    @Override
    public ProgramState execute(ProgramState programState) throws MyExceptionModel {
        var newProgram = new IfStmt(
                new RelationalExpr(exp, exp1, "=="),
                stmt1,
                new IfStmt(
                        new RelationalExpr(exp, exp2, "=="),
                        stmt2,
                        stmt3
                )
        );
        programState.exeStack().push(newProgram);
        return null;
    }

    @Override
    public String toString() {
        return "switch(" + exp + ") (case " + exp1 + ": " + stmt1 + ") (case " + exp2 + ": " + stmt2 + ") (default: " + stmt3 + ")";
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyExceptionModel {
        Type typeExp = exp.typecheck(typeEnv.deepcopy());
        Type typeExp1 = exp1.typecheck(typeEnv.deepcopy());
        Type typeExp2 = exp2.typecheck(typeEnv.deepcopy());
        if(!(typeExp.equals(typeExp1) || typeExp.equals(typeExp2))) {
            throw new MyExceptionModel("Type mismatch");
        }
        stmt1.typecheck(typeEnv.deepcopy());
        stmt2.typecheck(typeEnv.deepcopy());
        stmt3.typecheck(typeEnv.deepcopy());
        return typeEnv;
    }
}

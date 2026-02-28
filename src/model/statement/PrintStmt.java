package model.statement;

import model.exceptions.MyExceptionModel;
import model.expression.IExpression;
import model.programState.IDictionary;
import model.programState.IList;
import model.programState.ProgramState;
import model.type.Type;
import model.value.Value;

public record PrintStmt(IExpression expression) implements IStatement {

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState programState) throws MyExceptionModel {
        IList<Value> output = programState.output();
        IDictionary<String, Value> symTable = programState.symTable();
        output.add(expression.eval(symTable,  programState.heap()));
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyExceptionModel {
        expression.typecheck(typeEnv);
        return typeEnv;
    }
}

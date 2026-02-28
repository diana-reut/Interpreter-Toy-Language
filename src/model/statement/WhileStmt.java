package model.statement;

import model.exceptions.MyExceptionModel;
import model.expression.IExpression;
import model.programState.IDictionary;
import model.programState.ProgramState;
import model.type.BoolType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;

public record WhileStmt(IExpression expression, IStatement statement) implements IStatement{

    @Override
    public ProgramState execute(ProgramState programState) throws MyExceptionModel {
        Value condition = expression.eval(programState.symTable(), programState.heap());
        if(!(condition instanceof BoolValue)){
            throw new MyExceptionModel("Condition is not a boolean");
        }
        if(((BoolValue) condition).getValue()){
            programState.exeStack().push(new WhileStmt(expression, statement));
            programState.exeStack().push(statement);
        }
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyExceptionModel {
        if(!(expression.typecheck(typeEnv).equals(new BoolType()))){
            throw new MyExceptionModel("Condition is not a boolean");
        }
        statement.typecheck(typeEnv.deepcopy());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "while(" + expression + ") " + statement;
    }
}

package model.statement;

import model.exceptions.MyExceptionModel;
import model.expression.IExpression;
import model.programState.IDictionary;
import model.programState.ProgramState;
import model.type.BoolType;
import model.type.Type;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.Value;

public record IfStmt(IExpression condition, IStatement thenStatement, IStatement elseStatement) implements IStatement {
    @Override
    public ProgramState execute(ProgramState programState) throws MyExceptionModel {
        Value conditionValue = condition.eval(programState.symTable(), programState.heap());
        if(conditionValue instanceof BoolValue) {
            BoolValue boolValue = (BoolValue) conditionValue;
            if(boolValue.getValue()){
                programState.exeStack().push(thenStatement);
            }
            else{
                programState.exeStack().push(elseStatement);
            }
        }
        else if(conditionValue instanceof IntValue) {
            IntValue intValue = (IntValue) conditionValue;
            if(intValue.getValue() != 0){
                programState.exeStack().push(thenStatement);
            }
            else{
                programState.exeStack().push(elseStatement);
            }
        }
        else throw new MyExceptionModel("condition is not of supported type");
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyExceptionModel {
        Type typeCond = condition.typecheck(typeEnv);
        if(typeCond.equals(new BoolType())){
            thenStatement.typecheck(typeEnv.deepcopy());
            elseStatement.typecheck(typeEnv.deepcopy());
            return typeEnv;
        }
        throw  new MyExceptionModel("Condition type is not bool");
    }

    @Override
    public String toString() {
        return "if(" + condition.toString() + ") then (" + thenStatement.toString() + ") else (" + elseStatement.toString() + ")";
    }
}

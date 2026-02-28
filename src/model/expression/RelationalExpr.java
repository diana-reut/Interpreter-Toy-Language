package model.expression;

import model.exceptions.MyExceptionModel;
import model.programState.IDictionary;
import model.programState.IHeapDict;
import model.type.BoolType;
import model.type.IntType;
import model.type.Type;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.Value;

public record RelationalExpr(IExpression ex1, IExpression ex2, String operand) implements IExpression {
    @Override
    public Value eval(IDictionary<String, Value> symTable, IHeapDict<Integer, Value> heap) throws MyExceptionModel {
        Value v1 = ex1.eval(symTable, heap);
        Value v2 = ex2.eval(symTable, heap);
        if(v1.getType() instanceof BoolType) {
            if(v2.getType() instanceof BoolType) {
                if (operand == "==")
                    return new BoolValue(((BoolValue) v1).getValue() == ((BoolValue) v2).getValue());
                else if (operand == "!=")
                    return new BoolValue(((BoolValue) v1).getValue() != ((BoolValue) v2).getValue());
                else throw new MyExceptionModel("Operation not supported for boolean values");
            }
                else throw new MyExceptionModel("both expressions must be of the same type");
        }
        if(!(v1.getType() instanceof IntType)){
            throw new MyExceptionModel("first argument has unsupported type");
        }
        if(!(v2.getType() instanceof IntType)){
            throw new MyExceptionModel("second argument has unsupported type");
        }
        return switch (operand) {
            case ">" -> new BoolValue(((IntValue) v1).getValue() > ((IntValue) v2).getValue());
            case ">=" -> new BoolValue(((IntValue) v1).getValue() >= ((IntValue) v2).getValue());
            case "<" -> new BoolValue(((IntValue) v1).getValue() < ((IntValue) v2).getValue());
            case "<=" -> new BoolValue(((IntValue) v1).getValue() <= ((IntValue) v2).getValue());
            case "==" -> new BoolValue(((IntValue) v1).getValue() == ((IntValue) v2).getValue());
            case "!=" -> new BoolValue(((IntValue) v1).getValue() != ((IntValue) v2).getValue());
            default -> throw new MyExceptionModel("operation not defined");
        };
    }

    @Override
    public String toString() {
        return ex1.toString() + operand + ex2.toString();
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws MyExceptionModel {
        Type t1, t2;
        t1 = ex1.typecheck(typeEnv);
        t2 = ex2.typecheck(typeEnv);
        if(t1.equals(new IntType())) {
            if (t2.equals(new IntType())) {
                return new BoolType();
            }
            throw new MyExceptionModel("Second operand is not int");
        }
        throw new MyExceptionModel("First operand is not int");
    }
}

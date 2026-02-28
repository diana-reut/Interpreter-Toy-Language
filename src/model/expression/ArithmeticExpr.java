package model.expression;


import model.exceptions.MyExceptionModel;
import model.programState.IDictionary;
import model.programState.IHeapDict;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

public record ArithmeticExpr(IExpression ex1, IExpression ex2, String operand) implements IExpression {
    @Override
    public Value eval(IDictionary<String, Value> symTable, IHeapDict<Integer, Value> heap) throws MyExceptionModel {
        Value v1 = ex1.eval(symTable, heap);
        if(!(v1.getType() instanceof IntType)){
            throw new MyExceptionModel("Arithmetic expression requires an integer value");
        }
        Value v2 = ex2.eval(symTable, heap);
        if(!(v2.getType() instanceof IntType)){
            throw new MyExceptionModel("Arithmetic expression requires an integer value");
        }
        IntValue i1 = (IntValue)v1;
        IntValue i2 = (IntValue)v2;
        return switch (operand) {
            case "+" -> new IntValue(i1.getValue() + i2.getValue());
            case "-" -> new IntValue(i1.getValue() - i2.getValue());
            case "*" -> new IntValue(i1.getValue() * i2.getValue());
            case "/" -> {
                if (i2.getValue() == 0)
                    throw new MyExceptionModel("Division by zero");
                yield new IntValue(i1.getValue() / i2.getValue());
            }
            default -> throw new MyExceptionModel("Operation not supported");
        };
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws MyExceptionModel {
        Type t1, t2;
        t1 = ex1.typecheck(typeEnv);
        t2 = ex2.typecheck(typeEnv);
        if(t1.equals(new IntType())) {
            if (t2.equals(new IntType())) {
                return new IntType();
            }
            throw new MyExceptionModel("Second operand is not int");
        }
        throw new MyExceptionModel("First operand is not int");
    }

    @Override
    public String toString() {
        return ex1 + " " +  operand + " " + ex2;
    }
}

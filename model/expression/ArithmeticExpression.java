package model.expression;

import model.state.Heap;
import model.state.SymbolTable;
import model.type.IntegerType;
import model.type.Type;
import model.typecheck.ITypeEnvironment;
import model.typecheck.TypeCheckException;
import model.value.IntegerValue;
import model.value.Value;

/**
 * @param operator 1-plus, 2-minus, 3-star, 4-divide
 */
public record ArithmeticExpression(Expression e1, Expression e2, int operator) implements Expression {

    @Override
    public Value evaluate(SymbolTable symbolTable, Heap heap) throws WrongTypeException{
        Value v1, v2;
        v1 = e1.evaluate(symbolTable, heap);
        if (v1.getType() instanceof IntegerType) {
            v2 = e2.evaluate(symbolTable, heap);
            if (v2.getType() instanceof IntegerType) {
                IntegerValue i1 = (IntegerValue) (v1);
                IntegerValue i2 = (IntegerValue) (v2);
                int value1, value2;
                value1 = i1.value();
                value2 = i2.value();
                if (operator == 1) return new IntegerValue(value1 + value2);
                if (operator == 2) return new IntegerValue(value1 - value2);
                if (operator == 3) return new IntegerValue(value1 * value2);
                if (operator == 4) {
                    if (value2 == 0)
                        throw new DivisionException("Division by zero");
                    return new IntegerValue(value1 + value2);
                }
            } else throw new WrongTypeException("Second operand is not an integer");
        } else throw new WrongTypeException("First operand is not an integer");
        return null;
    }

    @Override
    public Type typeCheck(ITypeEnvironment env) throws TypeCheckException {
        Type type1, type2;
        type1 = e1.typeCheck(env);
        type2 = e2.typeCheck(env);
        if(type1 instanceof IntegerType){
            if(type2 instanceof IntegerType){
                return new IntegerType();
            }
            throw new TypeCheckException("Second operand is not an integer");
        }
        throw new TypeCheckException("First operand is not an integer");
    }
}

package model.expression;

import model.state.Heap;
import model.state.SymbolTable;
import model.type.BooleanType;
import model.type.Type;
import model.typecheck.ITypeEnvironment;
import model.typecheck.TypeCheckException;
import model.value.BooleanValue;
import model.value.Value;

/**
 * @param operator 1-and, 2-or
 */
public record LogicalExpression(Expression e1, Expression e2, int operator) implements Expression {

    @Override
    public Value evaluate(SymbolTable symbolTable, Heap heap) throws WrongTypeException{
        Value v1 = e1.evaluate(symbolTable, heap);
        if (v1.getType() instanceof BooleanType) {
            Value v2 = e2.evaluate(symbolTable, heap);
            if (v2.getType() instanceof BooleanType) {
                BooleanValue b1 = (BooleanValue) v1;
                BooleanValue b2 = (BooleanValue) v2;
                boolean value1 = b1.value();
                boolean value2 = b2.value();
                if (operator == 1) return new BooleanValue(value1 && value2);
                if (operator == 2) return new BooleanValue(value1 || value2);
            } else throw new WrongTypeException("Second operand is not an integer");
        } else throw new WrongTypeException("First operand is not a boolean");
        return null;
    }

    @Override
    public Type typeCheck(ITypeEnvironment env) throws TypeCheckException {
        Type type1,  type2;
        type1 = e1.typeCheck(env);
        type2 = e2.typeCheck(env);
        if (type1 instanceof BooleanType) {
            if (type2 instanceof BooleanType) {
                return new BooleanType();
            }
            throw new TypeCheckException("Second operand is not a boolean");
        }
        throw new TypeCheckException("First operand is not a boolean");
    }
}

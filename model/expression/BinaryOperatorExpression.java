package model.expression;

import model.state.Heap;
import model.state.SymbolTable;
import model.type.BooleanType;
import model.type.IntegerType;
import model.value.BooleanValue;
import model.value.IntegerValue;
import model.value.Value;

public record BinaryOperatorExpression
        (Expression left, Expression right, String operator) implements Expression{

    @Override
    public Value evaluate(SymbolTable symbolTable, Heap heap) throws WrongTypeException {
        Value leftTerm = left.evaluate(symbolTable, heap); //ctrl + alt + v pt a face singur o variabila ca sa stocheze valoarea
        Value rightTerm = right.evaluate(symbolTable, heap); //ctrl + alt + l ca sa formateze frumos liniile

        switch (operator) {
            case "<", "<=", "==", "!=", ">=", ">":
                if (!(leftTerm.getType() instanceof IntegerType) || !(rightTerm.getType() instanceof IntegerType)) {
                    throw new WrongTypeException("Wrong types for operator " + operator);
                }
                var leftValueC = (IntegerValue) leftTerm;
                var rightValueC = (IntegerValue) rightTerm;
                return evaluateRelationalExpression(leftValueC, rightValueC);

            case "+", "-", "*", "/":
                if (!(leftTerm.getType() instanceof IntegerType) || !(rightTerm.getType() instanceof IntegerType)) {
                    throw new WrongTypeException("Wrong types for operator " + operator);
                }
                var leftValue = (IntegerValue) leftTerm;
                var rightValue = (IntegerValue) rightTerm;
                return evaluateArithmeticExpression(leftValue, rightValue); //ctrl + alt + m

            case "&&", "||":
                if(!(leftTerm.getType() instanceof BooleanType) || !(rightTerm.getType() instanceof BooleanType)) {
                    throw new WrongTypeException("Wrong types for operator " + operator);
                }
                var leftValueB = (BooleanValue) leftTerm;
                var rightValueB = (BooleanValue) rightTerm;
                BooleanValue leftValueB1 = evaluateBooleanExpression(leftValueB, rightValueB);
                if (leftValueB1 != null) return leftValueB1;
        }
        throw new IllegalArgumentException("Wrong operator " + operator);
    }

    private Value evaluateRelationalExpression(IntegerValue leftValueC, IntegerValue rightValueC) {
        return switch(operator){
            case ">" -> new BooleanValue(leftValueC.value() > rightValueC.value());
            case ">=" -> new BooleanValue(leftValueC.value() >= rightValueC.value());
            case "<" -> new BooleanValue(leftValueC.value() < rightValueC.value());
            case "<=" -> new BooleanValue(leftValueC.value() <= rightValueC.value());
            case "==" -> new BooleanValue(leftValueC.value() == rightValueC.value());
            case "!=" -> new BooleanValue(leftValueC.value() != rightValueC.value());
            default -> null;
        };
    }

    private BooleanValue evaluateBooleanExpression(BooleanValue leftValueB, BooleanValue rightValueB) {
        return switch (operator) {
            case "&&" -> new BooleanValue(leftValueB.value() && rightValueB.value());
            case "||" -> new BooleanValue(leftValueB.value() || rightValueB.value());
            default -> null;
        };
    }

    private IntegerValue evaluateArithmeticExpression(IntegerValue leftValue, IntegerValue rightValue) {
        return switch (operator) {
            case "+" -> new IntegerValue(leftValue.value() + rightValue.value());
            case "-" -> new IntegerValue(leftValue.value() - rightValue.value());
            case "*" -> new IntegerValue(leftValue.value() * rightValue.value());
            case "/" -> {
                if (rightValue.value() == 0) throw new DivisionException("Division by zero");
                yield new IntegerValue(leftValue.value() / rightValue.value());
            }
            default -> null;
        };
    }

    @Override
    public String toString() {
        return left.toString() + " " + operator + " " + right.toString();
    }
}

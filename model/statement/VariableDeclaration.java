package model.statement;

import model.state.ProgramState;
import model.type.Type;
import model.typecheck.ITypeEnvironment;
import model.typecheck.TypeCheckException;

public record VariableDeclaration(String variableName, Type simpleType) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) {
        var SymbolTable = state.symbolTable();
        if (SymbolTable.isDefined(variableName)) {
            throw new RuntimeException("Variable is already declared!\n");
        }
        SymbolTable.declareVariable(variableName, simpleType);
        SymbolTable.update(variableName, simpleType.getDefaultValue());
        return null;
    }

    @Override
    public String toString() {
        return simpleType.toString() + " " + variableName;
    }

    @Override
    public String format(){
        return simpleType.toString() + " " + variableName;
    }

    @Override
    public ITypeEnvironment typeCheck(ITypeEnvironment env) throws TypeCheckException {
        env.add(variableName, simpleType);
        return env;
    }
}

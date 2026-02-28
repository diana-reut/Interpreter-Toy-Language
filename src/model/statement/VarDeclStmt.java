package model.statement;

import model.exceptions.MyExceptionModel;
import model.programState.IDictionary;
import model.programState.ProgramState;
import model.type.Type;

public record VarDeclStmt(Type type, String name) implements IStatement {
    @Override
    public ProgramState execute(ProgramState programState) throws MyExceptionModel {
        var symTable = programState.symTable();
        if(symTable.containsKey(name)) throw new MyExceptionModel("Variable already exists");
        symTable.put(name, type.getDefaultValue());
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyExceptionModel {
        typeEnv.put(name, type);
        return typeEnv;
    }

    @Override
    public String toString() {
        return type + " " + name;
    }
}

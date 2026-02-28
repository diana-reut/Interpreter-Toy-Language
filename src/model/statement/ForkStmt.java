package model.statement;

import model.exceptions.MyExceptionModel;
import model.programState.ExeStack;
import model.programState.IDictionary;
import model.programState.ProgramState;
import model.type.Type;

public record ForkStmt(IStatement statement) implements IStatement {
    @Override
    public ProgramState execute(ProgramState programState) throws MyExceptionModel {
        var exeStack = new ExeStack();
        exeStack.push(statement);
        var heap = programState.heap();
        var fileTable = programState.fileTable();
        var output = programState.output();
        var symTable = programState.symTable().deepcopy();
        return programState.createNewInstance(symTable, output, exeStack, fileTable, heap);
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyExceptionModel {
        statement.typecheck(typeEnv.deepcopy());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "fork(" + statement + ")";
    }
}

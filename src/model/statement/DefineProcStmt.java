package model.statement;

import model.exceptions.MyExceptionModel;
import model.programState.IDictionary;
import model.programState.ProgramState;
import model.type.Type;

import java.util.List;
import java.util.stream.Collectors;

public record DefineProcStmt(String name, List<String> parameters, IStatement body) implements IStatement {
    @Override
    public ProgramState execute(ProgramState programState) throws MyExceptionModel {
        if(programState.procTable().containsKey(name)){
            throw new MyExceptionModel("function already defined");
        }
        programState.procTable().put(name, parameters, body);
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyExceptionModel {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "procedure " + name + "(" +  parameters.stream().collect(Collectors.joining(",")) + ") " + body;
    }
}

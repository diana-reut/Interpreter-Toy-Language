package model.statement;

import model.state.ProgramState;
import model.typecheck.ITypeEnvironment;
import model.typecheck.TypeCheckException;

public class NoOperation implements Statement{

    @Override
    public ProgramState execute(ProgramState state) {
        return null;
    }

    @Override
    public ITypeEnvironment typeCheck(ITypeEnvironment env) throws TypeCheckException {
        return env;
    }

    @Override
    public String toString(){
        return "No operation";
    }
}

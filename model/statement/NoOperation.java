package model.statement;

import model.state.ProgramState;

public class NoOperation implements Statement{

    @Override
    public ProgramState execute(ProgramState state) {
        return null;
    }

    @Override
    public String toString(){
        return "No operation";
    }
}

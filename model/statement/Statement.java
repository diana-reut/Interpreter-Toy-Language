package model.statement;

import model.state.ProgramState;

public interface Statement {
    ProgramState execute(ProgramState state);
//    default Statement copyStatement() throws CloneNotSupportedException {
//        return (Statement) this.clone();
//    };
    default Statement copyStatement(){
        return this;
    };
    default String format(){
        return this.toString();
    }
}

package model.statement;

import model.state.ProgramState;
import model.typecheck.ITypeEnvironment;
import model.typecheck.TypeCheckException;

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
    ITypeEnvironment typeCheck(ITypeEnvironment env) throws TypeCheckException;
}

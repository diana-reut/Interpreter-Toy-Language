package repository;

import model.programState.ProgramState;
import repository.exceptions.MyExceptionRepository;

import java.util.List;

public interface IRepository {
    void addProgramState(ProgramState program);
    List<ProgramState> getProgramStates();
    void setProgramStates(List<ProgramState> programStates);
    void logProgramStateExec(ProgramState prg) throws MyExceptionRepository;
}

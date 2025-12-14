package repository;

import model.state.ProgramState;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    void addProgramState(ProgramState state);
    //ProgramState getProgramState();
    List<ProgramState> getProgramStates();
    void setProgramStates(List<ProgramState> programStates);
    void logProgramState(ProgramState state);
}

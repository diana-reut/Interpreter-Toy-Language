package repository;

import model.state.ProgramState;

import java.io.IOException;

public interface IRepository {
    void addProgramState(ProgramState state);
    ProgramState getProgramState();

    void logProgramState();
}

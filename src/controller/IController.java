package controller;

import model.programState.ProgramState;

import java.util.List;
import java.util.concurrent.ExecutorService;

public interface IController {
    void addProgramState(ProgramState programState);
    List<ProgramState> removeCompletedPrograms(List<ProgramState> programStates);
    void allStep();
    void oneStepForAllPrg(List<ProgramState> programStates);
    List<ProgramState> getProgramStates();

    ExecutorService getExecutor();
}

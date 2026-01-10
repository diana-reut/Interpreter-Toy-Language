package controller;

import model.state.ProgramState;
import repository.IRepository;

import java.util.List;

public interface IController {
    //ProgramState oneStep(ProgramState state) throws ControllerException;
    void addProgramState(ProgramState state);
    void allStep();
    void oneStepForAllPrograms(List<ProgramState> programStates);
    void runGarbageCollector(List<ProgramState> programStates);
    ProgramState getCurrentState(); // may also display the current program state
    List<ProgramState> removeCompletedPrograms(List<ProgramState> list);

    IRepository getRepository();
}

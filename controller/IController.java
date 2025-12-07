package controller;

import model.state.ProgramState;

public interface IController {
    ProgramState oneStep(ProgramState state) throws ControllerException;
    void addProgramState(ProgramState state);
    void allStep(boolean display);
    ProgramState getCurrentState(); // may also display the current program state
}

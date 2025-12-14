package repository;

import model.state.ProgramState;
import model.statement.exceptions.FileNotOpenException;

import java.io.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Repository implements IRepository {
    private List<ProgramState> programStates = new LinkedList<>();
    private final String logFilePath;

    public Repository(String logFilePath){
        this.logFilePath = logFilePath;
    }

    @Override
    public void addProgramState(ProgramState state) {
        programStates.add(state);
    }

//    @Override
//    public ProgramState getProgramState() {
//        return programStates.getLast();
//    }

    @Override
    public List<ProgramState> getProgramStates() {
        return Collections.unmodifiableList(programStates);
    }

    @Override
    public void setProgramStates(List<ProgramState> programStates) {
        this.programStates = programStates;
    }

    @Override
    public void logProgramState(ProgramState state) {
        //outer closable si de asta putem pune in paranteze la try()
        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(
                new FileWriter(logFilePath, true)))) {
            printWriter.println(state.toString());
        } catch (IOException ex) {
            throw new FileNotOpenException("file not open");
        }
    }
}
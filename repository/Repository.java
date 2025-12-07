package repository;

import model.state.ProgramState;
import model.statement.exceptions.FileNotOpenException;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Repository implements IRepository {
    private final List<ProgramState> programStates = new LinkedList<>();
    private final String logFilePath;

    public Repository(String logFilePath){
        this.logFilePath = logFilePath;
        // try {
        //     //to clear the file
        //     new PrintWriter(logFilePath).close();
        // } catch (FileNotFoundException e) {
        //     throw new RuntimeException(e);
        // }
    }

    @Override
    public void addProgramState(ProgramState state) {
        programStates.add(state);
    }

    @Override
    public ProgramState getProgramState() {
        return programStates.getLast();
    }

    @Override
    public void logProgramState() {
        //outer closable si de asta putem pune in paranteze la try()
        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(
                new FileWriter(logFilePath, true)))) {
            for (ProgramState programState : programStates) {
                printWriter.println(programState.toString());
            }
        } catch (IOException ex) {
            throw new FileNotOpenException("file not open");
        }
    }
}
package repository;

import model.programState.ProgramState;
import repository.exceptions.MyExceptionRepository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Repository implements IRepository {
    private List<ProgramState> programStates = new ArrayList<>();

    public Repository() {
    }

    @Override
    public void addProgramState(ProgramState program) {
        programStates.add(program);
    }

    @Override
    public List<ProgramState> getProgramStates() {
        return Collections.unmodifiableList(programStates);
    }

    @Override
    public void setProgramStates(List<ProgramState> programStates) {
        this.programStates.clear();
        this.programStates.addAll(programStates);
    }


    @Override
    public void logProgramStateExec(ProgramState prg) throws MyExceptionRepository {
        try {
            var logFile = new PrintWriter(new BufferedWriter(new FileWriter("execution.txt", true)));
            logFile.println(prg.toString());
            logFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

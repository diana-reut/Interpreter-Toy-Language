package controller;

import gc.GarbageCollector;
import model.state.ExecutionStack;
import model.state.ProgramState;
import model.statement.Statement;
import model.value.Value;
import repository.IRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Controller implements IController {
    private final IRepository repository;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProgramState oneStep(ProgramState state) throws ControllerException {
        ExecutionStack stack = state.executionStack();
        if (stack.isEmpty()) {
            throw new ControllerException("The stack is empty");
        }
        Statement statement = stack.pop();
        return statement.execute(state);
    }

    @Override
    public void addProgramState(ProgramState state) {
        repository.addProgramState(state);
    }

    @Override
    public void allStep(boolean display) {
        ProgramState programState = repository.getProgramState();
        repository.logProgramState();
        if (display) IO.print(programState);
        while (!programState.executionStack().isEmpty()) {
            oneStep(programState);
            repository.logProgramState();
            //we run the gc
            Map<Integer, Value> currentHeap = programState.heap().getMap();
            Collection<Value> symTableValues = programState.symbolTable().getSymbolTable().values();
            List<Integer> symTableAddr = GarbageCollector.getAddressFromSymTable(symTableValues);
            Map<Integer, Value> newHeap = GarbageCollector.completeGarbageCollector(symTableAddr, currentHeap);
            programState.heap().setMap(newHeap);
            repository.logProgramState();
            //here we print the program state
            if (display) IO.print(programState);
        }
        var output = programState.output().getOutputList();
        IO.print(output.stream()
                .map(Value::toString)
                .collect(Collectors.joining(" ")));
        IO.println();
        IO.println();
    }

    private static void printCurrentState(ProgramState programState) {
        var statements = programState.executionStack().getStatements();
        IO.print("ExeStack={");
        IO.print(statements.stream()
                .map(Statement::toString)
                .collect(Collectors.joining(" | ")));
        IO.print("}");
        IO.println();
        var symbols = programState.symbolTable().getSymbolTable();
        IO.print("SymbolTable={");
        IO.print(symbols.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining(" | ")));
        IO.print("}");
        IO.println();
        var output = programState.output().getOutputList();
        IO.print("Output={");
        IO.print(output.stream()
                .map(Value::toString)
                .collect(Collectors.joining(" | ")));
        IO.print("}");
        IO.println();
        IO.println();
    }

    @Override
    public ProgramState getCurrentState() {
        return repository.getProgramState();
    }
}

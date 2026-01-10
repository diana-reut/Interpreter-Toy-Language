package controller;

import garbageCollector.GarbageCollector;
import model.state.ProgramState;
import model.value.Value;
import repository.IRepository;

import java.util.*;
import java.util.concurrent.*;

public class Controller implements IController {
    private final IRepository repository;
    private ExecutorService executor;
    public Controller(IRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addProgramState(ProgramState state) {
        repository.addProgramState(state);
    }

    @Override
    public void allStep() {
        if (executor == null || executor.isShutdown()) {
            executor = Executors.newFixedThreadPool(2);
        }
        List<ProgramState> prgList = repository.getProgramStates();
        while(!(prgList.isEmpty())){
            runGarbageCollector(prgList);
            prgList.forEach(repository::logProgramState);
            oneStepForAllPrograms(prgList);
            prgList.forEach(repository::logProgramState);
            prgList = repository.getProgramStates();
        }
        executor.shutdownNow();
        repository.setProgramStates(prgList);
    }

    @Override
    public void oneStepForAllPrograms(List<ProgramState> programStates) {
        List<ProgramState> alive = removeCompletedPrograms(programStates);
        repository.setProgramStates(alive);
        if (executor == null || executor.isShutdown()) {
            executor = Executors.newFixedThreadPool(2);
        }
        programStates = new ArrayList<>(alive);
        List<Callable<ProgramState>> tasks = programStates.stream()
                .map(state -> (Callable<ProgramState>) (state::oneStep))
                .toList();
        try {
            var newProgramStates = executor.invokeAll(tasks).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();
            if(!(newProgramStates.isEmpty()))
                programStates.addAll(newProgramStates);
            repository.setProgramStates(programStates);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void runGarbageCollector(List<ProgramState> programStates) {
        // collect all values from symbol tables
        List<Value> symTableValues = programStates.stream()
                .flatMap(state -> state.symbolTable().getSymbolTable().values().stream())
                .toList();
        List<Integer> addresses = GarbageCollector.getAddressFromSymTable(symTableValues);
        //we run the gc
        Map<Integer, Value> newHeap =
                GarbageCollector.completeGarbageCollector(addresses,
                        programStates.get(0).heap().getMap());
        programStates.forEach(state -> state.heap().setMap(newHeap));
    }

    @Override
    public ProgramState getCurrentState() {
        return repository.getProgramStates().getLast();
    }

    @Override
    public List<ProgramState> removeCompletedPrograms(List<ProgramState> list) {
        return list.stream()
                .filter(Objects::nonNull)
                .filter(p -> !(p.executionStack().isEmpty()))
                .toList();
    }

    @Override
    public IRepository getRepository() {
        return repository;
    }
}

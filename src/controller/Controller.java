package controller;

import model.programState.ProgramState;
import model.type.RefType;
import model.value.RefValue;
import model.value.Value;
import repository.IRepository;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller implements IController {
    private IRepository repository;
    private ExecutorService executor;

    public Controller(IRepository repository) {
        this.repository = repository;
        executor = Executors.newFixedThreadPool(2);
    }

    @Override
    public void addProgramState(ProgramState programState) {
        repository.addProgramState(programState);
    }

    @Override
    public List<ProgramState> removeCompletedPrograms(List<ProgramState> programStates) {
        return programStates.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

//    @Override
//    public void oneStepForAllPrg(List<ProgramState> programStates) {
//        List<ProgramState> mutablePrgList = new ArrayList<>(programStates);
//        //moved this from all steps to one step to work with the gui
//        mutablePrgList = removeCompletedPrograms(repository.getProgramStates());
//        repository.setProgramStates(mutablePrgList);
//
//        var before = programStates.getLast().heap().deepcopy();
//        programStates.getLast().heap().setDictionary(garbageCollector(programStates));
//        if(!(before.equals(programStates.getLast().heap().getDictionary()))) {
//            programStates.forEach(programState -> {
//                System.out.println("Garbage collector deleted memory wasteful entries:\n");
//                repository.logProgramStateExec(programState);
//                System.out.println(programState.toString());
//                System.out.println("---------------------------------------");
//            });
//        }
//        //end of copy paste
//        programStates.forEach(programState -> {repository.logProgramStateExec(programState);});
//        List<Callable<ProgramState>> callables = programStates.stream()
//                .map((ProgramState p) ->(Callable<ProgramState>)(p::oneStep))
//                .toList();
//        try {
//            List<ProgramState> newProgramStates = executor.invokeAll(callables).stream()
//                    .map(future -> {
//                        try {
//                            return future.get();
//                        } catch (InterruptedException | ExecutionException e) {
//                            throw new RuntimeException(e);
//                        }
//                    })
//                    .filter(Objects::nonNull)
//                    .toList();
//            mutablePrgList.addAll(newProgramStates);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        programStates.forEach(programState -> {
//            repository.logProgramStateExec(programState);
//            System.out.println(programState.toString());
//            System.out.println("---------------------------------------");
//        });
//        repository.setProgramStates(mutablePrgList);
//    }
    @Override
    public void oneStepForAllPrg(List<ProgramState> programStates) {
        List<ProgramState> activePrograms = removeCompletedPrograms(programStates);
        if (activePrograms.isEmpty()) {
            repository.setProgramStates(activePrograms);
            return;
        }

        //garbage collector added here because it only ran in the all step
        Map<Integer, Value> newHeap = garbageCollector(activePrograms);
        activePrograms.get(0).heap().setDictionary(newHeap);

        activePrograms.forEach(p -> repository.logProgramStateExec(p));

        List<Callable<ProgramState>> callables = activePrograms.stream()
                .map(p -> (Callable<ProgramState>)(p::oneStep))
                .collect(Collectors.toList());

        try {
            List<ProgramState> newThreads = executor.invokeAll(callables).stream()
                    .map(future -> {
                        try { return future.get(); }
                        catch (InterruptedException | ExecutionException e) { throw new RuntimeException(e); }
                    })
                    .filter(Objects::nonNull)
                    .toList();
            activePrograms.addAll(newThreads);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        activePrograms.forEach(p -> {
            repository.logProgramStateExec(p);
            System.out.println(p.toString());
        });
        repository.setProgramStates(activePrograms);
    }

    @Override
    public List<ProgramState> getProgramStates() {
        return repository.getProgramStates();
    }

    Map<Integer, Value> garbageCollector(List<ProgramState> programStates){
        var symTables = programStates.stream()
                .map(ProgramState::symTable)
                .toList();
        var heap = programStates.getLast().heap().getDictionary();
        List<Integer> addresses = new ArrayList<>();
        for(var symTable : symTables){
            var newAddresses = getAddrFromSymTable(symTable.getDictionary().values());
            addresses.addAll(newAddresses);
        }
        List<Integer> allAddresses = getReachableAddr(addresses, heap);
        return heap.entrySet().stream()
                .filter(e->allAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        var addresses = symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v->{RefValue v1 = (RefValue) v; return v1.getAddress();})
                .collect(Collectors.toList());

        return addresses;
    }

    List<Integer> getReachableAddr(List<Integer> addresses, Map<Integer,Value> heap){
        boolean found = true;
        while (found) {
            found = false;
            List<Integer> foundAddresses = new ArrayList<>();
            for (int addr : addresses) {
                if(heap.containsKey(addr)) {
                    Value value = heap.get(addr);
                    if (value.getType() instanceof RefType) {
                        var newAddr = ((RefValue) value).getAddress();
                        if ((!(foundAddresses.contains(newAddr)) && !(addresses.contains(newAddr)))) {
                            foundAddresses.add(newAddr);
                            found = true;
                        }
                    }
                }
            }
            addresses.addAll(foundAddresses);
        }
        return addresses;
    }

    @Override
    public void allStep() {
        List<ProgramState> programStates = removeCompletedPrograms(repository.getProgramStates());
        programStates.forEach(programState -> {
            repository.logProgramStateExec(programState);
            System.out.println(programState.toString());
            System.out.println("---------------------------------------");
        });
        while(!programStates.isEmpty()){
            var before = programStates.getLast().heap().deepcopy();
            programStates.getLast().heap().setDictionary(garbageCollector(programStates));
            if(!(before.equals(programStates.getLast().heap().getDictionary()))) {
                programStates.forEach(programState -> {
                    System.out.println("Garbage collector deleted memory wasteful entries:\n");
                    repository.logProgramStateExec(programState);
                    System.out.println(programState.toString());
                    System.out.println("---------------------------------------");
                });
            }
            oneStepForAllPrg(programStates);
            programStates = removeCompletedPrograms(repository.getProgramStates());
        }
        executor.shutdownNow();
        repository.setProgramStates(programStates);
    }

    @Override
    public ExecutorService getExecutor() {
        return executor;
    }
}

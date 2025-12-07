package gc;

import model.value.RefValue;
import model.value.Value;

import java.util.*;
import java.util.stream.Collectors;

public class GarbageCollector {
    public static List<Integer> getAddressFromSymTable(Collection<Value> symTable) {
        return symTable.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue) v).getAddress())
                .collect(Collectors.toList());
    }

    public static Set<Integer> getReachableAddresses(Set<Integer> currentRoots, Map<Integer, Value> heap) {
        Set<Integer> newRoots = new HashSet<>();

        for (Integer rootAddr : currentRoots) {
            if (heap.containsKey(rootAddr)) {
                Value heapContent = heap.get(rootAddr);
                if (heapContent instanceof RefValue) {
                    int nestedAddr = ((RefValue) heapContent).getAddress();
                    if (nestedAddr != 0) {
                        newRoots.add(nestedAddr);
                    }
                }
            }
        }
        if (newRoots.isEmpty() || currentRoots.containsAll(newRoots)) {
            return currentRoots;
        } else {
            currentRoots.addAll(newRoots);
            return getReachableAddresses(currentRoots, heap);
        }
    }

    public static Map<Integer, Value> completeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap) {
        Set<Integer> reachableAddresses = getReachableAddresses(new HashSet<>(symTableAddr), heap);
        return heap.entrySet().stream()
                .filter(e -> reachableAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}

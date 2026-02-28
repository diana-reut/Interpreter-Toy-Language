package model.programState;

import java.util.Stack;

public interface IStack<T> {
    void push(T obj);
    T pop();
    Stack<T> getStack();
    boolean isEmpty();
}

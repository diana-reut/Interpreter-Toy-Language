package model.programState;


import model.statement.IStatement;
import model.value.Value;

import java.util.ListIterator;
import java.util.Stack;

public class ExeStack implements IStack<IStatement>{
    Stack<IStatement> stack = new Stack<IStatement>();

    @Override
    public void push(IStatement statement) {
        stack.push(statement);
    }

    @Override
    public IStatement pop() {
        return stack.pop();
    }

    @Override
    public Stack<IStatement> getStack() {
        return stack;
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("  Execution Stack:\n");
        ListIterator<IStatement> it = stack.listIterator(stack.size());
        while (it.hasPrevious()) {
            sb.append(it.previous().toString()).append("\n");
        }
        return sb.toString();
    }
}

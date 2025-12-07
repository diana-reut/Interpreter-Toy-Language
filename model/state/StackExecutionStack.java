package model.state;

import model.statement.Statement;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class StackExecutionStack implements ExecutionStack {
    private final List<Statement> stack = new LinkedList<>();

    @Override
    public void push(Statement statement) {
        stack.addFirst(statement);
    }

    @Override
    public Statement pop() {
        if(stack.isEmpty()){
            throw new IsEmptyException("Stack is empty");
        }
        return stack.removeFirst();
    }


    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String format() {
        StringBuilder sb = new StringBuilder("Execution stack:\n");
        for(Statement statement : stack){
            sb.append(statement);
        }
        return sb.toString();
    }

    @Override
    public List<Statement> getStatements() {
        return Collections.unmodifiableList(stack);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ExeStack:\n");
        for (Statement statement : stack){
            sb.append(statement).append('\n');
        }
        sb.append("\n");
        return sb.toString();
    }
}

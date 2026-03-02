package model.statement;

import javafx.util.Pair;
import model.exceptions.MyExceptionModel;
import model.expression.IExpression;
import model.programState.IDictionary;
import model.programState.ProgramState;
import model.programState.SymTable;
import model.type.Type;
import model.value.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record CallStmt(String name, List<IExpression> sentParameters) implements IStatement{
    @Override
    public ProgramState execute(ProgramState programState) throws MyExceptionModel {
        boolean exists = programState.procTable().containsKey(name);
        if(!exists){
            throw new MyExceptionModel("The procedure " + name + " does not exist");
        }
        Pair<List<String>, IStatement> value = programState.procTable().getValue(name);
        List<String> actualParameters = value.getKey();
        IStatement body = value.getValue();
        List<Value> valuesOfParameters = new ArrayList<>();
        for(var param : sentParameters){
            valuesOfParameters.add(param.eval(programState.symTable(), programState.heap()));
        }
        var newSymTable = new SymTable();
        for(int i = 0; i < actualParameters.size(); i++){
            newSymTable.put(actualParameters.get(i), valuesOfParameters.get(i) );
        }
        programState.symTableStack().push(newSymTable);

        programState.exeStack().push(new ReturnStmt());
        programState.exeStack().push(body);
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyExceptionModel {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "call " + name + "(" +  sentParameters.stream().map(Object::toString).collect(Collectors.joining(",")) + ")";
    }
}

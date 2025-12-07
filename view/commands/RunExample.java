package view.commands;

import controller.IController;

public class RunExample extends Command {
    private final IController ctr;

    public RunExample(String key, String desc,IController ctr){
        super(key, desc);
        this.ctr=ctr;
    }

    @Override
    public void execute() {
        try {
            ctr.allStep(true); // Run until completion
        } catch (Exception e) {
            System.err.println("Error during execution: " + e.getMessage());
        }
    }
}
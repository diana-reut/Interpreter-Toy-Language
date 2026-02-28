package view.command;

import controller.IController;

public class RunExample extends Command {
    private final IController controller;
    public RunExample(String key, String description, IController controller) {
        super(key, description);
        this.controller = controller;
    }
    @Override
    public void execute() {
        try {
            controller.allStep();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

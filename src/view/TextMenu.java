package view;

import view.command.Command;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private Map<String, Command> commands;
    public TextMenu(){
        commands = new HashMap<>();
    }
    public void addCommand(Command command){
        commands.put(command.getKey(), command);
    }
    private void printMenu(){
        commands.values().stream()
                .sorted(Comparator.comparingInt(cmd -> Integer.parseInt(cmd.getKey())))
                .forEach(command -> {
                    String line = String.format("%4s: %s", command.getKey(), command.getDescription());
                    System.out.println(line);
                });
    }
    public void show(){
        Scanner scanner = new Scanner(System.in);
        while(true){
            printMenu();
            IO.println("Input the option: ");
            String key = scanner.nextLine();
            Command command = commands.get(key);
            if(command == null){
                IO.println("Invalid option\n");
                continue;
            }
            command.execute();
        }
    }
}

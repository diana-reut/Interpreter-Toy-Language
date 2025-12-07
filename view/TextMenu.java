package view;

import view.commands.Command;

import java.util.*;

public class TextMenu {
    private Map<String, Command> commands;
    public  TextMenu(){ commands=new HashMap<>(); }
    public void addCommand(Command c){ commands.put(c.getKey(),c);}
    private void printMenu(){
        List<String> sortedKeys = new ArrayList<>(commands.keySet());
        sortedKeys.sort(Comparator.comparingInt(Integer::parseInt));
        for(String key : sortedKeys){
            Command com = commands.get(key);
            String line = String.format("%4s : %s", com.getKey(), com.getDescription());
            System.out.println(line);
        }
    }
    public void show(){
        Scanner scanner=new Scanner(System.in);
        while(true){
            printMenu();
            System.out.printf("Input the option: ");
            String key=scanner.nextLine();
            Command com=commands.get(key);
            if (com==null){
                System.out.println("Invalid Option");
                continue;  }
            com.execute();
        }
    }
}

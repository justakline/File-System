import Directories.AbstractDirectory;
import Directories.SingleDirectory;
import Files.FileOS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        AbstractDirectory directory = new SingleDirectory("myDir", "justa");
        FileOS file = new FileOS("h", "justa", "write", "exe");
        directory.add(file);
        Scanner scanner  = new Scanner(System.in);
        String command = "";
        List<String> arguments = new ArrayList<>();
        do {
            String line = scanner.nextLine();
            command = createCommand(line);
            arguments = processArguments(line);
            System.out.println(command);
            System.out.println(arguments);
            executeCommand(directory, command, arguments);
        } while(!command.equals("exit"));


    }

    private static List<String> processArguments(String line) {
        //no args
        if(!line.contains(" "))
            return new ArrayList<>();
        //Args, split along the spaces, and remove the first one because that is the command
        else {
            ArrayList<String> temp =new ArrayList<>(Arrays.asList(line.split(" ")));
            temp.remove(0);
            return temp;
        }
    }

    public static void executeCommand(AbstractDirectory directory, String command, List<String> arguments){

        //Split into commands without arguments are with arguments
        if(arguments.size() == 0){
            switch(command) {
                case "ls": //list all
                    System.out.println(directory.listDirectory());
                    break;


                default:
                    System.out.println("not a valid command");

            }
        }else{

            switch(command){
                case "rn":
                    directory.changeOwner(directory.owner, arguments.get(0) );
                    System.out.println(directory.listDirectory());
                    break;
                case "touch": //add new file in current directory

                    if(directory.find(arguments.get(0)) == null){
                        String name = arguments.get(0).split("[.]")[0];
                        String type = arguments.get(0).split("[.]")[1];
                        directory.add(new FileOS(name, directory.owner, "read", type));
                        break;
                    }
                default:
                    System.out.println("not a valid command");
            }
        }

    }

    //If its more than one word(with arguements), split it, else its just a one word command like ls
    public static String createCommand(String line){
        if(line.contains(" ")){
            return line.substring(0, line.indexOf(" "));
        }
        return line;
    }
}

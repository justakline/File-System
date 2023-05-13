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
//        FileOS file = new FileOS("h", "justa", "write", "exe");
        directory.add("h.exe");
        Scanner scanner = new Scanner(System.in);
        String command = "";
        List<String> arguments = new ArrayList<>();
        do {
            String line = scanner.nextLine();
            command = createCommand(line);
            arguments = processArguments(line);
            System.out.println(command + arguments);
            try{
                executeCommand(directory, command, arguments);
            }catch(Exception e){
                if(e instanceof NullPointerException){
                    System.out.println("File not found");
                } else if(e instanceof IndexOutOfBoundsException){
                    System.out.println("Not the right amount of arguments");
                }

                else {
                    System.out.println(e.toString());
                }
            }

        } while (!command.equals("exit"));


    }

    private static List<String> processArguments(String line) {
        //no args
        if (!line.contains(" "))
            return new ArrayList<>();
            //Args, split along the spaces, and remove the first one because that is the command
        else {
            ArrayList<String> temp = new ArrayList<>(Arrays.asList(line.split(" ")));
            temp.remove(0);
            return temp;
        }
    }

    public static void executeCommand(AbstractDirectory directory, String command, List<String> arguments) {


        switch (command) {
            case "ls": // no args      list all
                System.out.println(directory.listDirectory());
                break;

            case "rn": //oldfile, newfile
//                if(handleFIND(directory, arguments)!= null)
                    handleRN(directory, arguments);
//                else
                break;
            case "rm"://filename
                handleRM(directory, arguments);
                break;
            case "touch": //newfilename
                handleTOUCH(directory, arguments);
                break;
            case "find"://filename
                System.out.println(handleFIND(directory, arguments) != null);
                break;
            case "open"://filename
                handleFIND(directory, arguments).openFile();
                System.out.println("Opened " + arguments.get(0));
                break;
            case "close": //filename
                handleFIND(directory, arguments).closeFile();
                System.out.println("Closed " + arguments.get(0));
                break;
            case "chown"://filemame, newowner
                handleFIND(directory, arguments).setOwner(arguments.get(1));
                System.out.println("Changed ownership of " + arguments.get(0) + " to " + arguments.get(1));
                break;
            case "chmod": //filename, permission
                handleCHMOD(directory, arguments);
                break;
            case "read": //filename, lineindex
                handleREAD(directory,arguments);
                break;
            case "write": //filename, lineindex, newLine
                handleFIND(directory, arguments).write(Integer.valueOf(arguments.get(1)), arguments.get(2));
                System.out.println("The new line is " + handleFIND(directory, arguments).read(Integer.valueOf(arguments.get(1))));
                break;
            default:
                handleFIND(directory, arguments).openFile();
                System.out.println("not a valid command");
        }

    }

    //If its more than one word(with arguements), split it, else its just a one word command like ls
    public static String createCommand(String line) {
        if (line.contains(" ")) {
            return line.substring(0, line.indexOf(" "));
        }
        return line;
    }

    public static void handleRN(AbstractDirectory directory, List<String> arguments) {
        System.out.println(handleFIND(directory, arguments));
        //Rename the main directory
        if (arguments.size() == 1) {
            directory.rename(arguments.get(0));
            System.out.println("Directory Renamed to " + directory.getName());
            //If the file is alreadyu there, rename it
        } else if (handleFIND(directory, arguments) != null) {
            handleFIND(directory, arguments).rename(arguments.get(1));
            System.out.println("Renamed file to " + arguments.get(1));
        } else {
            System.out.println("Could not find file");
        }


    }

    public static void handleTOUCH(AbstractDirectory directory, List<String> arguments) {
        if (handleFIND(directory, arguments) == null) {
            directory.add(arguments.get(0));
        }
    }

    public static FileOS handleFIND(AbstractDirectory directory, List<String> arguments) {
        return directory.find(arguments.get(0));
    }

    public static void handleRM(AbstractDirectory directory, List<String> arguments) {
        if (handleFIND(directory, arguments) != null) {
            directory.remove(arguments.get(0));
            System.out.println("Removed file");
        } else {
            System.out.println("File not found");
        }

    }

    public static void handleCHMOD(AbstractDirectory directory, List<String> arguments) {
        if (!arguments.get(1).equals("read") && !arguments.get(1).equals("write")) {
            System.out.println("Only allowed to change permissions to read or write");
        } else {
            handleFIND(directory, arguments).setPermission(arguments.get(1));
            System.out.println("Changed modification of " + arguments.get(0) + " to " + arguments.get(1));
        }
    }

    public static void handleREAD(AbstractDirectory directory, List<String> arguments) {


        if (arguments.size() == 1) {//Read the entire file
            String line = handleFIND(directory, arguments).read();
            System.out.println(line);
        } else {//Read a linei
            if ((handleFIND(directory, arguments).getBody()).size() < Integer.valueOf(arguments.get(1))) {// Must not be out of bounds
                System.out.println("File is too small, no line there. Try a smaller line value");
            }else{
                String line = handleFIND(directory, arguments).read(Integer.valueOf(arguments.get(1)));
                System.out.println(line);
            }
        }


    }


}

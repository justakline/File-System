import Directories.AbstractDirectory;
import Directories.SingleDirectory;
import Directories.TreeDirectory;
import Directories.TwoLevelDirectory;
import Files.FileOS;

import java.util.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        AbstractDirectory directory = new SingleDirectory("root", "root");
//        FileOS file = new FileOS("h", "justa", "write", "exe");
//        directory.add("h.exe");
        Scanner scanner = new Scanner(System.in);
        String command = "";
        List<String> arguments = new ArrayList<>();
        do {
            String line = scanner.nextLine();
            command = createCommand(line);
            arguments = processArguments(line);
            try{
                executeCommand(directory, command, arguments);
            }catch(Exception e){
                if(e instanceof NullPointerException){
                    System.out.println("File not found");
                } else if(e instanceof IndexOutOfBoundsException){
                    System.out.println("Not the right amount of arguments ");

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


        if (directory instanceof SingleDirectory) {
            handleSingleDirectory(directory, command, arguments);
        } else if (directory instanceof TwoLevelDirectory) {
            handleTwoTierDirectory(directory, command, arguments);
        }else if(directory instanceof TreeDirectory){
            handleTreeDirectory(directory, command, arguments);
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

        if (arguments.size() == 1) {  //Rename the main directory
            directory.rename(arguments.get(0));
            System.out.println("Directory Renamed to " + directory.getName());
            //If the file is alreadyu there, rename it
        } else if (handleFIND(directory, arguments) != null) {//found a file
            handleFIND(directory, arguments).rename(arguments.get(1));
            System.out.println("Renamed file to " + arguments.get(1));
        } else if(!arguments.get(0).contains(".")){//if its a folder
            ((TwoLevelDirectory)directory).rename(arguments.get(0), arguments.get(1)); //get the directory and rename it
            System.out.println("Renamed directory to " + arguments.get(1));
        }else{
            System.out.println("Could not find" + arguments.get(0));
        }


    }

    public static void handleTOUCH(AbstractDirectory directory, List<String> arguments) {
        if (handleFIND(directory, arguments) == null) {
            directory.add(arguments.get(0));
        }else {
            System.out.println("Already a file in there or trying to add a file where it's not possible");
        }
    }

    public static FileOS handleFIND(AbstractDirectory directory, List<String> arguments) { // Finds Files
        if (arguments.get(0).contains("/")){ //we have a path
            String dir = arguments.get(0).split("/")[0];
            String file = arguments.get(0).split("/")[1];
           return ((TwoLevelDirectory) directory).findDir(dir).find(file);
        }
        return directory.find(arguments.get(0));
    }

    public static void handleRM(AbstractDirectory directory, List<String> arguments) {
        if (handleFIND(directory, arguments) != null) {//look for file and if its there then remove it
            directory.remove(arguments.get(0));
            System.out.println("Removed " + arguments.get(0));
        } else if (!arguments.get(0).contains(".")) {//If it is just a directory then remove it
            directory.remove(arguments.get(0));
        } else {
            System.out.println(arguments.get(0) + " not found");
        }

    }

    public static void handleCHMOD(AbstractDirectory directory, List<String> arguments) {
        if (!arguments.get(1).equals("read") && !arguments.get(1).equals("write")) {
            System.out.println("Only allowed to change permissions to read or write");
        } else {
            if (arguments.get(0).contains(".")){
                handleFIND(directory, arguments).setPermission(arguments.get(1));
            }else

            System.out.println("Changed modification of " + arguments.get(0) + " to " + arguments.get(1));
        }
    }

    public static void handleREAD(AbstractDirectory directory, List<String> arguments) {

        if(handleFIND(directory, arguments).isOpen()) {
            if (arguments.size() == 1) {//Read the entire file
                String line = handleFIND(directory, arguments).read();
                System.out.println(line);
            } else {//Read at lines
                if ((handleFIND(directory, arguments).getBody()).size() < Integer.valueOf(arguments.get(1))) {// Must not be out of bounds
                    System.out.println("File is too small, no line there. Try a smaller line value");
                } else {
                    String line = handleFIND(directory, arguments).read(Integer.valueOf(arguments.get(1)));
                    System.out.println(line);
                }
            }
        }else{
            System.out.println("File not open");
        }

    }

    //fromPath toPath || fromName toPath || fromPath to



    public static void handleSingleDirectory(AbstractDirectory directory, String command, List<String> arguments){
        switch (command) {
            case "ls": // no args      list all
                System.out.println(directory.listDirectory());
                break;

            case "rn": //oldfile, newfile
                handleRN(directory, arguments);
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
                handleREAD(directory, arguments);
                break;
            case "write": //filename, lineindex, newLine
                handleFIND(directory, arguments).write(Integer.valueOf(arguments.get(1)), arguments.get(2));
                System.out.println("The new line is " + handleFIND(directory, arguments).read(Integer.valueOf(arguments.get(1))));
                break;

            default:
                System.out.println("not a valid command");
        }
    }



    public static void handleTwoTierDirectory(AbstractDirectory directory, String command, List<String> arguments) {
        switch (command) {
            case "ls": // no args      list all
                LinkedList<SingleDirectory> current = ((TwoLevelDirectory)directory).getCurrent();
                if (current == null){ // not in a particular directory
                    System.out.println(((TwoLevelDirectory) directory).listAll());
                }else{
                    System.out.println(current.get(0).listDirectory());
                }
                break;

            case "rn": //oldfile, newfile
                handleRN(directory, arguments);
                break;
            case "rm"://name
                handleRM(directory, arguments);
                break;
            case "touch": //newfilename
                handleTOUCH(directory, arguments);
                break;
            case "find"://filename
                System.out.println(handleFIND(directory, arguments) != null);
                break;
            case "open"://name
                handleFIND(directory, arguments).openFile();
                System.out.println("Opened " + arguments.get(0));
                break;
            case "close": //name
                handleFIND(directory, arguments).closeFile();
                System.out.println("Closed " + arguments.get(0));
                break;
            case "chown"://name, newowner
                handleFIND(directory, arguments).setOwner(arguments.get(1));
                System.out.println("Changed ownership of " + arguments.get(0) + " to " + arguments.get(1));
                break;
            case "chmod": //filename, permission
                handleCHMOD(directory, arguments);
                break;
            case "read": //filename, lineindex
                handleREAD(directory, arguments);
                break;
            case "write": //filename, lineindex, newLine
                if(String.valueOf(handleFIND(directory, arguments).getPermission()).equals("write")){
                    handleFIND(directory, arguments).write(Integer.valueOf(arguments.get(1)), arguments.get(2));
                    System.out.println("The new line is " + handleFIND(directory, arguments).read(Integer.valueOf(arguments.get(1))));
                }else{
                    System.out.println("This file has read permissions");
                }

                break;
            case "mkdir"://dirname
                if(!arguments.get(0).contains("."))// Not a file,
                    ((TwoLevelDirectory)directory).add(arguments.get(0));
                break;
            case "cd..": //
                LinkedList<SingleDirectory> current1 = ((TwoLevelDirectory)directory).getCurrent();
                if (current1 == null) { // not in a particular directory
                    System.out.println("Already at the highest directory");
                }else{
                    ((TwoLevelDirectory)directory).setCurrent(null);
                }
                break;
            case "cd": //dirname
                ((TwoLevelDirectory)directory).changeDirectory(arguments.get(0));
                break;

            case "mv":// fromName toDir || fromPath toDir
                ((TwoLevelDirectory)directory).move(arguments.get(0), arguments.get(1));
                    break;
            case "cp":// fromName toDir || fromPath toDir
                ((TwoLevelDirectory)directory).copy(arguments.get(0), arguments.get(1));
                break;
            default:
                System.out.println("not a valid command");
        }
    }
    public static void handleTreeDirectory(AbstractDirectory directory, String command, List<String> arguments) {
        switch (command) {
            case "ls": // no args      list all
                System.out.println(directory.listDirectory());
                break;

            case "rn": //oldfile, newfile
                handleRN(directory, arguments);
                break;
            case "rm"://name
                handleRM(directory, arguments);
                break;
//            case "touch": //newfilename
//                handleTOUCH(directory, arguments);
//                break;
//            case "find"://filename
//                System.out.println(handleFIND(directory, arguments) != null);
//                break;
//            case "open"://name
//                handleFIND(directory, arguments).openFile();
//                System.out.println("Opened " + arguments.get(0));
//                break;
//            case "close": //name
//                handleFIND(directory, arguments).closeFile();
//                System.out.println("Closed " + arguments.get(0));
//                break;
//            case "chown"://name, newowner
//                handleFIND(directory, arguments).setOwner(arguments.get(1));
//                System.out.println("Changed ownership of " + arguments.get(0) + " to " + arguments.get(1));
//                break;
//            case "chmod": //filename, permission
//                handleCHMOD(directory, arguments);
//                break;
//            case "read": //filename, lineindex
//                handleREAD(directory, arguments);
//                break;
//            case "write": //filename, lineindex, newLine
//                if(String.valueOf(handleFIND(directory, arguments).getPermission()).equals("write")){
//                    handleFIND(directory, arguments).write(Integer.valueOf(arguments.get(1)), arguments.get(2));
//                    System.out.println("The new line is " + handleFIND(directory, arguments).read(Integer.valueOf(arguments.get(1))));
//                }else{
//                    System.out.println("This file has read permissions");
//                }
//
//                break;
            case "mkdir"://dirname
                if(!arguments.get(0).contains("."))// Not a file,
                    ((TreeDirectory)directory).add(arguments.get(0));
                break;
            case "cd..": //

                ((TreeDirectory)directory).changeDirectoryBack();

                break;
            case "cd": //dirname
                ((TreeDirectory)directory).changeDirectory(arguments.get(0));
                break;
            case "all":
                System.out.println(((TreeDirectory)directory).listAll());
                break;
//            case "mv":// fromName toDir || fromPath toDir
//                ((TwoLevelDirectory)directory).move(arguments.get(0), arguments.get(1));
//                break;
//            case "cp":// fromName toDir || fromPath toDir
//                ((TwoLevelDirectory)directory).copy(arguments.get(0), arguments.get(1));
//                break;
            default:
                System.out.println("not a valid command");
        }
    }
}

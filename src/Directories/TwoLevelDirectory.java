package Directories;

import Files.FileOS;

import java.util.ArrayList;
import java.util.LinkedList;


public class TwoLevelDirectory extends AbstractDirectory<SingleDirectory> {


    LinkedList<SingleDirectory> current;

    public TwoLevelDirectory(String name, String owner) {
        this.name = name;
        this.owner = owner;
        this.directory = new ArrayList<LinkedList<SingleDirectory>>();// Arraylist -> Users with Single Directories
    }
    @Override
    public String listDirectory() {

        if(current == null){ //Then we are not in one particular directory
            String s = "";
            for (LinkedList<SingleDirectory> list: directory) {
                SingleDirectory single = list.get(0);
                s += single.getName();
            }
            return s;
        }else {
            return current.get(0).listDirectory();
        }
    }

    @Override
    public void add(String name) { //add File
        if (!name.contains(".") && !listDirectory().contains(name)) { // Then its a directory and its not already a directory
            var newDirectory = new LinkedList<SingleDirectory>();
            newDirectory.add(new SingleDirectory(name));
            directory.add(newDirectory);
        }else if(name.contains("/") //File Path
                && findDir(name.split("/")[0]) != null //if the directory exists
                && findDir(name.split("/")[0]).find(name.split("/")[1]) == null){//the file does not already exist
            findDir(name.split("/")[0]).add(name.split("/")[1]); // To the directory, add the file
        }
        else if(current == null){ //Trying to add a file but not adding to a particular directory
            throw new RuntimeException("Can not make a file here");
        } else if (find(name) == null) { // If there is no file already there
            current.get(0).add(name);
        }

    }


    public void rename(String from, String to) {
        if(findDir(to)==null ){ //Can only have one of each name
            findDir(from).rename(to);
        }
    }

    @Override
    public void remove(String name) { // remove File or FilePath
        if(name.contains("/")){ //If a path
            String dirName = name.split("[/]")[0];;
            String fileName = name.split("[/]")[1];
            System.out.println("dirname = " + dirName);
            System.out.println("filename = " + fileName);
            findDir(dirName).remove(fileName);
        } else if (!name.contains(".")) { //If not a file
            removeDirectory(name);
        } else if(find(name) != null) { //If in the current directory try to find the file
            current.get(0).remove(name);
        }
    }

    public void removeDirectory(String name){
        LinkedList temp = null;
        for (LinkedList list: directory) {
            if(list.get(0).equals(findDir(name)) ){ //did we find the directory?
                temp = list;
            }
        }
        directory.remove(temp);

    }

    @Override
    public FileOS find(String name) {//Find file
        if(current != null){ //We are in a directory
            return current.get(0).find(name);
        }
        return null;
    }
    public SingleDirectory findDir(String name) {//Find directory
        for (LinkedList<SingleDirectory> list: directory) {
            SingleDirectory single = list.get(0);
            if(single.getName().equals(name)){
                return single;
            }

        }
        return null;
    }

    public void changeDirectory(String changeDir){
        if (findDir(changeDir)!= null){
            for (LinkedList<SingleDirectory> list: directory) {
                if(list.get(0).getName().equals(changeDir)){
                    current = list;
                    break;
                }
            }
        }

    }
    // fromPath toDir || fromName toDir
    public void move(String from, String to){
        if(from.contains("/") && !to.contains(".") && !to.contains("/")){//fromPath toDir, first is a path, second is not a path or file
            String fromDirName = from.split("/")[0];
            String fromFileName = from.split("/")[1];

            FileOS fromFile = findDir(fromDirName).find(fromFileName);
            FileOS toFile = findDir(to).find(fromFileName);

            if(toFile == null && fromFile != null){//The file exists and no file is already created in the new space
                findDir(to).addFile(fromFile);
                findDir(fromDirName).remove(fromFileName);
            }
        }else if (from.contains(".")&& !from.contains("/") && !to.contains("/") && !to.contains(".")){//fromName toPath, first a file and second not a path or name
            FileOS fromFile = find(from);
            SingleDirectory toDir = findDir(to);

            if(toDir != null && toDir.find(from) == null && fromFile != null){//The directoy exists and no file is already created in the new space, and the file to move is not null
                toDir.addFile(fromFile);
                remove(from);
            }

        }


    }


    public void addFile(FileOS file) {
        current.get(0).addFile(file);
    }

    public LinkedList<SingleDirectory> getCurrent(){
        return current;
    }

    public void setCurrent(LinkedList<SingleDirectory> current) {
        this.current = current;
    }

    public String listAll(){
        String s = "";
        for (LinkedList<SingleDirectory>link: directory) {
            //Get the name and then list directory
            s += link.get(0).getName() + ":\t\t" + link.get(0).listDirectory() + "\n";
        }
        return s;
    }
}

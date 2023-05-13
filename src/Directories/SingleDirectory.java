package Directories;


//import src.FileOS;

import Files.FileOS;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SingleDirectory extends AbstractDirectory<FileOS>{

    public SingleDirectory(String name, String owner){
        this.name = name;
        this.owner = owner;
        this.directory = new ArrayList<LinkedList<FileOS>>();
        commands.addAll(Arrays.asList());
    }



//Look at each linked list in directory and then look at file in the linked list
    public FileOS findFile(String name){
        for(LinkedList<FileOS> list : directory){
            for (FileOS file : list){
                if(file.getName() == name){
                    return file;
                }
            }
        }
        return null;
    }

    @Override
    public String listDirectory() {
        String myString = "";
        for(LinkedList<FileOS> list : directory) {
            for (FileOS file : list) {
                myString += String.format("%s.%s \t", file.getName(), file.getFileType());
            }
        }
        return myString;
    }



    @Override
    public void add(String name) {
        LinkedList<FileOS> newList = new LinkedList<FileOS>();
        String fileName= name.split("[.]")[0];
        System.out.println("name " + name);
        System.out.println("filename " + fileName);;
        String type = name.split("[.]")[1];
        newList.add(new FileOS(fileName, owner, "read", type));

        directory.add(newList);
    }

    @Override
    public void remove(String name) {

        //This is in a linked list, so if we find it, create a copy and remove it fro, the list
        FileOS temp = find(name);
        if (temp != null){
            LinkedList<FileOS> newList = new LinkedList<FileOS>();
            newList.add(temp);
            directory.remove(newList);
        }

    }

    @Override
    public FileOS find(String name) {
        for (LinkedList<FileOS> list : directory){
            for(FileOS file: list){
                String fullName = file.getName()+ "." + file.getType();
                if(fullName.equals( name)){ //Split into name and type, so recombine together

                    return file;
                }
            }
        }
        return null;
    }
}

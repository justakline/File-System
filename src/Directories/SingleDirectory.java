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
    public void add(FileOS file) {
        LinkedList<FileOS> newList = new LinkedList<FileOS>();
        newList.add(file);
        directory.add(newList);
    }

    @Override
    public void remove(FileOS file) {
        LinkedList<FileOS> newList = new LinkedList<FileOS>();
        newList.add(file);
        if (directory.contains(newList)){
            directory.remove(newList);
        }

    }

    @Override
    public FileOS find(String name) {
        for (LinkedList<FileOS> list : directory){
            for(FileOS file: list){
                if(file.getName() == name){
                    return file;
                }
            }
        }
        return null;
    }
}

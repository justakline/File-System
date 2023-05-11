package Directories;


//import src.FileOS;

import Files.FileOS;

import java.util.ArrayList;
import java.util.LinkedList;

public class SingleDirectory extends AbstractDirectory<FileOS>{

    public SingleDirectory(String name){
        this.name = name;
        this.directory = new ArrayList<LinkedList<FileOS>>();
    }

    public void addFile(FileOS file){
        LinkedList<FileOS> list = new LinkedList<FileOS>();
        list.add(file);
        directory.add( list );
    }
    public void removeFile(FileOS file){
        LinkedList<FileOS> list = new LinkedList<FileOS>();
        list.add(file);
        directory.remove( list );
    }

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
}

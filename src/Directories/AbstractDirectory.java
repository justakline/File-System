package Directories;

import Files.FileOS;


import java.util.LinkedList;
import java.util.List;

public abstract class AbstractDirectory<T> {

    public List<LinkedList<T>> directory;
    public String name, owner;



    //LS
    public List getDirectory(){
        return directory;
    }

    public String getName(){
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public abstract String listDirectory();



    public void rename(String name){
        this.name = name;
    }
    public void changeOwner(String setter, String name){
        if(setter == this.owner)
            this.owner = name;
    }
    public abstract void add(FileOS file);



    public abstract void remove(FileOS file);



    public abstract FileOS search(String name);



}

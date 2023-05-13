package Directories;

import Files.FileOS;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractDirectory<T> {

    public List<LinkedList<T>> directory;
    public String name, owner;
    public final List<String> commands = Arrays.asList( "ls","rn", "chown", "chmod", "add", "rm", "find", "owner", "mod", "open", "close" );
    //all directories have these functions assosciated with them, we will instantiate more in the concrete classes


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


    public abstract void add(String name);



    public abstract void remove(String name);



    public abstract FileOS find(String name);



}

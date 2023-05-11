package Directories;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractDirectory<T> {

    public List<LinkedList<T>> directory;
    public String name;

    //LS
    public List getDirectory(){
        return directory;
    }

    public String getName(){
        return name;
    }


}

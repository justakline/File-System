package Directories;

import Files.FileOS;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//We are splitting this one up, There will be the directory for having sub folders
//and there will be a file for storing all of the files at the current level
public class TreeDirectory extends AbstractDirectory<TreeDirectory>{

    LinkedList<TreeDirectory> current;
    public SingleDirectory files;
    private static TreeDirectory root = null;


    public TreeDirectory(String name, String owner){
        this.name = name;
        this.owner = owner;
        directory = new ArrayList<LinkedList<TreeDirectory>>();
        files = new SingleDirectory();
        current = new LinkedList<>();
        current.add(this);
        if(root == null){
            root = this;
        }

    }


    public SingleDirectory getFiles(){
        return files;
    }


    @Override
    public String listDirectory() {
        String s = "";
        List<LinkedList<TreeDirectory>> temp = (List<LinkedList<TreeDirectory>>)current.get(0).getDirectory();
        for (LinkedList<TreeDirectory> link : temp){
            s+= link.get(0).getName() + "\n";
        }
        return s;
    }

    public String listAll(){
        String s = "";
        for (LinkedList<TreeDirectory> link: directory) {
            s += link.get(0).getName() + ": \t" + link.get(0).listAll() +"\t\t" + link.get(0).getFiles().listDirectory()+ "\n";
        }
        return s;
    }

    @Override
    //add justa ||add go.exe|| add justa/videos/coolvideos || add justa/videos/go.exe
    public void add(String name) {
        if(!name.contains(".") && !name.contains("/")){ //add folder at curremt
            LinkedList<TreeDirectory> temp = new LinkedList<>();
            temp.add(new TreeDirectory(name, this.name));
            current.get(0).getDirectory().add(temp);
        }else if(name.contains(".") && !name.contains("/")){ //add file at curremt
            current.get(0).getFiles().add(name);
        }else if(!name.contains(".") && name.contains("/")){ //add folder at Absolute Path
            String directoryBeforeName = name.substring(0, name.lastIndexOf("/"));
            TreeDirectory directoryBefore= findDir(directoryBeforeName +"/");
            if( directoryBefore != null){
                String shortName = name.substring(name.lastIndexOf("/")+1);

                LinkedList<TreeDirectory> newFolder = new LinkedList<>();
                newFolder.add(new TreeDirectory(name, this.name));
                directoryBefore.getDirectory().add(shortName);
            }
        }else if(name.contains(".") && name.contains("/")) { //add file at Absolute Path
            String directoryBeforeName = name.substring(0, name.lastIndexOf("/"));
            TreeDirectory directoryBefore= findDir(directoryBeforeName + "/");
            if( directoryBefore != null){
                String shortName = name.substring(name.lastIndexOf("/")+1);
                LinkedList<TreeDirectory> newFolder = new LinkedList<>();
                newFolder.add(new TreeDirectory(name, this.name));
                directoryBefore.getFiles().add(shortName);
            }
        }
    }

    @Override

    //rm j.exe || rm a || rm
    public void remove(String name) {

    }



    @Override
    public FileOS find(String name) {
     return null;
    }

    //finds the directory based on a Absolute path
    public TreeDirectory findDir(String name){
        TreeDirectory temp = root;

        while(name.contains("/")){
            TreeDirectory switcher = null;
            for(LinkedList<TreeDirectory> link: (ArrayList<LinkedList>)temp.getDirectory()){
                System.out.println(name.substring(name.indexOf("/")));
                System.out.println(link.get(0).getName() + "\n");
                if(name.substring(name.indexOf("/")).equals(link.get(0).getName())){
                    switcher = link.get(0);
                    break;
                }
            }
            if(switcher != null){
                name = name.substring(name.indexOf("/"));
                temp = switcher;
            }else{
                return null;
            }
        }
        System.out.println(temp.getName());
        return temp;


    }




    public void changeDirectory(String changeDir){
        if (findDir(changeDir) != null){
            current.remove();
            current.add(findDir(changeDir));
        }

    }


    public LinkedList<TreeDirectory> getCurrent() {
        return current;
    }

    public void changeDirectoryBack(){
        if (current.get(0).getName() != "root") {
            String lastDirectoryName = current.get(0).getOwner();
            current = new LinkedList<>();
            if (lastDirectoryName.equals("root")) {
                current.add(root);
            } else {
                current.add(findDir(lastDirectoryName));
            }
        }
    }
}

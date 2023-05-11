import Directories.AbstractDirectory;
import Directories.SingleDirectory;
import Files.FileOS;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        AbstractDirectory directory = new SingleDirectory("myDir", "justa");
        FileOS file = new FileOS("h", "justa", "write", "exe");
        directory.add(file);
        System.out.println(directory.getOwner());
        directory.changeOwner("justa", "youa");
        System.out.println(directory.getOwner());
        System.out.println(directory.getName());
        System.out.println(directory.listDirectory());
        file = new FileOS("clean", "justa", "write", "txt");
        directory.add(file);
        file = new FileOS("me", "justa", "write", "txt");
        directory.add(file);
        System.out.println(directory.listDirectory());
    }
}

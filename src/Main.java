import Files.FileOS;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        FileOS file = new FileOS("h", 300, "write", "exe");
        System.out.println(file.getTime());
        System.out.println(file.getDate());

        System.out.println(file.getType());

        System.out.println(file.getPermission());

        System.out.println(file.getId());

    }
}

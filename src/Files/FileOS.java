package Files;

import java.time.LocalDateTime;

public class FileOS {


    private boolean isOpen;
    private String name, time, date, type, body;
    private long id, size;
    private Permission permission;
    private FileType fileType;
    private enum Permission{
        READ,
        WRITE;


    }

    private enum FileType{
        EXE,
        JAVA,

        C,

        JAR,
        O,

        PY;



    }

    public FileOS (String name, int size, String permission, String fileType) {
        this.name = name;
        String localDateTime = LocalDateTime.now().toString();
        this.date = localDateTime.split("T")[0];
        this.time = (localDateTime.split("T")[1]).split("[.]")[0];
        this.fileType = FileType.valueOf(fileType.toUpperCase());

//        this.fileType = FileType.O;
        this.permission = Permission.valueOf(permission.toUpperCase());
        this.body = "";
        this.isOpen = false;
        this.id = Long.parseLong(time.replace(":", "") + date.replace("-", "") + (int)(Math.random() *1000));

    }

    public void openFile(){
        isOpen = true;
    }
    public void closeFile(){
        isOpen = false;
    }

    public void rename(String name){
        this.name = name;
    }

    public String read(){
        if(isOpen) {
            return body;
        }
        return "";
    }

    public void write(String newBody){
        if(isOpen && permission == Permission.WRITE) {
            body = newBody;
            size = newBody.length();
        }
    }
    public void writeEnd(String newBody){
        if(isOpen && permission == Permission.WRITE) {
            body += newBody;
            size += newBody.length();
        }
    }

    public void modify(String oldText, String newText){
        if(isOpen && permission == Permission.WRITE) {
            body.replace(oldText, newText);
            size -= oldText.length();
            size += newText.length();
        }
    }




    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public FileType getType() {
        return fileType;
    }

    public long getId() {
        return id;
    }


    public long getSize() {
        return size;
    }

    public Permission getPermission() {
        return permission;
    }

}

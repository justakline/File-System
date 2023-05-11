package Files;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileOS {


    private boolean isOpen;
    private String name, time, date, type, owner;
    private long id, size;
    private List<String> body; // Line by Line body
    private Permission permission;
    private FileType fileType;
    private enum Permission{READ, WRITE}
    private enum FileType{exe, java, c, jar, o, py, txt}

    public FileOS (String name, String owner, String permission, String fileType) {
        this.name = name;
        this.owner = owner;
        String localDateTime = LocalDateTime.now().toString();
        this.date = localDateTime.split("T")[0];
        this.time = (localDateTime.split("T")[1]).split("[.]")[0];
        this.fileType = FileType.valueOf(fileType);

        this.permission = Permission.valueOf(permission.toUpperCase());
        this.body = new ArrayList<String>();
        this.size = 0;
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

    //Read at the index with the new string
    public String read(int lineIndex){
        if(isOpen) {
            return body.get(lineIndex);
        }
        return "";
    }

    //Change at the index with the new string, and update size
    public void write(String newLine, int lineIndex){
        if(isOpen && permission == Permission.WRITE) {
            size -= body.get(lineIndex).length();
            body.set(lineIndex, newLine);
            size += newLine.length();
        }
    }

    public boolean isOpen() {
        return isOpen;
    }

    public String getBody() {
        return body.toString();
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
    public FileType getFileType() {
        return fileType;
    }

}

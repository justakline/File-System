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
    private enum Permission{read, write}
    private enum FileType{exe, java, c, jar, o, py, txt}

    public FileOS (String name, String owner, String permission, String fileType) {
        this.name = name;
        this.owner = owner;
        String localDateTime = LocalDateTime.now().toString();
        this.date = localDateTime.split("T")[0];
        this.time = (localDateTime.split("T")[1]).split("[.]")[0];
        this.fileType = FileType.valueOf(fileType);

        this.permission = Permission.valueOf(permission);
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

        this.name = name.split("[.]")[0];
        this.fileType = FileType.valueOf(name.split("[.]")[1]);
    }

    //Read at the index with the new string
    public String read(int lineIndex){
        if(isOpen && lineIndex < body.size()) {
            return lineIndex + ".\t" + body.get(lineIndex);
        }
        return "";
    }
    //read the whole file
    public String read(){
        if(isOpen) {
            String whole = "";
            int counter = 0;
            for(String s : body){
              whole += counter + ".\t" + s +"\n";
              counter ++;
            }
            return whole;
        }
        return "";
    }

    //Change at the index with the new string, and update size
    public void write(int lineIndex, String newLine){
        if(isOpen && permission == Permission.write) {
            if(lineIndex >= body.size()){ //if they are past the size, then add new lines
                for(int i = body.size(); i <= lineIndex; i++){
                    body.add("");
                    size+=1;
                }
            }
            if(body.size() > 0){ //Something already to the list
                this.size -= body.get(lineIndex).length();
                this.body.set(lineIndex, newLine);
            }else {// nothing in the list yet, so add
                body.add(newLine);
            }

            this.size += newLine.length();
        }
    }

    public void setOwner(String newOwner){
        this.owner = newOwner;
    }
    public void setPermission(String permission){
        this.permission = Permission.valueOf(permission)   ;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public List<String> getBody() {
        return body;
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

    public String getOwner(){
        return owner;
    }

}

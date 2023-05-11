package Files;

import java.time.LocalTime;

public class FileOS {



    private String name, time, date, type;
    private long id, location, size;
    private Permission permission;
    private enum Permission{
        READ("read"),
        WRITE("write");

        Permission(String write) {
        }
    }

    public FileOS (String name) {
        this.name = name;
        this.time = LocalTime.now().toString();


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

    public String getType() {
        return type;
    }

    public long getId() {
        return id;
    }

    public long getLocation() {
        return location;
    }

    public long getSize() {
        return size;
    }

    public Permission getPermission() {
        return permission;
    }

}

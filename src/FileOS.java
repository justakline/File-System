import java.time.*;

public class FileOS {

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    private String name;
    private String time;
    public FileOS (String name) {
        this.name = name;
        this.time = LocalTime.now().toString();

    }




}

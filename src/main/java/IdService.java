import java.util.UUID;

public class IdService {
    private String generatedId;

    public String generateId(){
        return UUID.randomUUID().toString();
    }
}

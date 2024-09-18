import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileService {
    public void getFile() throws IOException {
        Path filePath = Path.of("transactions.txt");
        List<String> lines = Files.readAllLines(filePath);
    }
}

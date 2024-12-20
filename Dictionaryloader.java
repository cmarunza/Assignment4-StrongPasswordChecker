import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DictionaryLoader {
    public static List<String> loadDictionary(String urlString) throws IOException {
        List<String> dictionary = new ArrayList<>();
        URL url = new URL(urlString);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                dictionary.add(line.trim());
            }
        }
        return dictionary;
    }
}

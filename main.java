import java.io.*;
import java.net.URL;
import java.util.*;

public class Main {

    public static final int M_CHAINING = 1000;
    public static final int M_PROBING = 20000;

    public static void main(String[] args) throws IOException {
        // Load dictionary
        List<String> dictionary = loadDictionary("https://www.mit.edu/~ecprice/wordlist.10000");
        HashTableSeparateChaining hashTableChaining = new HashTableSeparateChaining(M_CHAINING);
        HashTableLinearProbing hashTableProbing = new HashTableLinearProbing(M_PROBING);

        // Insert dictionary words into both hash tables
        for (int i = 0; i < dictionary.size(); i++) {
            String word = dictionary.get(i);
            hashTableChaining.insert(word, i + 1);
            hashTableProbing.insert(word, i + 1);
        }

        // Test passwords
        String[] passwords = {
                "account8",
                "accountability",
                "9a$D#qW7!uX&Lv3zT",
                "B@k45*W!c$Y7#zR9P",
                "X$8vQ!mW#3Dz&Yr4K5"
        };

        for (String password : passwords) {
            System.out.println("Checking password: " + password);
            boolean isStrong = checkPassword(password, hashTableChaining, hashTableProbing);
            System.out.println("Is strong: " + isStrong + "\n");
        }
    }

    // Load dictionary from URL
    private static List<String> loadDictionary(String urlString) throws IOException {
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

    // Check if a password is strong
    private static boolean checkPassword(String password, HashTableSeparateChaining chaining, HashTableLinearProbing probing) {
        if (password.length() < 8) return false;

        int comparisonsChaining = chaining.search(password);
        int comparisonsProbing = probing.search(password);

        System.out.println("Comparisons (Separate Chaining): " + comparisonsChaining);
        System.out.println("Comparisons (Linear Probing): " + comparisonsProbing);

        if (comparisonsChaining != -1 || comparisonsProbing != -1) return false;

        // Check if it matches a dictionary word followed by a digit
        for (int i = 0; i < 10; i++) {
            String modified = password.substring(0, password.length() - 1);
            if (chaining.search(modified) != -1 || probing.search(modified) != -1) return false;
        }

        return true;
    }
}

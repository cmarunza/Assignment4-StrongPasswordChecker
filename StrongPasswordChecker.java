import java.io.IOException;
import java.util.List;

public class StrongPasswordChecker {

    public static final int M_CHAINING = 1000; // Size for chaining hash table
    public static final int M_PROBING = 20000; // Size for linear probing hash table

    public static void main(String[] args) throws IOException {
        // Load the dictionary
        String dictionaryUrl = "https://www.mit.edu/~ecprice/wordlist.10000";
        List<String> dictionary = DictionaryLoader.loadDictionary(dictionaryUrl);
        System.out.println("Dictionary loaded with " + dictionary.size() + " words.\n");

        // Initialize hash tables
        HashTableSeparateChaining hashTableChaining = new HashTableSeparateChaining(M_CHAINING);
        HashTableLinearProbing hashTableProbing = new HashTableLinearProbing(M_PROBING);

        // Populate hash tables
        for (int i = 0; i < dictionary.size(); i++) {
            String word = dictionary.get(i);
            hashTableChaining.insert(word, i + 1);
            hashTableProbing.insert(word, i + 1);
        }
        System.out.println("Hash tables initialized and populated.\n");

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

    private static boolean checkPassword(String password, HashTableSeparateChaining chaining, HashTableLinearProbing probing) {
        // Rule 1: Must be at least 8 characters long
        if (password.length() < 8) return false;

        // Rule 2: Must not be a dictionary word
        int chainingSearch = chaining.search(password);
        int probingSearch = probing.search(password);
        System.out.println("Comparisons (Separate Chaining): " + chainingSearch);
        System.out.println("Comparisons (Linear Probing): " + probingSearch);

        if (chainingSearch != -1 || probingSearch != -1) return false;

        // Rule 3: Must not be a dictionary word followed by a digit
        for (int i = 0; i < 10; i++) {
            String modified = password.substring(0, password.length() - 1) + i;
            if (chaining.search(modified) != -1 || probing.search(modified) != -1) return false;
        }

        return true;
    }
}

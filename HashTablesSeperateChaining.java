import java.util.LinkedList;

public class HashTableSeparateChaining {
    private LinkedList<Entry>[] table;
    private int size;

    public HashTableSeparateChaining(int size) {
        this.size = size;
        table = new LinkedList[size];
        for (int i = 0; i < size; i++) {
            table[i] = new LinkedList<>();
        }
    }

    public void insert(String key, int value) {
        int hash = hashCode(key) % size;
        table[hash].add(new Entry(key, value));
    }

    public int search(String key) {
        int hash = hashCode(key) % size;
        LinkedList<Entry> bucket = table[hash];
        int comparisons = 0;

        for (Entry entry : bucket) {
            comparisons++;
            if (entry.key.equals(key)) {
                return comparisons;
            }
        }
        return -1;
    }

    private int hashCode(String key) {
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash = (hash * 31) + key.charAt(i);
        }
        return Math.abs(hash);
    }

    private static class Entry {
        String key;
        int value;

        Entry(String key, int value) {
            this.key = key;
            this.value = value;
        }
    }
}

public class HashTableLinearProbing {
    private String[] table;
    private int[] values;
    private int size;

    public HashTableLinearProbing(int size) {
        this.size = size;
        table = new String[size];
        values = new int[size];
    }

    public void insert(String key, int value) {
        int hash = hashCode(key) % size;

        while (table[hash] != null) {
            hash = (hash + 1) % size;
        }

        table[hash] = key;
        values[hash] = value;
    }

    public int search(String key) {
        int hash = hashCode(key) % size;
        int comparisons = 0;

        while (table[hash] != null) {
            comparisons++;
            if (table[hash].equals(key)) {
                return comparisons;
            }
            hash = (hash + 1) % size;
        }

        return -1;
    }

    private int hashCode(String key) {
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash = (hash * 37) + key.charAt(i);
        }
        return Math.abs(hash);
    }
}

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomQue = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            String value = StdIn.readString();
            randomQue.enqueue(value);
        }

        for (int i = 0; i < k; i++) {
            randomQue.dequeue();
        }
    }
}

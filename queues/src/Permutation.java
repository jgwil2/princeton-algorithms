import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;

public class Permutation {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            rq.enqueue(StdIn.readString());
        }
        Iterator<String> iterator = rq.iterator();
        for (int i = 0; i < k; i++) {
            System.out.println(iterator.next());
        }
    }
}

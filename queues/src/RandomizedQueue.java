import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<T> implements Iterable<T> {
    private T[] arr;
    private int len = 2;
    private int first;
    private int next;

    private class RandomizedQueueIterator implements Iterator<T> {
        T[] randomizedOrder;
        int cur = 0;

        public RandomizedQueueIterator() {
            randomizedOrder = (T[]) new Object[size()];
            for (int i = 0; i < size(); i++) {
                randomizedOrder[i] = arr[i + first];
            }
            StdRandom.shuffle(randomizedOrder);
        }

        @Override
        public boolean hasNext() {
            return cur < randomizedOrder.length;
        }

        @Override
        public T next() {
            return randomizedOrder[cur++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        arr = (T[]) new Object[len];
        first = 0;
        next = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return first == next;
    }

    // return the number of items on the randomized queue
    public int size() {
        return next - first;
    }

    // add the item
    public void enqueue(T item) {
        int size = size();
        arr[next++] = item;
        // resize if full
        if (size() == len) {
            len = len * 2;
            T[] newArr = (T[]) new Object[len];
            for (int i = 0; i < next; i++) {
                newArr[i] = arr[i + first];
            }
            arr = newArr;
            next = size + 1;
        }
        // copy to front if at end of array
        else if (next == len) {
            for (int i = 0; i < next; i++) {
                arr[i] = arr[i + first];
            }
            next = size + 1;
            first = 0;
        }
    }

    // remove and return a random item
    public T dequeue() {
        T item = arr[first++];
        // resize if 1/4 full
        if (size() == len / 4) {
            len = len / 2;
            T[] newArr = (T[]) new Object[len];
            for (int i = 0; i < next - first; i++) {
                newArr[i] = arr[i + first];
            }
            arr = newArr;
            next = next - first;
            first = 0;
        }
        return item;
    }

    // return a random item (but do not remove it)
    public T sample() {
        int i = StdRandom.uniform(first, next);
        return arr[i];
    }

    // return an independent iterator over items in random order
    public Iterator<T> iterator() {
        return new RandomizedQueueIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> d = new RandomizedQueue<>();
        d.enqueue(1);
        d.enqueue(2);
        assert d.dequeue() == 1;
        assert d.dequeue() == 2;
        d.enqueue(1);
        d.enqueue(2);
        d.enqueue(3);
        d.enqueue(4);
        assert d.dequeue() == 1;
        assert d.dequeue() == 2;
        assert d.dequeue() == 3;
        assert d.dequeue() == 4;
    }

}

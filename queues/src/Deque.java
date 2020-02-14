import java.util.Iterator;

public class Deque<T> implements Iterable<T> {
    private Node<T> first;
    private Node<T> last;
    private int count;

    private class Node<T> {
        private T val;
        public Node<T> next;
        public Node<T> prev;

        public Node(T val) {
            this.val = val;
        }
    }

    private class DequeIterator implements java.util.Iterator<T> {
        private Node<T> current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            Node<T> node = current;
            current = node.next;
            return node.val;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return count == 0;
    }

    // return the number of items on the deque
    public int size() {
        return count;
    }

    // add the item to the front
    public void addFirst(T item) {
        if (item == null) throw new IllegalArgumentException();
        Node<T> oldFirst = first;
        first = new Node<>(item);
        first.next = oldFirst;
        if (isEmpty()) last = first;
        if (oldFirst != null) oldFirst.prev = first;
        count++;
    }

    // add the item to the back
    public void addLast(T item) {
        if (item == null) throw new IllegalArgumentException();
        Node<T> oldLast = last;
        last = new Node<>(item);
        last.prev = oldLast;
        if (isEmpty()) first = last;
        if (oldLast != null) oldLast.next = last;
        count++;
    }

    // remove and return the item from the front
    public T removeFirst() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        count--;
        Node<T> node = first;
        first = node.next;
        first.prev = null;
        return node.val;
    }

    // remove and return the item from the back
    public T removeLast() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        count--;
        Node<T> node = last;
        last = node.prev;
        last.next = null;
        return node.val;
    }

    // return an iterator over items in order from front to back
    public Iterator<T> iterator() {
        return new DequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> d = new Deque<>();
        d.addFirst(1);
        d.addFirst(2);
        d.addFirst(3);
        d.addLast(4);
        assert d.removeLast() == 4;
        assert d.removeLast() == 1;
        assert d.removeFirst() == 3;
        assert d.size() == 1;
    }
}

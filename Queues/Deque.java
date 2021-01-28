import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    // construct an empty deque
    public Deque() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        validateItem(item);
        Node oldFirst = this.first;
        this.first = new Node();
        this.first.item = item;
        this.size++;
        if (this.size == 1) {
            this.first.next = null;
            this.first.prev = null;
            this.last = this.first;
        } else {
            this.first.next = oldFirst;
            this.first.prev = null;
            oldFirst.prev = this.first;
        }

    }

    // add the item to the back
    public void addLast(Item item) {
        validateItem(item);
        Node oldLast = this.last;
        this.last = new Node();
        this.last.item = item;
        this.size++;
        if (this.size == 1) {
            this.last.next = null;
            this.last.prev = null;
            this.first = this.last;
        } else {
            this.last.next = null;
            this.last.prev = oldLast;
            oldLast.next = this.last;
        }

    }

    // remove and return the item from the front
    public Item removeFirst() {
        emptyCheck();
        Item removedItem = this.first.item;
        this.size--;
        if (this.size == 0) {
            this.first = null;
            this.last = null;
        } else {
            this.first = this.first.next;
            this.first.prev = null;
        }
        if (this.size == 1) {
            this.last = this.first;
        }
        return removedItem;
    }

    // remove and return the item from the back
    public Item removeLast() {
        emptyCheck();
        Item removedItem = this.last.item;
        this.size--;
        if (this.size == 0) {
            this.first = null;
            this.last = null;
        } else {
            this.last = last.prev;
            this.last.next = null;
        }
        if (this.size == 1) {
            this.first = this.last;
        }
        return removedItem;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private void validateItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
    }

    private void emptyCheck() {
        if (this.isEmpty()) {
            throw new java.util.NoSuchElementException("Deque is empty");
        }
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove operation is unsupported");
        }

        public Item next() {
            Item item = this.current.item;
            if (!this.hasNext()) {
                throw new java.util.NoSuchElementException("No more items to iterate over");
            }
            this.current = current.next;
            return item;
        }

    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> newDeque = new Deque<>();
        StdOut.println("Is deque empty:" + newDeque.isEmpty());
        StdOut.println("Deque size:" + newDeque.size());
        StdOut.println("Adding as first '1':");
        newDeque.addFirst(1);
        StdOut.println("Adding as last '4':");
        newDeque.addLast(4);
        StdOut.println("Adding as first '3':");
        newDeque.addFirst(3);
        StdOut.println("Items:");
        for (Integer item : newDeque) {
            StdOut.print(item + ", ");
        }
        StdOut.print("\n");
        StdOut.println("Removing first:" + newDeque.removeFirst());
        StdOut.println("Removing first:" + newDeque.removeFirst());
        StdOut.println("Removing last:" + newDeque.removeLast());
        StdOut.println("Items:");
        for (Integer item : newDeque) {
            StdOut.print(item + ", ");
        }
        StdOut.print("\n");
    }

}

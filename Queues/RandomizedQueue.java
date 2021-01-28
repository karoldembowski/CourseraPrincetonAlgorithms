import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] queue;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.size = 0;
        this.queue = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return this.size() == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return this.size;
    }

    // add the item
    public void enqueue(Item item) {
        validateItem(item);
        if (this.size == this.queue.length) resize(this.queue.length * 2);
        this.queue[this.size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        emptyCheck();
        Item removedItem;
        int randomIndex = StdRandom.uniform(this.size);
        removedItem = this.queue[randomIndex];
        this.queue[randomIndex] = this.queue[this.size - 1];
        this.queue[this.size - 1] = null;
        this.size--;
        if ((this.size > 0) && (this.size == this.queue.length / 4)) resize(this.queue.length / 2);
        return removedItem;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        emptyCheck();
        int randomIndex = StdRandom.uniform(this.size);
        return this.queue[randomIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        if (this.size >= 0) System.arraycopy(this.queue, 0, copy, 0, this.size);
        this.queue = copy;
    }

    private void validateItem(Item item) {
        if (item == null) throw new IllegalArgumentException("Item can not be null");
    }

    private void emptyCheck() {
        if (this.size == 0) throw new java.util.NoSuchElementException("Queue is empty");
    }

    private class RandomIterator implements Iterator<Item> {
        private int i = size();
        private final Item[] copiedQueue = queue;

        public boolean hasNext() {
            return this.i > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove operation is unsupported");
        }

        public Item next() {
            if (!this.hasNext()) {
                throw new java.util.NoSuchElementException("No more items to iterate over");
            }
            int randomIndex = StdRandom.uniform(this.i);
            Item result = copiedQueue[randomIndex];
            copiedQueue[randomIndex] = copiedQueue[this.i - 1];
            this.i--;
            return result;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> newRandQue = new RandomizedQueue<>();
        StdOut.println("Is que empty:" + newRandQue.isEmpty());
        StdOut.println("Que size:" + newRandQue.size());
        StdOut.println("Adding '1' to que");
        newRandQue.enqueue(1);
        StdOut.println("Adding '2' to que");
        newRandQue.enqueue(2);
        StdOut.println("Items: ");
        StdOut.print("[");
        for (Integer item : newRandQue) {
            StdOut.print(item + ",");
        }
        StdOut.print("] \n");
        StdOut.println("Sampling:" + newRandQue.sample());
        StdOut.println("Dequeing:" + newRandQue.dequeue());
        StdOut.println("Items: ");
        StdOut.print("[");
        for (Integer item : newRandQue) {
            StdOut.print(item + ",");
        }
        StdOut.print("] \n");
    }

}

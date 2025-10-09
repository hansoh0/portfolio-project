package components.phantomqueue;

import components.queue.Queue;
import components.queue.Queue1L;

/**
 * Proof-of-concept implementation of a "PhantomQueue" component.
 *
 * @param <T>
 *            the type of elements stored
 *
 * @convention internalQueue is never null
 * @correspondence this = (sequence of (element, ttl) pairs with ttl > 0)
 *
 * @author Canyon Bishop | Bishop.712
 *
 */
public final class PhantomQueue<T> {

    /**
     * Default time-to-live (TTL) for enqueued elements.
     */
    private static final int DEFAULT_TTL = 5;

    /**
     * Internal representation: queue of elements wrapped with TTL values.
     */
    private final Queue<QueueElement<T>> internalQueue;

    /**
     * Internal class representing an element with a TTL.
     *
     * @param <T>
     */
    private static final class QueueElement<T> {
        /**
         * The element stored in this {@code QueueElement}.
         */
        private final T element;
        /**
         * The time-to-live value that represents how long an
         * {@code queueElement} stays in this.
         */
        private int ttl;

        /**
         * Construction of a {@code QueueElement} with element and ttl.
         *
         * @param element
         * @param ttl
         */
        QueueElement(T element, int ttl) {
            this.element = element;
            this.ttl = ttl;
        }
    }

    /**
     * Creates a new empty PhantomQueue representation.
     */
    public PhantomQueue() {
        this.internalQueue = new Queue1L<>();
    }

    /**
     * Adds {@code e} with a specifiec TTL.
     *
     * @param e
     *            the element to add
     * @param ttl
     *            the time-to-live value
     */
    public void enqueue(T e, int ttl) {
        assert e != null : "Violation of: e is not null";
        assert ttl >= 0 : "Violation of: ttl >= 0";
        this.internalQueue.enqueue(new QueueElement<>(e, ttl));
    }

    /**
     * Adds {@code e} with a default TTL to queue.
     *
     * @param e
     *            the element to add
     */
    public void enqueue(T e) {
        assert e != null : "Violation of: e is not null";
        this.enqueue(e, DEFAULT_TTL);
    }

    /**
     * Decrements TTLs and removes expired elements.
     */
    public void tick() {
        Queue<QueueElement<T>> temp = new Queue1L<>();
        while (this.internalQueue.length() > 0) {
            QueueElement<T> current = this.internalQueue.dequeue();
            current.ttl--;
            if (current.ttl > 0) {
                temp.enqueue(current);
            }
        }
        this.internalQueue.transferFrom(temp);
    }

    /**
     * Returns (without removing) the next unexpired element.
     *
     * @return next unexpired element
     */
    public Object peek() {
        Queue<QueueElement<T>> temp = new Queue1L<>();
        // Cannot be null
        Object result = "";

        while (this.internalQueue.length() > 0) {
            QueueElement<T> current = this.internalQueue.dequeue();
            temp.enqueue(current);
            if (result.equals("") && current.ttl > 0) {
                result = current.element;
            }
        }
        this.internalQueue.transferFrom(temp);
        return result;
    }

    /**
     * Returns elements in queue (excluding TTL).
     *
     * @return string representation of elements in order.
     */
    public String getQueue() {
        StringBuilder result = new StringBuilder("<");
        Queue<QueueElement<T>> temp = new Queue1L<>();

        while (this.internalQueue.length() > 0) {
            QueueElement<T> current = this.internalQueue.dequeue();
            temp.enqueue(current);
            result.append(current.element);
            if (this.internalQueue.length() > 0) {
                result.append(", ");
            }
        }
        this.internalQueue.transferFrom(temp);
        result.append(">");
        return result.toString();
    }

    /**
     * Reports how many unexpired elements remain.
     *
     * @return size of queue
     */
    public int size() {
        int count = 0;
        Queue<QueueElement<T>> temp = new Queue1L<>();

        while (this.internalQueue.length() > 0) {
            QueueElement<T> current = this.internalQueue.dequeue();
            temp.enqueue(current);
            if (current.ttl > 0) {
                count++;
            }
        }
        this.internalQueue.transferFrom(temp);
        return count;
    }

    /**
     * PhantomQueue Demo.
     *
     * @param args
     *            command line arguments (unused).
     */
    public static void main(String[] args) {
        PhantomQueue<String> q = new PhantomQueue<>();
        q.enqueue("First", 3);
        q.enqueue("Second", 2);
        q.enqueue("Last", 1);

        System.out.println("Initial size: " + q.size());
        System.out.println("First Peek: " + q.peek());
        System.out.println("Queue Elements: " + q.getQueue());

        q.tick();
        System.out.println("\n1 tick:");
        System.out.println("Size: " + q.size());
        System.out.println("Second Peek: " + q.peek());
        System.out.println("Queue Elements: " + q.getQueue());

        q.tick();
        System.out.println("\n2 ticks:");
        System.out.println("Size: " + q.size());
        System.out.println("Third Peek: " + q.peek());
        System.out.println("Queue Elements: " + q.getQueue());

        q.tick();
        System.out.println("\n3 ticks:");
        System.out.println("Size: " + q.size());
        System.out.println("Fourth Peek: " + q.peek());
        System.out.println("Queue Elements: " + q.getQueue());
    }
}

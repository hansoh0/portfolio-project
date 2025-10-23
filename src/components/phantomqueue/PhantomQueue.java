package components.phantomqueue;

/**
 * Enhanced interface for {@code PhantomQueue}.
 *
 * @param <T>
 *            the type of elements stored in this queue
 *
 * @author Canyon Bishop | Bishop.712
 *
 */
public interface PhantomQueue<T> extends PhantomQueueKernel<T> {

    /**
     * Adds {@code e} with the default TTL.
     *
     * @param e
     *            the element to enqueue
     * @requires e != null
     * @ensures this = #this * [e with DEFAULT_TTL]
     */
    void enqueue(T e);

    /**
     * Returns (but does not remove) the next unexpired element.
     *
     * @return the next unexpired element
     * @requires this.size() > 0
     * @ensures peek = first unexpired element in this and this = #this
     */
    T peek();

    /**
     * Reports whether the queue is empty (no unexpired elements).
     *
     * @return true if no unexpired elements remain
     */
    boolean isEmpty();
}

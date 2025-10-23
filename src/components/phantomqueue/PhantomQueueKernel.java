package components.phantomqueue;

/**
 * Kernel interface for {@code PhantomQueue}.
 *
 * @param <T>
 *            the type of elements stored in this queue
 *
 * @author Canyon Bishop | Bishop.712
 *
 */
public interface PhantomQueueKernel<T> {

    /**
     * Adds {@code e} with a specified time-to-live counter {@code ttl}.
     *
     * @param e
     *            the element to enqueue
     * @param ttl
     *            the time-to-live value
     * @requires e != null and ttl >= 0
     * @ensures this = #this * [e with ttl]
     */
    void enqueue(T e, int ttl);

    /**
     * Removes and returns the oldest unexpired element in the queue.
     *
     * @return the oldest unexpired element
     * @requires this.size() > 0
     * @ensures #this = [dequeue] * this
     */
    T dequeue();

    /**
     * Decrements the TTL of all elements and removes those that have expired.
     *
     * @ensures all elements have TTL decreased by 1 and expired elements
     *          removed
     */
    void tick();

    /**
     * Reports the number of unexpired elements in the queue.
     *
     * @return number of unexpired elements
     */
    int size();

    /**
     * Clears all elements from the queue.
     *
     * @ensures this = empty_queue
     */
    void clear();
}

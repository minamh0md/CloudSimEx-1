package org.cloudbus.cloudsim.incubator.web;

import java.util.Iterator;

/**
 * A generator with a collection of in-memory pregenerated objects.
 * 
 * @author nikolay.grozev
 * 
 * @param <T>
 *            - the type of the generated elements.
 */
public class IterableGenerator<T> implements IGenerator<T> {

    private Iterator<T> iterator;
    private T peeked;

    /**
     * Creates the iterable generator with the list of prefetched instances.
     * @param collection
     */
    public IterableGenerator(final Iterable<T> collection) {
	this.iterator = collection.iterator();
    }

    /**
     * (non-Javadoc)
     * @see org.cloudbus.cloudsim.incubator.web.IGenerator#peek()
     */
    @Override
    public T peek() {
	peeked = peeked == null ? iterator.next() : peeked;
	return peeked;
    }

    /**
     * (non-Javadoc)
     * @see org.cloudbus.cloudsim.incubator.web.IGenerator#poll()
     */
    @Override
    public T poll() {
	T result = peeked;
	if (peeked != null) {
	    peeked = null;
	} else {
	    result = iterator.next();
	}
	return result;
    }

    /**
     * (non-Javadoc)
     * @see org.cloudbus.cloudsim.incubator.web.IGenerator#isEmpty()
     */
    @Override
    public boolean isEmpty() {
	return peeked == null && !iterator.hasNext();
    }

    /**
     * (non-Javadoc)
     * @see org.cloudbus.cloudsim.incubator.web.IGenerator#notifyOfTime(double)
     */
    @Override
    public void notifyOfTime(final double time) {
	// Do nothing
    }

}
package org.mentoring.link;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by user on 1/22/15.
 */
public class LinkList<E> implements Iterable {

    private Link<E> first;  //first node
    private Link<E> last;   //Last node
    private int size = 0;

    public void add(E element) {
        Link<E> lastLink = last;
        Link<E> newLink = new Link<>(element);
        last = newLink;
        if (lastLink == null) {
            //empty list
            first = newLink;
        } else {
            //non empty list
            lastLink.next = newLink;
        }
        size++;
    }

    public void addAll(Collection<? extends E> collection) {
        Object[] a = collection.toArray();

        for (Object o : a) {
            E e = (E) o;
            add(e);
        }
    }

    public int size() {
        return size;
    }

    public E get(int index) {
        checkIndex(index);
        Link<E> x = first;
        for (int i = 0; i < index; i++) {
            x = x.next;
        }
        return x.item;
    }

    public boolean contains(Object o) {
        if (o == null) {
            for (Link<E> x = first; x != null; x = x.next) {
                if (x.item == null)
                    return true;
            }
        } else {
            for (Link<E> x = first; x != null; x = x.next) {
                if (o.equals(x.item))
                    return true;
            }
        }
        return false;
    }

    public boolean remove(Object o) {
        Link<E> prev = null;
        if (o == null) {
            for (Link<E> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    if (x != first) {
                        prev.next = x.next;
                    } else {
                        first = x.next;
                    }
                    size--;
                    return true;
                }
            }
        } else {
            for (Link<E> x = first; x != null; x = x.next) {
                if (o.equals(x.item)) {
                    if (x != first) {
                        prev.next = x.next;
                    } else {
                        first = x.next;
                    }
                    size--;
                    return true;
                }
                prev = x;
            }
        }
        return false;
    }

    public boolean isIndexExist(int index) {
        return index >= 0 && index < size;
    }

    public void checkIndex(int index) {
        if (!isIndexExist(index))
            throw new IndexOutOfBoundsException();
    }

    public LinkIterator linkIterator() {
        return new LinkIterator();
    }

    @Override
    public Iterator iterator() {
        return linkIterator();
    }

    private static class Link<E> {

        public Link<E> next;
        //public Link<E> prev;
        E item;

        public Link(E element) {
            this.item = element;
            this.next = null;
        }
    }

    private class LinkIterator implements Iterator<E> {

        private int nextIndex;
        private Link<E> current;


        public LinkIterator() {
            this.current = first;

        }

        @Override
        public boolean hasNext() {
            return nextIndex < size - 1;
            //return current != null;
        }

        @Override
        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();
            current = current.next;
            this.nextIndex++;

            return current.item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


}
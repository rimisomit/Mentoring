package org.mentoring.link;

import java.util.Collection;

/**
 * Created by user on 1/22/15.
 */
public class LinkList<E> {


    private Link<E> first;  //first node
    private Link<E> last;   //Last node
    private int size = 0;


    public void add(E element) {
        Link<E> lastLink = last;
        Link<E> newLink = new Link<>(element, null);
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
        if (o == null) {
            for (Link<E> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    E element = x.item;
                    Link<E> next = x.next;


                    x.item = null;
                    size--;
                    return true;
                }
            }
        } else {
            for (Link<E> x = first; x != null; x = x.next) {
                if (o.equals(x.item)) {

                    return true;
                }
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

}
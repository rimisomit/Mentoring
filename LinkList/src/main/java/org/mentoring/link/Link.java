package org.mentoring.link;

import java.util.Collection;

/**
 *
 *
 */
public class Link<E> {

    public Link<E> next;
    //public Link<E> prev;
    E item;

    public Link(E element, Link<E> next) {
        this.item = element;
        this.next = next;
    }



}



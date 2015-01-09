package mentoring.mylinkedlist;

import java.util.Collection;

/**
 * Created by user on 10/1/14.
 */
public interface MyList<E> {

    //Query

    int size();
    E get(int index);
    boolean contains(Object o);


    //Modify

    boolean add(E e);
    void add(int index, E element);
    boolean remove(E e);


    //Bulk

    boolean addAll(int index, Collection<? extends E> c);

}

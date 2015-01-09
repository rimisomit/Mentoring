package mentoring.mylinkedlist;

/**
 * Created by user on 10/1/14.
 */
public interface MyIterator<E> {

    boolean hasNex();
    E next();
    void remove();

}

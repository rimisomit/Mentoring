package mentoring.mylinkedlist;

import java.util.Collection;

public class MyLinkedList<E> implements MyList<E> {

    private int size = 0;
    private int modCount = 0;
    private MyNode<E> first;
    private MyNode<E> last;


    @Override
    public int size() {
        return size;
    }

    @Override
    public E get(int index) {
        checkElementIndex(index);
        return myNode(index).item;
    }

    @Override
    public boolean contains(Object o) {
        int index = 0;
        if (o == null) {
            for (MyNode<E> x = first; x != null; x = x.next) {
                if (x.item == null)
                    return true;
                index++;
            }
        } else {
            for (MyNode<E> x = first; x != null; x = x.next) {
                if (o.equals(x.item))
                    return true;
                index++;
            }
        }
        return false;
    }

    @Override
    public boolean add(E e) {
        linkLastItem(e);
        return true;
    }

    public void add(int index, E element) {
        checkPositionIndex(index);
        if (index == size)
            linkLastItem(element);
        else
            linkBeforeItem(element, myNode(index));
    }

    @Override
    public boolean remove(E o) {
        if (o == null) {
            for (MyNode<E> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (MyNode<E> x = first; x != null; x = x.next) {
                if (o.equals(x.item)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
            checkPositionIndex(size);

            Object[] a = c.toArray();
            int numNew = a.length;
            if (numNew == 0)
                return false;

            MyNode<E> pred, succ;
            if (index == size) {
                succ = null;
                pred = last;
            } else {
                succ = myNode(index);
                pred = succ.prev;
            }

            for (Object o : a) {
                @SuppressWarnings("unchecked") E e = (E) o;
                MyNode<E> newNode = new MyNode<>(pred, e, null);
                if (pred == null)
                    first = newNode;
                else
                    pred.next = newNode;
                pred = newNode;
            }

            if (succ == null) {
                last = pred;
            } else {
                pred.next = succ;
                succ.prev = pred;
            }

            size += numNew;
            modCount++;
            return true;
        }
    //}

    public void linkFirstItem() {

    }

    public void linkLastItem(E e) {
        final MyNode<E> l = last;
        final MyNode<E> newNode = new MyNode<>(l, e, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        size++;
        //modCount++;
    }

    void linkBeforeItem(E e, MyNode<E> succ) {
        // assert succ != null;
        final MyNode<E> pred = succ.prev;
        final MyNode<E> newNode = new MyNode<>(pred, e, succ);
        succ.prev = newNode;
        if (pred == null)
            first = newNode;
        else
            pred.next = newNode;
        size++;
        //modCount++;
    }

    private static class MyNode<E> {
        E item;
        MyNode<E> next;
        MyNode<E> prev;

        MyNode(MyNode<E> prev, E element, MyNode<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    MyNode<E> myNode(int index) {
        if (index < (size >> 1)) {
            MyNode<E> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {
            MyNode<E> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }

    //TODO difference ???
    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    //TODO difference ???
    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    //TODO difference
    private void checkElementIndex(int index) {
        if (!isElementIndex(index))
            throw new IndexOutOfBoundsException();
    }

    //TODO difference
    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index))
            throw new IndexOutOfBoundsException();


    }

    E unlink(MyNode<E> x) {
        // assert x != null;
        final E element = x.item;
        final MyNode<E> next = x.next;
        final MyNode<E> prev = x.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.item = null;
        size--;
        //modCount++;
        return element;
    }
}

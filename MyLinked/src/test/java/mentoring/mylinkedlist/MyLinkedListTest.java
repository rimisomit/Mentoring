package mentoring.mylinkedlist;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class MyLinkedListTest{

    MyLinkedList<Integer> myLinkedListInt;

    @Before
    public void setUp(){
         myLinkedListInt = new MyLinkedList<>();
    }


    private void addItems() {
        myLinkedListInt.add(null);
        myLinkedListInt.add(1);
        Integer integer = new Integer(2);
        myLinkedListInt.add(integer);
        myLinkedListInt.add(2);
        myLinkedListInt.add(4);
    }

    @Test
    public void checkInitialSizeTest(){
        Assert.assertEquals(myLinkedListInt.size(), 0);
    }

    @Test
    public void itemsAddGetTest(){
        addItems();
        //int x = myLinkedListInt.get(0);
        Assert.assertEquals(myLinkedListInt.get(0), null);
        Assert.assertEquals(myLinkedListInt.get(1), 1);
        Assert.assertEquals(myLinkedListInt.get(2), 2);
        Assert.assertEquals(myLinkedListInt.get(3), 2);
        Assert.assertEquals(myLinkedListInt.get(4), 4);
    }

    @Test
    public void checkSizeTest(){
        addItems();
        Assert.assertEquals(5, myLinkedListInt.size());
    }

    @Test
    public void containsTest() {
        addItems();
        Assert.assertTrue(myLinkedListInt.contains(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void collectionBoundsTest() {
        itemsAddGetTest();
        int x = myLinkedListInt.get(100);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void collectionBoundsNegativeTest() {
        itemsAddGetTest();
        int x = myLinkedListInt.get(-2);
    }

    @Test
    public void removeTest() {
        addItems();
        myLinkedListInt.remove(1);
        Assert.assertEquals(myLinkedListInt.get(1), 2);
        myLinkedListInt.remove(null);
        Assert.assertEquals(myLinkedListInt.get(2), 4);
    }
}

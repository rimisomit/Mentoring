package org.mentoring.link;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by user on 1/22/15.
 */
public class LinkListTest {

    LinkList<Integer> linkList;

    @Before
    public void setUp() {
        linkList = new LinkList<Integer>();
    }

    private void addIntegers() {
        linkList.add(10);
        linkList.add(11);
        linkList.add(12);
        linkList.add(13);
        linkList.add(14);
        linkList.add(15);
        linkList.add(16);
        linkList.add(17);
    }

    @Test
    public void checkInitialSizeTest(){
        Assert.assertEquals(0, linkList.size());
    }

    @Test
    public void checkSizeAfterAddTest(){
        addIntegers();
        Assert.assertEquals(8, linkList.size());
    }

    @Test
    public void getTest(){
        addIntegers();
        Assert.assertEquals(10, linkList.get(0).intValue());
        Assert.assertEquals(11, linkList.get(1).intValue());
        Assert.assertEquals(12, linkList.get(2).intValue());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void boundsTest(){
        addIntegers();
        Assert.assertEquals(100, linkList.get(100).intValue());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void negativeIndexTest(){
        addIntegers();
        Assert.assertEquals(-1, linkList.get(100).intValue());
    }

    @Test
    public void containsTest(){
        addIntegers();
        Assert.assertTrue(linkList.contains(10));
        Assert.assertTrue(linkList.contains(12));
        Assert.assertFalse(linkList.contains(100));
    }

    @Test
    public void containsNullTest(){
        addIntegers();
        Assert.assertFalse(linkList.contains(null));
    }

    @Test
    public void removeTest(){
        addIntegers();
        Assert.assertTrue(linkList.remove(12));
        Assert.assertFalse(linkList.contains(12));
    }

    @Test
    public void removeLastTest(){
        addIntegers();
        Assert.assertTrue(linkList.remove(17));
        Assert.assertFalse(linkList.contains(17));
    }

    @Test
    public void removeFirstTest(){
        addIntegers();
        Assert.assertTrue(linkList.remove(10));
        Assert.assertFalse(linkList.contains(10));
    }

    @Test
    public void removeNotExistentTest(){
        addIntegers();
        Assert.assertFalse(linkList.remove(9));
    }

}

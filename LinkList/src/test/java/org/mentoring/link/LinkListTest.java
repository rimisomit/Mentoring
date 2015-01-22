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
    }

    @Test
    public void checkInitialSizeTest(){
        Assert.assertEquals(0, linkList.size());
    }

    @Test
    public void checkSizeAfterAddTest(){
        addIntegers();
        Assert.assertEquals(4, linkList.size());
    }

    @Test
    public void getTest(){
        addIntegers();
        Assert.assertEquals(10, linkList.get(0).intValue());
        Assert.assertEquals(11, linkList.get(1).intValue());
        Assert.assertEquals(12, linkList.get(2).intValue());
        Assert.assertEquals(13, linkList.get(3).intValue());
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
        Assert.assertFalse(linkList.contains(100));
    }

    @Test
    public void containsNullTest(){
        addIntegers();
        Assert.assertFalse(linkList.contains(null));
    }

}

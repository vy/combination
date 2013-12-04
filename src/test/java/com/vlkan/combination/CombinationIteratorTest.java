package com.vlkan.combination;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class CombinationIteratorTest {

    @Test(expected=IllegalArgumentException.class)
    public void testInvalidN() { new CombinationIterator(-1, 0); }

    @Test(expected=IllegalArgumentException.class)
    public void testInvalidR() { new CombinationIterator(0, -1); }

    @Test
    public void testBasics() {
        CombinationIterator ci;

        ci = new CombinationIterator(0, 0);
        Assert.assertTrue("0C0 should have a next", ci.hasNext());
        Assert.assertArrayEquals("0C0 should have a next of empty set",
                ci.next(), new int[0]);
        Assert.assertFalse("0C0 should not have a second next", ci.hasNext());

        ci = new CombinationIterator(0, 1);
        Assert.assertFalse("0C1 should have no next items", ci.hasNext());

        ci = new CombinationIterator(1, 1);
        Assert.assertTrue("1C1 should first have a next", ci.hasNext());
        Assert.assertArrayEquals("1C1 should first return [0]",
                ci.next(), new int[] {0});
        Assert.assertFalse("1C1 should secondly has no items", ci.hasNext());
        Assert.assertNull("1C1 should secondly return null", ci.next());
    }

    private void testCombinations(int n, int r, int[][] combinations) {
        CombinationIterator ci = new CombinationIterator(n, r);
        for (int k = 0; k < combinations.length; k++) {
            Assert.assertTrue(
                    String.format("%dC%d should have next for k=%d", n, r, k),
                    ci.hasNext());
            Assert.assertArrayEquals(
                    String.format("combinations for k=%d in %dC%d should match",
                            k, n, r),
                    combinations[k], ci.next());
        }
    }

    @Test
    public void test5C2() { testCombinations(5, 2, SampleCombinations.for5C2); }

    @Test
    public void test5C3() { testCombinations(5, 3, SampleCombinations.for5C3); }

    @Test(expected=IllegalArgumentException.class)
    public void testInvalidLoIndex() {
        new CombinationIterator(0, 0, -1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testInvalidHiIndex() {
        new CombinationIterator(0, 0, 1);
    }

    @Test
    public void testPartialCombinationBasics() {
        CombinationIterator ci;

        ci = new CombinationIterator(0, 0, 0);
        Assert.assertTrue("empty partial combination should have a next", ci.hasNext());
        Assert.assertArrayEquals("empty partial combination should have an empty next",
                ci.next(), new int[0]);

        ci = new CombinationIterator(5, 2, 9);
        Assert.assertTrue("5C2 should have a 9th element", ci.hasNext());
        Assert.assertArrayEquals("5C2 should return [3, 4] for 9th element",
                ci.next(), new int[] {3, 4});
    }

}

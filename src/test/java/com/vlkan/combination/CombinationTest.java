package com.vlkan.combination;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class CombinationTest {

    @Test(expected=IndexOutOfBoundsException.class)
    public void testInvalidN() { new Combination(-1, 0); }

    @Test(expected=IndexOutOfBoundsException.class)
    public void testInvalidR() { new Combination(0, -1); }

    @Test(expected=IndexOutOfBoundsException.class)
    public void testInvalidBufferSize() { Combination.get(5, 3, 1, new int[1]); }

    @Test(expected=IndexOutOfBoundsException.class)
    public void testInvalidCombination() { Combination.get(0, 0, 1, new int[0]); }

    @Test
    public void testBasics() {
        Combination c;

        c = new Combination(0, 0);
        Assert.assertEquals("size(0C0) should return 1", c.size(), 1);
        int[] b0 = new int[0];
        Combination.get(0, 0, 0, b0);
        Assert.assertArrayEquals("0C0 should return []", b0, new int[0]);

        c = new Combination(0, 1);
        Assert.assertEquals("size(0C1) should return 0", c.size(), 0);

        c = new Combination(2, 2);
        Assert.assertEquals("size(2C2) should return 1", c.size(), 1);
        int[] b2 = new int[2];
        Combination.get(2, 2, 0, b2);
        Assert.assertArrayEquals("2C2 should return [0, 1]", b2, new int[] {0, 1});
    }

    private void testCombinations(int n, int r, int[][] combinations) {
        Combination c = new Combination(n, r);
        Assert.assertEquals(
                String.format("#combinations should match for %dC%d", n, r),
                c.size(), combinations.length);

        int[] buffer = new int[r];
        for (int k = 0; k < combinations.length; k++) {
            c.get(k, buffer);
            Assert.assertArrayEquals(
                    String.format("combinations for k=%d in %dC%d should match",
                            k, n, r),
                    combinations[k], buffer);
        }
    }

    @Test
    public void test5C2() { testCombinations(5, 2, SampleCombinations.for5C2); }

    @Test
    public void test5C3() { testCombinations(5, 3, SampleCombinations.for5C3); }

}

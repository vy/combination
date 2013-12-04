package com.vlkan.combination;

import com.vlkan.combination.Combination;
import net.jcip.annotations.NotThreadSafe;

import java.util.Iterator;

/**
 * Generate combinations of size r of n numbers in lexicographic order.
 * Algorithm is adopted from Applied Combinatorics by Alan Tucker.
 *
 * Code is gently stolen from <a
 * href="http://kosbie.net/cmu/spring-10/15-190-mini/handouts/Combinations.java"
 * >David Kosbie's lecture notes</a>, which appears to be originally stolen from
 * <a href="http://www.koders.com/">koders.com</a>.
 */
@NotThreadSafe
public class CombinationIterator implements Iterator<int[]> {

    protected final long l;
    protected final int n, r;
    protected final int[] index;
    protected long k;

    public CombinationIterator(final int n, final int r, final int k) {
        this.index = new int[r];
        Combination.get(n, r, k, index);
        this.l = Combination.choose(n, r);
        this.n = n;
        this.r = r;
        this.k = k;
    }

    public CombinationIterator(final int n, final int r) {
        this.l = Combination.choose(n, r);
        this.n = n;
        this.r = r;
        this.k = 0;
        this.index = new int[r];
        for (int i = 0; i < r; i++) index[i] = i;
    }

    public int getN() { return n; }

    public int getR() { return r; }

    /**
     * The total number of available combinations.
     */
    public long getL() { return l; }

    /**
     * Lexicographic position of the current combination.
     */
    public long getK() { return k; }

    @Override
    public boolean hasNext() { return (k < l); }

    /**
     * Finds the index which can be bumped up.
     */
    private int rightmostIndexBelowMax() {
        for (int i = r-1; i >= 0; i--)
            if (index[i] < n - r + i) return i;
        return -1;
    }

    /**
     * Move the index forward a notch.
     *
     * The algorithm finds the rightmost index element that can be incremented,
     * increments it, and then changes the elements to the right to each be 1
     * plus the element on their left.
     *
     * For example, if an index of 5C3 at a time is at [0, 3, 4], only the 0 can
     * be incremented without running out of room. The next index is [1, 1+1, 1+2]
     * or [1, 2, 3]. This will be followed by [1, 2, 4], [1, 3, 4], and [2, 3, 4].
     */
    private void moveIndex() {
        final int i = rightmostIndexBelowMax();
        if (i >= 0) {
            index[i] = index[i] + 1;
            for (int j = i+1; j < r; j++)
                index[j] = index[j-1] + 1;
        }
    }

    @Override
    public int[] next() {
        if (!hasNext()) return null;
        if (k++ > 0) moveIndex();
        return index;
    }

    @Override
    public void remove() { throw new UnsupportedOperationException(); }

    @Override
    public String toString() {
        return String.format("com.vlkan.combination.CombinationIterator(n: %d, r: %d, k: %d)", n, r, k);
    }

}

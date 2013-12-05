package com.vlkan.combination;

import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;

@ThreadSafe
public class Combination implements Iterable<int[]> {

    protected final int n;
    protected final int r;
    protected final long size;

    public Combination(final int n, final int r) {
        Validators.validate(n, r);
        this.n = n;
        this.r = r;
        this.size = choose(n, r);
    }

    public int getN() { return n; }

    public int getR() { return r; }

    @Override
    public String toString() { return String.format("Combination(%d, %d)", n, r); }

    /**
     * Finds nCr.
     */
    public static long choose(final int n, final int r) {
        Validators.validate(n, r);
        if (n < r) return 0;
        if (n == r) return 1;
        final int s = Math.min(r, (n - r));
        long t = n;
        for (long a = n - 1, b = 2; b <= s; a--, b++)
            t = (t * a) / b;
        return t;
    }

    /**
     * Total number of combinations.
     */
    public long size() { return size; }

    @Override
    public Iterator<int[]> iterator() {
        return new CombinationIterator(n, r);
    }

    /**
     * Returns the largest v such that v < a and vCb <= k.
     */
    private static int largestV(final int a, final int b, final long x) {
        int v = a - 1;
        while (choose(v, b) > x) --v;
        return v;
    }

    /**
     * Finds the combinadic of k for nCr.
     *
     * Combinadic of k corresponds to the array of [c_1, c_2, ..., c_r] such
     * that k = choose(c_1, r) + choose(c_2, r-1) + ... + choose(c_r, 1).
     */
    private static void combinadic(final int n, final int r, final long k, final int[] c) {
        Validators.validate(n, r, k, c);
        long x = k;
        int a = n;
        int b = r;
        for (int i = 0; i < r; ++i) {
            c[i] = largestV(a, b, x);
            x -= choose(c[i], b);
            a = c[i];
            b -= 1;
        }
    }

    /**
     * Finds the k'th combination in nCr.
     */
    public static void get(final int n, final int r, final long k, final int[] c) {
        Validators.validate(n, r, k, c);
        final long nCr = choose(n, r);
        if (nCr == 0) return;
        final long d = nCr - k - 1;
        combinadic(n, r, d, c);
        for (int i = 0; i < r; i++)
            c[i] = n - c[i] - 1;
    }

    /**
     * Finds the k'th combination.
     */
    public void get(final long k, final int[] c) {
        get(n, r, k, c);
    }

}

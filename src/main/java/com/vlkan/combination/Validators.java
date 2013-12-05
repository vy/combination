package com.vlkan.combination;

class Validators {

    static void validate(final int n, final int r) {
        if (n < 0 || r < 0)
            throw new IndexOutOfBoundsException("Expecting positive integers.");
    }

    static void validate(final int n, final int r, final long k, final int[] c) {
        validate(n, r);
        if (c.length != r)
            throw new IndexOutOfBoundsException(String.format(
                    "Result vector must be of size %d. Found %d.", r, c.length));
        final long nCr = Combination.choose(n, r);
        if (nCr > 0 ? (k < 0 || nCr <= k) : k != 0)
            throw new IndexOutOfBoundsException(String.format(
                    "Expecting 0 <= k < choose(n, r) = %d. Found %d.", nCr, k));
    }

    static void validate(final int n, final int r, final long k, final long l) {
        validate(n, r);
        final long nCr = Combination.choose(n, r);
        if (nCr > 0 ? (k < 0 || nCr <= k) : k != 0)
            throw new IndexOutOfBoundsException(String.format(
                    "Expecting 0 <= k < choose(n, r) = %d. Found %d.", nCr, k));
        if (nCr > 0 ? (l < 1 && nCr < l) : l != 0)
            throw new IndexOutOfBoundsException(String.format(
                    "Expecting 0 < l <= choose(n, r) = %d. Found %d.", nCr, l));
    }

}

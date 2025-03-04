package org.example;

import java.util.BitSet;
import java.util.stream.IntStream;

// This is a slight adaptation of someone else's code.
// https://neilmadden.blog/2014/01/30/java-8-sieve-of-eratosthenes/#more-1935


// List every prime number that is a positive java integer using java streams
// Using a sieve of Eratosthenes. As the BitSet for the sieve is indexed using a
// Java integer it has to be offset by minus 1 to be able to hold a bit representing
// Integer.MAX_VALUE, (we start at 2 so the 0 and 1 indices are unused in the sieve).

// Using peek to fill the sieve might not be kosher.

public class PrimeStream {
    public static void main(String[] args) {
        final int limit =  Integer.MAX_VALUE;
        // Expensive but we do it only once or could be a const.
        final int rootLimit = (int) Math.sqrt(limit);
        final BitSet sieve = new BitSet(limit);
        final IntStream primes = IntStream.rangeClosed(2, limit)
                // filter out composite numbers,
                .filter(x -> !sieve.get(x - 1))
                .peek(x -> {
                    if (x < rootLimit) {
                        // The test for n > 0 is to end stream on numeric overflow
                        final IntStream multiples = IntStream.iterate(x, n -> n > 0, n -> n + x);
                        // Mark composite numbers in the sieve BitSet
                        multiples.forEach(multiple -> sieve.set(multiple - 1));
                    }
                });
        primes.forEach(System.out::println);
    }
}
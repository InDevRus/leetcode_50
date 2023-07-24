package org.example;


import java.util.AbstractList;
import java.util.Collections;
import java.util.RandomAccess;
import java.util.function.IntFunction;

@SuppressWarnings("unused")
class MappedRange<V> extends AbstractList<V> implements RandomAccess {
    private final int start;
    private final int end;
    private final IntFunction<V> mapper;
    private final int size;

    private MappedRange(int start, int end, IntFunction<V> mapper) {
        if (start > end) {
            throw new IllegalArgumentException();
        }
        this.start = start;
        this.end = end;
        this.mapper = mapper;
        this.size = end - start + 1;
    }

    static MappedRange<Integer> of(int end) {
        return new MappedRange<>(0, end, index -> index);
    }

    static MappedRange<Integer> of(int start, int end) {
        return new MappedRange<>(start, end, index -> index);
    }

    static <V> MappedRange<V> of(int end, IntFunction<V> mapper) {
        return new MappedRange<>(0, end, mapper);
    }

    static <V> MappedRange<V> of(int start, int end, IntFunction<V> mapper) {
        return new MappedRange<>(start, end, mapper);
    }

    @Override
    public V get(int index) {
        return mapper.apply(index - start);
    }

    @Override
    public int size() {
        return this.size;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}

@SuppressWarnings("unused")
class Solution {
    public int mySqrt(int x) {
        var range = MappedRange.of(x, index -> (long) index * index);
        var squareRoot = Collections.binarySearch(range, (long) x);
        squareRoot = squareRoot < 0 ? -(squareRoot + 1) - 1 : squareRoot;
        return squareRoot;
    }
}
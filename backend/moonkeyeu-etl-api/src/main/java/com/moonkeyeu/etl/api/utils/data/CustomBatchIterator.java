package com.moonkeyeu.etl.api.utils.data;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CustomBatchIterator<T> implements Iterator<List<T>> {
    private final int batchSize;
    private final Iterator<T> iterator;
    private List<T> currentBatch;

    public CustomBatchIterator(Iterator<T> sourceIterator, int batchSize) {
        this.batchSize = batchSize;
        this.iterator = sourceIterator;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public List<T> next() {
        prepareNextBatch();
        return currentBatch;
    }

    private void prepareNextBatch() {
        currentBatch = new ArrayList<>(batchSize);
        while (iterator.hasNext() && currentBatch.size() < batchSize) {
            currentBatch.add(iterator.next());
        }
    }

    /** Converts a Stream<T> into a Stream<List<T>> that emits batches **/
    public static <T> Stream<List<T>> batchStreamOf(Stream<T> stream, int batchSize) {
        return stream(new CustomBatchIterator<>(stream.iterator(), batchSize));
    }

    /** Converts an Iterator<T> into a Stream<T> **/
    private static <T> Stream<T> stream(Iterator<T> iterator) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false);
    }

}

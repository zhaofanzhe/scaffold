package io.github.zhaofanzhe.scaffold.toolkit;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class BatchRunner {

    public static <T> void run(Collection<T> collection, int threadCount, Consumer<T> consumer) throws InterruptedException {
        if (threadCount <= 0){
            throw new IllegalArgumentException("threadCount 最小值为 1");
        }
        if (threadCount == 1) {
            collection.forEach(consumer);
            return;
        }
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        collection.forEach(it -> executor.submit(() -> consumer.accept(it)));
        executor.shutdown();
        //noinspection ResultOfMethodCallIgnored
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
    }

}

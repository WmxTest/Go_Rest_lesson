package rest;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.api.parallel.ResourceLock;

@Disabled
@Execution(ExecutionMode.CONCURRENT) //junit.jupiter.execution.parallel.mode.default=concurrent
@ResourceLock("ConcurrentPerMethodTest")
public class ConcurrentPerMethodTest {

    private Long currentTime;

    @SneakyThrows
    @Test
    void name1() {
        System.setProperty("test", "1");
        currentTime = System.currentTimeMillis();
        System.out.println(currentTime);
        System.out.println(Thread.currentThread().getName());
    }

    @SneakyThrows
    @Test
    void name2() {
        System.setProperty("test", "2");
        System.out.println(currentTime);
        System.out.println(Thread.currentThread().getName());
    }

    @SneakyThrows
    @Test
    void name3() {
        System.setProperty("test", "3");
        System.out.println(currentTime);
        System.out.println(Thread.currentThread().getName());
    }
}

/*
@Execution(ExecutionMode.CONCURRENT)

ForkJoinPool-1-worker-3
ForkJoinPool-1-worker-7
ForkJoinPool-1-worker-5

@Execution(ExecutionMode.SAME_THREAD)

ForkJoinPool-1-worker-3
ForkJoinPool-1-worker-3
ForkJoinPool-1-worker-3
*/
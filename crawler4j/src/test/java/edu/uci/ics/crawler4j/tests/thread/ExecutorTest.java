package edu.uci.ics.crawler4j.tests.thread;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorTest {

    private ExecutorService executorService = Executors.newCachedThreadPool();

    @Test
    public void test() {
        executorService.submit(() -> {
            System.out.println(1);
        });

    }
}

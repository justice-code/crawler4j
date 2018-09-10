package edu.uci.ics.crawler4j.task;

import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class TaskConsumer {

    private BlockingQueue<ControllerTask> queue = new LinkedBlockingQueue<>();
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public TaskConsumer() {
        executorService.submit(() -> {
            while (true) {
                ControllerTask controllerTask = queue.take();
                controllerTask.getController().start(controllerTask.getClazz(), controllerTask.getNumberOfCrawlers());
            }
        });
    }

    public boolean offer(ControllerTask controllerTask) {
        return queue.offer(controllerTask);
    }

}
